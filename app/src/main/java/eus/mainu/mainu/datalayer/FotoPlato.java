package eus.mainu.mainu.datalayer;

/**
 * Created by Manole on 17/02/2018.
 * Clase del modelo de datos Foto Plato
 */

public class FotoPlato {

    private int id;
    private String url;
    private boolean visible;
    private int id_plato;
    private int id_usuario;

    public FotoPlato(int id, String url, boolean visible, int id_plato, int id_usuario) {
        this.id = id;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
