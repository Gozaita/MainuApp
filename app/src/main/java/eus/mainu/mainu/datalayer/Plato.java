package eus.mainu.mainu.datalayer;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Manole on 16/02/2018.
 * Clase del modelo de datos Plato
 */

public class Plato implements Serializable {

    private int id;
    private String nombre;
    private String descripcion;
    private double puntuacion;
    private ArrayList<Valoracion> valoraciones;
    private ArrayList<Imagen> fotos;

    public Plato(int id, String nombre, String descripcion, double puntuacion, ArrayList<Imagen> fotos) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.puntuacion = puntuacion;
        this.fotos = fotos;
    }

    public Plato(int id, String nombre, String descripcion, double puntuacion, ArrayList<Valoracion> valoraciones, ArrayList<Imagen> fotos) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.puntuacion = puntuacion;
        this.valoraciones = valoraciones;
        this.fotos = fotos;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public ArrayList<Valoracion> getValoraciones() {
        return valoraciones;
    }

    public void setValoraciones(ArrayList<Valoracion> valoraciones) {
        this.valoraciones = valoraciones;
    }

    public ArrayList<Imagen> getFotos() {
        return fotos;
    }

    public void setFotos(ArrayList<Imagen> fotos) {
        this.fotos = fotos;
    }
}