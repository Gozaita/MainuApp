package eus.mainu.mainu.Utilidades;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import eus.mainu.mainu.Activity_Elemento;
import eus.mainu.mainu.R;
import eus.mainu.mainu.datalayer.Complemento;

/**
 * Created by Manole on 20/02/2018.
 * Clase para inflar las cards del recycling view
 */

public class RecyclingViewCardAdapter extends RecyclerView.Adapter<RecyclingViewCardAdapter.ViewHolder> {

    private static final String TAG = "RecyclingViewCardAdapte";

    private ArrayList<Complemento> arrayComplementos = new ArrayList<>();
    private Context mContext;


    public RecyclingViewCardAdapter(Context mContext,ArrayList<Complemento> arrayComplementos) {
        this.arrayComplementos = arrayComplementos;
        this.mContext = mContext;
    }

    //Metodo que se utiliza para "Inflar" el contexto
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_grid_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
    return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {

        holder.nombre.setText(arrayComplementos.get(position).getNombre());
        holder.precio.setText(String.format("%.2f€",arrayComplementos.get(position).getPrecio()));
        holder.puntuacion.setText(String.format("%.1f",arrayComplementos.get(position).getPuntuacion()));


        //Accion que se ejecuta al pulsar en un objeto de la lista
        holder.complemento_layout.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+ arrayComplementos.get(position).getNombre());

                //Decimos que queremos navegar a la clase Elemento
                Intent intent = new Intent(mContext, Activity_Elemento.class);
                //Le pasamos la informacion que necesita la clase
                intent.putExtra("Complemento",arrayComplementos.get(position));
                //Iniciamos la actividad
                mContext.startActivity(intent);

            }
        }));
    }

    //Le dice al adaptador cuantos objetos tenemos en la lista, si devolvemos 0, no muestra ninguno
    @Override
    public int getItemCount() {
        return arrayComplementos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imagen;
        //ImageView estrella;
        TextView nombre;
        TextView precio;
        TextView puntuacion;
        RelativeLayout complemento_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imagen = itemView.findViewById(R.id.imagen_card);
            //this.estrella = itemView.findViewById(R.id.estrella);
            this.nombre = itemView.findViewById(R.id.nombre_card);
            this.precio = itemView.findViewById(R.id.precio_card);
            this.puntuacion = itemView.findViewById(R.id.puntuacionTextView);
            this.complemento_layout = itemView.findViewById(R.id.complemento_layout);
        }

    }
}
