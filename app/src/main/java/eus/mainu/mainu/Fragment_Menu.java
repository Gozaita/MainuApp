package eus.mainu.mainu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import eus.mainu.mainu.Utilidades.HttpGetRequest;
import eus.mainu.mainu.Utilidades.Menu;
import eus.mainu.mainu.Utilidades.PlatosListViewAdapter;
import eus.mainu.mainu.datalayer.Plato;

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

        //Referenciamos los listViews
        lvPrimeros = view.findViewById(R.id.listaPrimeros);
        lvSegundos = view.findViewById(R.id.listaSegundos);
        lvPostres  = view.findViewById(R.id.listaPostres);

        //Indicamos que esta cargando mediante una animacion
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        //Creamos un objeto para hacer las peticiones get y para hacer los hilos
        HttpGetRequest request = new HttpGetRequest();

        if(request.isConnected(getContext()) ){
            Menu menu = request.getMenu();
            progressBar.setVisibility(View.GONE); //Una vez ha cargado, lo quitamos

            setListView(menu.getPrimeros(),lvPrimeros);
            setListView(menu.getPrimeros(),lvSegundos);
            setListView(menu.getPrimeros(),lvPostres);


        } else {

        }



        return view;
    }


    private void setListView(ArrayList<Plato> platos, ListView listView) {

        //Quitamos las divisiones
        listView.setDivider(null);

        //Adaptamos la informacion que va dentro de ellos (los nombres)
        PlatosListViewAdapter adaptador = new PlatosListViewAdapter(getContext(),platos);

        //Metemos dentro la informacion
        listView.setAdapter(adaptador);

    }


}
