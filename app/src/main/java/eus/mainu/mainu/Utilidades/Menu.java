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

    private ArrayList<Plato> primeros = new ArrayList<>();
    private ArrayList<Plato> segundos = new ArrayList<>();
    private ArrayList<Plato> postres = new ArrayList<>();


    public ArrayList<Plato> getPrimeros() {
        return primeros;
    }

    void setPrimeros(ArrayList<Plato> primeros) {
        this.primeros = primeros;
    }

    public ArrayList<Plato> getSegundos() {
        return segundos;
    }

    void setSegundos(ArrayList<Plato> segundos) {
        this.segundos = segundos;
    }

    public ArrayList<Plato> getPostres() {
        return postres;
    }

    void setPostres(ArrayList<Plato> postres) {
        this.postres = postres;
    }

    public ArrayList<Plato> getPlatos()
    {
        ArrayList<Plato> platos = new ArrayList<>();
        int i;

        for(i = 0; i < primeros.size(); i++)
        {
            platos.add(primeros.get(i));
        }

        for(i = 0; i < segundos.size(); i++)
        {
            platos.add(segundos.get(i));
        }

        for(i = 0; i < postres.size(); i++)
        {
            platos.add(postres.get(i));
        }

        return platos;
    }

    public ArrayList<Imagen> getImagenes(){

        ArrayList<Imagen> imagenes = new ArrayList<>();

        int i;

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
