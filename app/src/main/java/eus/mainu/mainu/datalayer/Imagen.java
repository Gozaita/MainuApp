package eus.mainu.mainu.datalayer;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by narciso on 2/03/18.
 * Clase que contiene la ruta de las imagenes
 */

public class Imagen implements Serializable{

    private int id;
    private String ruta;

    public Imagen(int id, String ruta) {
        this.id = id;
        this.ruta = ruta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
}
