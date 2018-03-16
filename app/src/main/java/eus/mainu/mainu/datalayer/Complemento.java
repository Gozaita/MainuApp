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
    private int tipo;
    private ArrayList<Valoracion> valoraciones  = new ArrayList<>();
    private ArrayList<Imagen> fotos             = new ArrayList<>();

    public Complemento() {
    }

    public Complemento(int id, String nombre, double precio, double puntuacion, int tipo,ArrayList<Imagen> fotos) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.puntuacion = puntuacion;
        this.fotos = fotos;
        this.tipo = tipo;
    }

    public Complemento(int id, String nombre, double precio, double puntuacion, ArrayList<Imagen> fotos, ArrayList<Valoracion> valoraciones) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.puntuacion = puntuacion;
        this.valoraciones = valoraciones;
        this.fotos = fotos;
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

    public ArrayList<Imagen> getFotos() {
        return fotos;
    }

    public void setFotos(ArrayList<Imagen> fotos) {
        this.fotos = fotos;
    }
}

