package eus.mainu.mainu.otros;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import eus.mainu.mainu.R;
import eus.mainu.mainu.conexion.HttpGetRequest;
import eus.mainu.mainu.data.Otro;

public class FragmentOtros extends Fragment {

	private final String TAG = "Otros";
	private final int NUM_COLUMNS = 2;   // Columnas del CardView

	// Layout
	private Context mContext;
	private SwipeRefreshLayout swipe;
	private RecyclerView vistaOtros;

	ArrayList<Otro> otros = new ArrayList<>();

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.d(TAG, "onCreate: Otros");

		mContext = getContext();
		Bundle bundle = this.getArguments();
		if (bundle != null) {
			otros = (ArrayList<Otro>) bundle.getSerializable("otros");
		}
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_otros, container, false);

		// Cogemos los elementos del layout
		vistaOtros = view.findViewById(R.id.rec1);
		swipe = view.findViewById(R.id.swipeComplementos);

		// Poner swipeToRefresh
		swipe.setColorSchemeColors(getResources().getColor(R.color.blue));

		// Inflar vista
		setOtros();

		// Escuchar swipeToRefresh
		listenToSwipe();

		return view;
	}

	private void setOtros() {
		/**
		 * Rellena el cardView
		 */

		AdaptadorOtros adaptador = new AdaptadorOtros(otros, getActivity());

		StaggeredGridLayoutManager cardAdapter = new StaggeredGridLayoutManager(
				NUM_COLUMNS, LinearLayoutManager.VERTICAL);

		vistaOtros.setLayoutManager(cardAdapter);
		vistaOtros.setAdapter(adaptador);
	}

	private void listenToSwipe() {
		/**
		 * Define la acción que se ejecuta al realizar el swipe
		 */
		swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				HttpGetRequest request = new HttpGetRequest();

				if (request.isConnected(mContext) && otros.isEmpty()) {
					otros = request.getOtros();
					setOtros();
				}

				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						swipe.setRefreshing(false);
					}
				}, 2000); // Tiempo durante el que se muestra el icono del swipe
			}
		});
	}

}
