package eus.mainu.mainu.inicio;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import eus.mainu.mainu.data.Bocadillo;
import eus.mainu.mainu.data.Ingrediente;
import eus.mainu.mainu.data.Otro;

public class CacheAdmin {

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

        Object object;
        String fichero = "lastUpdate"+tipo;
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
    public void guardarListaOtros (Context context, ArrayList<Otro> listaOtros) {

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

        Object listaOtros = new ArrayList<Otro>();
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

    //**********************************************************************************************
    public void guardarListaIngredientes (Context context, ArrayList<Ingrediente> listaIngredientes) {

        try {
            FileOutputStream fos = context.openFileOutput("arrayIngredientes", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream (fos);
            oos.writeObject(listaIngredientes);
            oos.close();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object leerListaIngredientes (Context context) {

        Object listaIngredientes = new ArrayList<Ingrediente>();
        try {
            FileInputStream fis = context.openFileInput("arrayIngredientes");
            ObjectInputStream ois = new ObjectInputStream(fis);
            listaIngredientes = ois.readObject();
            ois.close();
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaIngredientes;
    }



}
