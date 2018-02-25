package eus.mainu.mainu.Utilidades;

import java.util.ArrayList;

import eus.mainu.mainu.datalayer.Plato;

/**
 * Created by narciso on 24/02/18.
 * Clase para crear el menu del dia y adaptar el contenido
 */

public class Menu {

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

    public ArrayList<String> getNombrePrimeros()
    {
        ArrayList<String> nombres = new ArrayList<String>();

        for(int i = 0; i < primeros.size(); i++)
        {
            nombres.add(primeros.get(i).getNombre());
        }

        return nombres;
    }

    public ArrayList<String> getNombreSegundos()
    {
        ArrayList<String> nombres = new ArrayList<String>();

        for(int i = 0; i < segundos.size(); i++)
        {
            nombres.add(segundos.get(i).getNombre());
        }

        return nombres;
    }

    public ArrayList<String> getNombrePostres()
    {
        ArrayList<String> nombres = new ArrayList<String>();

        for(int i = 0; i < postres.size(); i++)
        {
            nombres.add(postres.get(i).getNombre());
        }

        return nombres;
    }

}
