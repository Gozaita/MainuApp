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

import eus.mainu.mainu.ActivityElemento;
import eus.mainu.mainu.R;
import eus.mainu.mainu.datalayer.Bocadillo;
import eus.mainu.mainu.datalayer.Ingrediente;

public class AdaptadorBocadillos extends RecyclerView.Adapter<AdaptadorBocadillos.ViewHolder> {

	private final String TAG = "AdaptadorBocadillos";
	private Context mContext;
	private ArrayList<Bocadillo> bocadillos;

	public AdaptadorBocadillos(ArrayList<Bocadillo> bocadillos, Context mContext) {
		this.bocadillos = bocadillos;
		this.mContext = mContext;
	}

	// Método utilizado para "inflar" el contexto
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
		}

		// onItemClick:
		holder.bocadillo_layout.setOnClickListener((new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "onClick: " + bocadillos.
						get(holder.getAdapterPosition()).getNombre());

				// --> Element Class
				Intent intent = new Intent(mContext, ActivityElemento.class);
				// Le pasamos la informacion que necesita la clase
				intent.putExtra("bocadillo", bocadillos.get(holder.getAdapterPosition()));
				// Iniciamos la actividad
				mContext.startActivity(intent);
			}
		}));
	}

	@Override
	public int getItemCount() {
		return bocadillos.size();
	}

	class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {

		TextView nombre;
		TextView ingredientes;
		TextView precio;
		TextView puntuacion;
		RelativeLayout bocadillo_layout;

		private ViewHolder(View itemView) {
			super(itemView);
			nombre = itemView.findViewById(R.id.nombreTextView);
			ingredientes = itemView.findViewById(R.id.descripcionTextView);
			precio = itemView.findViewById(R.id.precioTextView);
			puntuacion = itemView.findViewById(R.id.puntuacionTextView);
			bocadillo_layout = itemView.findViewById(R.id.bocadillo_layout);
		}
	}

	// Ingredientes --> String
	private String getIngredientes(int position) {

		StringBuilder ingredientes = new StringBuilder();
		String descripcion = "";
		int i;

		ArrayList<Ingrediente> arrayIngredientes = bocadillos.get(position).getIngredientes();

		for (i = 0; i < arrayIngredientes.size(); i++) {
			ingredientes.append(arrayIngredientes.get(i).getNombre()).append(", ");
		}

		if (!bocadillos.get(position).getIngredientes().isEmpty()) {
			ingredientes.setLength(ingredientes.length() - 2);
			descripcion = ingredientes.substring(0, 1).toUpperCase() +
					ingredientes.substring(1).toLowerCase();
		}

		return descripcion;
	}

}
