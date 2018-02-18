package eus.mainu.mainu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by narciso on 17/02/18.
 * Clase infladora del fragmento de bocadillos
 */

public class MenuFragment extends Fragment{


    private ListView lvPrimeros;
    private ListView lvSegundos;
    private ListView lvPostres;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        setMenu(view);

        return view;
    }

    private void setMenu(View view){

        //Cogemos la referencia de los textviews
        lvPrimeros = view.findViewById(R.id.listaPrimeros);
        lvSegundos = view.findViewById(R.id.listaSegundos);
        lvPostres = view.findViewById(R.id.listaPostres);

        //Quita el borde gris de cada listview item
        lvPrimeros.setDivider(null);
        lvSegundos.setDivider(null);
        lvPostres.setDivider(null);

        //Adaptamos la informacion que va dentro de ellos
        ArrayAdapter<String> ad = new ArrayAdapter<>(getActivity(), R.layout.textcenter, R.id.textItem, new String[]{"Lentejas","Cocido","Ensalada de pollo"} );
        lvPrimeros.setAdapter(ad);
        //ArrayAdapter<String> arrayAdapterPrimeros = new ArrayAdapter<>(this, R.layout.listview_platos, R.id.nombreTextView, new String[]{"Lentejas","Cocido","Ensalada de pollo"});
        ArrayAdapter<String> arrayAdapterSegundos = new ArrayAdapter<>(getActivity(), R.layout.listview_platos, R.id.nombreTextView, new String[]{"Albondigas","Tortilla Francesa","Arraingorri"});
        ArrayAdapter<String> arrayAdapterPostres = new ArrayAdapter<>(getActivity(), R.layout.listview_platos, R.id.nombreTextView, new String[]{"Helado"});

        //Metemos dentro la informacion
        //lvPrimeros.setAdapter(arrayAdapterPrimeros);
        lvSegundos.setAdapter(arrayAdapterSegundos);
        lvPostres.setAdapter(arrayAdapterPostres);
    }

}
