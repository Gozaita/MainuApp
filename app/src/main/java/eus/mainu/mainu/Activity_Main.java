package eus.mainu.mainu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import eus.mainu.mainu.Utilidades.AdaptadorDeSeccionesPagerView;

public class Activity_Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViewPager();
    }

    //Responsable de añadir 3 fragmentos: Bocadillos, Menu, Otros
    private void setupViewPager(){

        //Creamos los fragmentos

        AdaptadorDeSeccionesPagerView adapter = new AdaptadorDeSeccionesPagerView(getSupportFragmentManager());
        adapter.addFragment(new Fragment_Menu()); //index 0
        adapter.addFragment(new Fragment_Bocadillos()); //index 1
        adapter.addFragment(new Fragment_Otros()); //index 2

        ViewPager viewPager = (ViewPager) findViewById(R.id.contenedor);
        viewPager.setAdapter(adapter);

        //Creamos las tabulaciones
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        //Añadimos 3 iconos
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_menu);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_sandwich);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_french_fries);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
