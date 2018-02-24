package eus.mainu.mainu.Utilidades;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import eus.mainu.mainu.R;
import eus.mainu.mainu.datalayer.Plato;

/**
 * Created by narciso on 22/02/18.
 * Clase para adaptar los platos al listview
 */

public class ListViewAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Plato> platos;

    public ListViewAdapter(Context mContext, ArrayList<Plato> platos) {
        this.mContext = mContext;
        this.platos = platos;
    }

    @Override
    public int getCount() {
        return platos.size();
    }

    @Override
    public Object getItem(int position) {
        return platos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return platos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView lvPrimeros;
        RatingBar puntuacion;

        View view = View.inflate(mContext, R.layout.listview_platos,null);

        //Cogemos la referencia de los textviews
        lvPrimeros = view.findViewById(R.id.nombreTextView);
        puntuacion = view.findViewById(R.id.ratingBarEstrellitas);


        //Metemos dentro la informacion
        lvPrimeros.setText(platos.get(position).getNombre());
        puntuacion.setRating((float)platos.get(position).getValoracion());

        return view;
    }
}
