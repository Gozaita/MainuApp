package eus.mainu.mainu;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;
import android.widget.TextView;

import eus.mainu.mainu.datalayer.Bocadillo;
import eus.mainu.mainu.datalayer.Complemento;

public class Activity_Elemento extends AppCompatActivity {

    private static final String TAG = "Activity Elemento";
    TextView nombre;
    TextView puntuacion;
    RatingBar ratingBar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__elemento);

        nombre = findViewById(R.id.textViewNombre);
        puntuacion = findViewById(R.id.textViewPuntuacion);
        ratingBar = findViewById(R.id.estrellitasElemento);
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
    }

    private void setBocadillo(Bocadillo bocadillo) {

        nombre.setText(bocadillo.getNombre());
        puntuacion.setText(Double.toString(bocadillo.getPuntuacion()));
        ratingBar.setRating((float) bocadillo.getPuntuacion());
    }

    private void setComplemento(Complemento complemento) {

        nombre.setText(complemento.getNombre());
        puntuacion.setText(Double.toString(complemento.getPuntuacion()));
        ratingBar.setRating((float) complemento.getPuntuacion());
    }

}
