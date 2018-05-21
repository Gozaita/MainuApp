package eus.mainu.mainu.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Otro implements Serializable {

    private int id;
    private String nombre;
    private double precio;
    private double puntuacion;
    private int tipo;
    private ArrayList<Valoracion> valoraciones  = new ArrayList<>();
    private ArrayList<Imagen> imagenes = new ArrayList<>();

    public Otro() {
    }

    public Otro(int id, String nombre, double precio, double puntuacion, int tipo, ArrayList<Imagen> imagenes) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.puntuacion = puntuacion;
        this.imagenes = imagenes;
        this.tipo = tipo;
    }

    public Otro(int id, String nombre, double precio, double puntuacion, ArrayList<Imagen> imagenes, ArrayList<Valoracion> valoraciones) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.puntuacion = puntuacion;
        this.valoraciones = valoraciones;
        this.imagenes = imagenes;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
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

    public ArrayList<Imagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(ArrayList<Imagen> imagenes) {
        this.imagenes = imagenes;
    }
}

