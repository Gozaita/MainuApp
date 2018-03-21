package eus.mainu.mainu;

import android.app.Activity;
import android.content.Context;
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
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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

import java.util.ArrayList;

import eus.mainu.mainu.Utilidades.Adaptador_Busqueda;
import eus.mainu.mainu.Utilidades.Adaptador_Fragmentos;
import eus.mainu.mainu.datalayer.Bocadillo;

public class Activity_Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

    //Variables globales
    private static final String TAG = "Activity MAIN";
    private static final int SIGN_IN_CODE = 777; //Es 777 porque yo he querido, podria ser cualquiera

    //Elementos layout
    private DrawerLayout drawer;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private ImageView fotoUsuario;
    private TextView nombre, email;
    private SearchView searchView;

    private String[] posiblesBocadillos = new String[]{"atun con queso", "bacon", "ensalada cesar"};
    private ListView searchResult;
    private ArrayList<Bocadillo> arrayList;
    private ArrayList<String> arrayListSring = new ArrayList<String>();
    private ArrayAdapter adapter;

    //Datos del usuario
    private GoogleApiClient googleApiClient;

    //Fragmentos
    private Fragment_Menu fMenu = new Fragment_Menu();
    private Fragment_Bocadillos fBocadillos = new Fragment_Bocadillos();
    private Fragment_Otros fOtros = new Fragment_Otros();

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
        searchResult = findViewById(R.id.searchResult);
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
        setupViewPager();

        searchView.setIconified(true);
        arrayListSring.add("atun");
        arrayListSring.add("bacon");
        arrayListSring.add("ensalada");
        adapter = new ArrayAdapter<String>(this, R.layout.listview_testtt, arrayListSring);
        searchResult.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("bobo \n\n\n");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Activity_Main.this.adapter.getFilter().filter(newText);

                return false;
            }
        });
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
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_menu);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_sandwich);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_french_fries);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Quitamos el teclado si esta abierto
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(viewPager.getWindowToken(), 0);
                switch (position) {
                    case 0:
                        getSupportActionBar().setTitle(R.string.menuDelDia);
                        break;
                    case 1:
                        getSupportActionBar().setTitle(R.string.bocadillos);
                        break;
                    case 2:
                        getSupportActionBar().setTitle(R.string.complementos);
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
     *
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
