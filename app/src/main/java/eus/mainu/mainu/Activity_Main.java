package eus.mainu.mainu;

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

import eus.mainu.mainu.Utilidades.Adaptador_Fragmentos;
import eus.mainu.mainu.Utilidades.HttpPostRequest;
import eus.mainu.mainu.Utilidades.Menu;
import eus.mainu.mainu.datalayer.Bocadillo;
import eus.mainu.mainu.datalayer.Complemento;
import eus.mainu.mainu.datalayer.Ingrediente;

public class Activity_Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

    //Variables globales
    private final String TAG = "Activity MAIN";
    private final int SIGN_IN_CODE = 777; //Es 777 porque yo he querido, podria ser cualquiera

    //Variables locales
    private boolean searchIsFocused = false;

    //Elementos layout
    private DrawerLayout drawer;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private ImageView fotoUsuario;
    private TextView nombre, email;
    private SearchView searchView;

    //Datos del usuario
    private GoogleApiClient googleApiClient;

    //Fragmentos
    private Fragment_Menu fMenu = new Fragment_Menu();
    private Fragment_Bocadillos fBocadillos = new Fragment_Bocadillos();
    private Fragment_Otros fOtros = new Fragment_Otros();

    private ArrayList<Bocadillo> listaBocadillos;
    private ArrayList<Bocadillo> listaBocadillosFiltrada;
    private ArrayList<Complemento> listaOtros;
    private ArrayList<String> ingredientesFiltro = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: Inicia Main Activity");

        //Seccion para referenciar a todos los elementos layout
        drawer = findViewById(R.id.drawer_layout);
        viewPager = findViewById(R.id.contenedor);
        tabLayout = findViewById(R.id.tabs);
        toolbar = findViewById(R.id.toolbar);
        searchView = findViewById(R.id.searchView);
        setSupportActionBar(toolbar);

        //Hay que hacer esto para referenciar los elementos del nav header
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        fotoUsuario = header.findViewById(R.id.imagenUsuario);
        nombre = header.findViewById(R.id.nombre);
        email = header.findViewById(R.id.correo);

        setToolbar();
        setCuenta();
        setDrawer();

        Menu menu = (Menu) getIntent().getSerializableExtra("Menu");
        listaBocadillos = (ArrayList<Bocadillo>) getIntent().getSerializableExtra("listaBocadillos");
        //listaBocadillos.add(new Bocadillo(9999, "Todavía no te decides?", 0, 0, new ArrayList<Ingrediente>() ));

        listaBocadillosFiltrada = new ArrayList<>();
        listaOtros = (ArrayList<Complemento>) getIntent().getSerializableExtra("listaOtros");



        Bundle bundle = new Bundle();
        bundle.putSerializable("Menu", menu);
        fMenu.setArguments(bundle);

        bundle = new Bundle();
        bundle.putSerializable("listaBocadillos", listaBocadillos);
        fBocadillos.setArguments(bundle);

        bundle = new Bundle();
        bundle.putSerializable("listaOtros", listaOtros);
        fOtros.setArguments(bundle);
        setupViewPager();

        searchView.setIconified(true);
        final EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.blanco));
        searchEditText.setHintTextColor(getResources().getColor(R.color.blanco));

        ImageView closeButton = (ImageView)searchView.findViewById(R.id.search_close_btn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fBocadillos.actualizaListaBocadillos(listaBocadillos);
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchView.onActionViewCollapsed();
                tabLayout.setVisibility(View.VISIBLE);
                findViewById(R.id.tabLayout2).setVisibility(View.VISIBLE);
                findViewById(R.id.recyclerView_ingredientes).setVisibility(View.GONE);
                toolbar.setTitle("Bocadillos");
                // Reseteamos la lista de ingredientes filtrados
                fBocadillos.actualizaListaBocadillos(listaBocadillos);
                fBocadillos.deseleccionarTodosIngredientes();
                ingredientesFiltro.clear();
            }
        });
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                searchIsFocused = hasFocus;
                if(hasFocus) {
                    tabLayout.setVisibility(View.GONE);
                    findViewById(R.id.recyclerView_ingredientes).setVisibility(View.VISIBLE);
                    findViewById(R.id.tabLayout2).setVisibility(View.GONE);
                    toolbar.setTitle("");
                }
                else {
                    searchView.setIconified(true);
                    tabLayout.setVisibility(View.VISIBLE);
                    findViewById(R.id.tabLayout2).setVisibility(View.VISIBLE);
                    toolbar.setTitle("Bocadillos");
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
                listaBocadillosFiltrada.clear();
                for(Bocadillo b : listaBocadillos){
                    // Importante pasar todoo a minusculas
                    if(b.getNombre().toLowerCase().contains( newText.toLowerCase() )){
                        listaBocadillosFiltrada.add(b);
                    } else {
                        for(Ingrediente i : b.getIngredientes()){
                            if(i.getNombre().toLowerCase().contains( newText.toLowerCase()))
                                listaBocadillosFiltrada.add(b);
                        }
                    }
                }

                fBocadillos.actualizaListaBocadillos(listaBocadillosFiltrada);
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(searchIsFocused){
            searchView.setIconified(true);
            tabLayout.setVisibility(View.VISIBLE);
            findViewById(R.id.recyclerView_ingredientes).setVisibility(View.GONE);
            toolbar.setTitle("Bocadillos");
        } else{
            // Si no los usuarios no pueden salir de la app
            super.onBackPressed();
        }
    }

    public void filterByIngredient(String ingrediente) {
        // Si está, lo borramos
        if(ingredientesFiltro.contains(ingrediente))
            ingredientesFiltro.remove(ingrediente);
        // Si no está, lo añadimos
        else
            ingredientesFiltro.add(ingrediente);

        // Importante pasar todoo a minusculas
        if(ingredientesFiltro.size() > 0){
            listaBocadillosFiltrada.clear();
            for (Bocadillo b : listaBocadillos) {
                int nPass = 0;
                for (String ing : ingredientesFiltro){
                    boolean pass = false;
                    for (Ingrediente i : b.getIngredientes()) {
                        if (i.getNombre().toLowerCase().equalsIgnoreCase(ing)){
                            pass = true;
                        }
                    }
                    // Si en todos los ingredientes de un bocadiilo no está, fuera
                    if(pass)
                        nPass++;
                }
                if(nPass == ingredientesFiltro.size())
                    listaBocadillosFiltrada.add(b);
            }

            fBocadillos.actualizaListaBocadillos(listaBocadillosFiltrada);
        } else{
            // Si no hay ingredientes a filtar --> reseteamos
            fBocadillos.actualizaListaBocadillos(listaBocadillos);
            ingredientesFiltro.clear();
        }
    }

    /********************************************************************************************/
    //Metodo para customizar la toolbar e implementar la busqueda, se puede convinar con el metodo on page scroll
    private void setToolbar(){
        getSupportActionBar().setTitle(R.string.menuDelDia);
    }

    /********************************************************************************************/
    private void setDrawer(){
        Log.d(TAG, "setDrawer");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.blanco));
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    /********************************************************************************************/
    //Responsable de añadir 3 fragmentos: Bocadillos, Menu, Otros
    private void setupViewPager(){
        Log.d(TAG, "setupViewPager");

        //Creamos los fragmentos
        Adaptador_Fragmentos adapter = new Adaptador_Fragmentos(getSupportFragmentManager());
        adapter.addFragment(fMenu); //index 0
        adapter.addFragment(fBocadillos); //index 1
        adapter.addFragment(fOtros); //index 2

        //Creamos las tabulaciones
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        //Añadimos 3 iconos
        tabLayout.getTabAt(0).setIcon(R.drawable.nav_menu);
        tabLayout.getTabAt(1).setIcon(R.drawable.nav_bocadillos);
        tabLayout.getTabAt(2).setIcon(R.drawable.nav_otros);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Quitamos el teclado si esta abierto
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(viewPager.getWindowToken(), 0);
                switch (position) {
                    case 0:
                        getSupportActionBar().setTitle(R.string.menuDelDia);
                        searchView.setVisibility(View.INVISIBLE);
                        searchView.setIconified(true);

                        // Al cambiar de vista, reseteamos
                        // Lo ponemos aquí para que el usuario no se entere (y no lo vea)
                        findViewById(R.id.recyclerView_ingredientes).setVisibility(View.GONE);
                        if(!ingredientesFiltro.isEmpty() ){
                            fBocadillos.actualizaListaBocadillos(listaBocadillos);
                            fBocadillos.deseleccionarTodosIngredientes();
                            ingredientesFiltro.clear();
                        }
                        break;
                    case 1:
                        getSupportActionBar().setTitle(R.string.bocadillos);
                        searchView.setVisibility(View.VISIBLE);
                        searchView.setIconified(true);
                        findViewById(R.id.recyclerView_ingredientes).setVisibility(View.GONE);
                        break;
                    case 2:
                        getSupportActionBar().setTitle(R.string.complementos);
                        searchView.setVisibility(View.INVISIBLE);
                        searchView.setIconified(true);

                        // Al cambiar de vista, reseteamos
                        // Lo ponemos aquí para que el usuario no se entere (y no lo vea)
                        findViewById(R.id.recyclerView_ingredientes).setVisibility(View.GONE);
                        if(!ingredientesFiltro.isEmpty() ){
                            fBocadillos.actualizaListaBocadillos(listaBocadillos);
                            fBocadillos.deseleccionarTodosIngredientes();
                            ingredientesFiltro.clear();
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

     /********************************************************************************************
     * Codigo que tiene que ver con el registro del usuario con Google
     */

    //Configuramos el navigation Drawer
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Para definir las acciones de los iconos

        int id = item.getItemId();

        switch (id){
            case R.id.home:
                homeClick();
                break;
            case R.id.informacion:
                Intent intent = new Intent(this, Activity_Info.class);
                this.startActivity(intent);
                break;
            case R.id.comparte:
                comparteClick();
                break;
            case R.id.error:
                muestraCuadroTexto();
                break;
            case R.id.iniciarSesion:
                iniciarSesionClick();
                break;
            case R.id.cerrarSesion:
                cerrarSesionClick();
                break;
        }

        //Cerramos el drawer
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    /********************************************************************************************/
    private void homeClick(){
        drawer.closeDrawer(GravityCompat.START);

    }

    /********************************************************************************************/
    private void comparteClick(){
        //Metodo para enviar un mensaje a otra aplicacion
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,"¡Mira como mola! --> www.mainu.eus");
        startActivity(Intent.createChooser(intent,"Compartir con"));
    }

    /********************************************************************************************/
    public void muestraCuadroTexto() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_box, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);

        dialogBuilder.setTitle(R.string.Ayudanos);
        dialogBuilder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                try {
                    JSONObject postData = new JSONObject();
                    postData.put("report",edt.getText().toString());
                    new HttpPostRequest().execute("https://api  .mainu.eus/report", postData.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast.makeText(Activity_Main.this,R.string.agradecimiento,Toast.LENGTH_SHORT).show();
            }
        });
        dialogBuilder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }


    /********************************************************************************************/
    private void iniciarSesionClick(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, SIGN_IN_CODE); //Codigo unico que devuelve al registrarse
    }

    /********************************************************************************************/
    private void cerrarSesionClick(){
        //Cerramos la sesion del usuario e indicamos con un Toast como ha ido
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()){
                    Toast.makeText(Activity_Main.this, R.string.despedida, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Activity_Main.this, R.string.fail, Toast.LENGTH_SHORT).show();
                }
            }
        });

        resetNavigationDrawer();
    }


    /********************************************************************************************/
    private void setCuenta(){
        Log.d(TAG, "setCuenta");

        //Pedimos la cuenta del usuario con nuestro codigo de la API
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)//Asi seria el normal
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();

        ///Permite gestionar el ciclo de vida de la autenticacion con el de la actividad y dice quien escucha en caso de que algo salga mal
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this) //El segundo parametro dice quien escucha
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }


    /********************************************************************************************/
    private void resetNavigationDrawer(){
        Log.d(TAG, "resetNavigationDrawer");

        nombre.setText(R.string.iniciaSesion);
        email.setText(R.string.utilizaGoogle);
        VariablesGlobales.idToken = "666";

        Picasso.with(this).load(R.drawable.logo_blanco).fit().into(fotoUsuario, new Callback() {
            @Override
            public void onSuccess() {
                Bitmap imageBitmap = ((BitmapDrawable) fotoUsuario.getDrawable()).getBitmap();
                RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(getResources(), imageBitmap);
                imageDrawable.setCircular(true);
                imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                fotoUsuario.setImageDrawable(imageDrawable);
            }

            @Override
            public void onError() {

            }
        });
    }

    /********************************************************************************************/
    //Se hace un silent sing in cuando se inicia la actividad
    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart");

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()) {
            GoogleSignInResult result = opr.get();
            resultadoSilentSingIn(result);
        } else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult result) {
                    resultadoSilentSingIn(result);
                }
            });
        }
    }

    /********************************************************************************************/
    //Comprobamos si el silent sign in ha sido existoso y mostramos la informacion si ha sido asi
    private void resultadoSilentSingIn(GoogleSignInResult result){
        Log.d(TAG, "resultadoSilentSingIn: "+ result.isSuccess());

        if(result.isSuccess()) {

            GoogleSignInAccount cuenta = result.getSignInAccount();

            //Ponemos los datos del usuario
            nombre.setText(cuenta.getDisplayName());
            email.setText(cuenta.getEmail());
            VariablesGlobales.idToken = cuenta.getIdToken();

            Picasso.with(this).load(cuenta.getPhotoUrl()).fit().into(fotoUsuario, new Callback() {
                @Override
                public void onSuccess() {
                    Bitmap imageBitmap = ((BitmapDrawable) fotoUsuario.getDrawable()).getBitmap();
                    RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(getResources(), imageBitmap);
                    imageDrawable.setCircular(true);
                    imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                    fotoUsuario.setImageDrawable(imageDrawable);
                }

                @Override
                public void onError() {

                }
            });
        } /*else {
            //No se ha autenticado bien
        }*/
    }

    /********************************************************************************************/
    //Cuando algo sale mal en la conexion
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed");

        Toast.makeText(this, R.string.fail ,Toast.LENGTH_SHORT).show();
    }

    /********************************************************************************************/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult");

        if(requestCode == SIGN_IN_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            resultadoSignIn(result);
        }
    }

    /********************************************************************************************/
    //Metodo que gestiona el resultado del sign in
    private  void resultadoSignIn(GoogleSignInResult result){

        Log.d(TAG, "resultadoSignIn: "+ result.isSuccess());

        if (result.isSuccess()){
            Toast.makeText(this,R.string.agradecimiento,Toast.LENGTH_SHORT).show();

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

                }
            });
        } else {
            Toast.makeText(this, R.string.fail ,Toast.LENGTH_SHORT).show();
        }
    }


}
