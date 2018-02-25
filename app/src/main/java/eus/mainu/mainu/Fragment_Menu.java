package eus.mainu.mainu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import eus.mainu.mainu.Utilidades.HttpGetRequest;
import eus.mainu.mainu.Utilidades.Menu;

public class Fragment_Menu extends Fragment {

    private TextView titulo;
    private ListView lvPrimeros;
    private ListView lvSegundos;
    private ListView lvPostres;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        // ATENCION ERROR Attempt to invoke virtual method 'void android.widget.TextView.setText(java.lang.CharSequence)' on a null object reference
        //Ponemos el titulo en la toolbat
        //titulo = view.findViewById(R.id.textViewActividad);
        //titulo.setText(getString(R.string.menuDelDia));

        HttpGetRequest request = new HttpGetRequest();

        Menu menu = request.getMenu();

        //Referenciamos los listViews
        lvPrimeros = view.findViewById(R.id.listaPrimeros);
        lvSegundos = view.findViewById(R.id.listaSegundos);
        lvPostres = view.findViewById(R.id.listaPostres);


        //Creo arrays de nombres de los platos para poder usar el inflador por defecto de listviews
        ArrayList<String> nombrePrimeros = menu.getNombrePrimeros();
        ArrayList<String> nombreSegundos = menu.getNombreSegundos();
        ArrayList<String> nombrePostres = menu.getNombrePostres();

        //Adaptamos los listviews
        setListView(nombrePrimeros, lvPrimeros);
        setListView(nombreSegundos, lvSegundos);
        setListView(nombrePostres, lvPostres);


        return view;
    }


    private void setListView(ArrayList<String> listaPlatos, ListView listView) {
        //Quitamos las divisiones
        listView.setDivider(null);

        //Adaptamos la informacion que va dentro de ellos (los nombres)
        ArrayAdapter<String> arrayAdapterPrimeros = new ArrayAdapter<String>(getActivity(), R.layout.listview_platos, R.id.nombreTextView, listaPlatos);

        //Metemos dentro la informacion
        listView.setAdapter(arrayAdapterPrimeros);

    }





}
