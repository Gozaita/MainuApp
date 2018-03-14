package eus.mainu.mainu.Utilidades;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import eus.mainu.mainu.datalayer.Bocadillo;
import eus.mainu.mainu.datalayer.Complemento;

public class Administrador_Cache {

    public void guardarLastUpdate (Context context, String tipo, String fecha) {

        try {
            FileOutputStream fos = context.openFileOutput("lastUpdate" + tipo, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream (fos);
            oos.writeObject(fecha);
            oos.close();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String leerLastUpdate (Context context, String tipo) {

        Object object = null;
        String fichero = "lastUdate"+tipo;
        try {
            FileInputStream fis = context.openFileInput(fichero);
            ObjectInputStream ois = new ObjectInputStream(fis);
            object = ois.readObject();
            ois.close();
            fis.close();


        } catch (Exception e) {
            guardarLastUpdate(context, tipo, "0");
            object = "0";
            e.printStackTrace();
        }

        return object.toString();
    }

    //**********************************************************************************************
    public void guardarListaBocadillos (Context context, ArrayList<Bocadillo> arrayBocadillos) {

        try {
            FileOutputStream fos = context.openFileOutput("arrayBocadillos", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream (fos);
            oos.writeObject(arrayBocadillos);
            oos.close ();
            fos.close ();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object leerListaBocadillos (Context context) {

        Object object = new ArrayList<Bocadillo>();
        try {
            FileInputStream fis = context.openFileInput("arrayBocadillos");
            ObjectInputStream ois = new ObjectInputStream(fis);
            object = ois.readObject();
            ois.close();
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return object;
    }

    //**********************************************************************************************
    public void guardarListaOtros (Context context, ArrayList<Complemento> listaOtros) {

        try {
            FileOutputStream fos = context.openFileOutput("arrayOtros", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream (fos);
            oos.writeObject(listaOtros);
            oos.close();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object leerListaOtros (Context context) {

        Object listaOtros = new ArrayList<Complemento>();
        try {
            FileInputStream fis = context.openFileInput("arrayOtros");
            ObjectInputStream ois = new ObjectInputStream(fis);
            listaOtros = ois.readObject();
            ois.close();
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaOtros;
    }
}
