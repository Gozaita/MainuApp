package eus.mainu.mainu;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import eus.mainu.mainu.Utilidades.Administrador_Cache;
import eus.mainu.mainu.Utilidades.HttpGetRequest;
import eus.mainu.mainu.Utilidades.Adaptador_Bocadillos;
import eus.mainu.mainu.datalayer.Bocadillo;

//Clase del fragmento responsable de visualizar los bocadillos
public class Fragment_Bocadillos extends Fragment {
    private static final String TAG = "Bocadillos";

    //Elementos Layout
    private Context mContext;
    private SwipeRefreshLayout swipeRefreshLayout;
    private android.support.v7.widget.RecyclerView recyclerView;

    //Variables
    private ArrayList<Bocadillo> arrayBocadillos = new ArrayList<Bocadillo>();
    Adaptador_Bocadillos adapter;
    private boolean actualizado = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getContext();
        administraPeticionesCacheBocadillos();
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
    private void administraPeticionesCacheBocadillos(){
        //Cada request se puede usar una vez
        HttpGetRequest request1 = new HttpGetRequest();
        HttpGetRequest request2 = new HttpGetRequest();

        Administrador_Cache cache = new Administrador_Cache();
        Object cacheLastUpdate = cache.leerLastUpdate(mContext,"bocadillos");

        //Comprueba si hay conexion
        if(request1.isConnected(mContext)){
            try {
                //Comprueba si la cache esta actualizada
                String remoteLastUpdate = request1.getLastUpdate("bocadillos");

                //Comprobamos si existe cache
                if(cacheLastUpdate != null){
                    DateFormat formato = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss", Locale.ENGLISH);
                    Date fechaServidor = formato.parse(remoteLastUpdate);
                    Date fechaCache = formato.parse(cacheLastUpdate.toString());

                    //Comprobamos la fecha del servidor
                    if(fechaServidor.after(fechaCache)){
                        arrayBocadillos = request2.getBocadillos();
                    } else {
                        arrayBocadillos = (ArrayList<Bocadillo>) cache.leerListaBocadillos( mContext);
                    }

                }else{
                    //Leemos los bocadillos de la api
                    arrayBocadillos = request2.getBocadillos();
                    //Guardamos en cache
                    cache.guardarLastUpdate(mContext, "bocadillos", remoteLastUpdate);
                    cache.guardarListaBocadillos(mContext, arrayBocadillos);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        //Si no hay conexion, leemos de cache
        } else {
            //Si no existe cache, no leemos
            if(cacheLastUpdate != null) {
                arrayBocadillos = (ArrayList<Bocadillo>) cache.leerListaBocadillos( mContext);
            }
        }

        /*
        //Comprobamos si hay conexion para hacer las peticiones de los arrays
        Administrador_Cache cache = new Administrador_Cache();
        boolean usarCache = false;
        if(request1.isConnected(mContext) ){

            String remoteLastUpdate = request1.getLastUpdate("bocadillos");
            String localLastUpdate  = "";
            if(cache.leerLastUpdate( mContext, "bocadillos") != null){
                localLastUpdate = cache.leerLastUpdate( mContext, "bocadillos").toString();
            }

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
        if(usarCache)
            arrayBocadillos = (ArrayList<Bocadillo>) cache.leerListaBocadillos( mContext);
            */
    }

    //**********************************************************************************************
    //Clase para crear y adaptar la informacion al recycling view
    private void setBocadillos(){
        //Creamos el objeto de la clase adaptador
        adapter = new Adaptador_Bocadillos(arrayBocadillos, getActivity());

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



}
