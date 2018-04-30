package eus.mainu.mainu.Utilidades;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import eus.mainu.mainu.R;
import eus.mainu.mainu.datalayer.Imagen;

/**
 * Created by narciso on 22/03/18.
 * Adaptador para hacer swipe de las imagenes
 */

public class Adaptador_Imagenes_Swipe extends PagerAdapter {

    private ArrayList<Imagen> imagenes = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private boolean vacio = false;
    private int tipo;

    public Adaptador_Imagenes_Swipe(ArrayList<Imagen> imagenes, Context mContext, int tipo) {
        this.imagenes= imagenes;
        this.mContext = mContext;
        this.tipo = tipo;
    }

    @Override
    public int getCount() {

        if(imagenes.isEmpty()){
            vacio = true;
            return 1;
        } else {
            return imagenes.size();

        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mLayoutInflater.inflate(R.layout.layout_swipe_image,container,false);
        ImageView imageView = view.findViewById(R.id.imagenSwipe);

        // PICASO....
        if(vacio){
            if(tipo == 1){
                Picasso.with(mContext)
                        .load(R.drawable.deafult_image)
                        .fit()
                        .centerCrop()
                        .into(imageView);
            }else {
                Picasso.with(mContext)
                        .load(R.drawable.deafult_menu)
                        .fit()
                        .centerCrop()
                        .into(imageView);
            }
        } else {
            if(imagenes.get(position).getRuta() != "noImagen"){
                Picasso.with(mContext)
                        .load(imagenes.get(position).getRuta())
                        .fit()
                        .centerCrop()
                        .into(imageView);
            } else {
                Picasso.with(mContext)
                        .load(R.drawable.deafult_image)
                        .fit()
                        .centerCrop()
                        .into(imageView);
            }
        }


        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((LinearLayout)object);

    }
}
