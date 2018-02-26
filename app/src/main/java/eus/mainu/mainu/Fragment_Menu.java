package eus.mainu.mainu;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

        //Referenciamos los listViews
        lvPrimeros = view.findViewById(R.id.listaPrimeros);
        lvSegundos = view.findViewById(R.id.listaSegundos);
        lvPostres  = view.findViewById(R.id.listaPostres);

        ArrayList<String> nombrePrimeros = new ArrayList<String>();
        ArrayList<String> nombreSegundos = new ArrayList<String>();
        ArrayList<String> nombrePostres  = new ArrayList<String>();

        HttpGetRequest request = new HttpGetRequest();

        if(isConnected() ){
            Menu menu = request.getMenu();

            //Creo arrays de nombres de los platos para poder usar el inflador por defecto de listviews
            nombrePrimeros = menu.getNombrePrimeros();
            nombreSegundos = menu.getNombreSegundos();
            nombrePostres  = menu.getNombrePostres();
        } else {

        }

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

    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


}
