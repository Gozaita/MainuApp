package eus.mainu.mainu.datalayer;

/**
 * Created by Manole on 17/02/2018.
 */

public class ValoracionComplemento {

    private int id;
    private int puntuacion;
    private String texto;
    private boolean visible;
    private int id_complemento;
    private int id_usuario;

    public ValoracionComplemento(int id, int puntuacion, String texto, boolean visible, int id_complemento, int id_usuario) {
        this.id = id;
        this.puntuacion = puntuacion;
        this.texto = texto;
        this.visible = visible;
        this.id_complemento = id_complemento;
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

    public int getId_complemento() {
        return id_complemento;
    }

    public void setId_complemento(int id_complemento) {
        this.id_complemento = id_complemento;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
}
