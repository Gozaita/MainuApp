package eus.mainu.mainu;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.Arrays;

import eus.mainu.mainu.Utilidades.Adaptador_Ingredientes;
import eus.mainu.mainu.Utilidades.HttpGetRequest;
import eus.mainu.mainu.Utilidades.Adaptador_Bocadillos;
import eus.mainu.mainu.datalayer.Bocadillo;

//Clase del fragmento responsable de visualizar los bocadillos
public class Fragment_Bocadillos extends Fragment implements MenuItem.OnActionExpandListener {

    private final String TAG = "Bocadillos";

    //Elementos Layout
    private Context mContext;
    private SwipeRefreshLayout swipeRefreshLayout;
    private android.support.v7.widget.RecyclerView recyclerView;
    private android.support.v7.widget.RecyclerView recyclerViewIngredientes;

    //Variables
    private ArrayList<Bocadillo> arrayBocadillos = new ArrayList<>();
    private ArrayList<String> arrayIngredientes = new ArrayList<>(Arrays.asList(
            "Bacon", "Bonito",
            "Calamares", "Cebolla", "Champiñones", "Chorizo", "Croissant",
            "Hamburguesa", "Huevo", "Huevo cocido",
            "Jamón york", "Jamón serrano",
            "Lechuga", "Lomo",
            "Mahonesa",
            "Pan de molde", "Pavo", "Pimientos verdes", "Pollo", "Pollo empanado",
            "Queso",
            "Salchichas", "Salsa césar",
            "Ternera", "Tomate", "Tortilla"
            ));

    public Fragment_Bocadillos(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: Inicia Fragment Bocadillos");

        mContext = getContext();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
             arrayBocadillos = (ArrayList<Bocadillo>) bundle.getSerializable("listaBocadillos");
        }
    }

    public void deseleccionarTodosIngredientes(){
        for (int i = 0; i < recyclerViewIngredientes.getChildCount(); i++) {
            View mChild = recyclerViewIngredientes.getChildAt(i);

            //Replace R.id.checkbox with the id of CheckBox in your layout
            CheckBox mCheckBox = (CheckBox) mChild.findViewById(R.id.ingredienteCheckBox);
            mCheckBox.setChecked(false);
        }
    }

    //**********************************************************************************************
    //Metodo que se ejecuta al visualizar el fragmento, se representa en funcion del layout fragment_bocadillos
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bocadillos, container, false);

        //Cogemos el recycling view
        recyclerView = view.findViewById(R.id.recycler_view_lista_bocadillos);
        recyclerViewIngredientes = view.findViewById(R.id.recyclerView_ingredientes);

        Adaptador_Ingredientes adaptorIngredientes = new Adaptador_Ingredientes(arrayIngredientes, getActivity());
        Adaptador_Bocadillos aa = new Adaptador_Bocadillos(arrayBocadillos, getActivity());
        recyclerViewIngredientes.setAdapter( adaptorIngredientes );
        recyclerViewIngredientes.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        //recyclerViewIngredientes.setLayoutManager(new LinearLayoutManager(getActivity()));

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

    // Para acceder a setBocadillos pasandole la lista filtrada
    public void actualizaListaBocadillos(ArrayList<Bocadillo> filtrada){
        this.arrayBocadillos = filtrada;

        setBocadillos();
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
