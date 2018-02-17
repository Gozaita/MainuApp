package eus.mainu.mainu.datalayer;

import java.util.ArrayList;

/**
 * Created by Manole on 16/02/2018.
 * Clase de Ingrediente
 */

public class Ingrediente {

    private int id;
    private String nombre;
    private ArrayList<Integer> id_bocadillos;

    public Ingrediente(int id, String nombre, ArrayList<Integer> id_bocadillos) {
        this.id = id;
        this.nombre = nombre;
        this.id_bocadillos = id_bocadillos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Integer> getId_bocadillos() {
        return id_bocadillos;
    }

    public void setId_bocadillos(ArrayList<Integer> id_bocadillos) {
        this.id_bocadillos = id_bocadillos;
    }
}
