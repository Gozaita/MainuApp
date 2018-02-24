package eus.mainu.mainu.datalayer;

import java.util.ArrayList;

/**
 * Created by narciso on 24/02/18.
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
}
