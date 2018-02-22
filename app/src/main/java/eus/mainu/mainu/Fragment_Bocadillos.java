package eus.mainu.mainu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import eus.mainu.mainu.Utilidades.HttpGetRequest;
import eus.mainu.mainu.Utilidades.RecyclerViewAdapter;
import eus.mainu.mainu.datalayer.Bocadillo;

//Clase del fragmento responsable de visualizar los bocadillos
public class Fragment_Bocadillos extends Fragment{

    private static final String TAG = "Fragment_Bocadillos";
    private TextView titulo;


    //Metodo que se ejecuta al visualizar el fragmento, se representa en funcion del layout fragment_bocadillos
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bocadillos, container, false);


        //Ponemos el titulo en la toolbat
        //titulo = view.findViewById(R.id.Activity);
        //titulo.setText(getResources().getString(R.string.bocadillos));

        setBocadillos(view);

        return view;
    }

    //Clase para crear y adaptar la informacion al recycling view
    private void setBocadillos(View view){

        //Pedimos los bocadillos a la API
        ArrayList<Bocadillo> arrayBocadillos = getBocadillos();

        //Referenciamos el recyclingView
        android.support.v7.widget.RecyclerView recyclerView = view.findViewById(R.id.recycler_view_lista_bocadillos);

        //Creamos el objeto de la clase adaptador
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(arrayBocadillos, getActivity());

        //Adaptamos el recyclingview
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private ArrayList<Bocadillo> getBocadillos(){

        ArrayList<Bocadillo> arrayBocadillos = new ArrayList<Bocadillo>();

        String result;
        HttpGetRequest getRequest = new HttpGetRequest();
        try {
            result = getRequest.execute("https://api.mainu.eus/get_bocadillos").get();
            JSONArray obj = new JSONArray(result);
            for (int i = 0; i < obj.length(); i++){
                JSONObject o = obj.getJSONObject(i);
                arrayBocadillos.add(
                        new Bocadillo( o.getInt("id"), o.getString("nombre"), "Falta descripcion en la API", o.getDouble("precio"))
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arrayBocadillos;
    }

}
