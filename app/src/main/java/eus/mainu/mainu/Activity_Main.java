package eus.mainu.mainu;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import eus.mainu.mainu.Utilidades.AdaptadorDeSeccionesPagerView;

public class Activity_Main extends AppCompatActivity {
    private TextView mTextMessage;
    private ListView lvPrimeros;
    private ListView lvSegundos;
    private ListView lvPostres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViewPager();
    }

    //Responsable de añadir 3 fragmentos: Bocadillos, Menu, Otros
    private void setupViewPager(){
        AdaptadorDeSeccionesPagerView adapter = new AdaptadorDeSeccionesPagerView(getSupportFragmentManager());
        adapter.addFragment(new Fragment_Bocadillos()); //index 0
        adapter.addFragment(new Fragment_Menu()); //index 1
        adapter.addFragment(new Fragment_Otros()); //index 2

        ViewPager viewPager = (ViewPager) findViewById(R.id.contenedor);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.mipmap.icon);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_menu);
        tabLayout.getTabAt(2).setIcon(R.mipmap.icon);
    }

}
