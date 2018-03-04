package eus.mainu.mainu.Utilidades;

import android.content.Context;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ExecutionException;

import eus.mainu.mainu.datalayer.Bocadillo;
import eus.mainu.mainu.datalayer.Complemento;
import eus.mainu.mainu.datalayer.Imagen;
import eus.mainu.mainu.datalayer.Ingrediente;
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


    //Metodo para pedir el listado de bocadillos, todas las operaciones de parseo del mensaje JSON se hacen dentro de el
    public ArrayList<Bocadillo> getBocadillos(){

        ArrayList<Bocadillo> arrayBocadillos = new ArrayList<Bocadillo>();

        String result;
        try {
            result = execute("https://api.mainu.eus/bocadillos").get();
            JSONArray obj = new JSONArray(result);
            for (int i = 0; i < obj.length(); i++){
                JSONObject o = obj.getJSONObject(i);
                //Cogemos la lista de los ingredientes
                JSONArray ingredientes = o.getJSONArray("ingredientes");
                //Mapeamos la lista de JSON en ingredientes
                ArrayList<Ingrediente> arrayingredientes = new ArrayList<Ingrediente>();
                for(int j = 0; j < ingredientes.length(); j++) {
                    JSONObject a = ingredientes.getJSONObject(j);
                    arrayingredientes.add(new Ingrediente(getInt(a,"id"),getString(a,"nombre")));
                }
                //Creamos el bocadillo
                arrayBocadillos.add(new Bocadillo( getInt(o,"id"), getString(o,"nombre"), getDouble(o,"precio"),getDouble(o,"puntuacion"), arrayingredientes));
                //Ordenamos por nombre
                Collections.sort(arrayBocadillos, new Comparator<Bocadillo>() {
                    @Override
                    public int compare(Bocadillo bocadillo1, Bocadillo bocadillo2){
                        return  bocadillo1.getNombre().compareTo(bocadillo2.getNombre());
                    }
                });
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
            result = execute("https://api.mainu.eus/menu").get();

            JSONObject obj = new JSONObject(result);

            //Cogemos los primeros, los segundos y los postres
            JSONArray oPostres = obj.getJSONArray("postre");
            JSONArray oPrimeros = obj.getJSONArray("primeros");
            JSONArray oSegundos = obj.getJSONArray("segundos");

            //Rellenamos los platos de los primeros y los segundos
            menu.setPostres(getPlatos(oPostres));
            menu.setPrimeros(getPlatos(oPrimeros));
            menu.setSegundos(getPlatos(oSegundos));

        } catch (Exception e) {
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

                //Cogemos la lista de imagenes
                JSONArray images = o.getJSONArray("images");
                //Mapeamos la lista de JSON en ingredientes
                ArrayList<Imagen> arrayimagenes = new ArrayList<Imagen>();
                for(int j = 0; j < images.length(); j++) {
                    JSONObject a = images.getJSONObject(j);
                    arrayimagenes.add( new Imagen(a.getInt("id"),a.getString("url")));
                }
                //Añadimos al array el plato
                plato.add(new Plato(getInt(o,"id"), getString(o,"nombre"), getString(o,"descripcion"),getDouble(o,"puntuacion"), getImagenes(o)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return plato;
    }

    //Método para crear el array de Complementos
    public ArrayList<Complemento> getOtros()
    {
        ArrayList<Complemento> arrayComplementos = new ArrayList<>();

        String result;
        try {
            result = execute("https://api.mainu.eus/otros").get();
            JSONArray obj = new JSONArray(result);
            for (int i = 0; i < obj.length(); i++){
                JSONObject o = obj.getJSONObject(i);
                    arrayComplementos.add(
                        new Complemento( getInt(o,"id"), getString(o,"nombre"), getDouble(o,"precio"), getDouble(o,"puntuacion"), getImagenes(o))
                );
            }

            Collections.sort(arrayComplementos, new Comparator<Complemento>() {
                @Override
                public int compare(Complemento complemento1, Complemento complemento2){
                    return  complemento1.getNombre().compareTo(complemento2.getNombre());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return arrayComplementos;
    }


    //Metodo para leer un INT de un JSONObject
    private int getInt(JSONObject o, String campo) throws JSONException {

        int id = 0;

        if(!o.isNull(campo)) {
            id = o.getInt(campo);
        }

        return id;
    }

    //Metodo para leer un String de un JSONObject
    private String getString(JSONObject o, String campo) throws JSONException {

        String texto = "";

        if(!o.isNull(campo)) {
            texto = o.getString(campo);
        }

        return texto;
    }


    //Metodo para leer la puntuacion de un JSONObject
    private double getDouble(JSONObject o, String nombre) throws JSONException {

        double puntuacion = 0;

        if(!o.isNull(nombre)) {
            puntuacion = o.getDouble(nombre);
        }

        return puntuacion;
    }


    //Metodo para leer las imagenes de un elemento
    private ArrayList<Imagen> getImagenes(JSONObject o) throws JSONException {

        ArrayList<Imagen> arrayImagenes = new ArrayList<>();

        //Cogemos la lista de imagenes
        JSONArray images = o.getJSONArray("images");

        //Mapeamos la lista de JSON en ingredientes
        for(int j = 0; j < images.length(); j++) {
            JSONObject a = images.getJSONObject(j);
            arrayImagenes.add( new Imagen(a.getInt("id"),a.getString("url")));
        }

        return arrayImagenes;
    }



}
