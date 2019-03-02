package eus.mainu.mainu.conexion;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import eus.mainu.mainu.data.Ingrediente;
import eus.mainu.mainu.menu.Menu;
import eus.mainu.mainu.data.Bocadillo;
import eus.mainu.mainu.data.Otro;
import eus.mainu.mainu.data.Plato;


//Clase para hacer las peticiones GET
public class HttpGetRequest extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        String url = params[0];
        String result = "", inputLine;

        try {
            URL myUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection)myUrl.openConnection();

            connection.setRequestMethod("GET");
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.connect();

            if( connection.getResponseCode() == 200){
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }

                reader.close();
                streamReader.close();
                result = stringBuilder.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
            result = null;
        }

        return result;
    }

    public boolean isConnected(Context mContext) {
            ConnectivityManager cm =
                    (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
    }


    //**********************************************************************************************
    public String getLastUpdate(String tipo){
        String lastUpdate = "0";

        if (tipo.equals("bocadillos") || tipo.equals("menu") || tipo.equals("otros") || tipo.equals("ingredientes")) {
            try{
                if(tipo.equals("ingredientes")) // los ingredientes y bocadillos comparten actualización
                    tipo = "bocadillos";

                lastUpdate = execute("https://api.mainu.eus/last_update/" + tipo).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(lastUpdate != null){
            lastUpdate = lastUpdate.replaceAll("\"","");
        }
        return lastUpdate;
    }

    //Metodo para pedir el listado de bocadillos, todas las operaciones de parseo del mensaje JSON se hacen dentro de el
    public ArrayList<Bocadillo> getBocadillos(){

        ArrayList<Bocadillo> arrayBocadillos = new ArrayList<>();

        try {
            String result = execute("https://api.mainu.eus/bocadillos").get();
            Administrador_JSON json = new Administrador_JSON();
            arrayBocadillos = json.getBocadillos(result);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return arrayBocadillos;
    }



    //Metodo para pedir el menu del dia, todas las operaciones de parseo del mensaje JSON se hacen dentro de el
    public Menu getMenu() {

        Menu menu = new Menu();

        String result;

        try {
            result = execute("https://api.mainu.eus/menu").get();

            Administrador_JSON json = new Administrador_JSON();
            menu = json.getMenu(result);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return menu;

    }

    //Método para crear el array de Complementos
    public ArrayList<Otro> getOtros()
    {
        ArrayList<Otro> arrayOtros = new ArrayList<>();

        try {
            String result = execute("https://api.mainu.eus/otros").get();

            Administrador_JSON json = new Administrador_JSON();
            arrayOtros = json.getOtros(result);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return arrayOtros;
    }

    //Método para crear el array de Complementos
    public ArrayList<Ingrediente> getIngredientes()
    {
        ArrayList<Ingrediente> arrayIngredientes = new ArrayList<>();

        try {
            String result = execute("https://api.mainu.eus/ingredientes").get();

            Administrador_JSON json = new Administrador_JSON();
            arrayIngredientes = json.getIngredientes(result);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return arrayIngredientes;
    }


    //Para recuperar un solo bocadillo a partir de su id
    public Bocadillo getBocadillo(int id){

        Bocadillo bocadillo = new Bocadillo();

        StringBuilder url = new StringBuilder();
        url.append("https://api.mainu.eus/bocadillos/");
        url.append(id);

        try {
            String result = execute(url.toString()).get();

            Administrador_JSON json = new Administrador_JSON();
            bocadillo = json.getBocadillo(result);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bocadillo;
    }

    //Para recuperar un solo complemento a partir de su id
    public Otro getComplemento(int id){

        Otro otro = new Otro();

        StringBuilder url = new StringBuilder();
        url.append("https://api.mainu.eus/otros/");
        url.append(id);

        try {
            String result = execute(url.toString()).get();

            Administrador_JSON json = new Administrador_JSON();
            otro = json.getComplemento(result);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return otro;
    }

    //Para recuperar un solo bocadillo a partir de su id
    public Plato getPlato(int id){

        Plato plato = new Plato();

        StringBuilder url = new StringBuilder();
        url.append("https://api.mainu.eus/menu/");
        url.append(id);

        try {
            String result = execute(url.toString()).get();

            Administrador_JSON json = new Administrador_JSON();
            plato = json.getPlato(result);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return plato;
    }
}
