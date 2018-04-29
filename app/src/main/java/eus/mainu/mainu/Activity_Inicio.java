package eus.mainu.mainu;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import eus.mainu.mainu.Utilidades.Administrador_Cache;
import eus.mainu.mainu.Utilidades.HttpGetRequest;
import eus.mainu.mainu.Utilidades.Menu;
import eus.mainu.mainu.datalayer.Bocadillo;
import eus.mainu.mainu.datalayer.Complemento;

public class Activity_Inicio extends AppCompatActivity {

    private final String TAG = "Activity Inicio";
    //Duracion Splash Screen
    private static final long SPLASH_SCREEN_DELAY = 50;

    private Menu menu;
    private ArrayList<Bocadillo> listaBocadillos;
    private ArrayList<Complemento> listaOtros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        Log.d(TAG, "onCreate: SE HA INICIADO");

        //ImageView logo = findViewById(R.id.logoinicio);
        ProgressBar iniciando = findViewById(R.id.iniciando);
        iniciando.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);


        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                //Codigo que se ejecuta al de x tiempo
                consigueDatos();

                //Decimos que queremos navegar a la activity main
                Intent intent = new Intent().setClass(
                        Activity_Inicio.this, Activity_Main.class);
                //Le pasamos la informacion que necesita la clase
                intent.putExtra("Menu", menu);
                intent.putExtra("listaBocadillos", listaBocadillos);
                intent.putExtra("listaOtros", listaOtros);
                //Iniciamos la actividad
                startActivity(intent);
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);

    }

    private void consigueDatos(){

        boolean conectado = false;

        HttpGetRequest request1 = new HttpGetRequest();

        if(request1.isConnected(this) ) {
            conectado = true;
            menu = request1.getMenu();
        }

        listaBocadillos = administraPeticionesCacheBocadillos(conectado);
        listaOtros = administraPeticionesCacheOtros(conectado);

        Log.d(TAG, "onCreate: Datos conseguidos");


    }

    /********************************************************************************************/
    private ArrayList<Bocadillo> administraPeticionesCacheBocadillos(boolean conectado){

        //Cada request se puede usar una vez
        HttpGetRequest request1 = new HttpGetRequest();
        HttpGetRequest request2 = new HttpGetRequest();

        ArrayList<Bocadillo> arrayBocadillos = new ArrayList<>();

        //Comprobamos si hay conexion para hacer las peticiones de los arrays
        Administrador_Cache cache = new Administrador_Cache();
        boolean usarCache = false;

        if(conectado){
            String remoteLastUpdate = request1.getLastUpdate("bocadillos");
            String localLastUpdate  = cache.leerLastUpdate( this, "bocadillos");

            if(!remoteLastUpdate.equalsIgnoreCase(localLastUpdate) ){
                arrayBocadillos = request2.getBocadillos();
                cache.guardarLastUpdate(this, "bocadillos", remoteLastUpdate);
                cache.guardarListaBocadillos(this, arrayBocadillos);
            } else{
                usarCache = true;
            }
        } else{ //Si no hay internet, uso la cache
            usarCache = true;
        }
        if(usarCache) {
            arrayBocadillos = (ArrayList<Bocadillo>) cache.leerListaBocadillos(this);
        }
        return arrayBocadillos;
    }

    private ArrayList<Complemento> administraPeticionesCacheOtros(boolean conectado){

        //Cada request se puede usar una vez
        HttpGetRequest request1 = new HttpGetRequest();
        HttpGetRequest request2 = new HttpGetRequest();

        ArrayList<Complemento> arrayComplementos = new ArrayList<>();

        //Comprobamos si hay conexion para hacer las peticiones de los arrays
        Administrador_Cache cache = new Administrador_Cache();
        boolean usarCache = false;
        if(conectado){

            String remoteLastUpdate = request1.getLastUpdate("otros");
            String localLastUpdate  = cache.leerLastUpdate( this, "otros");


            if(!remoteLastUpdate.equalsIgnoreCase(localLastUpdate) ){
                arrayComplementos = request2.getOtros();
                cache.guardarLastUpdate(this, "otros", remoteLastUpdate);
                cache.guardarListaOtros(this, arrayComplementos);
            } else{
                usarCache = true;
            }
        } else{ //Si no hay internet, uso la cache
            usarCache = true;
        }
        if(usarCache) {
            arrayComplementos = (ArrayList<Complemento>) cache.leerListaOtros(this);
        }
        return arrayComplementos;
    }
}
