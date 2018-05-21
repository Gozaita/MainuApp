package eus.mainu.mainu.imagenes;

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
import eus.mainu.mainu.data.Imagen;

public class AdaptadorImagenes extends PagerAdapter {

	private ArrayList<Imagen> imagenes = new ArrayList<>();
	private Context mContext;
	private LayoutInflater mLayoutInflater;
	private boolean vacio = false;
	private int tipo;

	public AdaptadorImagenes(ArrayList<Imagen> imagenes, Context mContext, int tipo) {
		this.imagenes = imagenes;
		this.mContext = mContext;
		this.tipo = tipo;
	}

	@Override
	public int getCount() {
		if (imagenes.isEmpty()) {
			vacio = true;
			return 1;
		} else {
			return imagenes.size();
		}
	}

	@Override
	public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
		return (view == object);
	}

	@NonNull
	@Override
	public Object instantiateItem(@NonNull ViewGroup container, int position) {
		mLayoutInflater = (LayoutInflater) mContext.
				getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = mLayoutInflater.inflate(R.layout.layout_swipe_image, container,
				false);
		ImageView imageView = view.findViewById(R.id.imagenSwipe);
		if (vacio) {
			if (tipo == 1) {
				Picasso.with(mContext)
						.load(R.drawable.deafult_image)
						.fit()
						.centerCrop()
						.into(imageView);
			} else {
				Picasso.with(mContext)
						.load(R.drawable.deafult_menu)
						.fit()
						.centerCrop()
						.into(imageView);
			}
		} else {
			if (imagenes.get(position).getRuta() != "noImagen") {
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
		container.removeView((LinearLayout) object);
	}
}
