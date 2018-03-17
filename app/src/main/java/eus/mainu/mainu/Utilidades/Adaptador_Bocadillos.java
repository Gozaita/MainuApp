package eus.mainu.mainu.Utilidades;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import eus.mainu.mainu.Activity_Elemento;
import eus.mainu.mainu.R;
import eus.mainu.mainu.datalayer.Bocadillo;
import eus.mainu.mainu.datalayer.Ingrediente;

/**
 * Created by narciso on 15/02/18.
 * Clase para la adaptar el contenido al recycling view
 */

public class Adaptador_Bocadillos extends RecyclerView.Adapter<Adaptador_Bocadillos.ViewHolder> {

    //Globales
    private static final String TAG = "Adaptador_Bocadillos";

    //Variables
    private Context mContext;
    private ArrayList<Bocadillo> arrayBocadillos = new ArrayList<>();


    public Adaptador_Bocadillos(ArrayList<Bocadillo> arrayBocadillos, Context context) {
        this.arrayBocadillos = arrayBocadillos;
        mContext = context;
    }


    //Metodo que se utiliza para "Inflar" el contexto
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclingview_listitem, parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG,"onBindViewHolder: called."); //Para debuguear

    holder.nombre.setText(arrayBocadillos.get(position).getNombre());
    holder.ingredientes.setText(getIngredientes(position));
    holder.precio.setText(String.format(Locale.getDefault(),"%.2f€",arrayBocadillos.get(position).getPrecio()));
    if(arrayBocadillos.get(position).getPuntuacion() != 0){
        holder.puntuacion.setText(String.format(Locale.getDefault(),"%.1f",arrayBocadillos.get(position).getPuntuacion()));
    }
    else {
        holder.puntuacion.setText("N/A");
    }

    //Accion que se ejecuta al pulsar en un objeto de la lista
    holder.bocadillo_layout.setOnClickListener((new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: "+ arrayBocadillos.get(position).getNombre());

            //Decimos que queremos navegar a la clase Elemento
            Intent intent = new Intent(mContext, Activity_Elemento.class);
            //Le pasamos la informacion que necesita la clase
            intent.putExtra("Bocadillo",arrayBocadillos.get(position));
            //Iniciamos la actividad
            mContext.startActivity(intent);

        }
    }));
    }

    //Le dice al adaptador cuantos objetos tenemos en la lista, si devolvemos 0, no muestra ninguno
    @Override
    public int getItemCount() {
        return arrayBocadillos.size();
    }

    class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {

        TextView nombre;
        TextView ingredientes;
        TextView precio;
        TextView puntuacion;
        //ImageView estrella;
        RelativeLayout bocadillo_layout;

        private ViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombreTextView);
            ingredientes = itemView.findViewById(R.id.descripcionTextView);
            precio = itemView.findViewById(R.id.precioTextView);
            puntuacion = itemView.findViewById(R.id.puntuacionTextView);
            bocadillo_layout = itemView.findViewById(R.id.bocadillo_layout);
            //estrella = itemView.findViewById(R.id.estrella);
        }
    }

    //Metodo para poner los ingredientes en String
    private String getIngredientes(int position){

        StringBuilder ingredientes = new StringBuilder();
        String descripcion = "";
        int i;

        ArrayList<Ingrediente> arrayIngredientes = arrayBocadillos.get(position).getIngredientes();

        for(i = 0; i < arrayIngredientes.size();i++){
            ingredientes.append(arrayIngredientes.get(i).getNombre()).append(", ");
        }

        if(!arrayBocadillos.get(position).getIngredientes().isEmpty()){
            ingredientes.setLength(ingredientes.length()-2);
            descripcion = ingredientes.substring(0, 1).toUpperCase() + ingredientes.substring(1).toLowerCase();
        }


        return descripcion;
    }

}
