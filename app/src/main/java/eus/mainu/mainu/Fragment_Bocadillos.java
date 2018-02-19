package eus.mainu.mainu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.ArrayList;
import eus.mainu.mainu.Utilidades.RecyclerViewAdapter;
import eus.mainu.mainu.datalayer.Bocadillo;

public class Fragment_Bocadillos extends Fragment{

    private static final String TAG = "Fragment_Bocadillos";
    private ListView listaBocadillos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bocadillos, container, false);

        setBocadillos(view);

        return view;
    }

    private void setBocadillos(View view){


        ArrayList<Bocadillo> arrayBocadillos = new ArrayList<Bocadillo>();

        Bocadillo aveCesar  = new Bocadillo(1,"Ave Cesar","Bocadillo Romano",3f);
        Bocadillo aperribai = new Bocadillo(2,"Aperribai","Bocadillo Vasco",3.25f);
        Bocadillo lomoQueso = new Bocadillo(3,"Lomo Solo","Bocadillo Terrible", 2.25f);


        arrayBocadillos.add(aveCesar);
        arrayBocadillos.add(aperribai);
        arrayBocadillos.add(lomoQueso);

        android.support.v7.widget.RecyclerView recyclerView = view.findViewById(R.id.recycler_view_lista_bocadillos);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(arrayBocadillos, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

}
