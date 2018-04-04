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
    private double puntuacion;
    private ArrayList<Valoracion> valoraciones  = new ArrayList<>();
    private ArrayList<Imagen> fotos             = new ArrayList<>();

    public Plato(){
    }

    public Plato(String nombre) {
        this.nombre = nombre;
    }

    public Plato(int id, String nombre, double puntuacion, ArrayList<Imagen> fotos) {
        this.id = id;
        this.nombre = nombre;
        this.puntuacion = puntuacion;
        this.fotos = fotos;
    }

    public Plato(int id, String nombre, double puntuacion, ArrayList<Imagen> fotos, ArrayList<Valoracion> valoraciones) {
        this.id = id;
        this.nombre = nombre;
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