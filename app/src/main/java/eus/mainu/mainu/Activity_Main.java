package eus.mainu.mainu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import eus.mainu.mainu.Utilidades.AdaptadorDeSeccionesPagerView;
import eus.mainu.mainu.Utilidades.IActivityMain;
import eus.mainu.mainu.Utilidades.Menu;
import eus.mainu.mainu.datalayer.Bocadillo;
import eus.mainu.mainu.datalayer.Complemento;

public class Activity_Main extends AppCompatActivity {

    private static final String TAG = "Activity MAIN";

    //Elementos de la vista
    private TextView titulo;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    //Variables que inicializo
    //private Menu menu;
    //private ArrayList<Bocadillo> arrayBocadillos;
    //private ArrayList<Complemento> arrayComplementos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titulo = findViewById(R.id.textViewActividad);
        viewPager = findViewById(R.id.contenedor);
        tabLayout = findViewById(R.id.tabs);

        setupViewPager();
        setUpTitulo();
    }

    //Responsable de añadir 3 fragmentos: Bocadillos, Menu, Otros
    private void setupViewPager(){

        //Creamos los fragmentos
        AdaptadorDeSeccionesPagerView adapter = new AdaptadorDeSeccionesPagerView(getSupportFragmentManager());
        adapter.addFragment(new Fragment_Menu()); //index 0
        adapter.addFragment(new Fragment_Bocadillos()); //index 1
        adapter.addFragment(new Fragment_Otros()); //index 2

        //Creamos las tabulaciones
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        //Añadimos 3 iconos
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_menu);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_sandwich);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_french_fries);

    }

    //Metodo que sirve para cambiar el titulo de la toolbar en funcion del fragmento
    private void setUpTitulo(){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position==0) {
                    titulo.setText("Menu del dia");
                }
                if(position==1) {
                    titulo.setText("Bocadillos");
                }
                if(position==2) {
                    titulo.setText("Otros");
                }
            }
            @Override
            public void onPageSelected(int position) {

            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
