package eus.mainu.mainu.Utilidades;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import eus.mainu.mainu.conexion.HttpGetRequest;
import eus.mainu.mainu.data.Bocadillo;

import static junit.framework.Assert.assertEquals;

public class HttpGetRequestTest {

    @Test
    public void doInBackground() {
    }

    @Test
    public void isConnected() {
    }

    @Test
    public void onPostExecute() {
    }

    @Test
    public void getLastUpdate() {
    }



    @Test
    public void getBocadillos() {
        HttpGetRequest h = new HttpGetRequest();
        final String esperado = "bacon";
        final ArrayList<Bocadillo> bocadillos = h.getBocadillos();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //Codigo que se ejecuta al de x tiempo
                assertEquals(bocadillos.get(0).getNombre(), esperado);
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 1000);
    }





    @Test
    public void getMenu() {
    }

    @Test
    public void getOtros() {
    }

    @Test
    public void getBocadillo() {
    }

    @Test
    public void getComplemento() {
    }

    @Test
    public void getPlato() {
    }
}