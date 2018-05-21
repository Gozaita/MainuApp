package eus.mainu.mainu.otros;

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
import java.util.Locale;

import eus.mainu.mainu.elemento.ActivityElemento;
import eus.mainu.mainu.R;
import eus.mainu.mainu.data.Otro;

public class AdaptadorOtros extends RecyclerView.Adapter<AdaptadorOtros.ViewHolder> {

	private final String TAG = "AdaptadorOtros";
	private Context mContext;
	private ArrayList<Otro> otros = new ArrayList<>();

	public AdaptadorOtros(ArrayList<Otro> otros, Context mContext) {
		this.otros = otros;
		this.mContext = mContext;
	}

	// Método utilizado para "inflar" el contexto
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(mContext).inflate(R.layout.layout_grid_item,
				parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, int position) {
		/**
		 * 1. Se ponen el nombre y el precio.
		 * 2. Si hay puntuación --> Se añade
		 * 	  Si no hay puntuación --> Se pone N/A
		 * 3. Si hay fotos --> Se pone la primera (oficial)
		 * 	  Si no hay fotos --> Se pone la imagen por defecto
		 */
		holder.nombre.setText(otros.get(position).getNombre());
		holder.precio.setText(String.format(Locale.getDefault(), "%.2f€",
				otros.get(position).getPrecio()));

		if (otros.get(position).getPuntuacion() != 0) {
			holder.puntuacion.setText(String.format(Locale.getDefault(), "%.1f",
					otros.get(position).getPuntuacion()));
		} else {
			holder.puntuacion.setText("N/A");
		}

		if (!otros.get(position).getImagenes().isEmpty()) {
			Picasso.with(mContext)
					.load(otros.get(position).getImagenes().get(0).getRuta())
					.fit()
					.centerCrop()
					.into(holder.imagen);
		} else {
			Picasso.with(mContext)
					.load(R.drawable.deafult_otros)
					.fit()
					.centerCrop()
					.into(holder.imagen);
		}

		// onItemClick:
		holder.otro_layout.setOnClickListener((new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "onClick: " + otros.
						get(holder.getAdapterPosition()).getNombre());

				// --> Element Class
				Intent intent = new Intent(mContext, ActivityElemento.class);
				// Le pasamos la informacion que necesita la clase
				intent.putExtra("otro", otros.get(holder.getAdapterPosition()));
				// Iniciamos la actividad
				mContext.startActivity(intent);
			}
		}));
	}

	@Override
	public int getItemCount() {
		return otros.size();
	}

	class ViewHolder extends RecyclerView.ViewHolder {

		ImageView imagen;
		TextView nombre;
		TextView precio;
		TextView puntuacion;
		RelativeLayout otro_layout;

		ViewHolder(View itemView) {
			super(itemView);
			this.imagen = itemView.findViewById(R.id.imagen_card);
			this.nombre = itemView.findViewById(R.id.nombre_card);
			this.precio = itemView.findViewById(R.id.precio_card);
			this.puntuacion = itemView.findViewById(R.id.puntuacionTextView);
			this.otro_layout = itemView.findViewById(R.id.complemento_layout);
		}

	}
}
