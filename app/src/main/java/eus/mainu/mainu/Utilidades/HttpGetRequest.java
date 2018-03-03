package eus.mainu.mainu.Utilidades;

import android.content.Context;
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
            result = execute("https://api.mainu.eus/get_bocadillos").get();
            JSONArray obj = new JSONArray(result);
            for (int i = 0; i < obj.length(); i++){
                JSONObject o = obj.getJSONObject(i);

                //Cogemos la lista de los ingredientes
                JSONArray ingredientes = o.getJSONArray("ingredientes");

                //Mapeamos la lista de JSON en ingredientes
                ArrayList<Ingrediente> arrayingredientes = new ArrayList<Ingrediente>();
                for(int j = 0; j < ingredientes.length(); j++) {

                    Ingrediente ingrediente = new Ingrediente(j,(String )ingredientes.get(j));
                    arrayingredientes.add(ingrediente);
                }

                double puntuacion = 0;

                if(!o.isNull("puntuacion")) {
                    puntuacion = o.getDouble("puntuacion");
                }

                //Creamos el bocadillo
                arrayBocadillos.add(
                        new Bocadillo( o.getInt("id"), o.getString("nombre"), o.getDouble("precio"),puntuacion, arrayingredientes)
                );

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
            result = execute("https://api.mainu.eus/get_menu").get();

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

                double puntuacion = 0;

                if(!o.isNull("puntuacion")) {
                    puntuacion = o.getDouble("puntuacion");
                }

                ArrayList<Imagen> imagenes = new ArrayList<>();
                Imagen imagen = new Imagen(0,o.getString("imagen"),true,true);
                imagenes.add(imagen);

                    plato.add(new Plato(o.getInt("id"), o.getString("nombre"), "",puntuacion, imagenes));
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
            result = execute("https://api.mainu.eus/get_otros").get();
            JSONArray obj = new JSONArray(result);
            for (int i = 0; i < obj.length(); i++){
                JSONObject o = obj.getJSONObject(i);

                double puntuacion = 0;

                if(!o.isNull("puntuacion")) {
                    puntuacion = o.getDouble("puntuacion");
                }

                    arrayComplementos.add(
                        new Complemento( o.getInt("id"), o.getString("nombre"), o.getDouble("precio"),puntuacion)
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



}
