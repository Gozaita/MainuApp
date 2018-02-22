package eus.mainu.mainu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import eus.mainu.mainu.Utilidades.RecyclingViewCardAdapter;

public class Fragment_Otros extends Fragment{

    private static final String TAG = "Fragment_Otros";
    private static final int NUM_COLUMNS = 2;   //Numero de columnas del cardview
    private TextView titulo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_otros, container, false);

        //Ponemos el titulo en la toolbat
        //titulo = view.findViewById(R.id.Activity);
        //titulo.setText(getResources().getString(R.string.complementos));

        //Ponemos el contenido del cardView
        setOtros(view);

        return view;
    }


    //Metodo para rellenar el cardview
    private void setOtros(View view){

        //Referenciamos el cardview
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_otros);

        //Creamos el contenido del cardView
        ArrayList<String> nombres = new ArrayList<>();
        ArrayList<String> precios = new ArrayList<>();

        nombres.add("Cocacola");
        nombres.add("Fanta");
        nombres.add("alioli");
        nombres.add("Tosta Plus");
        nombres.add("Cafe con leche");

        precios.add("2.00");
        precios.add("2.00");
        precios.add("3.00");
        precios.add("3.00");
        precios.add("1.10");

        //Adaptamos
        RecyclingViewCardAdapter recyclingViewCardAdapter = new RecyclingViewCardAdapter(getActivity(),nombres,precios);
        StaggeredGridLayoutManager recyclingViewCardAdapterManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(recyclingViewCardAdapterManager);
        recyclerView.setAdapter(recyclingViewCardAdapter);

    }

}
