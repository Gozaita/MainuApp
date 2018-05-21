package eus.mainu.mainu.menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import eus.mainu.mainu.R;
import eus.mainu.mainu.imagenes.AdaptadorImagenes;
import eus.mainu.mainu.conexion.HttpGetRequest;
import eus.mainu.mainu.data.Imagen;


public class FragmentMenu extends Fragment {

	private final String TAG = "Menu";

	// Layout
	private Context mContext;
	private SwipeRefreshLayout swipe;
	private ListView listaMenu;
	private ViewPager vistaMenu;

	// Variables
	private Menu menu = new Menu();

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.d(TAG, "onCreate: Menu");

		mContext = getContext();

		Bundle bundle = this.getArguments();
		if (bundle != null) {
			menu = (Menu) bundle.getSerializable("menu");
		}


	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_menu, container, false);

		//Ponemos el viewPager
		vistaMenu = view.findViewById(R.id.viewPagerMenu);

		TabLayout tabLayout = view.findViewById(R.id.tab_fotos_menu);
		tabLayout.setupWithViewPager(vistaMenu, true);

		vistaMenu.setCurrentItem((int) (Math.random() * (5 + 1)));

		setViewPager(vistaMenu);
		listaMenu = view.findViewById(R.id.listaMenu);

		// Poner swipeToRefresh
		swipe = view.findViewById(R.id.swipeMenu);
		swipe.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

		// Inflar la vista
		setMenu();

		// Escuchar swipeToRefresh
		listenToSwipe();

		return view;
	}


	@SuppressLint("ClickableViewAccessibility")
	// TODO: Solucionar problemas de accesibilidad
	private void setViewPager(ViewPager viewPager) {
		/**
		 * Evita interferencias entre el swipeToRefresh y el viewPager
		 */
		viewPager.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
					case MotionEvent.ACTION_MOVE:
						swipe.setEnabled(false);
						break;
					case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_CANCEL:
						swipe.setEnabled(true);
						break;
				}
				return false;
			}
		});

	}

	public void setMenu() {
		ArrayList<Imagen> imagenes = new ArrayList<>();

		if (menu != null) {
			imagenes = menu.getImagenes();
		}

		AdaptadorImagenes adaptadorImg = new AdaptadorImagenes(imagenes, mContext, 2);
		vistaMenu.setAdapter(adaptadorImg);

		AdaptadorMenu adaptadorMenu = new AdaptadorMenu(mContext);

		if (menu != null) {
			adaptadorMenu.addSectionHeaderItem(getResources().getString(R.string.primer_Plato));
			for (int i = 0; i < menu.getPrimeros().size(); i++) {
				adaptadorMenu.addItem(menu.getPrimeros().get(i));
			}
			adaptadorMenu.addSectionHeaderItem(getResources().getString(R.string.segundo_Plato));
			for (int i = 0; i < menu.getSegundos().size(); i++) {
				adaptadorMenu.addItem(menu.getSegundos().get(i));
			}
			adaptadorMenu.addSectionHeaderItem(getResources().getString(R.string.postre));
			for (int i = 0; i < menu.getPostres().size(); i++) {
				adaptadorMenu.addItem(menu.getPostres().get(i));
			}
		} else {
			adaptadorMenu.addSectionHeaderItem(getResources().getString(R.string.primer_Plato));
			adaptadorMenu.addSectionHeaderItem(getResources().getString(R.string.segundo_Plato));
			adaptadorMenu.addSectionHeaderItem(getResources().getString(R.string.postre));
		}
		listaMenu.setDivider(null);
		listaMenu.setAdapter(adaptadorMenu);
	}

	private void listenToSwipe() {
		/**
		 * Define la acción que se ejecuta al realizar el swipe
		 */
		swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				HttpGetRequest request = new HttpGetRequest();

				if (request.isConnected(mContext) && menu == null) {
					menu = request.getMenu();
					setMenu();
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
