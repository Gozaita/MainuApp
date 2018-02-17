package eus.mainu.mainu.datalayer;

import java.util.ArrayList;

/**
 * Created by Manole on 16/02/2018.
 * Clase del modelo de datos Usuario
 */

public class Usuario {

    private int id;
    private String nombre;
    private String email;
    private String urlFoto;
    private boolean verificado;
    private ArrayList<ValoracionBocadillo> valoracionesBocadillos;
    private ArrayList<ValoracionPlato> valoracionesPlatos;
    private ArrayList<ValoracionComplemento> valoracionesComplementos;
    private ArrayList<FotoBocadillo> fotosBocadillos;
    private ArrayList<FotoPlato> fotosPlatos;
    private ArrayList<FotoComplemento> fotosComplementos;

    public Usuario(int id, String nombre, String email, String urlFoto, boolean verificado, ArrayList<ValoracionBocadillo> valoracionesBocadillos, ArrayList<ValoracionPlato> valoracionesPlatos, ArrayList<ValoracionComplemento> valoracionesComplementos, ArrayList<FotoBocadillo> fotosBocadillos, ArrayList<FotoPlato> fotosPlatos, ArrayList<FotoComplemento> fotosComplementos) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.urlFoto = urlFoto;
        this.verificado = verificado;
        this.valoracionesBocadillos = valoracionesBocadillos;
        this.valoracionesPlatos = valoracionesPlatos;
        this.valoracionesComplementos = valoracionesComplementos;
        this.fotosBocadillos = fotosBocadillos;
        this.fotosPlatos = fotosPlatos;
        this.fotosComplementos = fotosComplementos;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public boolean isVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }

    public ArrayList<ValoracionBocadillo> getValoracionesBocadillos() {
        return valoracionesBocadillos;
    }

    public void setValoracionesBocadillos(ArrayList<ValoracionBocadillo> valoracionesBocadillos) {
        this.valoracionesBocadillos = valoracionesBocadillos;
    }

    public ArrayList<ValoracionPlato> getValoracionesPlatos() {
        return valoracionesPlatos;
    }

    public void setValoracionesPlatos(ArrayList<ValoracionPlato> valoracionesPlatos) {
        this.valoracionesPlatos = valoracionesPlatos;
    }

    public ArrayList<ValoracionComplemento> getValoracionesComplementos() {
        return valoracionesComplementos;
    }

    public void setValoracionesComplementos(ArrayList<ValoracionComplemento> valoracionesComplementos) {
        this.valoracionesComplementos = valoracionesComplementos;
    }

    public ArrayList<FotoBocadillo> getFotosBocadillos() {
        return fotosBocadillos;
    }

    public void setFotosBocadillos(ArrayList<FotoBocadillo> fotosBocadillos) {
        this.fotosBocadillos = fotosBocadillos;
    }

    public ArrayList<FotoPlato> getFotosPlatos() {
        return fotosPlatos;
    }

    public void setFotosPlatos(ArrayList<FotoPlato> fotosPlatos) {
        this.fotosPlatos = fotosPlatos;
    }

    public ArrayList<FotoComplemento> getFotosComplementos() {
        return fotosComplementos;
    }

    public void setFotosComplementos(ArrayList<FotoComplemento> fotosComplementos) {
        this.fotosComplementos = fotosComplementos;
    }
}
