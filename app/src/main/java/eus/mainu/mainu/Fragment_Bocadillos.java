package eus.mainu.mainu;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import eus.mainu.mainu.Utilidades.HttpGetRequest;
import eus.mainu.mainu.Utilidades.RecyclerViewAdapter;
import eus.mainu.mainu.datalayer.Bocadillo;

//Clase del fragmento responsable de visualizar los bocadillos
public class Fragment_Bocadillos extends Fragment{


    private static final String TAG = "Bocadillos";

    //private TextView titulo;
    private Context mContext;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Bocadillo> arrayBocadillos =new ArrayList<Bocadillo>();
    private boolean actualizado = false;
    private android.support.v7.widget.RecyclerView recyclerView;



    //Metodo que se ejecuta al visualizar el fragmento, se representa en funcion del layout fragment_bocadillos
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bocadillos, container, false);

        // ATENCION ERROR Attempt to invoke virtual method 'void android.widget.TextView.setText(java.lang.CharSequence)' on a null object reference
        //Ponemos el titulo en la toolbat
        //titulo = view.findViewById(R.id.textViewActividad);
        //titulo.setText(getString(R.string.bocadillos));

        //Cogemos el contexto
        mContext = view.getContext();

        //Cogemos el recycling view
        recyclerView = view.findViewById(R.id.recycler_view_lista_bocadillos);


        //Creamos un objeto para hacer las peticiones get y para hacer los hilos
        HttpGetRequest request = new HttpGetRequest();


        //Comprobamos si hay conexion para hacer las peticiones de los arrays
        if(request.isConnected(mContext) ){

            setBocadillos(request);

        }

        //Ponemos escuchando el SwipeToRefresh
        swipeRefreshLayout = view.findViewById(R.id.swipeBocadillos);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            //Accion que se ejecuta cuando se activa
            @Override
            public void onRefresh() {

                //Creamos otro request porque solo se puede llamar al asynctask una vez
                HttpGetRequest request = new HttpGetRequest();

                //Chequeamos si tenemos el menu actualizado
                //request.checkMenuActualizados();


                if(request.isConnected(mContext) && arrayBocadillos.isEmpty()){
                    setBocadillos(request);
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


    //Clase para crear y adaptar la informacion al recycling view
    private void setBocadillos(HttpGetRequest request){



        arrayBocadillos = request.getBocadillos();

        /*
        AdministradorDeCache cache = new AdministradorDeCache();
        //Copia de seguridad de los datos
        ArrayList<Bocadillo> a = arrayBocadillos;
        //Guardamos en cache
        cache.guardarArrayBocadillos(mContext,"arrayB",arrayBocadillos);
        //Leemos lo que acabamos de guardar
        arrayBocadillos = (ArrayList<Bocadillo>) cache.leeObjetoCache(mContext, "arrayB");
        if(arrayBocadillos != null) {
            Log.d("Es null","NO HA LEIDO NADA");
            arrayBocadillos = a;
        }
        else{
            Log.d("BIEN","HA LEIDO ALGO");
        }*/

        //Creamos el objeto de la clase adaptador
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(arrayBocadillos, getActivity());

        //Adaptamos el recyclingview
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }



}
