package eus.mainu.mainu.datalayer;

/**
 * Created by Manole on 17/02/2018.
 */

public class ValoracionPlato {

    private int id;
    private int puntuacion;
    private String texto;
    private boolean visible;
    private int id_plato;
    private int id_usuario;

    public ValoracionPlato(int id, int puntuacion, String texto, boolean visible, int id_plato, int id_usuario) {
        this.id = id;
        this.puntuacion = puntuacion;
        this.texto = texto;
        this.visible = visible;
        this.id_plato = id_plato;
        this.id_usuario = id_usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getId_plato() {
        return id_plato;
    }

    public void setId_plato(int id_plato) {
        this.id_plato = id_plato;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
}
