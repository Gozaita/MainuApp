package eus.mainu.mainu.Utilidades;

import java.io.Serializable;
import java.util.ArrayList;

import eus.mainu.mainu.datalayer.Imagen;
import eus.mainu.mainu.datalayer.Plato;

/**
 * Created by narciso on 24/02/18.
 * Clase para crear el menu del dia y adaptar el contenido
 */

public class Menu implements Serializable{

    ArrayList<Plato> primeros = new ArrayList<>();
    ArrayList<Plato> segundos = new ArrayList<>();
    ArrayList<Plato> postres = new ArrayList<>();

    public ArrayList<Plato> getPrimeros() {
        return primeros;
    }

    public void setPrimeros(ArrayList<Plato> primeros) {
        this.primeros = primeros;
    }

    public ArrayList<Plato> getSegundos() {
        return segundos;
    }

    public void setSegundos(ArrayList<Plato> segundos) {
        this.segundos = segundos;
    }

    public ArrayList<Plato> getPostres() {
        return postres;
    }

    public void setPostres(ArrayList<Plato> postres) {
        this.postres = postres;
    }

    public ArrayList<String> getNombres()
    {
        ArrayList<String> nombres = new ArrayList<String>();
        int i;

        for(i = 0; i < primeros.size(); i++)
        {
            nombres.add(primeros.get(i).getNombre());
        }

        for(i = 0; i < segundos.size(); i++)
        {
            nombres.add(segundos.get(i).getNombre());
        }

        for(i = 0; i < postres.size(); i++)
        {
            nombres.add(postres.get(i).getNombre());
        }

        return nombres;
    }

    public ArrayList<Imagen> getImagenes(){

        ArrayList<Imagen> imagenes = new ArrayList<Imagen>();

        int i,j;

        //Cogemos las imagenes oficiales de los platos
        for(i = 0; i < primeros.size(); i++) {
            if(!primeros.get(i).getFotos().get(0).getRuta().isEmpty()){
                imagenes.add(primeros.get(i).getFotos().get(0));
            }
        }

        //Cogemos las imagenes oficiales de los platos
        for(i = 0; i < segundos.size(); i++) {
            if(!segundos.get(i).getFotos().get(0).getRuta().isEmpty()){
                imagenes.add(segundos.get(i).getFotos().get(0));
            }
        }

        //Cogemos las imagenes oficiales de los platos
        for(i = 0; i < postres.size(); i++) {
            if(!postres.get(i).getFotos().get(0).getRuta().isEmpty()){
                imagenes.add(postres.get(i).getFotos().get(0));
            }
        }

        return imagenes;
    }

}
