package eus.mainu.mainu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import eus.mainu.mainu.Utilidades.HttpGetRequest;
import eus.mainu.mainu.Utilidades.ListViewAdapter;
import eus.mainu.mainu.datalayer.Menu;
import eus.mainu.mainu.datalayer.Plato;

public class Fragment_Menu extends Fragment {

    private TextView titulo;
    private ListView lvPrimeros;
    private ListView lvSegundos;
    private ListView lvPostres;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

/*
        //Creamos los platos
        Plato primero1 = new Plato(1,"Lentejas",3.2f);
        Plato primero2 = new Plato(2,"Arroz a la cubana",5.5f);
        Plato primero3 = new Plato(3,"Ensalada Mixta",4.1f);

        Plato segundo1 = new Plato(4,"Albondigas",4.5f);
        Plato segundo2 = new Plato(5,"Tortilla Francesa",2);
        Plato segundo3 = new Plato(6,"Arraingorri",3.5f);

        Plato postre1 = new Plato(7,"Helado",3.9f);

        ArrayList<Plato> primeros = new ArrayList<>();
        primeros.add(primero1);
        primeros.add(primero2);
        primeros.add(primero3);

        ArrayList<Plato> segundos = new ArrayList<>();
        segundos.add(segundo1);
        segundos.add(segundo2);
        segundos.add(segundo3);

        ArrayList<Plato> postres = new ArrayList<>();
        postres.add(postre1);

*/
        Menu menu = getMenu();

        //Referenciamos los listViews
        lvPrimeros = view.findViewById(R.id.listaPrimeros);
        lvSegundos = view.findViewById(R.id.listaSegundos);
        lvPostres = view.findViewById(R.id.listaPostres);

        //Adaptamos los listviews
        setListView(menu.getPrimeros(), lvPrimeros);
        setListView(menu.getSegundos(), lvSegundos);
        setListView(menu.getPostres(), lvPostres);


        return view;
    }

    private void setListView(ArrayList<Plato> listaPlatos, ListView listView) {
        //Quitamos las divisiones
        listView.setDivider(null);

        //Adaptamos la lista
        ListViewAdapter adapter = new ListViewAdapter(getActivity(), listaPlatos);

        //Metemos dentro la informacion
        listView.setAdapter(adapter);

    }


    private Menu getMenu() {

        Menu menu = new Menu();

        String result;
        HttpGetRequest getRequest = new HttpGetRequest();

        try {
            result = getRequest.execute("https://api.mainu.eus/get_menu").get();

            JSONObject obj = new JSONObject(result);

            //Cogemos el plato del postre
            JSONObject oPostre = obj.getJSONObject("postre");

            //Lo metemos en la lista de los postres
            menu.getPostres().add((new Plato(oPostre.getInt("id"), oPostre.getString("nombre"), 5f, oPostre.getString("imagen"))));

            //Cogemos los primeros y los segundos
            JSONArray oPrimeros = obj.getJSONArray("primeros");
            JSONArray oSegundos = obj.getJSONArray("segundos");

            //Rellenamos los platos de los primeros y los segundos
            menu.setPrimeros(getPlatos(oPrimeros));
            menu.setSegundos(getPlatos(oSegundos));

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return menu;

    }

    //Metodo para sacar el array de platos
    private ArrayList<Plato> getPlatos(JSONArray obj){

        ArrayList<Plato> plato = new ArrayList<>();

        try {
            for(int i = 0; i < obj.length(); i++){
                JSONObject o = obj.getJSONObject(i);
                plato.add(new Plato(o.getInt("id"), o.getString("nombre"), 5f, o.getString("imagen")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return plato;
    }



}
