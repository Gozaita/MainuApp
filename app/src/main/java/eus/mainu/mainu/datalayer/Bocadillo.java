package eus.mainu.mainu.datalayer;

import java.util.ArrayList;

/**
 * Created by Manole on 16/02/2018.
 * Clase de Bocadillo
 */

public class Bocadillo {

    private int id;
    private String nombre;
    private String descripcion;
    private float precio;
    private ArrayList<Ingrediente> ingredientes;
    private ArrayList<ValoracionBocadillo> valoraciones;
    private ArrayList<FotoBocadillo> fotos;

    public Bocadillo(int id, String nombre, String descripcion, float precio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public Bocadillo(int id, String nombre, String descripcion, float precio, ArrayList<Ingrediente> ingredientes, ArrayList<ValoracionBocadillo> valoraciones, ArrayList<FotoBocadillo> fotos) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public ArrayList<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(ArrayList<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public ArrayList<ValoracionBocadillo> getValoraciones() {
        return valoraciones;
    }

    public void setValoraciones(ArrayList<ValoracionBocadillo> valoraciones) {
        this.valoraciones = valoraciones;
    }

    public ArrayList<FotoBocadillo> getFotos() {
        return fotos;
    }

    public void setFotos(ArrayList<FotoBocadillo> fotos) {
        this.fotos = fotos;
    }
}
