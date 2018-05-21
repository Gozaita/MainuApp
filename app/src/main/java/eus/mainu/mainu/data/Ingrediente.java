package eus.mainu.mainu.data;

import java.io.Serializable;

public class Ingrediente implements Serializable{

    private int id;
    private String nombre;
    private boolean checked;

    public Ingrediente(String nombre) {
        this.nombre = nombre;
    }

    public Ingrediente(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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

    public boolean isChecked() { return checked; }
    public void setChecked(boolean checked) { this.checked = checked; }

}
