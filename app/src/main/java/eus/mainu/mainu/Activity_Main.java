package eus.mainu.mainu;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import eus.mainu.mainu.Utilidades.AdaptadorDeSeccionesPagerView;

public class Activity_Main extends AppCompatActivity {

    private static final String TAG = "Activity MAIN";
    private TextView titulo;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageButton searchButton;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titulo = findViewById(R.id.textViewActividad);
        viewPager = findViewById(R.id.contenedor);
        tabLayout = findViewById(R.id.tabs);
        searchButton = findViewById(R.id.search_button);

        ListView list = (ListView) findViewById(R.id.theList);
        EditText thefilter = (EditText) findViewById(R.id.searchFilter);

        ArrayList<String> names = new ArrayList<>();
        names.add("bobo");
        names.add("estupido");
        names.add("subnormal");
        names.add("bocachancla");

        adapter = new ArrayAdapter(this, R.layout.listivew_search, names);
        list.setAdapter(adapter);

        thefilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                (Activity_Main.this).adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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


        searchButton.setOnClickListener(searchClickListener);
    }

    //Metodo que sirve para cambiar el titulo de la toolbar en funcion del fragmento
    private void setUpTitulo(){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 0:
                        titulo.setText(R.string.menuDelDia);
                        break;
                    case 1:
                        titulo.setText(R.string.bocadillos);
                        break;
                    case 2:
                        titulo.setText(R.string.complementos);
                        break;
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

    View.OnClickListener searchClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (v.equals(searchButton)) {
                findViewById(R.id.searchFilter).setVisibility(View.VISIBLE);
                findViewById(R.id.theList).setVisibility(View.VISIBLE);
            }
        }
    };


}
