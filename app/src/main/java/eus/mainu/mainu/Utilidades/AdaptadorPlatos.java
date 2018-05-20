package eus.mainu.mainu.Utilidades;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import eus.mainu.mainu.ActivityElemento;
import eus.mainu.mainu.R;
import eus.mainu.mainu.datalayer.Plato;

/**
 * Created by narciso on 25/02/18.
 * Clase para adaptar los platos al listview, en este momento no vale para nada, hace que la aplicacion cargue mas despacio
 */

public class AdaptadorPlatos extends BaseAdapter {

    private final String TAG = "Adaptador de Plato";

    private Context mContext;
    private ArrayList<Plato> platos;

    public AdaptadorPlatos(Context mContext, ArrayList<Plato> platos) {
        this.mContext = mContext;
        this.platos = platos;
    }

    @Override
    public int getCount() {
        return platos.size();
    }

    @Override
    public Object getItem(int i) {
        return platos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = LayoutInflater.from(mContext).
                    inflate(R.layout.listview_platos, viewGroup, false);
        }

        // get current item to be displayed
        Plato plato = (Plato) getItem(i);

        // get the TextView for item name and item description
        TextView textView = view.findViewById(R.id.nombreTextView);
        RatingBar estrellitas = view.findViewById(R.id.ratingBarEstrellitas);

        //sets the text for item name and item description from the current item object
        textView.setText(plato.getNombre());
        estrellitas.setRating((float) plato.getPuntuacion());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Decimos que queremos navegar a la clase Elemento
                Intent intent = new Intent(mContext, ActivityElemento.class);
                //Le pasamos la informacion que necesita la clase
                intent.putExtra("Plato",platos.get(i));
                //Iniciamos la actividad
                mContext.startActivity(intent);
            }
        });
        // returns the view for the current row
        return view;
    }

}