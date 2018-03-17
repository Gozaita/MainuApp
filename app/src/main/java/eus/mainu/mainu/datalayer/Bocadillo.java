package eus.mainu.mainu.datalayer;

import java.io.Serializable;
import java.util.ArrayList;

public class Bocadillo implements Serializable {

    private int id;
    private String nombre;
    private double precio;
    private double puntuacion;
    private ArrayList<Ingrediente> ingredientes = new ArrayList<>();
    private ArrayList<Valoracion> valoraciones  = new ArrayList<>();
    private ArrayList<Imagen> fotos             = new ArrayList<>();

    public Bocadillo() {
    }

    public Bocadillo(int id, String nombre, double precio, double puntuacion, ArrayList<Ingrediente> ingredientes) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.puntuacion = puntuacion;
        this.ingredientes = ingredientes;
    }

    public Bocadillo(int id, String nombre, double precio, double puntuacion, ArrayList<Ingrediente> ingredientes, ArrayList<Imagen> fotos, ArrayList<Valoracion> valoraciones) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.puntuacion = puntuacion;
        this.ingredientes = ingredientes;
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

    public ArrayList<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(ArrayList<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
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

