package eus.mainu.mainu.datalayer;

import java.io.Serializable;

/**
 * Created by narciso on 2/03/18.
 * Clase que contiene la valoracion de los usuarios
 */

public class Valoracion implements Serializable{

    private int id;
    private String nombre;
    private double puntuacion;
    private String comentario;
    private Usuario usuario;

    public Valoracion(int id, String nombre, double puntuacion, String comentario) {
        this.id = id;
        this.nombre = nombre;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
        this.usuario = usuario;
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

    public double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
