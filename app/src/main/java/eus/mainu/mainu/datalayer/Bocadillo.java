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
    private ArrayList<String> urlValoracion;
    private ArrayList<String> urlFoto;


    public Bocadillo(int id, String nombre, String descripcion, float precio, ArrayList<Ingrediente> ingredientes, ArrayList<String> urlValoracion, ArrayList<String> urlFoto) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.ingredientes = ingredientes;
        this.urlValoracion = urlValoracion;
        this.urlFoto = urlFoto;
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

    public ArrayList<String> getUrlValoracion() {
        return urlValoracion;
    }

    public void setUrlValoracion(ArrayList<String> urlValoracion) {
        this.urlValoracion = urlValoracion;
    }

    public ArrayList<String> getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(ArrayList<String> urlFoto) {
        this.urlFoto = urlFoto;
    }
}
