package eus.mainu.mainu;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

public class BocadillosActivity extends AppCompatActivity {

    //private TextView mTextMessage;
    private ListView listaBocadillos;
    String[] bocadillos;
    String[] descripcion;
    String[] precios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bocadillos);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.navigation_home:
                        Intent intent1 = new Intent(BocadillosActivity.this, MainActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.navigation_dashboard:
                        Intent intent2 = new Intent(BocadillosActivity.this, BocadillosActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.navigation_notifications:
                        Intent intent3 = new Intent(BocadillosActivity.this, RecyclerViewActivity.class);
                        startActivity(intent3);
                        break;
                }
                return false;
            }
        });
        bocadillos  = new String[]{"Aperribai", "Ave Cesar", "Lomo-queso"};
        descripcion = new String[]{"Pollo, lechuga, mayonesa, tomate", "mierda con tomate", "Ingredientes secretos"};
        precios     = new String[]{"3.35€", "3.40€", "3.15"};

        Resources res = getResources();
        listaBocadillos = findViewById(R.id.listaBocadillos);


        //Adaptamos el formato del listView en funcion del layout que hemos creado
        //AdaptadorDeObjetos adaptadorDeObjetos = new AdaptadorDeObjetos(this, bocadillos, descripcion, precios);
        //listaBocadillos.setAdapter(adaptadorDeObjetos);

    }

}
