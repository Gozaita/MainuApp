package eus.mainu.mainu;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

import eus.mainu.mainu.Utilidades.Adaptador_Imagenes_Swipe;
import eus.mainu.mainu.Utilidades.Adaptador_Platos;
import eus.mainu.mainu.Utilidades.HttpGetRequest;
import eus.mainu.mainu.Utilidades.Menu;
import eus.mainu.mainu.datalayer.Plato;

public class Fragment_Menu extends Fragment {

    private final String TAG = "Menu";

    //Elementos de la vista
    private Context mContext;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listaMenu;
    //private ListView lvSegundos;
    //private ListView lvPostres;
    //private ImageView imagen;
    //private ImageButton flecha_izquierda;
    //private ImageButton flecha_derecha;

    //Variables
    private Menu menu = new Menu();
    //private boolean actualizado = false;


    //Metodo que se llama antes de onCreateView, se suelen coger las variables aqui
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: Fragmento Menu");

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

        //Ponemos el viewPager
        ViewPager viewPager = view.findViewById(R.id.viewPagerMenu);
        Adaptador_Imagenes_Swipe adaptadorImagenes = new Adaptador_Imagenes_Swipe(menu.getImagenes(),mContext);
        viewPager.setAdapter(adaptadorImagenes);
        TabLayout tabLayout = view.findViewById(R.id.tab_fotos_menu);
        tabLayout.setupWithViewPager(viewPager, true);

        viewPager.setCurrentItem((int)(Math.random() * (5 + 1)));

        setViewPager(viewPager);

        //Referenciamos los listViews
        listaMenu = view.findViewById(R.id.listaMenu);
        //lvSegundos = view.findViewById(R.id.listaSegundos);
        //lvPostres  = view.findViewById(R.id.listaPostres);
        //imagen = view.findViewById(R.id.imagenMenu);
        //flecha_izquierda = view.findViewById(R.id.flecha_izquierda);
        //flecha_derecha = view.findViewById(R.id.flecha_derecha);

        //Ponemos el SwipeToRefresh
        swipeRefreshLayout = view.findViewById(R.id.swipeMenu);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        //Inflamos la vista
        setListView();

        //setFlechaDerecha();
        //setFlechaIzquierda();

        //Escuchamos un posible swipe to refresh
        escuchamosSwipe();

        return view;
    }


    //Metodo para que el swipe to refresh y el view pager no se molesten
    private void setViewPager(ViewPager viewPager){
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        swipeRefreshLayout.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        swipeRefreshLayout.setEnabled(true);
                        break;
                }
                return false;
            }
        });

        //viewPager.addOnPageChangeListener(...); Para hacer el loop infinito
    }

    //Metodo para inflar los listviews y las imagenes
    public void setListView(){

        inflaListView(menu.getPlatos(),listaMenu);
        //inflaListView(menu.getSegundos(),lvSegundos);
        //inflaListView(menu.getPostres(),lvPostres);
    }

    //Metodo para inflar un listview cualquiera
    private void inflaListView(ArrayList<Plato> platos, ListView listView) {

        //Quitamos las divisiones
        listView.setDivider(null);
        //Adaptamos la informacion que va dentro de ellos (los nombres)
        Adaptador_Platos adaptador = new Adaptador_Platos(getContext(),platos);
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

                if(request.isConnected(mContext) && menu.getPlatos().isEmpty()){
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

    /*
    private void setFlechaDerecha(){
        flecha_derecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!menu.getImagenes().isEmpty()) {
                    if (contador == 6) {
                        contador = 0;
                    } else {
                        contador++;
                    }
                    Picasso.with(mContext)
                            .load(menu.getImagenes().get(contador).getRuta())
                            .fit()
                            .centerCrop()
                            .into(imagen);
                }
            }
        });
    }

    private void setFlechaIzquierda(){
        flecha_izquierda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!menu.getImagenes().isEmpty()) {
                    if (contador == 0) {
                        contador = 6;
                    } else {
                        contador--;
                    }
                    Picasso.with(mContext)
                            .load(menu.getImagenes().get(contador).getRuta())
                            .fit()
                            .centerCrop()
                            .into(imagen);
                }
            }
        });
    }
    */


}
