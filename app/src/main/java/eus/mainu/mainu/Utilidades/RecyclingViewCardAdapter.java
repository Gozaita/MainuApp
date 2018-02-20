package eus.mainu.mainu.Utilidades;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import eus.mainu.mainu.R;

/**
 * Created by Manole on 20/02/2018.
 * Clase para inflar las cards del recycling view
 */

public class RecyclingViewCardAdapter extends RecyclerView.Adapter<RecyclingViewCardAdapter.ViewHolder> {

    private static final String TAG = "RecyclingViewCardAdapte";

    private ArrayList<String> nombres = new ArrayList<>();
    private ArrayList<String> precios = new ArrayList<>();
    private Context mContext;


    public RecyclingViewCardAdapter(Context mContext) {
        //this.nombres = nombres;
        //this.precios = precios;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_grid_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
    return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.nombre.setText("NombrePrueba");
        holder.precio.setText("6.50");
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        //ImageView imagen;
        TextView nombre;
        TextView precio;

        public ViewHolder(View itemView) {
            super(itemView);
            //this.imagen = imagen;
            this.nombre = itemView.findViewById(R.id.nombre_card);
            this.precio = itemView.findViewById(R.id.precio_card);
        }

    }
}
