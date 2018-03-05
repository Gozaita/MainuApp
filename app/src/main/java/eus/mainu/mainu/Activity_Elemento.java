package eus.mainu.mainu;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import eus.mainu.mainu.datalayer.Bocadillo;
import eus.mainu.mainu.datalayer.Complemento;
import eus.mainu.mainu.datalayer.Plato;

public class Activity_Elemento extends AppCompatActivity {

    private static final String TAG = "Activity Elemento";

    private TextView nombre;
    private TextView puntuacion;
    private TextView precio;
    private ImageButton imagen;
    private RatingBar ratingBar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__elemento);


        nombre = findViewById(R.id.textViewNombre);
        puntuacion = findViewById(R.id.textViewPuntuacion);
        ratingBar = findViewById(R.id.estrellitasElemento);
        precio = findViewById(R.id.textViewPrecio);
        imagen = findViewById(R.id.botonImagenElemento);
        //Para poner las estrellas blancas
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        //Miramos la informacion que nos pasan
        getInformacion();
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

    private void setBocadillo(Bocadillo bocadillo) {

        nombre.setText(bocadillo.getNombre());
        precio.setText(String.format("%.2f€",bocadillo.getPrecio()));

        if(bocadillo.getFotos() != null){
            Picasso.with(this)
                    .load(bocadillo.getFotos().get(0).getRuta())
                    .fit()
                    .centerCrop()
                    .into(imagen);        }

        if(bocadillo.getPuntuacion() != 0){
            puntuacion.setText(String.format("%.1f",bocadillo.getPuntuacion()));
            ratingBar.setRating((float) bocadillo.getPuntuacion());

        }
        else{
            puntuacion.setText("N/A");
        }
    }

    private void setComplemento(Complemento complemento) {

        nombre.setText(complemento.getNombre());
        precio.setText(String.format("%.2f€",complemento.getPrecio()));

        if(complemento.getFotos() != null){
            Picasso.with(this)
                    .load(complemento.getFotos().get(0).getRuta())
                    .fit()
                    .centerCrop()
                    .into(imagen);        }

        if(complemento.getPuntuacion() != 0){
            puntuacion.setText(String.format("%.1f",complemento.getPuntuacion()));
            ratingBar.setRating((float) complemento.getPuntuacion());
        }
        else{
            puntuacion.setText("N/A");
        }

    }

    private void setPlato(Plato plato) {

        nombre.setText(plato.getNombre());
        precio.setVisibility(View.GONE);

        if(plato.getFotos() != null){
            Picasso.with(this)
                    .load(plato.getFotos().get(0).getRuta())
                    .fit()
                    .centerCrop()
                    .into(imagen);
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
