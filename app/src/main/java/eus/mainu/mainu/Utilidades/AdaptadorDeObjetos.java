package eus.mainu.mainu.Utilidades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import eus.mainu.mainu.R;
import eus.mainu.mainu.datalayer.Bocadillo;

/**
 * Created by narciso on 9/02/18.
 *
 * Clase para adaptar los listados en listViews
 */

public class AdaptadorDeObjetos extends BaseAdapter {

    LayoutInflater mInflator; //la m de mInflator es porque es miembro de la clase
    ArrayList<Bocadillo> arrayBocadillos;


    //Contructor de la clase
    public AdaptadorDeObjetos(Context c, ArrayList<Bocadillo> arrayBocadillos){
        this.arrayBocadillos = arrayBocadillos;
        mInflator = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayBocadillos.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayBocadillos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = mInflator.inflate(R.layout.listview_bocadillos,null);
        TextView nombreTextView = (TextView) v.findViewById(R.id.nombreTextView);
        TextView descripcionTextView = (TextView) v.findViewById(R.id.descripcionTextView);
        TextView precioTextView = (TextView) v.findViewById(R.id.precioTextView);

        Bocadillo bocadillo = arrayBocadillos.get(i);

        String nom = bocadillo.getNombre();
        String des = bocadillo.getDescripcion();
        String pre = Float.toString(bocadillo.getPrecio());

        nombreTextView.setText(nom);
        descripcionTextView.setText(des);
        precioTextView.setText(pre);

        return v;
    }

}
