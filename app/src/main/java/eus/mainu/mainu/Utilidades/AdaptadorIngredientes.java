package eus.mainu.mainu.Utilidades;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.ArrayList;

import eus.mainu.mainu.ActivityMain;
import eus.mainu.mainu.R;
import eus.mainu.mainu.datalayer.Ingrediente;

public class AdaptadorIngredientes extends RecyclerView.Adapter<AdaptadorIngredientes.ViewHolder> {

	private final String TAG = "AdaptadorIngredientes";
	private Context mContext;
	private ArrayList<Ingrediente> ingredientes;


	public AdaptadorIngredientes(ArrayList<Ingrediente> ingredientes, Context mContext) {
		this.ingredientes = ingredientes;
		this.mContext = mContext;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(mContext).inflate(R.layout.recyclingview_ingredientes,
				parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, int position) {
		Log.d(TAG, "onBindViewHolder: called.");

		holder.checkbox.setText(ingredientes.get(position).getNombre());
		holder.checkbox.setChecked(ingredientes.get(position).isChecked());

		holder.checkbox.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ingredientes.get(holder.getAdapterPosition());
				String ing = ((AppCompatCheckBox) v).getText().toString();

				ingredientes.get(holder.getAdapterPosition()).setChecked(!ingredientes.
						get(holder.getAdapterPosition()).isChecked());

				ActivityMain a = (ActivityMain) mContext;
				a.filterByIngredient(ing);
			}
		});
	}

	@Override
	public int getItemCount() {
		return ingredientes.size();
	}

	class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {

		CheckBox checkbox;
		LinearLayout ingredientes_layout;

		private ViewHolder(View itemView) {
			super(itemView);
			checkbox = itemView.findViewById(R.id.ingredienteCheckBox);
			ingredientes_layout = itemView.findViewById(R.id.ingredientes_Layout);
		}
	}
}
