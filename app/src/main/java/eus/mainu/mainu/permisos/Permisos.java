package eus.mainu.mainu.permisos;


import android.Manifest;

/**
 * Created by narciso on 22/03/18.
 * Clase para solicitar los permisos a los usuarios
 */


public class Permisos {

    public static final String[] PERMISOS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };
}
