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
    TextView textView;
    RatingBar ratingBar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__elemento);

        textView = findViewById(R.id.textViewNombre);
        ratingBar = findViewById(R.id.estrellitasElemento);
        //Para poner las estrellas blancas
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        //Miramos la informacion que nos pasan
        getInformacion();
    }

    //Metodo para recibir la informacion que se pasa a la actividad
    private void getInformacion() {

        //Vemos si la info es de bocadillos
        if(getIntent().hasExtra("Bocadillo")){

            Bocadillo bocadillo = (Bocadillo) getIntent().getSerializableExtra("Bocadillo");
            setBocadillo(bocadillo);
        }
        //Vemos si la info es de complementos
        if(getIntent().hasExtra("Complemento")){

            Complemento complemento = (Complemento) getIntent().getSerializableExtra("Complemento");
            setComplemento(complemento);

        }
    }

    private void setBocadillo(Bocadillo bocadillo) {

        textView.setText(bocadillo.getNombre());
        ratingBar.setRating(3.5f);
    }

    private void setComplemento(Complemento complemento) {

        textView.setText(complemento.getNombre());
        ratingBar.setRating(3.5f);
    }

}
