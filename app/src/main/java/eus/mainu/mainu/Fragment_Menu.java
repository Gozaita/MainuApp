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
import eus.mainu.mainu.Utilidades.Adaptador_Menu;
import eus.mainu.mainu.Utilidades.Adaptador_Platos;
import eus.mainu.mainu.Utilidades.HttpGetRequest;
import eus.mainu.mainu.Utilidades.Menu;
import eus.mainu.mainu.Utilidades.Menu;
import eus.mainu.mainu.datalayer.Plato;


public class Fragment_Menu extends Fragment {

    private final String TAG = "Menu";

    //Elementos de la vista
    private Context mContext;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listaMenu;

    private Menu menu = new Menu();


    //Metodo que se llama antes de onCreateView, se suelen coger las variables aqui
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: Fragmento Menu");

        //Cogemos el contexto
        mContext = getContext();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            menu = (Menu )bundle.getSerializable("Menu");
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

        //Ponemos el SwipeToRefresh
        swipeRefreshLayout = view.findViewById(R.id.swipeMenu);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        //Inflamos la vista
        setListView();

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

    }

    //Metodo para inflar los listviews y las imagenes
    public void setListView(){

        Adaptador_Menu mAdapter = new Adaptador_Menu(mContext);

        mAdapter.addSectionHeaderItem(getResources().getString(R.string.primer_Plato));
        for(int i = 0; i < menu.getPrimeros().size(); i++){
            mAdapter.addItem(menu.getPrimeros().get(i));
        }
        mAdapter.addSectionHeaderItem(getResources().getString(R.string.segundo_Plato));
        for(int i = 0; i < menu.getSegundos().size(); i++){
            mAdapter.addItem(menu.getSegundos().get(i));
        }
        mAdapter.addSectionHeaderItem(getResources().getString(R.string.postre));
        for(int i = 0; i < menu.getPostres().size(); i++){
            mAdapter.addItem(menu.getPostres().get(i));
        }
        listaMenu.setDivider(null);
        listaMenu.setAdapter(mAdapter);


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



}
