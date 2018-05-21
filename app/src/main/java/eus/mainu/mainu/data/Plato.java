package eus.mainu.mainu.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Plato implements Serializable {

    private int id;
    private String nombre;
    private double puntuacion;
    private ArrayList<Valoracion> valoraciones  = new ArrayList<>();
    private ArrayList<Imagen> imagenes = new ArrayList<>();

    public Plato(){
    }

    public Plato(String nombre) {
        this.nombre = nombre;
    }

    public Plato(int id, String nombre, double puntuacion, ArrayList<Imagen> imagenes) {
        this.id = id;
        this.nombre = nombre;
        this.puntuacion = puntuacion;
        this.imagenes = imagenes;
    }

    public Plato(int id, String nombre, double puntuacion, ArrayList<Imagen> imagenes, ArrayList<Valoracion> valoraciones) {
        this.id = id;
        this.nombre = nombre;
        this.puntuacion = puntuacion;
        this.valoraciones = valoraciones;
        this.imagenes = imagenes;
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