package eus.mainu.mainu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import eus.mainu.mainu.Utilidades.Adaptador_Comentarios;
import eus.mainu.mainu.Utilidades.HttpGetRequest;
import eus.mainu.mainu.datalayer.Bocadillo;
import eus.mainu.mainu.datalayer.Complemento;
import eus.mainu.mainu.datalayer.Plato;
import eus.mainu.mainu.datalayer.Valoracion;

public class Activity_Elemento extends AppCompatActivity {

    private static final String TAG = "Activity Elemento";
    private static final int CAMERA_REQUEST_CODE = 5;

    private TextView nombre;
    private TextView puntuacion;
    private TextView precio;
    private ImageButton imagen;
    private ImageButton botonCamara;
    private RatingBar ratingBar;
    private RecyclerView listaComentarios;
    private EditText comentario;


    ArrayList<Valoracion> arrayValoraciones = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elemento);

        //Para swype back
        gestureDetector = new GestureDetector(this, new SwipeDetector());

        nombre = findViewById(R.id.textViewNombre);
        puntuacion = findViewById(R.id.textViewPuntuacion);
        ratingBar = findViewById(R.id.estrellitasElemento);
        precio = findViewById(R.id.textViewPrecio);
        imagen = findViewById(R.id.botonImagenElemento);
        listaComentarios = findViewById(R.id.recycler_view_lista_comentarios);
        comentario = findViewById(R.id.editText);
        botonCamara = findViewById(R.id.botonCamara);


        //Para que no influya en el scroll
        listaComentarios.setNestedScrollingEnabled(false);

        //Para que no se muestre seleccionado al entrar en la actividad
        comentario.setCursorVisible(false);

        //Miramos la informacion que nos pasan
        getInformacion();

        //Mostramos las valoraciones en el recycling view
        setValoraciones();

        //Todo el codigo que teniene que ver con el boton la camara
        setBotonCamara();


        //Para poner las estrellas blancas mediante codigo,
        //Las ponemos mediante el xml, aunque solo funciona para la version de android 5.0 en adelante
        //LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        //stars.getDrawable(2).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

    }

    //Metodo para inflar el recyclingview de los comentarios
    private void setValoraciones() {

        //Inicializamos el adaptador de las valoraciones
        Adaptador_Comentarios adapter = new Adaptador_Comentarios(arrayValoraciones, this);

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

        //Vemos si la info es de un bocadillo
        if (getIntent().hasExtra("Bocadillo")) {
            Bocadillo bocadillo = (Bocadillo) getIntent().getSerializableExtra("Bocadillo");
            setBocadillo(bocadillo);
        }
        //Vemos si la info es de un complemento
        if (getIntent().hasExtra("Complemento")) {

            Complemento complemento = (Complemento) getIntent().getSerializableExtra("Complemento");
            setComplemento(complemento);
        }
        //Vemos si es un plato
        if (getIntent().hasExtra("Plato")) {
            Plato plato = (Plato) getIntent().getSerializableExtra("Plato");
            setPlato(plato);

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

        if (bocadillo.getFotos() != null) {
            if (!bocadillo.getFotos().isEmpty()) {
                Picasso.with(this)
                        .load(bocadillo.getFotos().get(0).getRuta())
                        .fit()
                        .centerCrop()
                        .into(imagen);
            }
        }

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

        if (complemento.getFotos() != null) {
            if (!complemento.getFotos().isEmpty()) {
                Picasso.with(this)
                        .load(complemento.getFotos().get(0).getRuta())
                        .fit()
                        .centerCrop()
                        .into(imagen);
            }
        }

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


        if (plato.getFotos() != null) {
            if (!plato.getFotos().isEmpty()) {
                Picasso.with(this)
                        .load(plato.getFotos().get(0).getRuta())
                        .fit()
                        .centerCrop()
                        .into(imagen);
            }
        }

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
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Comprobamos que se ha sacado la foto con el result_ok, comprobamos que es el codigo de la camara al que llaman
        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_REQUEST_CODE) {
            Log.d(TAG, "onActivityResult: done taking a photo.");
            Log.d(TAG, "onActivityResult: attempting to navigate to final share screen.");

            Bitmap bitmap;
            bitmap = (Bitmap) data.getExtras().get("data");

            if (bitmap != null) {
                Toast.makeText(this, R.string.agradecimiento, Toast.LENGTH_LONG).show();
                imagen.setImageBitmap(bitmap);
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
