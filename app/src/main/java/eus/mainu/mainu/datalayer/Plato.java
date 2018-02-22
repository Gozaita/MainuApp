package eus.mainu.mainu.datalayer;

import java.util.ArrayList;

/**
 * Created by Manole on 16/02/2018.
 * Clase del modelo de datos Plato
 */

public class Plato {

    private int id;
    private String nombre;
    private int tipo;
    private boolean actual;
    private String descripcion;
    private float valoracion;
    private ArrayList<FotoPlato> fotos;

    public Plato(int id, String nombre, float valoracion) {
        this.id = id;
        this.nombre = nombre;
        this.valoracion = valoracion;
    }

    public Plato(int id, String nombre, int tipo, boolean actual, String descripcion, float valoracion, ArrayList<FotoPlato> fotos) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.actual = actual;
        this.descripcion = descripcion;
        this.valoracion = valoracion;
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

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public boolean isActual() {
        return actual;
    }

    public void setActual(boolean actual) {
        this.actual = actual;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getValoracion() {
        return valoracion;
    }

    public void setValoracion(float valoracion) {
        this.valoracion = valoracion;
    }

    public ArrayList<FotoPlato> getFotos() {
        return fotos;
    }

    public void setFotos(ArrayList<FotoPlato> fotos) {
        this.fotos = fotos;
    }
}
