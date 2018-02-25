package eus.mainu.mainu.Utilidades;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import eus.mainu.mainu.R;
import eus.mainu.mainu.datalayer.Plato;

/**
 * Created by narciso on 25/02/18.
 * Clase para adaptar los platos al listview, en este momento no vale para nada, hace que la aplicacion cargue mas despacio
 */

public class PlatosListViewAdapter extends ArrayAdapter<Plato> {

    private static final String TAG = "Adaptador de Plato";

    private Context mContext;
    private int mResource;


    public PlatosListViewAdapter(@NonNull Context context, int resource, @NonNull List<Plato> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //Cogemos la informacion de cada plato
        String nombre = getItem(position).getNombre();
        double puntuacion = getItem(position).getValoracion();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent,false);

        TextView textView = convertView.findViewById(R.id.nombreTextView);
        RatingBar estrellita = convertView.findViewById(R.id.ratingBarEstrellitas);

        textView.setText(nombre);
        estrellita.setRating((float)puntuacion);


        return convertView;

    }
}
