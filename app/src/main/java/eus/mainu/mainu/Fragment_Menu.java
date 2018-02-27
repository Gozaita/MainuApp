package eus.mainu.mainu;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import eus.mainu.mainu.Utilidades.HttpGetRequest;
import eus.mainu.mainu.Utilidades.Menu;
import eus.mainu.mainu.Utilidades.PlatosListViewAdapter;
import eus.mainu.mainu.datalayer.Plato;

public class Fragment_Menu extends Fragment {

    //private TextView titulo;
    private Context mContext;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView lvPrimeros;
    private ListView lvSegundos;
    private ListView lvPostres;
    private Menu menu = new Menu();
    private boolean actualizado = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);


        // ATENCION ERROR Attempt to invoke virtual method 'void android.widget.TextView.setText(java.lang.CharSequence)' on a null object reference
        //Ponemos el titulo en la toolbat
        //titulo = view.findViewById(R.id.textViewActividad);
        //titulo.setText(getString(R.string.menuDelDia));

        //Cogemos el contexto
        mContext = view.getContext();

        //Referenciamos los listViews
        lvPrimeros = view.findViewById(R.id.listaPrimeros);
        lvSegundos = view.findViewById(R.id.listaSegundos);
        lvPostres  = view.findViewById(R.id.listaPostres);


        //Creamos un objeto para hacer las peticiones get y para hacer los hilos
        HttpGetRequest request = new HttpGetRequest();

        //Comprobamos si hay conexion para hacer las peticiones de los arrays
        if(request.isConnected(mContext) ){

            setListView(request);

        }

        //Ponemos escuchando el SwipeToRefresh
        swipeRefreshLayout = view.findViewById(R.id.swipeMenu);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            //Accion que se ejecuta cuando se activa
            @Override
            public void onRefresh() {

                //Creamos otro request porque solo se puede llamar al asynctask una vez
                HttpGetRequest request = new HttpGetRequest();

                //Chequeamos si tenemos el menu actualizado
                //request.checkMenuActualizados();


                if(request.isConnected(mContext) && menu.getNombrePrimeros().isEmpty()){
                    setListView(request);
                }

                //Esto es para ejecutar un hilo que se encarga de hacer la accion, creo
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);   //Tiempo en ms durante el cual se muestra el icono de refresh
            }
        });


        return view;
    }

    //Metodo para hacer la peticion de los bocadillos e inflar la vista
    public void setListView(HttpGetRequest request){

        menu = request.getMenu();

        inflaListView(menu.getPrimeros(),lvPrimeros);
        inflaListView(menu.getSegundos(),lvSegundos);
        inflaListView(menu.getPostres(),lvPostres);

    }

    private void inflaListView(ArrayList<Plato> platos, ListView listView) {

        //Quitamos las divisiones
        listView.setDivider(null);

        //Adaptamos la informacion que va dentro de ellos (los nombres)
        PlatosListViewAdapter adaptador = new PlatosListViewAdapter(getContext(),platos);

        //Metemos dentro la informacion
        listView.setAdapter(adaptador);

    }


}
