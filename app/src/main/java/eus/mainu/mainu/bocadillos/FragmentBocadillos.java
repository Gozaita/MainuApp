package eus.mainu.mainu.bocadillos;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.Arrays;

import eus.mainu.mainu.R;
import eus.mainu.mainu.data.Bocadillo;
import eus.mainu.mainu.data.Ingrediente;

public class FragmentBocadillos extends Fragment implements MenuItem.OnActionExpandListener {

	private final String TAG = "Bocadillos";

	// Layout
	private Context mContext;
	private RecyclerView vistaBocadillos;
	private RecyclerView vistaIngredientes;

	// Variables
	private ArrayList<Bocadillo> bocadillos = new ArrayList<>();
	private ArrayList<Ingrediente> ingredientes = new ArrayList<>();

	public FragmentBocadillos() {

	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.d(TAG, "onCreate: Bocadillos");

		mContext = getContext();

		Bundle bundle = this.getArguments();
		if (bundle != null) {
			bocadillos = (ArrayList<Bocadillo>) bundle.getSerializable("bocadillos");
			ingredientes = (ArrayList<Ingrediente>) bundle.getSerializable("ingredientes");
		}
	}

	public void uncheckIngredientes() {
		for (int i = 0; i < vistaIngredientes.getChildCount(); i++) {
			View mChild = vistaIngredientes.getChildAt(i);
			CheckBox mCheckBox = (CheckBox) mChild.findViewById(R.id.ingredienteCheckBox);
			mCheckBox.setChecked(false);
		}
	}

	// Se ejecuta al visualizar el fragmento
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_bocadillos, container, false);

		vistaBocadillos = view.findViewById(R.id.recycler_view_lista_bocadillos);
		vistaIngredientes = view.findViewById(R.id.recyclerView_ingredientes);

		// Bocadillos: línea debajo de cada objeto
		DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mContext,
				new LinearLayoutManager(mContext).getOrientation());
		vistaBocadillos.addItemDecoration(dividerItemDecoration);

		setIngredientes();
		setBocadillos();

		return view;
	}

	private void setIngredientes() {
		AdaptadorIngredientes adaptadorIng = new AdaptadorIngredientes(ingredientes,
				getActivity());

		vistaIngredientes.setAdapter(adaptadorIng);
		vistaIngredientes.setLayoutManager(new LinearLayoutManager(getActivity(),
				LinearLayoutManager.HORIZONTAL, false));
	}

	private void setBocadillos() {
		AdaptadorBocadillos adaptadorBoc = new AdaptadorBocadillos(bocadillos, getActivity());
		vistaBocadillos.setAdapter(adaptadorBoc);
		vistaBocadillos.setLayoutManager(new LinearLayoutManager(getActivity()));
	}

	public void setBocadillos(ArrayList<Bocadillo> filtrada) {
		this.bocadillos = filtrada;
		setBocadillos();
	}

	private void resetBocadillos() {
		AdaptadorBocadillos adaptadorBoc = new AdaptadorBocadillos(bocadillos, getActivity());
		vistaBocadillos.setAdapter(adaptadorBoc);
	}

	@Override
	public boolean onMenuItemActionExpand(MenuItem menuItem) {
		return true;
	}

	@Override
	public boolean onMenuItemActionCollapse(MenuItem menuItem) {
		return true;
	}
}
