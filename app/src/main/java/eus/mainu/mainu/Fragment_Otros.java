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
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import eus.mainu.mainu.Utilidades.HttpGetRequest;
import eus.mainu.mainu.Utilidades.RecyclingViewCardAdapter;
import eus.mainu.mainu.datalayer.Complemento;

public class Fragment_Otros extends Fragment{

    private static final String TAG = "Fragment_Otros";
    private static final int NUM_COLUMNS = 2;   //Numero de columnas del cardview
    private TextView titulo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_otros, container, false);

        // ATENCION ERROR Attempt to invoke virtual method 'void android.widget.TextView.setText(java.lang.CharSequence)' on a null object reference
        //Ponemos el titulo en la toolbat
        //titulo = view.findViewById(R.id.textViewActividad);
        //titulo.setText(getString(R.string.complementos));

        //Ponemos el contenido del cardView
        setOtros(view);

        return view;
    }


    //Metodo para rellenar el cardview
    private void setOtros(View view){

        //Referenciamos el cardview
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_otros);

        //Indicamos que esta cargando mediante una animacion
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        //Creamos el contenido del cardView
        HttpGetRequest request = new HttpGetRequest();

        ArrayList<Complemento> arrayComplementos = request.getOtros();
        if (arrayComplementos.size() != 0 )progressBar.setVisibility(View.GONE);   //Quitamos la animacion

        //Adaptamos
        RecyclingViewCardAdapter recyclingViewCardAdapter = new RecyclingViewCardAdapter(getActivity(),arrayComplementos);
        StaggeredGridLayoutManager recyclingViewCardAdapterManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(recyclingViewCardAdapterManager);
        recyclerView.setAdapter(recyclingViewCardAdapter);

    }

}
