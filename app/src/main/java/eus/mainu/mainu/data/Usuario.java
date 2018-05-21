package eus.mainu.mainu.data;

import java.io.Serializable;

public class Usuario implements Serializable{

    private int id;
    private String nombre;
    private String foto;
    private int verificado;

    public Usuario(int id, String nombre, String foto, int verificado) {
        this.id = id;
        this.nombre = nombre;
        this.foto = foto;
        this.verificado = verificado;
    }

    public int getVerificado() {
        return verificado;
    }

    public void setVerificado(int verificado) {
        this.verificado = verificado;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}