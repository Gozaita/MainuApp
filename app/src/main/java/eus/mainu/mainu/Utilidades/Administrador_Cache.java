package eus.mainu.mainu.Utilidades;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import eus.mainu.mainu.datalayer.Bocadillo;

/**
 * Created by Manole on 28/02/2018.
 * Clase para administrar la cache
 */

public class Administrador_Cache {

    //Metodo para guardar el array
    public void guardarArrayBocadillos (Context context, String nombreArchivo, ArrayList<Bocadillo> arrayBocadillos) {

        String tempFile = null;
        for (Bocadillo file : arrayBocadillos) {
            FileOutputStream fos = null;
            try {
                fos = context.openFileOutput (nombreArchivo, Context.MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream (fos);
                oos.writeObject (arrayBocadillos);
                oos.close ();
                fos.close ();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    //Método para leer cualquier objeto de Cache
    public Object leeObjetoCache (Context context, String nombreArchivo) {

        FileInputStream fis = null;
        Object object = null;
        try {
            fis = context.openFileInput (nombreArchivo);
            ObjectInputStream ois = new ObjectInputStream (fis);
            object = ois.readObject ();
            return object;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }
}
