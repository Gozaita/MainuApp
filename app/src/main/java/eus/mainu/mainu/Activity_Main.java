package eus.mainu.mainu;

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
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

import eus.mainu.mainu.Utilidades.Adaptador_Fragmentos;

public class Activity_Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "Activity MAIN";
    private static final int SIGN_IN_CODE = 777; //Es 777 porque yo he querido, podria ser cualquiera

    private TextView titulo;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageButton searchButton;
    private ImageButton backArrow;
    private EditText filter;
    private RelativeLayout layoutblanco;

    //NavigationView
    NavigationView navigationView;

    //Datos del usuario
    private ImageView fotoUsuario;
    private TextView nombre, email;

    //Variables
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

        titulo = findViewById(R.id.textViewActividad);
        viewPager = findViewById(R.id.contenedor);
        tabLayout = findViewById(R.id.tabs);
        searchButton = findViewById(R.id.search_button);
        filter = findViewById(R.id.searchFilter);
        backArrow = findViewById(R.id.back_button);
        layoutblanco = findViewById(R.id.barra_blanca);
        navigationView = findViewById(R.id.nav_view);

        //Nav
        View header = navigationView.getHeaderView(0);
        fotoUsuario = header.findViewById(R.id.imagenUsuario);
        nombre = header.findViewById(R.id.nombre);
        email = header.findViewById(R.id.correo);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)//Asi seria el normal
                .requestEmail()//asi pedimos el id
                .build();

        //Autenticacion
        googleApiClient = new GoogleApiClient.Builder(this).
                enableAutoManage(this,this) //permite gestionar el ciclo de vida con la actividad y dice quien escucha en caso de que algo salga mal
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        //taskAcabada = (ITaskAcabada) this;

        setUpTitulo();

        setupViewPager();

        setIntroduceTextoEditText();
        setEnterEditText();

        setBackButton();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.blanco));
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

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

    //Comprobamos si el loggin ha sido exitoso y accedemos a la informacion del usuario
    private void resultadoSilentSingIn(GoogleSignInResult result){

        if(result.isSuccess()) {

            GoogleSignInAccount cuenta = result.getSignInAccount();

            //Ponemos los datos del usuario
            nombre.setText(cuenta.getDisplayName());
            email.setText(cuenta.getEmail());

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

    //Responsable de añadir 3 fragmentos: Bocadillos, Menu, Otros
    private void setupViewPager(){

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


        searchButton.setOnClickListener(searchClickListener);
    }


    //Metodo que sirve para cambiar el titulo de la toolbar en funcion del fragmento
    private void setUpTitulo(){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Quitamos el teclado si esta abierto
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(viewPager.getWindowToken(), 0);
                //Reseteamos la toolbar
                filter.setVisibility(View.GONE);
                layoutblanco.setVisibility(View.GONE);
                backArrow.setVisibility(View.GONE);
                filter.setText("");

                switch (position) {
                    case 0:
                        titulo.setText(R.string.menuDelDia);
                        searchButton.setVisibility(View.GONE);
                        break;
                    case 1:
                        titulo.setText(R.string.bocadillos);
                        searchButton.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        titulo.setText(R.string.complementos);
                        searchButton.setVisibility(View.VISIBLE);
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

    View.OnClickListener searchClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (v.equals(searchButton)) {
                //Mostramos el cuadro de texto
                layoutblanco.setVisibility(View.VISIBLE);
                backArrow.setVisibility(View.VISIBLE);
                findViewById(R.id.searchFilter).setVisibility(View.VISIBLE);
                //Focuseamos el cuadro
                filter.requestFocus();
                //Abrimos el teclado en el cuadro de texto
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(filter, InputMethodManager.SHOW_IMPLICIT);
            }
        }
    };

    //Metodo para saber si se ha pulsado el enter, esconder el teclado y el cuadro de texto
    private void setEnterEditText(){
        filter.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //Comprueba si se devuelve un 0
                if (actionId == EditorInfo.IME_NULL) {
                    //taskAcabada.setTexto(busqueda);
                    //Esconde teclado
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    //Esconde texto
                    filter.setVisibility(View.GONE);
                    backArrow.setVisibility(View.GONE);
                    layoutblanco.setVisibility(View.GONE);

                    return true;
                }
                return false;
            }
        });
    }

    //Metodo que gestiona la introduccion del texto en el filtro
    private void setIntroduceTextoEditText(){
        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
    }

    private void setBackButton(){
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutblanco.setVisibility(View.GONE);
                backArrow.setVisibility(View.GONE);
                filter.setVisibility(View.GONE);
                filter.setText("");
                //Esconde teclado
                //Quitamos el teclado si esta abierto
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(viewPager.getWindowToken(), 0);
            }
        });
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {

        } else if (id == R.id.informacion) {

        } else if (id == R.id.comparte) {

        } else if (id == R.id.error) {

        } else if (id == R.id.iniciarSesion) {

            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
            startActivityForResult(signInIntent, SIGN_IN_CODE); //Codigo unico que devuelve al registrarse


        } else if (id == R.id.cerrarSesion) {

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


            nombre.setText(R.string.nombre);
            email.setText(R.string.email);

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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Cuando algo sale mal en la conexion
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGN_IN_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            resultadoSignIn(result);
        }
    }

    //Metodo que gestiona el resultado del sign in
    private  void resultadoSignIn(GoogleSignInResult result){

        if (result.isSuccess()){
            Toast.makeText(this,R.string.agradecimiento,Toast.LENGTH_SHORT).show();

            GoogleSignInAccount cuenta = result.getSignInAccount();
            nombre.setText(cuenta.getDisplayName());
            email.setText(cuenta.getEmail());

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
        } else {
            Toast.makeText(this, R.string.fail ,Toast.LENGTH_SHORT).show();
        }
    }


}
