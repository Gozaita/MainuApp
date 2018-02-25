package eus.mainu.mainu.Utilidades;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import eus.mainu.mainu.datalayer.Bocadillo;
import eus.mainu.mainu.datalayer.Plato;


//Clase para hacer las peticiones GET
public class HttpGetRequest extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        String url = params[0];
        String result, inputLine;

        try {
            URL myUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection)myUrl.openConnection();

            connection.setRequestMethod("GET");
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.connect();

            InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
            }

            reader.close();
            streamReader.close();
            result = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            result = null;
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
    }


    //Metodo para pedir el listado de bocadillos, todas las operaciones de parseo del mensaje JSON se hacen dentro de el
    public ArrayList<Bocadillo> getBocadillos(){

        ArrayList<Bocadillo> arrayBocadillos = new ArrayList<Bocadillo>();

        String result;
        try {
            result = execute("https://api.mainu.eus/get_bocadillos").get();
            JSONArray obj = new JSONArray(result);
            for (int i = 0; i < obj.length(); i++){
                JSONObject o = obj.getJSONObject(i);

                //Cogemos la lista de los ingredientes
                JSONArray ingredientes = o.getJSONArray("ingredientes");
                StringBuilder descripcion = new StringBuilder();
                for(int j = 0; j < ingredientes.length(); j++) {
                    descripcion.append(ingredientes.getString(j)+" ");
                }

                arrayBocadillos.add(
                        new Bocadillo( o.getInt("id"), o.getString("nombre"), descripcion.toString(), o.getDouble("precio"))
                );
            }
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
            result = execute("https://api.mainu.eus/get_menu").get();

            JSONObject obj = new JSONObject(result);

            //Cogemos el plato del postre
            JSONObject oPostre = obj.getJSONObject("postre");

            //Lo metemos en la lista de los postres
            menu.getPostres().add((new Plato(oPostre.getInt("id"), oPostre.getString("nombre"), 5f, oPostre.getString("imagen"))));

            //Cogemos los primeros y los segundos
            JSONArray oPrimeros = obj.getJSONArray("primeros");
            JSONArray oSegundos = obj.getJSONArray("segundos");

            //Rellenamos los platos de los primeros y los segundos
            menu.setPrimeros(getPlatos(oPrimeros));
            menu.setSegundos(getPlatos(oSegundos));

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return menu;

    }

    //Metodo para sacar el array de platos
    private ArrayList<Plato> getPlatos(JSONArray obj){

        ArrayList<Plato> plato = new ArrayList<>();

        try {
            for(int i = 0; i < obj.length(); i++){
                JSONObject o = obj.getJSONObject(i);
                plato.add(new Plato(o.getInt("id"), o.getString("nombre"), 5f, o.getString("imagen")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return plato;
    }

}
