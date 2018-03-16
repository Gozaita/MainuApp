package eus.mainu.mainu;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import eus.mainu.mainu.Utilidades.Administrador_Cache;
import eus.mainu.mainu.Utilidades.HttpGetRequest;
import eus.mainu.mainu.Utilidades.Adaptador_Otros;
import eus.mainu.mainu.datalayer.Complemento;

public class Fragment_Otros extends Fragment{

    private static final String TAG = "Otros";
    private static final int NUM_COLUMNS = 2;   //Numero de columnas del cardview

    //Elementos de la vista
    private Context mContext;
    private SwipeRefreshLayout swipeRefreshLayout;
    private android.support.v7.widget.RecyclerView rec1, rec2, rec3, rec4, rec5;

    //Arrays
    ArrayList<Complemento> arrayComplementos = new ArrayList<Complemento>();
    ArrayList<Complemento> arrayBebidasCalientes = new ArrayList<Complemento>();
    ArrayList<Complemento> arrayBebidasFrias = new ArrayList<Complemento>();
    ArrayList<Complemento> arrayTostasPizzas = new ArrayList<Complemento>();
    ArrayList<Complemento> arrayBolleria = new ArrayList<>();
    ArrayList<Complemento> arrayMas = new ArrayList<Complemento>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getContext();
        administraPeticionesCacheOtros();
        clasifica();
    }

    //Clasificamos el arrayComplementos en 5 categorias
    private void clasifica(){
        for(Complemento comp : arrayComplementos){
            switch (comp.getTipo()) {
                case 1:
                    arrayTostasPizzas.add(comp);
                    break;
                case 2:
                    arrayBolleria.add(comp);
                    break;
                case 3:
                    arrayBebidasCalientes.add(comp);
                    break;
                case 4:
                    arrayBebidasFrias.add(comp);
                    break;
                default:
                    arrayMas.add(comp);
                    break;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_otros, container, false);

        //Cogemos los  RecyclingViews
        rec1 = view.findViewById(R.id.rec1);
        rec2 = view.findViewById(R.id.rec2);
        rec3 = view.findViewById(R.id.rec3);
        rec4 = view.findViewById(R.id.rec4);
        rec5 = view.findViewById(R.id.rec5);

        //Ponemos escuchando el SwipeToRefresh
        swipeRefreshLayout = view.findViewById(R.id.swipeComplementos);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        //Quitamos el scroll en ellos
        rec1.setNestedScrollingEnabled(false);
        rec2.setNestedScrollingEnabled(false);
        rec3.setNestedScrollingEnabled(false);
        rec4.setNestedScrollingEnabled(false);
        rec5.setNestedScrollingEnabled(false);


        //Inflamos la vista
        setOtros();

        //Escuchamos un posible swipe to refresh
        escuchamosSwipe();

        return view;
    }


    //**********************************************************************************************
    private void administraPeticionesCacheOtros(){

        //Cada request se puede usar una vez
        HttpGetRequest request1 = new HttpGetRequest();
        HttpGetRequest request2 = new HttpGetRequest();

        //Comprobamos si hay conexion para hacer las peticiones de los arrays
        Administrador_Cache cache = new Administrador_Cache();
        boolean usarCache = false;
        if(request1.isConnected(mContext) ){

            String remoteLastUpdate = request1.getLastUpdate("otros");
            String localLastUpdate  = cache.leerLastUpdate( mContext, "bocadillos");

            if(!remoteLastUpdate.equalsIgnoreCase(localLastUpdate) ){
                arrayComplementos = request2.getOtros();
                cache.guardarLastUpdate(mContext, "otros", remoteLastUpdate);
                cache.guardarListaOtros(mContext, arrayComplementos);
            } else{
                usarCache = true;
            }
        } else{ //Si no hay internet, uso la cache
            usarCache = true;
        }
        if(usarCache)
            arrayComplementos = (ArrayList<Complemento>) cache.leerListaOtros( mContext);
    }

    //Metodo para rellenar los recyclerViews
    private void setOtros(){
        setRecyclerView(arrayBebidasCalientes,rec1);
        setRecyclerView(arrayBebidasFrias,rec2);
        setRecyclerView(arrayTostasPizzas,rec3);
        setRecyclerView(arrayBolleria,rec4);
        setRecyclerView(arrayMas,rec5);
    }

    private void setRecyclerView(ArrayList<Complemento> array, RecyclerView recyclerView){

        //Creamos el objeto adaptador para todos los arrays
        Adaptador_Otros adaptador = new Adaptador_Otros(getActivity(),array);

        //Creamos el objeto de la clase que nos lo va a poner en dos columnas
        StaggeredGridLayoutManager cardAdapter = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);

        //Adaptamos el cardview
        recyclerView.setLayoutManager(cardAdapter);

        //Adaptamos el contenido
        recyclerView.setAdapter(adaptador);

    }

    //**********************************************************************************************

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
