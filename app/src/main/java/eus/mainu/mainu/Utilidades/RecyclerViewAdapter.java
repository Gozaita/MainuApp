package eus.mainu.mainu.Utilidades;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import eus.mainu.mainu.R;
import eus.mainu.mainu.datalayer.Bocadillo;

/**
 * Created by narciso on 15/02/18.
 * Clase para la adaptar el contenido al recycling view
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    //Para debbuguear
    private static final String TAG = "RecyclerViewAdapter";
    ArrayList<Bocadillo> arrayBocadillos = new ArrayList<Bocadillo>();
    private Context mContext;


    public RecyclerViewAdapter(ArrayList<Bocadillo> arrayBocadillos, Context context) {
        this.arrayBocadillos = arrayBocadillos;
        mContext = context;
    }


    //Metodo que se utiliza para "Inflar" el contexto
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclingview_listitem, parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    //metodo importante que cambia en funcion del layout, sera llamado cada vez que
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG,"onBindViewHolder: called."); //Para debuguear

    /*
    Código para hacer la petición de las imagenes, no funciona y depende de librerías externas

        // Glide.with(mContext) //Coge el contexto
           //     .asBitmap() //Dice que es un bitmap
             //   .load(mImagenes.get(position))
              //  .into(holder.imagen);

        holder.nombre.setText(mNombres.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Log.d(TAG,"onClick: clicked on: "+ mImagenes.get(position));

                Toast.makeText(mContext,mImagenes.get(position), Toast.LENGTH_SHORT).show();
            }
        });*/

    holder.nombre.setText(arrayBocadillos.get(position).getNombre());
    holder.descripcion.setText(arrayBocadillos.get(position).getDescripcion());
    holder.precio.setText(Float.toString(arrayBocadillos.get(position).getPrecio()));



    }

    //Le dice al adaptador cuantos objetos tenemos en la lista, si devolvemos 0, no muestra ninguno
    @Override
    public int getItemCount() {
        return arrayBocadillos.size();
    }

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {

        TextView nombre;
        TextView descripcion;
        TextView precio;

        RelativeLayout parentLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombreTextView);
            descripcion = itemView.findViewById(R.id.descripcionTextView);
            precio = itemView.findViewById(R.id.precioTextView);
        }
    }

}
