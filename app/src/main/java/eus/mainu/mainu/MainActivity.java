package eus.mainu.mainu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ListView lvPrimeros;
    private ListView lvSegundos;
    private ListView lvPostres;

    /*private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    //mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Para navegar entre activitys con los botones de abajo
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.navigation_home:
                        Intent intent1 = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.navigation_dashboard:
                        Intent intent2 = new Intent(MainActivity.this, Bocadillos.class);
                        startActivity(intent2);
                        break;

                    case R.id.navigation_notifications:
                        //Vacio de momento
                        break;
                }
                return false;
            }
        });

        //Cogemos la referencia de los textviews
        lvPrimeros = findViewById(R.id.listaPrimeros);
        lvSegundos = findViewById(R.id.listaSegundos);
        lvPostres = findViewById(R.id.listaPostres);

        //Adaptamos la informacion que va dentro de ellos
        ArrayAdapter<String> arrayAdapterPrimeros = new ArrayAdapter<>(this, R.layout.listview_platos, R.id.nombreTextView, new String[]{"Lentejas","Cocido","Ensalada de pollo"});
        ArrayAdapter<String> arrayAdapterSegundos = new ArrayAdapter<>(this, R.layout.listview_platos, R.id.nombreTextView, new String[]{"Albondigas","Tortilla Francesa","Arraingorri"});
        ArrayAdapter<String> arrayAdapterPostres = new ArrayAdapter<>(this, R.layout.listview_platos, R.id.nombreTextView, new String[]{"Helado"});

        //Metemos dentro la informacion
        lvPrimeros.setAdapter(arrayAdapterPrimeros);
        lvSegundos.setAdapter(arrayAdapterSegundos);
        lvPostres.setAdapter(arrayAdapterPostres);



    }

}
