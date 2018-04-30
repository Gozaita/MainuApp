package eus.mainu.mainu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import eus.mainu.mainu.Utilidades.Adaptador_Bocadillos;
import eus.mainu.mainu.datalayer.Bocadillo;
import eus.mainu.mainu.datalayer.Ingrediente;

//Clase del fragmento responsable de visualizar los bocadillos
public class Fragment_Bocadillos extends Fragment implements MenuItem.OnActionExpandListener {

    private final String TAG = "Bocadillos";

    //Elementos Layout
    private Context mContext;
    private android.support.v7.widget.RecyclerView recyclerView;
    private android.support.v7.widget.RecyclerView recyclerViewIngredientes;

    //Variables
    private ArrayList<Bocadillo> arrayBocadillos = new ArrayList<>();
    private ArrayList<Ingrediente> arrayIngredientes2 = new ArrayList<Ingrediente>( Arrays.asList(
            new Ingrediente("bacon"), new Ingrediente("bonito"),
            new Ingrediente("calamares"), new Ingrediente("cebolla"), new Ingrediente("champiñones"),
            new Ingrediente("chorizo"), new Ingrediente("croissant"),
            new Ingrediente("hamburguesa"), new Ingrediente("huevo"), new Ingrediente("huevo cocido"),
            new Ingrediente("jamón serrano"), new Ingrediente("jamón york"),
            new Ingrediente("lechuga"), new Ingrediente("lomo"),
            new Ingrediente("mahonesa"),
            new Ingrediente("pan de molde"), new Ingrediente("pavo"),
            new Ingrediente("pimientos verdes"),
            new Ingrediente("pollo"), new Ingrediente("pollo empanado"),
            new Ingrediente("queso"),
            new Ingrediente("salchichas"), new Ingrediente("salsa césar"),
            new Ingrediente("ternera"), new Ingrediente("tomate"),
            new Ingrediente("tortilla") ));

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

        Adaptador_Ingredientes adaptorIngredientes = new Adaptador_Ingredientes(arrayIngredientes2, getActivity());
        Adaptador_Bocadillos aa = new Adaptador_Bocadillos(arrayBocadillos, getActivity());
        recyclerViewIngredientes.setAdapter( adaptorIngredientes );
        recyclerViewIngredientes.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        //Añadimos una linea debajo de cada objeto
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mContext, new LinearLayoutManager(mContext).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        //Inflamos la vista
        setBocadillos();

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
