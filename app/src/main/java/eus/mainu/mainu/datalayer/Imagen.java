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
    private boolean visible;
    private boolean oficial;

    public Imagen(int id, String ruta, boolean visible, boolean oficial) {
        this.id = id;
        this.ruta = ruta;
        this.visible = visible;
        this.oficial = oficial;
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isOficial() {
        return oficial;
    }

    public void setOficial(boolean oficial) {
        this.oficial = oficial;
    }
}
