package eus.mainu.mainu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import eus.mainu.mainu.Utilidades.ListViewAdapter;
import eus.mainu.mainu.datalayer.Plato;

public class Fragment_Menu extends Fragment{

    private TextView titulo;
    private ListView lvPrimeros;
    private ListView lvSegundos;
    private ListView lvPostres;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        /*No funciona, no hace nada

        //Para cerrar el teclado cuando lleguemos a este fragmento
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        */

        //Ponemos el titulo en la toolbat
        titulo = view.findViewById(R.id.Activity);
        titulo.setText(getResources().getString(R.string.menuDelDia));

        setMenu(view);

        return view;
    }

    private void setMenu(View view){


        //Cogemos la referencia de los textviews
        lvPrimeros = view.findViewById(R.id.listaPrimeros);
        lvSegundos = view.findViewById(R.id.listaSegundos);
        lvPostres  = view.findViewById(R.id.listaPostres);

        //Quita el borde gris de cada listview item
        lvPrimeros.setDivider(null);
        lvSegundos.setDivider(null);
        lvPostres.setDivider(null);

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
        primeros.add(segundo1);
        primeros.add(segundo2);
        primeros.add(segundo3);

        ArrayList<Plato> postres = new ArrayList<>();
        primeros.add(postre1);

        /*
        //Instantiate new instance
        String result;
        HttpGetRequest getRequest = new HttpGetRequest();
        try {
            result = getRequest.execute("https://api.mainu.eus/get_bocadillos").get();
            JSONArray obj = new JSONArray(result);
            for (int i = 0; i < obj.length(); i++){
                JSONObject o = obj.getJSONObject(i);
                menuPri.add( o.getString("nombre") );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        */

        ListViewAdapter adapter1 = new ListViewAdapter(getActivity(),primeros);
        ListViewAdapter adapter2 = new ListViewAdapter(getActivity(),segundos);
        ListViewAdapter adapter3 = new ListViewAdapter(getActivity(),postres);


        //Metemos dentro la informacion
        lvPrimeros.setAdapter(adapter1);
        lvSegundos.setAdapter(adapter2);
        lvPostres.setAdapter(adapter3);
    }

}
