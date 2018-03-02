package eus.mainu.mainu.Utilidades;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import eus.mainu.mainu.R;
import eus.mainu.mainu.datalayer.Plato;

/**
 * Created by narciso on 25/02/18.
 * Clase para adaptar los platos al listview, en este momento no vale para nada, hace que la aplicacion cargue mas despacio
 */

public class PlatosListViewAdapter extends BaseAdapter {

    private static final String TAG = "Adaptador de Plato";

    private Context mContext;
    private ArrayList<Plato> platos;

    public PlatosListViewAdapter(Context mContext, ArrayList<Plato> platos) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = LayoutInflater.from(mContext).
                    inflate(R.layout.listview_platos, viewGroup, false);
        }

        // get current item to be displayed
        Plato plato = (Plato) getItem(i);

        // get the TextView for item name and item description
        TextView textView = view.findViewById(R.id.nombreTextView);
        RatingBar estrellitas = view.findViewById(R.id.ratingBarEstrellitas);
        LayerDrawable stars = (LayerDrawable) estrellitas.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#0E70B7"), PorterDuff.Mode.SRC_ATOP);

        //sets the text for item name and item description from the current item object
        textView.setText(plato.getNombre());
        estrellitas.setRating((float) plato.getPuntuacion());

        // returns the view for the current row
        return view;
    }

/*
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

    } */
}
