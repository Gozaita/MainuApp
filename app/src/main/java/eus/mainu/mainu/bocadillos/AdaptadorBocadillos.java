package eus.mainu.mainu.bocadillos;

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

import eus.mainu.mainu.elemento.ActivityElemento;
import eus.mainu.mainu.R;
import eus.mainu.mainu.data.Bocadillo;
import eus.mainu.mainu.data.Ingrediente;

public class AdaptadorBocadillos extends RecyclerView.Adapter<AdaptadorBocadillos.ViewHolder> {

	private final String TAG = "AdaptadorBocadillos";
	private Context mContext;
	private ArrayList<Bocadillo> bocadillos;

	public AdaptadorBocadillos(ArrayList<Bocadillo> bocadillos, Context mContext) {
		this.bocadillos = bocadillos;
		this.mContext = mContext;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(mContext).inflate(R.layout.recyclingview_listitem,
				parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, int position) {
		holder.nombre.setText(bocadillos.get(position).getNombre());
		holder.ingredientes.setText(getIngredientes(position));
		holder.precio.setText(String.format(Locale.getDefault(), "%.2f€",
				bocadillos.get(position).getPrecio()));
		if (bocadillos.get(position).getPuntuacion() != 0) {
			holder.puntuacion.setText(String.format(Locale.getDefault(), "%.1f",
					bocadillos.get(position).getPuntuacion()));
		} else {
			holder.puntuacion.setText("N/A");

		}holder.layout.setOnClickListener((new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(TAG, "onClick: " + bocadillos.
						get(holder.getAdapterPosition()).getNombre());

				Intent intent = new Intent(mContext, ActivityElemento.class);
				intent.putExtra("bocadillo", bocadillos.get(holder.getAdapterPosition()));
				mContext.startActivity(intent);
			}
		}));
	}

	@Override
	public int getItemCount() {
		return bocadillos.size();
	}

	private String getIngredientes(int position) {

		StringBuilder ing = new StringBuilder();
		String descripcion = "";
		int i;

		ArrayList<Ingrediente> ingredientes = bocadillos.get(position).getIngredientes();

		for (i = 0; i < ingredientes.size(); i++) {
			ing.append(ingredientes.get(i).getNombre()).append(", ");
		}

		if (!bocadillos.get(position).getIngredientes().isEmpty()) {
			ing.setLength(ing.length() - 2);
			descripcion = ing.substring(0, 1).toUpperCase() +
					ing.substring(1).toLowerCase();
		}

		return descripcion;
	}

	class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {

		TextView nombre;
		TextView ingredientes;
		TextView precio;
		TextView puntuacion;
		RelativeLayout layout;

		private ViewHolder(View itemView) {
			super(itemView);
			nombre = itemView.findViewById(R.id.nombreTextView);
			ingredientes = itemView.findViewById(R.id.descripcionTextView);
			precio = itemView.findViewById(R.id.precioTextView);
			puntuacion = itemView.findViewById(R.id.puntuacionTextView);
			layout = itemView.findViewById(R.id.bocadillo_layout);
		}
	}

}
