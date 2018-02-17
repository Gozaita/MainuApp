package eus.mainu.mainu;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import eus.mainu.mainu.Utilidades.AdaptadorDeSeccionesPagerView;

public class OtrosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otros);
    }

    /**
     * Responsable de añadir 3 fragmentos: Bocadillos, Menu, Otros
     *
     */
    private void setupViewPager(){
        AdaptadorDeSeccionesPagerView adapter = new AdaptadorDeSeccionesPagerView(getSupportFragmentManager());
        adapter.addFragment(new BocadillosFragment()); //index 0
        adapter.addFragment(new MenuFragment()); //index 1
        adapter.addFragment(new OtrosFragment()); //index 2

        ViewPager viewPager = (ViewPager) findViewById(R.id.contenedor);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_dashboard_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_menu);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_home_black_24dp);
    }

}
