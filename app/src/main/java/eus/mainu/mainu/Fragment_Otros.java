package eus.mainu.mainu;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import eus.mainu.mainu.Utilidades.HttpGetRequest;
import eus.mainu.mainu.Utilidades.RecyclingViewCardAdapter;
import eus.mainu.mainu.datalayer.Complemento;

public class Fragment_Otros extends Fragment{

    private static final String TAG = "Otros";
    private static final int NUM_COLUMNS = 2;   //Numero de columnas del cardview

    //Elementos de la vista
    private Context mContext;
    private SwipeRefreshLayout swipeRefreshLayout;
    private android.support.v7.widget.RecyclerView recyclerView;

    //Variables
    ArrayList<Complemento> arrayComplementos = new ArrayList<Complemento>();
    private boolean actualizado = false;


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
            arrayComplementos = request.getOtros();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_otros, container, false);

        //Cogemos el RecyclingView
        recyclerView = view.findViewById(R.id.recycler_view_otros);
        //Ponemos escuchando el SwipeToRefresh
        swipeRefreshLayout = view.findViewById(R.id.swipeComplementos);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        //Inflamos la vista
        setOtros();

        //Escuchamos un posible swipe to refresh
        escuchamosSwipe();

        return view;
    }

    //Metodo para rellenar el cardview
    private void setOtros(){

        //Primero creamos el objeto de la clase adaptador
        RecyclingViewCardAdapter recyclingViewCardAdapter = new RecyclingViewCardAdapter(getActivity(),arrayComplementos);

        //Creamos el objeto de la clase que nos lo va a poner en dos columnas
        StaggeredGridLayoutManager recyclingViewCardAdapterManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);

        //Adaptamos las columnas
        recyclerView.setLayoutManager(recyclingViewCardAdapterManager);

        //Adaptamos el recyclingView
        recyclerView.setAdapter(recyclingViewCardAdapter);

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


                if(request.isConnected(mContext) && arrayComplementos.isEmpty()){
                    arrayComplementos = request.getOtros();
                    setOtros();
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
