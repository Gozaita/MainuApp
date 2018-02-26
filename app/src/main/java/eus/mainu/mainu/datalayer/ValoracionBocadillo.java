package eus.mainu.mainu.datalayer;

public class ValoracionBocadillo {

    private int id;
    private int puntuacion;
    private String texto;
    private boolean visible;
    private int id_bocadillo;
    private int id_usuario;

    public ValoracionBocadillo(int id, int puntuacion, String texto, boolean visible, int id_bocadillo, int id_usuario) {
        this.id = id;
        this.puntuacion = puntuacion;
        this.texto = texto;
        this.visible = visible;
        this.id_bocadillo = id_bocadillo;
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

    public int getId_bocadillo() {
        return id_bocadillo;
    }
    public void setId_bocadillo(int id_bocadillo) {
        this.id_bocadillo = id_bocadillo;
    }

    public int getId_usuario() {
        return id_usuario;
    }
    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
}
