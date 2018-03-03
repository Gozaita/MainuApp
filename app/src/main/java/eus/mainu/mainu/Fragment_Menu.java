package eus.mainu.mainu;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import eus.mainu.mainu.Utilidades.HttpGetRequest;
import eus.mainu.mainu.Utilidades.IActivityMain;
import eus.mainu.mainu.Utilidades.Menu;
import eus.mainu.mainu.Utilidades.PlatosListViewAdapter;
import eus.mainu.mainu.datalayer.Plato;

public class Fragment_Menu extends Fragment {

    private static final String TAG = "Menu";
    private IActivityMain mIActivityMain;

    //Elementos de la vista
    private Context mContext;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView lvPrimeros;
    private ListView lvSegundos;
    private ListView lvPostres;
    private ImageView imagen;

    //Variables
    private Menu menu = new Menu();
    private boolean actualizado = false;

    //Metodo que se llama antes de onCreate
    //Siempre se instancian las interfaces aqui
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //mIActivityMain = (IActivityMain) getActivity();
    }

    //Metodo que se llama antes de onCreateView, se suelen coger las variables aqui
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //mIActivityMain.setTexto(TAG);//Pasamos un texto a la actividad Main
        //Cogemos el contexto
        mContext = getContext();
        //Creamos un objeto para hacer las peticiones get y para hacer los hilos
        HttpGetRequest request = new HttpGetRequest();
        //Comprobamos si hay conexion para hacer las peticiones de los arrays
        if(request.isConnected(mContext) ){
            //Pedimos los complementos a la API
            menu = request.getMenu();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        //Cogemos el contexto
        mContext = view.getContext();

        //Referenciamos los listViews
        lvPrimeros = view.findViewById(R.id.listaPrimeros);
        lvSegundos = view.findViewById(R.id.listaSegundos);
        lvPostres  = view.findViewById(R.id.listaPostres);
        imagen = view.findViewById(R.id.imagenMenu);

        //Ponemos el SwipeToRefresh
        swipeRefreshLayout = view.findViewById(R.id.swipeMenu);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        //Inflamos la vista
        setListView();

        //Escuchamos un posible swipe to refresh
        escuchamosSwipe();

        return view;
    }

    //Metodo para inflar los listviews y las imagenes
    public void setListView(){

        inflaListView(menu.getPrimeros(),lvPrimeros);
        inflaListView(menu.getSegundos(),lvSegundos);
        inflaListView(menu.getPostres(),lvPostres);
        if(!menu.getImagenes().isEmpty()) {
            Picasso.with(mContext).load(menu.getImagenes().get(0).getRuta()).resize(405, 200).centerCrop().into(imagen);
        }

    }

    //Metodo para inflar un listview cualquiera
    private void inflaListView(ArrayList<Plato> platos, ListView listView) {

        //Quitamos las divisiones
        listView.setDivider(null);
        //Adaptamos la informacion que va dentro de ellos (los nombres)
        PlatosListViewAdapter adaptador = new PlatosListViewAdapter(getContext(),platos);
        //Metemos dentro la informacion
        listView.setAdapter(adaptador);

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


                if(request.isConnected(mContext) && menu.getNombres().isEmpty()){
                    menu = request.getMenu();
                    setListView();
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



}
