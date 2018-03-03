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

    //Elementos Layout
    private Context mContext;
    private SwipeRefreshLayout swipeRefreshLayout;
    private android.support.v7.widget.RecyclerView recyclerView;

    //Variables
    private ArrayList<Bocadillo> arrayBocadillos =new ArrayList<Bocadillo>();
    private boolean actualizado = false;

    //Metodo que se llama antes de onCreateView, se suelen coger las variables aqui
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Cogemos el contexto
        mContext = getContext();
        //Creamos un objeto para hacer las peticiones get y para hacer los hilos
        HttpGetRequest request = new HttpGetRequest();
        //Comprobamos si hay conexion para hacer las peticiones de los arrays
        if(request.isConnected(mContext) ){
            //Pedimos los complementos a la API
            arrayBocadillos = request.getBocadillos();
        }
    }

    //Metodo que se ejecuta al visualizar el fragmento, se representa en funcion del layout fragment_bocadillos
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bocadillos, container, false);

        //Cogemos el recycling view
        recyclerView = view.findViewById(R.id.recycler_view_lista_bocadillos);
        //Ponemos el SwipeToRefresh
        swipeRefreshLayout = view.findViewById(R.id.swipeBocadillos);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        //Inflamos la vista
        setBocadillos();

        //Escuchamos un posible swipe to refresh
        escuchamosSwipe();

        return view;
    }


    //Clase para crear y adaptar la informacion al recycling view
    private void setBocadillos(){

        //Creamos el objeto de la clase adaptador
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(arrayBocadillos, getActivity());

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
