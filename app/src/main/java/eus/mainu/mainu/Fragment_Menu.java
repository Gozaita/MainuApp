package eus.mainu.mainu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Fragment_Menu extends Fragment{
    private ListView lvPrimeros;
    private ListView lvSegundos;
    private ListView lvPostres;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

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

        ArrayList<String> menuPri = new ArrayList<String>();
        ArrayList<String> menuSeg = new ArrayList<String>();
        ArrayList<String> menuPos = new ArrayList<String>();

        menuPri.add("Lentejas");
        menuPri.add("Arroz a la cubana");
        menuPri.add("Ensalada mixta");


        menuSeg.add("Albondigas");
        menuSeg.add("Tortilla Francesa");
        menuSeg.add("Arraingorri");
        menuPos.add("Helado");

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


        //Adaptamos la informacion que va dentro de ellos
        ArrayAdapter<String> arrayAdapterPrimeros = new ArrayAdapter<>(getActivity(), R.layout.listview_platos, R.id.nombreTextView, menuPri );
        ArrayAdapter<String> arrayAdapterSegundos = new ArrayAdapter<>(getActivity(), R.layout.listview_platos, R.id.nombreTextView, menuSeg);
        ArrayAdapter<String> arrayAdapterPostres  = new ArrayAdapter<>(getActivity(), R.layout.listview_platos, R.id.nombreTextView, menuPos);

        //Metemos dentro la informacion
        lvPrimeros.setAdapter(arrayAdapterPrimeros);
        lvSegundos.setAdapter(arrayAdapterSegundos);
        lvPostres.setAdapter(arrayAdapterPostres);
    }

}
