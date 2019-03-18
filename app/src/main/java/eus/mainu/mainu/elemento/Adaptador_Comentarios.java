package eus.mainu.mainu.elemento;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import eus.mainu.mainu.R;
import eus.mainu.mainu.data.Valoracion;

/**
 * Created by Manole on 08/03/2018.
 * Clase para adaptar los comentarios de los usuarios
 */

public class Adaptador_Comentarios extends RecyclerView.Adapter<Adaptador_Comentarios.ViewHolder> {

    //Para debuggear
    private final String TAG = "Adaptador_Comentarios";
    private ArrayList<Valoracion> arrayValoraciones = new ArrayList<>();
    private Context mContext;

    public Adaptador_Comentarios(ArrayList<Valoracion> arrayValoraciones, Context context) {
        this.arrayValoraciones = arrayValoraciones;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclingview_comentarios, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Log.d(TAG, "onBindViewHolder: Called");


        try
        {
            String decode = URLDecoder.decode(arrayValoraciones.get(position).getComentario(),"UTF-8");
            holder.comentario.setText(decode);

            Log.e("UTF 8",decode);
        }
        catch (UnsupportedEncodingException e)
        {
            Log.e("utf8", "conversion", e);
        }

        holder.nombre.setText(arrayValoraciones.get(position).getUsuario().getNombre());
        holder.estrellas.setRating((float) arrayValoraciones.get(position).getPuntuacion());

        //Ponemos la imagen del usuario circular
        Picasso.with(mContext).load(arrayValoraciones.get(position).getUsuario().getFoto()).fit().into(holder.foto, new Callback() {
            @Override
            public void onSuccess() {
                Bitmap imageBitmap = ((BitmapDrawable) holder.foto.getDrawable()).getBitmap();
                RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(mContext.getResources(), imageBitmap);
                imageDrawable.setCircular(true);
                imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                holder.foto.setImageDrawable(imageDrawable);
            }

            @Override
            public void onError() {
                holder.foto.setImageResource(R.drawable.logo_base);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayValoraciones.size();
    }

    class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {

        TextView comentario;
        ImageButton foto;
        TextView nombre;
        RatingBar estrellas;

        ViewHolder(View itemView) {
            super(itemView);
            comentario = itemView.findViewById(R.id.comentarioUsuario);
            foto = itemView.findViewById(R.id.fotoUsuario);
            nombre = itemView.findViewById(R.id.nombreUsuario);
            estrellas = itemView.findViewById(R.id.puntuacionUsuario);
            //estrella = itemView.findViewById(R.id.estrella);
        }
    }


}
