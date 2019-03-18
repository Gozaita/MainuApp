package eus.mainu.mainu.conexion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import eus.mainu.mainu.menu.Menu;
import eus.mainu.mainu.data.Bocadillo;
import eus.mainu.mainu.data.Otro;
import eus.mainu.mainu.data.Imagen;
import eus.mainu.mainu.data.Ingrediente;
import eus.mainu.mainu.data.Plato;
import eus.mainu.mainu.data.Usuario;
import eus.mainu.mainu.data.Valoracion;

/**
 * Created by Manole on 12/03/2018.
 * Clase para administrar los JSON que recibimos de la API
 */

class Administrador_JSON {

    //Metodo para pedir el listado de bocadillos, todas las operaciones de parseo del mensaje JSON se hacen dentro de el
    ArrayList<Bocadillo> getBocadillos(String result){

        ArrayList<Bocadillo> arrayBocadillos = new ArrayList<>();

        try {
            JSONArray obj = new JSONArray(result);
            for (int i = 0; i < obj.length(); i++){
                JSONObject o = obj.getJSONObject(i);

                //Creamos el bocadillo
                arrayBocadillos.add(new Bocadillo( getInt(o,"id"), getString(o,"nombre"), getDouble(o,"precio"),getDouble(o,"puntuacion"), getIngredientes(o)));
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
    Menu getMenu(String result) {

        Menu menu = new Menu();

        try {
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
                //Añadimos al array el plato
                plato.add(new Plato(getInt(o,"id"), getString(o,"nombre"), getDouble(o,"puntuacion"), getImagenes(o)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return plato;
    }

    //Método para crear el array de Complementos
    ArrayList<Otro> getOtros(String result)
    {
        ArrayList<Otro> arrayOtros = new ArrayList<>();

        try {
            JSONArray obj = new JSONArray(result);
            for (int i = 0; i < obj.length(); i++){
                JSONObject o = obj.getJSONObject(i);
                arrayOtros.add(
                        new Otro( getInt(o,"id"),
                                getString(o,"nombre"),
                                getDouble(o,"precio"),
                                getDouble(o,"puntuacion"),
                                getInt(o,"tipo"),
                                getImagenes(o))
                );
            }

            Collections.sort(arrayOtros, new Comparator<Otro>() {
                @Override
                public int compare(Otro otro1, Otro otro2){
                    return  otro1.getNombre().compareTo(otro2.getNombre());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return arrayOtros;
    }

    //Método para crear el array de Ingredientes
    ArrayList<Ingrediente> getIngredientes(String result)
    {
        ArrayList<Ingrediente> arrayIngredientes = new ArrayList<>();

        try {
            JSONArray obj = new JSONArray(result);
            for (int i = 0; i < obj.length(); i++){
                JSONObject o = obj.getJSONObject(i);
                arrayIngredientes.add(
                        new Ingrediente( getInt(o,"id"),
                                getString(o,"nombre"))
                );
            }

            Collections.sort(arrayIngredientes, new Comparator<Ingrediente>() {
                @Override
                public int compare(Ingrediente ingrediente1, Ingrediente ingrediente2){
                    return  ingrediente1.getNombre().compareTo(ingrediente2.getNombre());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return arrayIngredientes;
    }


    //Para recuperar un solo bocadillo a partir de su id
    Bocadillo getBocadillo(String result){

        Bocadillo bocadillo = new Bocadillo();

        try {
            //Cogemos el bocadillo de esta direccion
            JSONObject o = new JSONObject(result);

            bocadillo = new Bocadillo( getInt(o,"id"),
                    getString(o,"nombre"),
                    getDouble(o,"precio"),
                    getDouble(o,"puntuacion"),
                    getIngredientes(o),
                    getImagenes(o),
                    getValoraciones(o));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bocadillo;
    }

    //Para recuperar un solo complemento a partir de su id
    Otro getComplemento(String result){

        Otro otro = new Otro();

        try {
            //Cogemos el bocadillo de esta direccion
            JSONObject o = new JSONObject(result);

            otro = new Otro( getInt(o,"id"),
                    getString(o,"nombre"),
                    getDouble(o,"precio"),
                    getDouble(o,"puntuacion"),
                    getImagenes(o),
                    getValoraciones(o));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return otro;
    }

    //Para recuperar un solo bocadillo a partir de su id
    Plato getPlato(String result){

        Plato plato = new Plato();
        

        try {
            //Cogemos el bocadillo de esta direccion
            JSONObject o = new JSONObject(result);

            plato = new Plato(getInt(o,"id"),
                    getString(o,"nombre"),
                    getDouble(o,"puntuacion"),
                    getImagenes(o),
                    getValoraciones(o));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return plato;
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

        double puntuacion;

        if(!o.isNull(nombre)) {
            puntuacion = o.getDouble(nombre);
        }
        else {
            puntuacion = 0;
        }

        return puntuacion;
    }


    private Usuario getUsuario(JSONObject o) throws JSONException {

        return new Usuario(getInt(o,"id"),
                getString(o,"nombre"),
                getString(o,"foto"),
                getInt(o, "verificado"));
    }


    //Metodo para leer las imagenes de un elemento
    private ArrayList<Imagen> getImagenes(JSONObject o) throws JSONException {

        ArrayList<Imagen> arrayImagenes = new ArrayList<>();

        //Cogemos la lista de imagenes
        JSONArray images = o.getJSONArray("images");

        //Mapeamos la lista de JSON en ingredientes
        for(int j = 0; j < images.length(); j++) {
            JSONObject a = images.getJSONObject(j);
            arrayImagenes.add( new Imagen(getInt(a,"id"),
                    getString(a,"url"),
                    getUsuario(a.getJSONObject("usuario"))));
        }

        return arrayImagenes;
    }

    private ArrayList<Valoracion> getValoraciones(JSONObject o) throws JSONException{

        ArrayList<Valoracion> arrayValoraciones = new ArrayList<>();

        //Cogemos la lista de valoraciones
        JSONArray valoraciones = o.getJSONArray("valoraciones");

        //Mapeamos la lista de JSON en valoraciones
        for(int i = 0; i < valoraciones.length(); i++){
            JSONObject a = valoraciones.getJSONObject(i);
            arrayValoraciones.add(new Valoracion(getInt(a,"id"),
                    getDouble(a,"puntuacion"),
                    getString(a,"texto"),
                    getUsuario(a.getJSONObject("usuario"))));
        }


        return arrayValoraciones;
    }


    private ArrayList<Ingrediente> getIngredientes(JSONObject o) throws JSONException {

        //Cogemos la lista de los ingredientes
        JSONArray ingredientes = o.getJSONArray("ingredientes");

        //Mapeamos la lista de JSON en ingredientes
        ArrayList<Ingrediente> arrayIngredientes = new ArrayList<>();

        for(int j = 0; j < ingredientes.length(); j++) {
            JSONObject a = ingredientes.getJSONObject(j);
            arrayIngredientes.add(new Ingrediente(getInt(a,"id"),getString(a,"nombre")));
        }

        return arrayIngredientes;
    }




}
