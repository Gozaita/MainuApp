package eus.mainu.mainu.datalayer;

import java.util.ArrayList;

/**
 * Created by Manole on 16/02/2018.
 * Clase del modelo de datos Complemento
 */

public class Complemento {

    private int id;
    private String nombre;
    private float precio;
    private ArrayList<ValoracionComplemento> valoraciones;
    private ArrayList<FotoComplemento> fotos;

    public Complemento(int id, String nombre, float precio, ArrayList<ValoracionComplemento> valoraciones, ArrayList<FotoComplemento> fotos) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
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

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public ArrayList<ValoracionComplemento> getValoraciones() {
        return valoraciones;
    }

    public void setValoraciones(ArrayList<ValoracionComplemento> valoraciones) {
        this.valoraciones = valoraciones;
    }

    public ArrayList<FotoComplemento> getFotos() {
        return fotos;
    }

    public void setFotos(ArrayList<FotoComplemento> fotos) {
        this.fotos = fotos;
    }
}
