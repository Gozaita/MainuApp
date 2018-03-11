package eus.mainu.mainu;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import eus.mainu.mainu.Utilidades.AdaptadorComentarios;
import eus.mainu.mainu.Utilidades.HttpGetRequest;
import eus.mainu.mainu.datalayer.Bocadillo;
import eus.mainu.mainu.datalayer.Complemento;
import eus.mainu.mainu.datalayer.Plato;
import eus.mainu.mainu.datalayer.Valoracion;

public class Activity_Elemento extends AppCompatActivity {

    private static final String TAG = "Activity Elemento";

    private TextView nombre;
    private TextView puntuacion;
    private TextView precio;
    private ImageButton imagen;
    private RatingBar ratingBar;
    private RecyclerView listaComentarios;

    ArrayList<Valoracion> arrayValoraciones = new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__elemento);


        nombre = findViewById(R.id.textViewNombre);
        puntuacion = findViewById(R.id.textViewPuntuacion);
        ratingBar = findViewById(R.id.estrellitasElemento);
        precio = findViewById(R.id.textViewPrecio);
        imagen = findViewById(R.id.botonImagenElemento);
        listaComentarios = findViewById(R.id.recycler_view_lista_comentarios);

        //Para que no influya en el scroll
        listaComentarios.setNestedScrollingEnabled(false);

        //Para poner las estrellas blancas mediante codigo,
        //Las ponemos mediante el xml, aunque solo funciona para la version de android 5.0 en adelante
        //LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        //stars.getDrawable(2).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        //Miramos la informacion que nos pasan
        getInformacion();

        //Mostramos las valoraciones en el recycling view
        setValoraciones();

    }

    //Metodo para recibir la informacion que se pasa a la actividad
    private void getInformacion() {

        //Vemos si la info es de un bocadillo
        if(getIntent().hasExtra("Bocadillo")){

            Bocadillo bocadillo = (Bocadillo) getIntent().getSerializableExtra("Bocadillo");
            setBocadillo(bocadillo);
        }
        //Vemos si la info es de un complemento
        if(getIntent().hasExtra("Complemento")){

            Complemento complemento = (Complemento) getIntent().getSerializableExtra("Complemento");
            setComplemento(complemento);

        }

        if(getIntent().hasExtra("Plato")){
            Plato plato = (Plato) getIntent().getSerializableExtra("Plato");
            setPlato(plato);

        }
    }

    private void setValoraciones(){

        //Inicializamos el adaptador de las valoraciones
        AdaptadorComentarios adapter = new AdaptadorComentarios(arrayValoraciones, this);

        listaComentarios.setFocusable(false );
        listaComentarios.setAdapter(adapter);
        listaComentarios.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setBocadillo(Bocadillo bocadillo) {

        HttpGetRequest request = new HttpGetRequest();
        Bocadillo nuevo = request.getBocadillo(bocadillo.getId());

        nombre.setText(bocadillo.getNombre());
        precio.setText(String.format("%.2f€",bocadillo.getPrecio()));

        //Metemos la nueva informacion en el bocadillo
        bocadillo.setFotos(nuevo.getFotos());
        bocadillo.setValoraciones(nuevo.getValoraciones());

        arrayValoraciones = bocadillo.getValoraciones();

        if(bocadillo.getFotos() != null){
            if(!bocadillo.getFotos().isEmpty()){
                Picasso.with(this)
                        .load(bocadillo.getFotos().get(0).getRuta())
                        .fit()
                        .centerCrop()
                        .into(imagen);
            }
        }

        if(bocadillo.getPuntuacion() != 0){
            puntuacion.setText(String.format("%.1f",bocadillo.getPuntuacion()));
            ratingBar.setRating((float) bocadillo.getPuntuacion());

        }
        else{
            puntuacion.setText("N/A");
        }
    }

    private void setComplemento(Complemento complemento) {

        HttpGetRequest request = new HttpGetRequest();
        Complemento nuevo = request.getComplemento(complemento.getId());

        nombre.setText(complemento.getNombre());
        precio.setText(String.format("%.2f€",complemento.getPrecio()));

        //Metemos la nueva informacion
        complemento.setFotos(nuevo.getFotos());
        complemento.getValoraciones();

        arrayValoraciones = complemento.getValoraciones();

        if(complemento.getFotos() != null){
            if(!complemento.getFotos().isEmpty()){
                Picasso.with(this)
                        .load(complemento.getFotos().get(0).getRuta())
                        .fit()
                        .centerCrop()
                        .into(imagen);
            }
       }

        if(complemento.getPuntuacion() != 0){
            puntuacion.setText(String.format("%.1f",complemento.getPuntuacion()));
            ratingBar.setRating((float) complemento.getPuntuacion());
        }
        else{
            puntuacion.setText("N/A");
        }

    }

    private void setPlato(Plato plato) {

        HttpGetRequest request = new HttpGetRequest();
        Plato nuevo = request.getPlato(plato.getId());

        nombre.setText(plato.getNombre());
        precio.setVisibility(View.GONE);

        //Metemos la nueva informacion
        plato.setFotos(nuevo.getFotos());
        plato.setValoraciones(nuevo.getValoraciones());

        arrayValoraciones = plato.getValoraciones();


        if(plato.getFotos() != null){
            if(!plato.getFotos().isEmpty()){
                Picasso.with(this)
                        .load(plato.getFotos().get(0).getRuta())
                        .fit()
                        .centerCrop()
                        .into(imagen);
            }
        }

        if(plato.getPuntuacion() != 0){
            puntuacion.setText(String.format("%.1f",plato.getPuntuacion()));
            ratingBar.setRating((float) plato.getPuntuacion());
        }
        else{
            puntuacion.setText("N/A");
        }

    }

}
