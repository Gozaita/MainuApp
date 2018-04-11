package eus.mainu.mainu;

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

import eus.mainu.mainu.Utilidades.Adaptador_Comentarios;
import eus.mainu.mainu.Utilidades.Adaptador_Imagenes_Swipe;
import eus.mainu.mainu.Utilidades.HttpGetRequest;
import eus.mainu.mainu.Utilidades.HttpPostRequest;
import eus.mainu.mainu.Utilidades.Permisos;
import eus.mainu.mainu.datalayer.Bocadillo;
import eus.mainu.mainu.datalayer.Complemento;
import eus.mainu.mainu.datalayer.Plato;
import eus.mainu.mainu.datalayer.Valoracion;

public class Activity_Elemento extends AppCompatActivity {

    private static final String TAG = "Activity Elemento";
    private static final int CAMERA_REQUEST_CODE = 2;
    private static final int COMPRUEBA_PERMISOS = 1;

    private Adaptador_Imagenes_Swipe adaptadorImagenes;

    private TextView nombre;
    private TextView puntuacion;
    private TextView precio;
    private ImageButton atras, flechaIzquierda, flechaDerecha;
    //private ImageButton imagen;
    private ImageButton botonCamara;
    private ImageButton enviar;
    private RatingBar ratingBar,ratUsuario;
    private RecyclerView listaComentarios;
    private EditText comentario;

    private String tipo = "";
    private int id=0;
    //private String idToken = "666";
    private Uri imagenUri;
    ArrayList<Valoracion> arrayValoraciones = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elemento);

        //Para swype back, da problemas con el
        //gestureDetector = new GestureDetector(this, new SwipeDetector());
        final ViewPager viewPager = findViewById(R.id.viewPagerElemento);
        nombre = findViewById(R.id.textViewNombre);
        TextView primerComentario = findViewById(R.id.primerComentario);
        puntuacion = findViewById(R.id.textViewPuntuacion);
        ratingBar = findViewById(R.id.estrellitasElemento);
        ratUsuario = findViewById(R.id.ratingBarUsuario);
        precio = findViewById(R.id.textViewPrecio);
        //imagen = findViewById(R.id.botonImagenElemento);
        listaComentarios = findViewById(R.id.recycler_view_lista_comentarios);
        comentario = findViewById(R.id.editText);
        botonCamara = findViewById(R.id.botonCamara);
        enviar = findViewById(R.id.botonEnviar);
        atras = findViewById(R.id.atrasButton);
        flechaIzquierda = findViewById(R.id.flecha_izquierda);
        flechaDerecha = findViewById(R.id.flecha_derecha);


        //Para que no influya en el scroll
        listaComentarios.setNestedScrollingEnabled(false);

        //Miramos la informacion que nos pasan
        getInformacion();


        if(VariablesGlobales.idToken.equals("666")) {
            comentario.setHint(R.string.noRegistro);
            comentario.setEnabled(false);
            ratUsuario.setEnabled(false);
            enviar.setEnabled(false);;
            //botonCamara.setEnabled(false);
        } else {
            //Para que no se muestre seleccionado al entrar en la actividad
            comentario.setCursorVisible(false);
            comentario.setHint(R.string.danos);
            sendValoracion();
        }
        setBotonCamara();



        //Ponemos el adaptador
        viewPager.setAdapter(adaptadorImagenes);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(adaptadorImagenes.getCount() == 1){
                    flechaIzquierda.setVisibility(View.GONE);
                    flechaDerecha.setVisibility(View.GONE);
                } else {
                    if(position == 0){
                        flechaIzquierda.setVisibility(View.GONE);
                        flechaDerecha.setVisibility(View.VISIBLE);
                    } else if(position == adaptadorImagenes.getCount()-1){
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

        setFlechaIzquierda(viewPager);
        setFlechaDerecha(viewPager);


        //Mostramos las valoraciones en el recycling view
        setValoraciones();

        if(arrayValoraciones.isEmpty()){
            primerComentario.setVisibility(View.VISIBLE);
        }

        setAtrasButton();


        //Para poner las estrellas blancas mediante codigo,
        //Las ponemos mediante el xml, aunque solo funciona para la version de android 5.0 en adelante
        //LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        //stars.getDrawable(2).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

    }


    private void setFlechaIzquierda(final ViewPager viewPager){
        flechaIzquierda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
            }
        });

    }

    private void setFlechaDerecha(final ViewPager viewPager){

        flechaDerecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            }
        });
    }


    public void pidePermisos(String[] permisos){
        Log.d(TAG, "compruebaPermisos: Comprobando permisos");

        ActivityCompat.requestPermissions(
                this,
                permisos,
                COMPRUEBA_PERMISOS
        );

    }


    /**
     * Metodos para pedir permisos
     */

    private boolean checkPermisos(String[] permisos){

        for (String check : permisos) {
            if (!checkPermiso(check)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkPermiso(String permiso){

        int peticionPermisos = ActivityCompat.checkSelfPermission(this,permiso);

        if(peticionPermisos != PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "checkPermiso: \n NO tenemos permisos para: " + permiso);
            return false;
        }
        else{
            Log.d(TAG, "checkPermiso: \n SI tenemos permisos para " + permiso);
            return true;
        }
    }

    private void setAtrasButton(){

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    //Metodo para poner escuchando el cuadro de texto y enviar la valoracion
    private void sendValoracion() {

        enviar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {
                    enviar.setVisibility(View.GONE);
                    //Quitamos el teclado si esta abierto
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    //Creamos el JSON
                    JSONObject postData = new JSONObject();
                    postData.put("idToken",VariablesGlobales.idToken);
                    JSONObject valoracion = new JSONObject();
                    valoracion.put("puntuacion",ratUsuario.getRating());
                    ratUsuario.setEnabled(false);
                    valoracion.put("texto",comentario.getText().toString());
                    comentario.setEnabled(false);
                    postData.put("valoracion",valoracion);

                    new HttpPostRequest().execute("https://api.mainu.eus/add_valoracion/" + tipo+"/"+id, postData.toString());


                    Toast.makeText(getApplicationContext(), R.string.agradecimiento, Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //Metodo para inflar el recyclingview de los comentarios
    private void setValoraciones() {

        //Inicializamos el adaptador de las valoraciones
        Adaptador_Comentarios adapter = new Adaptador_Comentarios(arrayValoraciones, this);

        //Inflamos la lista de comentarios
        listaComentarios.setFocusable(false);
        listaComentarios.setAdapter(adapter);
        listaComentarios.setLayoutManager(new LinearLayoutManager(this));
    }


    /**
     * Metodos que estan relacionados con leer la informacion que se le pasa a la actividad
     * y deciden si es un Plato/Bocadillo/Complemento
     */

    //Metodo para recibir la informacion que se pasa a la actividad
    private void getInformacion() {


        //idToken = VariablesGlobales.idToken;
        //Vemos si la info es de un bocadillo
        if (getIntent().hasExtra("Bocadillo")) {
            Bocadillo bocadillo = (Bocadillo) getIntent().getSerializableExtra("Bocadillo");
            setBocadillo(bocadillo);
            tipo = "bocadillos";
            id = bocadillo.getId();

        }
        //Vemos si la info es de un complemento
        if (getIntent().hasExtra("Complemento")) {

            Complemento complemento = (Complemento) getIntent().getSerializableExtra("Complemento");
            setComplemento(complemento);
            tipo = "otro";
            id = complemento.getId();

        }
        //Vemos si es un plato
        if (getIntent().hasExtra("Plato")) {
            Plato plato = (Plato) getIntent().getSerializableExtra("Plato");
            setPlato(plato);
            tipo = "menu";
            id = plato.getId();

        }
    }

    private void setBocadillo(Bocadillo bocadillo) {

        Log.d(TAG, "setBocadillo: " + bocadillo.getNombre());

        HttpGetRequest request = new HttpGetRequest();

        Bocadillo nuevo = new Bocadillo();
        if (request.isConnected(this)) {
            nuevo = request.getBocadillo(bocadillo.getId());
        }

        nombre.setText(bocadillo.getNombre());
        precio.setText(String.format(Locale.getDefault(), "%.2f€", bocadillo.getPrecio()));

        //Metemos la nueva informacion en el bocadillo
        bocadillo.setFotos(nuevo.getFotos());
        bocadillo.setValoraciones(nuevo.getValoraciones());

        arrayValoraciones = bocadillo.getValoraciones();
        adaptadorImagenes = new Adaptador_Imagenes_Swipe(bocadillo.getFotos(),this);

        /*
        if (bocadillo.getFotos() != null) {
            if (!bocadillo.getFotos().isEmpty()) {
                Picasso.with(this)
                        .load(bocadillo.getFotos().get(0).getRuta())
                        .fit()
                        .centerCrop()
                        .into(imagen);
            }
        }
        */

        if (bocadillo.getPuntuacion() != 0) {
            puntuacion.setText(String.format(Locale.getDefault(), "%.1f", bocadillo.getPuntuacion()));
            ratingBar.setRating((float) bocadillo.getPuntuacion());

        } else {
            puntuacion.setText("N/A");
        }
    }

    private void setComplemento(Complemento complemento) {

        Log.d(TAG, "setComplemento: " + complemento.getNombre());

        HttpGetRequest request = new HttpGetRequest();

        Complemento nuevo = new Complemento();
        if (request.isConnected(this)) {
            nuevo = request.getComplemento(complemento.getId());
        }

        nombre.setText(complemento.getNombre());
        precio.setText(String.format(Locale.getDefault(), "%.2f€", complemento.getPrecio()));

        //Metemos la nueva informacion
        complemento.setFotos(nuevo.getFotos());
        complemento.setValoraciones(nuevo.getValoraciones());

        arrayValoraciones = complemento.getValoraciones();
        adaptadorImagenes = new Adaptador_Imagenes_Swipe(complemento.getFotos(),this);

        /*
        if (complemento.getFotos() != null) {
            if (!complemento.getFotos().isEmpty()) {
                Picasso.with(this)
                        .load(complemento.getFotos().get(0).getRuta())
                        .fit()
                        .centerCrop()
                        .into(imagen);
            }
        }
        */

        if (complemento.getPuntuacion() != 0) {
            puntuacion.setText(String.format(Locale.getDefault(), "%.1f", complemento.getPuntuacion()));
            ratingBar.setRating((float) complemento.getPuntuacion());
        } else {
            puntuacion.setText("N/A");
        }

    }

    private void setPlato(Plato plato) {

        Log.d(TAG, "setPlato: " + plato.getNombre());

        HttpGetRequest request = new HttpGetRequest();

        Plato nuevo = new Plato();
        if (request.isConnected(this)) {
            nuevo = request.getPlato(plato.getId());
        }

        nombre.setText(plato.getNombre());
        precio.setVisibility(View.GONE);

        //Metemos la nueva informacion
        plato.setFotos(nuevo.getFotos());
        plato.setValoraciones(nuevo.getValoraciones());

        arrayValoraciones = plato.getValoraciones();
        adaptadorImagenes = new Adaptador_Imagenes_Swipe(plato.getFotos(),this);

        /*
        if (plato.getFotos() != null) {
            if (!plato.getFotos().isEmpty()) {
                Picasso.with(this)
                        .load(plato.getFotos().get(0).getRuta())
                        .fit()
                        .centerCrop()
                        .into(imagen);
            }
        }*/

        if (plato.getPuntuacion() != 0) {
            puntuacion.setText(String.format(Locale.getDefault(), "%.1f", plato.getPuntuacion()));
            ratingBar.setRating((float) plato.getPuntuacion());
        } else {
            puntuacion.setText("N/A");
        }

    }

    /**
     * Metodos que estan relacionados con el uso de la camara
     */

    //Metodo para llamar a la camara
    private void setBotonCamara() {
        botonCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Boton Camara");

                if(!VariablesGlobales.idToken.equals("666")) {
                    if(checkPermisos(Permisos.PERMISOS)){
                        //Tenemos permisos, comprobamos internet
                        if(new HttpGetRequest().isConnected(view.getContext())){
                            //Hay conexion

                            //Iniciamos la camara intent
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                            //Abrimos el directorio donde guardamos la imagen
                            File directorioImagenes = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

                            //Creamos un nombre unico para cada imagen
                            String nombre = getNombre();

                            //Juntamos el directorio y el nombre
                            File imagen = new File(directorioImagenes,nombre);

                            //Lo pasamos a este formato
                            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                            StrictMode.setVmPolicy(builder.build());
                            imagenUri = Uri.fromFile(imagen);

                            //imagenUri = FileProvider.getUriForFile(view.getContext(), view.getContext().getPackageName(), imagen);
                            //Decimos que se guarde en la uri
                            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imagenUri);
                            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);


                        }else {
                            Toast.makeText(view.getContext(), R.string.noConexion, Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        pidePermisos(Permisos.PERMISOS);
                    }
                } else {
                    Toast.makeText(view.getContext(), R.string.noRegistro, Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private String getNombre(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss",Locale.getDefault());
        String timestamp = sdf.format(new Date());
        return "MainU_" +timestamp+".jpg";

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Comprobamos que se ha sacado la foto con el result_ok, comprobamos que es el codigo de la camara al que llaman
        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_REQUEST_CODE) {
            Log.d(TAG, "onActivityResult: done taking a photo.");
            Log.d(TAG, "onActivityResult: attempting to navigate to final share screen.");

            if (imagenUri != null ) {

                try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imagenUri);
                //imagen.setImageBitmap(bitmap);

                //Para comprimir la imagen en JPEG
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                String encodedImage = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);

                //Enviamos la imagen en un JSON codificada en Base 64

                    JSONObject postData = new JSONObject();
                    postData.put("idToken",VariablesGlobales.idToken);
                    postData.put("imagen",encodedImage);

                    new HttpPostRequest().execute("https://api.mainu.eus/upload_image/"+tipo+"/"+id, postData.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }

                Toast.makeText(this, R.string.agradecimiento, Toast.LENGTH_LONG).show();
            }
        }

    }

    /**
     * Metodos que estan relacionados con el swipe
     */


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

            //toast( "start = "+String.valueOf( e1.getX() )+" | end = "+String.valueOf( e2.getX() )  );
            //from left to right
            if (e2.getX() > e1.getX()) {
                if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    onSwipeRight();
                    return true;
                }
            }

            if (e1.getX() > e2.getX()) {
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
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


}
