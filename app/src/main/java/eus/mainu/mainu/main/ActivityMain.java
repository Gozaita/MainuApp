package eus.mainu.mainu.main;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import eus.mainu.mainu.R;
import eus.mainu.mainu.conexion.HttpPostRequest;
import eus.mainu.mainu.menu.Menu;
import eus.mainu.mainu.VariablesGlobales;
import eus.mainu.mainu.bocadillos.FragmentBocadillos;
import eus.mainu.mainu.data.Bocadillo;
import eus.mainu.mainu.data.Otro;
import eus.mainu.mainu.data.Ingrediente;
import eus.mainu.mainu.info.ActivityInfo;
import eus.mainu.mainu.menu.FragmentMenu;
import eus.mainu.mainu.otros.FragmentOtros;

public class ActivityMain extends AppCompatActivity implements
		NavigationView.OnNavigationItemSelectedListener,
		GoogleApiClient.OnConnectionFailedListener {

	private final String TAG = "Main";
	private final int SIGN_IN_CODE = 777; // Es 777 porque yo he querido, podria ser cualquiera

	private boolean searchIsFocused = false;

	// Layout
	private DrawerLayout drawer;
	private ViewPager viewPager;
	private TabLayout tabLayout;
	private Toolbar toolbar;
	private ImageView fotoUsuario;
	private ImageButton clearFilter;
	private TextView nombre, email;
	private SearchView searchView;

	// Datos de usuario
	private GoogleApiClient googleApiClient;

	// Fragmentos
	private FragmentMenu fMenu = new FragmentMenu();
	private FragmentBocadillos fBocadillos = new FragmentBocadillos();
	private FragmentOtros fOtros = new FragmentOtros();

	private ArrayList<Bocadillo> bocadillos;
	private ArrayList<Bocadillo> bocadillosFiltrados;
	private ArrayList<Otro> otros;
	private ArrayList<Ingrediente> ingredientes;
	private ArrayList<String> ingredientesSeleccionados = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Log.d(TAG, "onCreate: Inicia Main Activity");

		// Seccion para referenciar a todos los elementos layout
		drawer = findViewById(R.id.drawer_layout);
		viewPager = findViewById(R.id.contenedor);
		tabLayout = findViewById(R.id.tabs);
		toolbar = findViewById(R.id.toolbar);
		searchView = findViewById(R.id.searchView);
		clearFilter = findViewById(R.id.clearFilter);
		clearFilter.setVisibility(View.GONE);
		setSupportActionBar(toolbar);

		// Hay que hacer esto para referenciar los elementos del nav header
		NavigationView navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
		View header = navigationView.getHeaderView(0);
		fotoUsuario = header.findViewById(R.id.imagenUsuario);
		nombre = header.findViewById(R.id.nombre);
		email = header.findViewById(R.id.correo);

		setToolbar();
		setCuenta();
		setDrawer();

		Menu menu = (Menu) getIntent().getSerializableExtra("menu");
		bocadillos = (ArrayList<Bocadillo>) getIntent().getSerializableExtra("bocadillos");
		ingredientes = (ArrayList<Ingrediente>) getIntent().getSerializableExtra("ingredientes");
		bocadillosFiltrados = new ArrayList<>();
		otros = (ArrayList<Otro>) getIntent().getSerializableExtra("otros");

		Bundle bundle = new Bundle();
		bundle.putSerializable("menu", menu);
		fMenu.setArguments(bundle);

		bundle = new Bundle();
		bundle.putSerializable("bocadillos", bocadillos);
		bundle.putSerializable("ingredientes", ingredientes);
		fBocadillos.setArguments(bundle);

		bundle = new Bundle();
		bundle.putSerializable("otros", otros);
		fOtros.setArguments(bundle);

		setupViewPager();

		searchView.setIconified(true);

		final EditText searchEditText = searchView.
				findViewById(android.support.v7.appcompat.R.id.search_src_text);
		searchEditText.setTextColor(getResources().getColor(R.color.white));
		searchEditText.setHintTextColor(getResources().getColor(R.color.white));

		ImageView closeButton = searchView.findViewById(R.id.search_close_btn);
		closeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				fBocadillos.setBocadillos(bocadillos);
				searchView.setQuery("", false);
				searchView.setIconified(true);
				searchView.onActionViewCollapsed();
				tabLayout.setVisibility(View.VISIBLE);
				findViewById(R.id.tabLayout2).setVisibility(View.VISIBLE);
				toolbar.setTitle("Bocadillos");

				// Reseteamos la lista de ingredientes filtrados
				fBocadillos.setBocadillos(bocadillos);
				fBocadillos.uncheckIngredientes();
				ingredientesSeleccionados.clear();
			}
		});

		searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				searchIsFocused = hasFocus;
				if (hasFocus) {
					tabLayout.setVisibility(View.GONE);
					findViewById(R.id.tabLayout2).setVisibility(View.GONE);
					toolbar.setTitle("");
					findViewById(R.id.recyclerView_ingredientes).setVisibility(View.GONE);
				} else {
					searchView.setIconified(true);
					tabLayout.setVisibility(View.VISIBLE);
					findViewById(R.id.tabLayout2).setVisibility(View.VISIBLE);
					toolbar.setTitle("Bocadillos");
					findViewById(R.id.recyclerView_ingredientes).setVisibility(View.VISIBLE);
				}
			}
		});

		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				bocadillosFiltrados.clear();
				for (Bocadillo b : bocadillos) {
					// Importante pasar a minusculas
					if (b.getNombre().toLowerCase().contains(newText.toLowerCase())) {
						bocadillosFiltrados.add(b);
					} else {
						for (Ingrediente i : b.getIngredientes()) {
							if (i.getNombre().toLowerCase().contains(newText.toLowerCase()))
								bocadillosFiltrados.add(b);
						}
					}
				}
				fBocadillos.setBocadillos(bocadillosFiltrados);
				return false;
			}
		});

		clearFilter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				tabLayout.setVisibility(View.VISIBLE);
				findViewById(R.id.tabLayout2).setVisibility(View.VISIBLE);
				toolbar.setTitle("Bocadillos");

				clearFilter.setVisibility(View.GONE);
				searchView.setVisibility(View.VISIBLE);

				// Reseteamos la lista de ingredientes filtrados
				fBocadillos.setBocadillos(bocadillos);
				fBocadillos.uncheckIngredientes();
				ingredientesSeleccionados.clear();
			}
		});
	}

	@Override
	public void onBackPressed() {
		if (searchIsFocused) {
			searchView.setIconified(true);
			tabLayout.setVisibility(View.VISIBLE);
			toolbar.setTitle("Bocadillos");
		} else {
			// Si no, los usuarios no pueden salir de la app
			super.onBackPressed();
		}
	}

	public void filterByIngredient(String ingrediente) {
		/**
		 * 1. Al hacer clic en un ingrediente se pueden dar dos casos:
		 * 		A) Ya estaba seleccionado --> Se quita de la lista
		 * 		B) No estaba seleccionado --> Se añade a la lista
		 */
		if (ingredientesSeleccionados.contains(ingrediente))
			ingredientesSeleccionados.remove(ingrediente);
		else
			ingredientesSeleccionados.add(ingrediente);
		/**
		 * 2. Para cada bocadillo de la lista: se comprueba si todos los ingredientes de la
		 * lista de ingredientes seleccionados coinciden con los del bocadillo.
		 * 3. Si los ingredientes que coinciden es igual al número de la lista --> Se añade
		 * 4. Se actualiza la lista de bocadillos visibles
		 */
		if (ingredientesSeleccionados.size() > 0) {
			/** si hay ingrediente filtrado, mostramos el boton de cancelar y ocultamos searchView */
			clearFilter.setVisibility(View.VISIBLE);
			searchView.setVisibility(View.GONE);

			bocadillosFiltrados.clear();
			for (Bocadillo b : bocadillos) {
				int nPass = 0;
				for (String ing : ingredientesSeleccionados) {
					boolean pass = false;
					for (Ingrediente i : b.getIngredientes()) {
						if (i.getNombre().toLowerCase().equalsIgnoreCase(ing)) {
							pass = true;
						}
					}
					// Si en todos los ingredientes de un bocadiilo no está, fuera
					if (pass)
						nPass++;
				}
				if (nPass == ingredientesSeleccionados.size())
					bocadillosFiltrados.add(b);
			}
			fBocadillos.setBocadillos(bocadillosFiltrados);
		} else {
			/** si hay ingrediente filtrado, mostramos el boton de cancelar y ocultamos searchView */
			clearFilter.setVisibility(View.GONE);
			searchView.setVisibility(View.VISIBLE);

			fBocadillos.setBocadillos(bocadillos);
			ingredientesSeleccionados.clear();
		}
	}

	private void setToolbar() {
		/**
		 * Personalización de toolbar. Compatible con onPageScroll.
		 */
		getSupportActionBar().setTitle(R.string.title_menu);
	}

	private void setDrawer() {
		Log.d(TAG, "setDrawer");

		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar,R.string.navigation_drawer_open,
				R.string.navigation_drawer_close);
		toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
		drawer.addDrawerListener(toggle);
		toggle.syncState();
	}

	private void setupViewPager() {
		/**
		 * Responsable de la creación de tres fragmentos:
		 * 	-	Menú
		 * 	-	Bocadillos
		 * 	-	Otros
		 */
		Log.d(TAG, "setupViewPager");

		// Creamos los fragmentos
		Adaptador_Fragmentos adapter = new Adaptador_Fragmentos(getSupportFragmentManager());
		adapter.addFragment(fMenu); //index 0
		adapter.addFragment(fBocadillos); //index 1
		adapter.addFragment(fOtros); //index 2

		// Creamos las pestañas
		viewPager.setAdapter(adapter);
		tabLayout.setupWithViewPager(viewPager);

		// Añadimos 3 iconos
		tabLayout.getTabAt(0).setIcon(R.drawable.ic_plato);
		tabLayout.getTabAt(1).setIcon(R.drawable.ic_bocadillos);
		tabLayout.getTabAt(2).setIcon(R.drawable.ic_otros);

		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset,
									   int positionOffsetPixels) {
				// Cerrar teclado si está abierto
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.
						INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(viewPager.getWindowToken(), 0);

				switch (position) {
					case 0:
						getSupportActionBar().setTitle(R.string.title_menu);
						searchView.setVisibility(View.INVISIBLE);
						searchView.setIconified(true);

						// !Bocadillos --> Resetear filtro de ingredientes
						if (!ingredientesSeleccionados.isEmpty()) {
							fBocadillos.setBocadillos(bocadillos);
							fBocadillos.uncheckIngredientes();
							ingredientesSeleccionados.clear();
						}
						break;
					case 1:
						getSupportActionBar().setTitle(R.string.title_bocadillos);
						searchView.setVisibility(View.VISIBLE);
						searchView.setIconified(true);
						break;
					case 2:
						getSupportActionBar().setTitle(R.string.title_otros);
						searchView.setVisibility(View.INVISIBLE);
						searchView.setIconified(true);

						// !Bocadillos --> Resetear filtro de ingredientes
						if (!ingredientesSeleccionados.isEmpty()) {
							fBocadillos.setBocadillos(bocadillos);
							fBocadillos.uncheckIngredientes();
							ingredientesSeleccionados.clear();
						}
						break;
				}
			}

			@Override
			public void onPageSelected(int position) {
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		/**
		 * Definición de las acciones del NavigationDrawer.
		 */

		int id = item.getItemId();

		switch (id) {
			case R.id.home:
				homeClick();
				break;
			case R.id.informacion:
				Intent intent = new Intent(this, ActivityInfo.class);
				this.startActivity(intent);
				break;
			case R.id.comparte:
				compartir();
				break;
			case R.id.error:
				reporte();
				break;
			case R.id.iniciarSesion:
				iniciarSesion();
				break;
			case R.id.cerrarSesion:
				cerrarSesion();
				break;
		}

		// Selección --> Cierre
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

	private void homeClick() {
		drawer.closeDrawer(GravityCompat.START);

	}

	private void compartir() {
		/**
		 * Compartir a través de otras aplicaciones del sistema
		 */
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT,
				"¡Yo ya uso MainU! La cafe, en mi bolsillo con mainu.eus");
		startActivity(Intent.createChooser(intent, "Compartir con"));
	}

	public void reporte() {
		/**
		 * Reporte de errores y sugerencias
		 */
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		LayoutInflater inflater = this.getLayoutInflater();
		@SuppressLint("InflateParams") final View dialogView =
				inflater.inflate(R.layout.alert_box, null);
		dialogBuilder.setView(dialogView);

		final EditText edt = dialogView.findViewById(R.id.edit1);

		dialogBuilder.setTitle(R.string.header_report);
		dialogBuilder.setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				try {
					JSONObject postData = new JSONObject();
					postData.put("report", edt.getText().toString());
					new HttpPostRequest().execute("https://api.mainu.eus/report",
							postData.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}

				Toast.makeText(ActivityMain.this, R.string.greetings,
						Toast.LENGTH_SHORT).show();
			}
		});
		dialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				//pass
			}
		});
		AlertDialog b = dialogBuilder.create();
		b.show();
	}

	private void iniciarSesion() {
		/**
		 * Inicio de sesión. SIGN_IN_CODE es un código único que se devuelve al iniciar sesión.
		 */
		Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
		startActivityForResult(signInIntent, SIGN_IN_CODE);
	}

	private void cerrarSesion() {
		/**
		 * Cierre de sesión. Devuelve un toast indicando cómo ha ido.
		 */
		Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
				new ResultCallback<Status>() {
			@Override
			public void onResult(@NonNull Status status) {
				if (status.isSuccess()) {
					Toast.makeText(ActivityMain.this, R.string.bye, Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(ActivityMain.this, R.string.fail, Toast.LENGTH_SHORT).show();
				}
			}
		});
		resetNavigationDrawer();
	}

	private void setCuenta() {
		/**
		 * Al pedir la cuetna de usuario se dbee pasar el client_id, que debe coincidir con el
		 * almacenado en la API.
		 */
		Log.d(TAG, "setCuenta");

		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
				GoogleSignInOptions.DEFAULT_SIGN_IN)
				.requestIdToken(getString(R.string.server_client_id))
				.requestEmail()
				.build();
		/**
		 * Se debe gestionar el ciclo de vida de la autenticación con el de la actividad. Se indica
		 * quién escucha en caso de que algo salga mal (onConnectionFailedListener).
		 */
		googleApiClient = new GoogleApiClient.Builder(this)
				.enableAutoManage(this, this)
				.addApi(Auth.GOOGLE_SIGN_IN_API, gso)
				.build();
	}

	private void resetNavigationDrawer() {
		Log.d(TAG, "resetNavigationDrawer");

		nombre.setText(R.string.default_username);
		email.setText(R.string.default_mail);
		VariablesGlobales.idToken = "666";

		Picasso.with(this).load(R.drawable.logo_blanco).fit().into(fotoUsuario, new Callback() {
			@Override
			public void onSuccess() {
				Bitmap imageBitmap = ((BitmapDrawable) fotoUsuario.getDrawable()).getBitmap();
				RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(
						getResources(), imageBitmap);
				imageDrawable.setCircular(true);
				imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(),
						imageBitmap.getHeight()) / 2.0f);
				fotoUsuario.setImageDrawable(imageDrawable);
			}

			@Override
			public void onError() {

			}
		});
	}

	@Override
	protected void onStart() {
		/**
		 * Cuando se inicia la actividad, se hace un silent sign-in para comprobar si el usuario
		 * ya ha iniciado sesión.
		 */
		super.onStart();

		Log.d(TAG, "onStart");

		OptionalPendingResult<GoogleSignInResult> opr =
				Auth.GoogleSignInApi.silentSignIn(googleApiClient);
		if (opr.isDone()) {
			GoogleSignInResult result = opr.get();
			solveSignIn(result, false);
		} else {
			opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
				@Override
				public void onResult(@NonNull GoogleSignInResult result) {
					solveSignIn(result, false);
				}
			});
		}
	}

	@Override
	public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
		/**
		 * Se ejecuta en caso de fallo de conexión
		 */
		Log.d(TAG, "onConnectionFailed");

		Toast.makeText(this, R.string.fail, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Log.d(TAG, "onActivityResult");

		if (requestCode == SIGN_IN_CODE) {
			GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
			solveSignIn(result, true);
		}
	}

	private void solveSignIn(GoogleSignInResult result, Boolean notify) {
		/**
		 * Comprueba el resultado devuelto por el sign-in y, en caso de que haya ido
		 * correctamente, muestra la información de usuario en el NavigationDrawer.
		 * Si el parámetro 'notify' está activado, se notifica un inicio de sesión correcto.
		 */
		Log.d(TAG, "resultadoSignIn: " + result.isSuccess());

		if (result.isSuccess()) {
			if (notify) {
				Toast.makeText(this, R.string.login_success, Toast.LENGTH_SHORT).show();
			}
			GoogleSignInAccount cuenta = result.getSignInAccount();
			nombre.setText(cuenta.getDisplayName());
			email.setText(cuenta.getEmail());
			VariablesGlobales.idToken = cuenta.getIdToken();

			Picasso.with(this).load(cuenta.getPhotoUrl()).fit().into(fotoUsuario, new Callback() {
				@Override
				public void onSuccess() {
					Log.d(TAG, "onSuccess");
					Bitmap imageBitmap = ((BitmapDrawable) fotoUsuario.getDrawable()).getBitmap();
					RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(getResources(), imageBitmap);
					imageDrawable.setCircular(true);
					imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
					fotoUsuario.setImageDrawable(imageDrawable);
				}

				@Override
				public void onError() {
					Log.d(TAG, "onError");
					// TODO: Realizar acciones en caso de que falle el silent sign-in
				}
			});
		}
	}
}
