package eus.mainu.mainu.datalayer;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Manole on 16/02/2018.
 * Clase del modelo de datos Complemento
 */

public class Complemento implements Serializable {

    private int id;
    private String nombre;
    private double precio;
    private double puntuacion;
    private ArrayList<Valoracion> valoraciones;
    private ArrayList<Imagen> fotos;

    public Complemento(int id, String nombre, double precio, double puntuacion, ArrayList<Imagen> fotos) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.puntuacion = puntuacion;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
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

