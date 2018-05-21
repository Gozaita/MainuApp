package eus.mainu.mainu.elemento;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import eus.mainu.mainu.R;
import eus.mainu.mainu.imagenes.AdaptadorImagenes;
import eus.mainu.mainu.conexion.HttpGetRequest;
import eus.mainu.mainu.conexion.HttpPostRequest;
import eus.mainu.mainu.permisos.Permisos;
import eus.mainu.mainu.VariablesGlobales;
import eus.mainu.mainu.data.Bocadillo;
import eus.mainu.mainu.data.Otro;
import eus.mainu.mainu.data.Plato;
import eus.mainu.mainu.data.Valoracion;

public class ActivityElemento extends AppCompatActivity {

	private static final String TAG = "Elemento";
	private static final int CAMERA_REQUEST_CODE = 2;
	private static final int COMPRUEBA_PERMISOS = 1;

	private AdaptadorImagenes adaptadorImagenes;

	private TextView nombre;
	private TextView puntuacion;
	private TextView precio;
	private ImageButton atras, flechaIzquierda, flechaDerecha;
	private ImageButton botonCamara;
	private ImageButton enviar;
	private RatingBar ratingBar, puntuacionUsuario;
	private RecyclerView vistaValoraciones;
	private EditText comentario;

	private String tipo = "";
	private int id = 0;
	private Uri imagenUri;

	ArrayList<Valoracion> valoraciones = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_elemento);

		// Para swipe back
		gestureDetector = new GestureDetector(this, new SwipeDetector());

		final ViewPager vistaImagenes = findViewById(R.id.viewPagerElemento);
		nombre = findViewById(R.id.textViewNombre);
		TextView primerComentario = findViewById(R.id.primerComentario);
		puntuacion = findViewById(R.id.textViewPuntuacion);
		ratingBar = findViewById(R.id.estrellitasElemento);
		puntuacionUsuario = findViewById(R.id.ratingBarUsuario);
		puntuacionUsuario.setRating(0);
		precio = findViewById(R.id.textViewPrecio);
		vistaValoraciones = findViewById(R.id.recycler_view_lista_comentarios);
		comentario = findViewById(R.id.editText);
		botonCamara = findViewById(R.id.botonCamara);
		enviar = findViewById(R.id.botonEnviar);
		atras = findViewById(R.id.atrasButton);
		flechaIzquierda = findViewById(R.id.flecha_izquierda);
		flechaDerecha = findViewById(R.id.flecha_derecha);

		// Para que no influya en el scroll
		vistaValoraciones.setNestedScrollingEnabled(false);

		// Miramos la informacion que nos pasan
		getInformacion();

		if (VariablesGlobales.idToken.equals("666")) {
			comentario.setHint(R.string.login_warning);
			comentario.setEnabled(false);
			puntuacionUsuario.setEnabled(false);
			enviar.setEnabled(false);
			;
		} else {
			// Para que no se muestre seleccionado al entrar en la actividad
			comentario.setCursorVisible(false);
			comentario.setHint(R.string.default_report);
			sendValoracion();
		}

		setBotonCamara();

		vistaImagenes.setAdapter(adaptadorImagenes);
		vistaImagenes.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				if (adaptadorImagenes.getCount() == 1) {
					flechaIzquierda.setVisibility(View.GONE);
					flechaDerecha.setVisibility(View.GONE);
				} else {
					if (position == 0) {
						flechaIzquierda.setVisibility(View.GONE);
						flechaDerecha.setVisibility(View.VISIBLE);
					} else if (position == adaptadorImagenes.getCount() - 1) {
						flechaIzquierda.setVisibility(View.VISIBLE);
						flechaDerecha.setVisibility(View.GONE);
					} else {
						flechaDerecha.setVisibility(View.VISIBLE);
						flechaIzquierda.setVisibility(View.VISIBLE);
					}
				}
			}

			@Override
			public void onPageSelected(int position) {

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

		setFlechaIzquierda(vistaImagenes);
		setFlechaDerecha(vistaImagenes);

		setValoraciones();

		if (valoraciones.isEmpty()) {
			primerComentario.setVisibility(View.VISIBLE);
		}

		setAtrasButton();
	}

	private void setFlechaIzquierda(final ViewPager viewPager) {
		flechaIzquierda.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
			}
		});
	}

	private void setFlechaDerecha(final ViewPager viewPager) {

		flechaDerecha.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
			}
		});
	}

	private void setAtrasButton() {
		atras.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void sendValoracion() {
		/**
		 * Espera a que el usuario envíe una valoración
		 */
		enviar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					enviar.setVisibility(View.GONE);

					// Quitamos el teclado si esta abierto
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

					// Creamos el JSON
					JSONObject postData = new JSONObject();
					postData.put("idToken", VariablesGlobales.idToken);
					JSONObject valoracion = new JSONObject();
					valoracion.put("puntuacion", puntuacionUsuario.getRating());
					puntuacionUsuario.setEnabled(false);
					valoracion.put("texto", comentario.getText().toString());
					comentario.setEnabled(false);
					postData.put("valoracion", valoracion);

					new HttpPostRequest().execute("https://api.mainu.eus/add_valoracion/"
							+ tipo + "/" + id, postData.toString());


					Toast.makeText(getApplicationContext(), R.string.greetings,
							Toast.LENGTH_SHORT).show();

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void setValoraciones() {
		Adaptador_Comentarios adapter = new Adaptador_Comentarios(valoraciones, this);
		vistaValoraciones.setFocusable(false);
		vistaValoraciones.setAdapter(adapter);
		vistaValoraciones.setLayoutManager(new LinearLayoutManager(this));
	}

	private void getInformacion() {
		// Bocadillo
		if (getIntent().hasExtra("bocadillo")) {
			Bocadillo bocadillo = (Bocadillo) getIntent().getSerializableExtra("bocadillo");
			bocadillo = getBocadillo(bocadillo.getId());

			tipo = "bocadillos";
			id = bocadillo.getId();

			setBocadillo(bocadillo);
		}
		// Otro
		if (getIntent().hasExtra("otro")) {
			Otro otro = (Otro) getIntent().getSerializableExtra("otro");
			otro = getOtro(otro.getId());

			tipo = "otros";
			id = otro.getId();

			setOtro(otro);
		}
		// Plato
		if (getIntent().hasExtra("plato")) {
			Plato plato = (Plato) getIntent().getSerializableExtra("plato");
			plato = getPlato(plato.getId());

			tipo = "menu";
			id = plato.getId();

			setPlato(plato);
		}
	}

	private Bocadillo getBocadillo(int id) {

		HttpGetRequest request = new HttpGetRequest();

		Bocadillo nuevo = new Bocadillo();
		if (request.isConnected(this)) {
			nuevo = request.getBocadillo(id);
		}

		return nuevo;
	}

	private Otro getOtro(int id) {

		HttpGetRequest request = new HttpGetRequest();

		Otro nuevo = new Otro();
		if (request.isConnected(this)) {
			nuevo = request.getComplemento(id);
		}

		return nuevo;
	}

	private Plato getPlato(int id) {

		HttpGetRequest request = new HttpGetRequest();

		Plato nuevo = new Plato();
		if (request.isConnected(this)) {
			nuevo = request.getPlato(id);
		}

		return nuevo;
	}

	private void setBocadillo(Bocadillo bocadillo) {

		Log.d(TAG, "setBocadillo: " + bocadillo.getNombre());

		nombre.setText(bocadillo.getNombre());
		precio.setText(String.format(Locale.getDefault(), "%.2f€", bocadillo.getPrecio()));

		valoraciones = bocadillo.getValoraciones();
		adaptadorImagenes = new AdaptadorImagenes(bocadillo.getImagenes(), this, 1);

		if (bocadillo.getPuntuacion() != 0) {
			puntuacion.setText(String.format(Locale.getDefault(), "%.1f",
					bocadillo.getPuntuacion()));
			ratingBar.setRating((float) bocadillo.getPuntuacion());

		} else {
			puntuacion.setText("N/A");
		}
	}

	private void setOtro(Otro otro) {
		Log.d(TAG, "setComplemento: " + otro.getNombre());

		nombre.setText(otro.getNombre());
		precio.setText(String.format(Locale.getDefault(), "%.2f€", otro.getPrecio()));

		valoraciones = otro.getValoraciones();
		adaptadorImagenes = new AdaptadorImagenes(otro.getImagenes(), this, 1);


		if (otro.getPuntuacion() != 0) {
			puntuacion.setText(String.format(Locale.getDefault(), "%.1f",
					otro.getPuntuacion()));
			ratingBar.setRating((float) otro.getPuntuacion());
		} else {
			puntuacion.setText("N/A");
		}

	}

	private void setPlato(Plato plato) {
		Log.d(TAG, "setPlato: " + plato.getNombre());

		nombre.setText(plato.getNombre());
		precio.setVisibility(View.GONE);

		valoraciones = plato.getValoraciones();
		adaptadorImagenes = new AdaptadorImagenes(plato.getImagenes(), this, 1);

		if (plato.getPuntuacion() != 0) {
			puntuacion.setText(String.format(Locale.getDefault(), "%.1f", plato.getPuntuacion()));
			ratingBar.setRating((float) plato.getPuntuacion());
		} else {
			puntuacion.setText("N/A");
		}

	}

	private void setBotonCamara() {
		botonCamara.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.d(TAG, "onClick: Boton Camara");

				if (!VariablesGlobales.idToken.equals("666")) {
					boolean camara = false;
					do {
						if (checkPermisos(Permisos.PERMISOS)) {
							//Tenemos permisos, comprobamos internet
							if (new HttpGetRequest().isConnected(view.getContext())) {
								//Iniciamos la camara intent
								Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
								cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

								//Abrimos el directorio donde guardamos la imagen
								File directorioImagenes = Environment.
										getExternalStoragePublicDirectory(Environment.
												DIRECTORY_PICTURES);

								//Creamos un nombre unico para cada imagen
								String nombre = getNombre();

								//Juntamos el directorio y el nombre
								File imagen = new File(directorioImagenes, nombre);

								//Lo pasamos a este formato
								StrictMode.VmPolicy.Builder builder = new StrictMode.
										VmPolicy.Builder();
								StrictMode.setVmPolicy(builder.build());
								imagenUri = Uri.fromFile(imagen);

								//Decimos que se guarde en la uri
								cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imagenUri);
								startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
								camara = false;

							} else {
								Toast.makeText(view.getContext(), R.string.connection_fail,
										Toast.LENGTH_SHORT).show();
							}
						} else {
							pidePermisos(Permisos.PERMISOS);
							if (checkPermisos(Permisos.PERMISOS)) {
								camara = true;
							}
						}
					} while (camara);
				} else {
					Toast.makeText(view.getContext(), R.string.login_warning,
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private String getNombre() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss",
				Locale.getDefault());
		String timestamp = sdf.format(new Date());
		return "MainU_" + timestamp + ".jpg";
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// Comprueba que se ha sacado la foto
		if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_REQUEST_CODE) {
			Log.d(TAG, "onActivityResult: done taking a photo.");
			Log.d(TAG, "onActivityResult: attempting to navigate to final share screen.");

			if (imagenUri != null) {

				try {
					Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),
							imagenUri);
					//Para comprimir la imagen en JPEG
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
					String encodedImage = Base64.encodeToString(stream.toByteArray(),
							Base64.DEFAULT);
					// JSON POST de la imagen codificada en base64
					JSONObject postData = new JSONObject();
					postData.put("idToken", VariablesGlobales.idToken);
					postData.put("imagen", encodedImage);
					new HttpPostRequest().execute("https://api.mainu.eus/upload_image/"
							+ tipo + "/" + id, postData.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
				Toast.makeText(this, R.string.greetings,
						Toast.LENGTH_LONG).show();
			}
		}
	}

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private GestureDetector gestureDetector;

	protected void onSwipeRight() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
	}

	protected void onSwipeLeft() {
		//TO-DO
	}

	public class SwipeDetector extends GestureDetector.SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

			// Check movement along the Y-axis. If it exceeds SWIPE_MAX_OFF_PATH,
			// then dismiss the swipe.
			if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
				return false;
			}

			// Para que no interfiera con el swipe de las fotos o de las estrellas
			if (e1.getY() < 1300 || e2.getY() < 1300) {
				return false;
			}

			//from left to right
			if (e2.getX() > e1.getX()) {
				if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX)
						> SWIPE_THRESHOLD_VELOCITY) {
					onSwipeRight();
					return true;
				}
			}

			if (e1.getX() > e2.getX()) {
				if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX)
						> SWIPE_THRESHOLD_VELOCITY) {
					onSwipeLeft();
					return true;
				}
			}

			return false;
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TouchEvent dispatcher.
		if (gestureDetector != null) {
			if (gestureDetector.onTouchEvent(ev))
				// If the gestureDetector handles the event, a swipe has been
				// executed and no more needs to be done.
				return true;
		}

		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	}

	/**
	 * Petición de permisos para sacar fotos y almacenar de forma temporal en el dispositivo
	 */

	public void pidePermisos(String[] permisos) {
		Log.d(TAG, "compruebaPermisos: Comprobando permisos");

		ActivityCompat.requestPermissions(
				this,
				permisos,
				COMPRUEBA_PERMISOS
		);

	}

	private boolean checkPermisos(String[] permisos) {
		for (String check : permisos) {
			if (!checkPermiso(check)) {
				return false;
			}
		}
		return true;
	}

	public boolean checkPermiso(String permiso) {
		int peticionPermisos = ActivityCompat.checkSelfPermission(this, permiso);
		if (peticionPermisos != PackageManager.PERMISSION_GRANTED) {
			Log.d(TAG, "checkPermiso: \n NO tenemos permisos para: " + permiso);
			return false;
		} else {
			Log.d(TAG, "checkPermiso: \n SI tenemos permisos para " + permiso);
			return true;
		}
	}
}
