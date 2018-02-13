package eus.mainu.mainu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by narciso on 9/02/18.
 *
 * Clase para adaptar los listados en listViews
 */

public class AdaptadorDeObjetos extends BaseAdapter {

    LayoutInflater mInflator; //la m de mInflator es porque es miembro de la clase
    String[] bocadillos;
    String[] descripcion;
    String[] precios;

    //Contructo de la clase
    public AdaptadorDeObjetos(Context c, String[] b, String[] d, String[] p){
        bocadillos = b;
        descripcion = d;
        precios = p;
        mInflator = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return bocadillos.length;
    }

    @Override
    public Object getItem(int i) {
        return bocadillos[i];
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

        String nom = bocadillos[i];
        String des = descripcion[i];
        String pre = precios[i];

        nombreTextView.setText(nom);
        descripcionTextView.setText(des);
        precioTextView.setText(pre);

        return v;
    }
}
