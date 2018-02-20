package eus.mainu.mainu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import eus.mainu.mainu.Utilidades.HttpGetRequest;
import eus.mainu.mainu.Utilidades.RecyclerViewAdapter;
import eus.mainu.mainu.datalayer.Bocadillo;

public class Fragment_Bocadillos extends Fragment{

    private static final String TAG = "Fragment_Bocadillos";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bocadillos, container, false);

        setBocadillos(view);

        return view;
    }

    private void setBocadillos(View view){
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

        android.support.v7.widget.RecyclerView recyclerView = view.findViewById(R.id.recycler_view_lista_bocadillos);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(arrayBocadillos, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

}
