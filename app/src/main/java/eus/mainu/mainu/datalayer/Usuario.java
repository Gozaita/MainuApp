package eus.mainu.mainu.datalayer;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Manole on 16/02/2018.
 * Clase del modelo de datos Usuario
 */

public class Usuario implements Serializable{

    private int id;
    private String nombre;
    private String foto;
    private boolean verificado;

    public Usuario(int id, String nombre, String foto, boolean verificado) {
        this.id = id;
        this.nombre = nombre;
        this.foto = foto;
        this.verificado = verificado;
    }

    public boolean isVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}