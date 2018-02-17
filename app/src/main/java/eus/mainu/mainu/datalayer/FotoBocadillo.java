package eus.mainu.mainu.datalayer;

/**
 * Created by Manole on 17/02/2018.
 * Clase del modelo de datos Foto Bocadillo
 */

public class FotoBocadillo {

    private int id;
    private String url;
    private boolean visible;
    private int id_complemento;
    private int id_usuario;


    public FotoBocadillo(int id, String url, boolean visible, int id_complemento, int id_usuario) {
        this.id = id;
        this.url = url;
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
