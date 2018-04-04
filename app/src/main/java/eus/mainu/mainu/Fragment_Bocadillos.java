package eus.mainu.mainu;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import eus.mainu.mainu.Utilidades.Administrador_Cache;
import eus.mainu.mainu.Utilidades.HttpGetRequest;
import eus.mainu.mainu.Utilidades.Adaptador_Bocadillos;
import eus.mainu.mainu.datalayer.Bocadillo;

//Clase del fragmento responsable de visualizar los bocadillos
public class Fragment_Bocadillos extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    private final String TAG = "Bocadillos";

    //Elementos Layout
    private Context mContext;
    private SwipeRefreshLayout swipeRefreshLayout;
    private android.support.v7.widget.RecyclerView recyclerView;

    //Variables
    private ArrayList<Bocadillo> arrayBocadillos = new ArrayList<>();

    public Fragment_Bocadillos(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: Inicia Fragment Bocadillos");

        mContext = getContext();
        //administraPeticionesCacheBocadillos();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
             arrayBocadillos = (ArrayList<Bocadillo>) bundle.getSerializable("listaBocadillos");
        }
    }

    //**********************************************************************************************
    private void administraPeticionesCacheBocadillos(){
        //Cada request se puede usar una vez
        HttpGetRequest request1 = new HttpGetRequest();
        HttpGetRequest request2 = new HttpGetRequest();

        //Comprobamos si hay conexion para hacer las peticiones de los arrays
        Administrador_Cache cache = new Administrador_Cache();
        boolean usarCache = false;

        if(request1.isConnected(mContext) ){
            String remoteLastUpdate = request1.getLastUpdate("bocadillos");
            String localLastUpdate  = cache.leerLastUpdate( mContext, "bocadillos");

            if(!remoteLastUpdate.equalsIgnoreCase(localLastUpdate) ){
                arrayBocadillos = request2.getBocadillos();
                cache.guardarLastUpdate(mContext, "bocadillos", remoteLastUpdate);
                cache.guardarListaBocadillos(mContext, arrayBocadillos);
            } else{
                usarCache = true;
            }
        } else{ //Si no hay internet, uso la cache
            usarCache = true;
        }
        if(usarCache) {
            arrayBocadillos = (ArrayList<Bocadillo>) cache.leerListaBocadillos(mContext);
        }

    }

    //Metodo que se ejecuta al visualizar el fragmento, se representa en funcion del layout fragment_bocadillos
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bocadillos, container, false);

        //Cogemos el recycling view
        recyclerView = view.findViewById(R.id.recycler_view_lista_bocadillos);

        //Añadimos una linea debajo de cada objeto
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mContext, new LinearLayoutManager(mContext).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        //Ponemos el SwipeToRefresh
        swipeRefreshLayout = view.findViewById(R.id.swipeBocadillos);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        //Inflamos la vista
        setBocadillos();
        escuchamosSwipe();


        return view;
    }

    //**********************************************************************************************
    //Clase para crear y adaptar la informacion al recycling view
    private void setBocadillos(){
        //Creamos el objeto de la clase adaptador
        Adaptador_Bocadillos adapter = new Adaptador_Bocadillos(arrayBocadillos, getActivity());

        //Adaptamos el recyclingview
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    //Metodo para definir la accion del swipe to refresh
    private void escuchamosSwipe() {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            //Accion que se ejecuta cuando se activa
            @Override
            public void onRefresh() {
                //Creamos otro request porque solo se puede llamar al asynctask una vez
                HttpGetRequest request = new HttpGetRequest();

                //Chequeamos si tenemos el menu actualizado
                //request.checkMenuActualizados();

                if(request.isConnected(mContext) && arrayBocadillos.isEmpty()){
                    arrayBocadillos = request.getBocadillos();
                    setBocadillos();
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
    }

    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.activity__principal, menu);
        MenuItem itemBusqueda = menu.findItem(R.id.buscar);
        SearchView searchView = searchI
    }*/

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText != null || newText.trim().isEmpty()) {
            resetLista();
            return false;
        }

        ArrayList<Bocadillo> listaFiltrada = new ArrayList<>();
        for (Bocadillo bocadillo : arrayBocadillos) {
            if (bocadillo.getNombre().contains(newText)) {
                listaFiltrada.add(bocadillo);
            }
        }

        Adaptador_Bocadillos adapter = new Adaptador_Bocadillos(listaFiltrada, getActivity());
        //Adaptamos el recyclingview
        recyclerView.setAdapter(adapter);

        return false;
    }

    private void resetLista(){
        //Creamos el objeto de la clase adaptador
        Adaptador_Bocadillos adapter = new Adaptador_Bocadillos(arrayBocadillos, getActivity());
        //Adaptamos el recyclingview
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem menuItem) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
        return true;
    }
}
