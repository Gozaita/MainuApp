package eus.mainu.mainu.inicio;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import eus.mainu.mainu.main.ActivityMain;
import eus.mainu.mainu.R;
import eus.mainu.mainu.conexion.HttpGetRequest;
import eus.mainu.mainu.menu.Menu;
import eus.mainu.mainu.data.Bocadillo;
import eus.mainu.mainu.data.Otro;
import eus.mainu.mainu.data.Ingrediente;

public class ActivityInicio extends AppCompatActivity {

	private final String TAG = "Inicio";

	private static final long SPLASH_SCREEN_DELAY = 50;

	private Menu menu;
	private ArrayList<Bocadillo> bocadillos;
	private ArrayList<Otro> otros;
	private ArrayList<Ingrediente> ingredientes;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inicio);

		Log.d(TAG, "onCreate: se ha iniciado");

		ProgressBar iniciando = findViewById(R.id.iniciando);
		iniciando.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				getData();
				// --> ActivityMain class
				Intent intent = new Intent().setClass(
						ActivityInicio.this, ActivityMain.class);
				intent.putExtra("menu", menu);
				intent.putExtra("bocadillos", bocadillos);
				intent.putExtra("otros", otros);
				intent.putExtra("ingredientes",ingredientes);
				startActivity(intent);
				finish();
			}
		};

		Timer timer = new Timer();
		timer.schedule(task, SPLASH_SCREEN_DELAY);

	}

	private void getData() {
		/**
		 * Elementos que se almacenan en caché:
		 * 	-	Lista de bocadillos
		 * 	-	Lista de eus.mainu.mainu.otros productos
		 * Elementos que necesitan conexión:
		 * 	-	Menú del día
		 * Se comprueba la conexión:
		 * 	A)	Hay conexión: se guarda el menú, se almacena el estado y se continua con B.
		 * 	B)	No hay conexión: se llama a los gestores de caché que, a su vez, comprueban
		 * 		el estado (conectado o no) y en función de ello llaman a los datos en caché
		 * 		o consultan la última modificación y piden nuevos.
		 */
		boolean conectado = false;
		HttpGetRequest req = new HttpGetRequest();

		if (req.isConnected(this)) {
			conectado = true;
			menu = req.getMenu();
		}

		manageCache("bocadillos",conectado);
		manageCache("otros", conectado);
		manageCache("ingredientes",conectado);

		Log.d(TAG, "onCreate: Se han obtenido los datos");
	}

	private void manageCache(String type, boolean conectado) {
		/*
		 * 2 objetos de petición/request:
		 * 	-	Obtener última fecha de modificación
		 * 	-	Obtener lista
		 * Si no hay conexión --> Usa caché
		 * Si hay conexión:
		 * 	Si la fecha obtenida es diferente a la almacenada --> Obtiene y almacena lista
		 * 	Si la fecha obtenida es igual a la almacenada --> Usa caché
		 * Al finalizar, actualiza los atributos
		 */
		HttpGetRequest req1 = new HttpGetRequest();
		HttpGetRequest req2 = new HttpGetRequest();
		ArrayList<Bocadillo>  bocadillos = new ArrayList<>();
		ArrayList<Otro> otros = new ArrayList<>();
		ArrayList<Ingrediente> ingredientes = new ArrayList<>();


		CacheAdmin cache = new CacheAdmin();
		boolean usarCache = false;

		if (conectado) {
			String remoteLastUpdate = req1.getLastUpdate(type);
			String localLastUpdate = cache.leerLastUpdate(this, type);
			if(remoteLastUpdate != null) {
				if (!remoteLastUpdate.equalsIgnoreCase(localLastUpdate)) {
					if (type.equals("bocadillos")) {
						bocadillos = req2.getBocadillos();
						if(bocadillos != null) {
							if (bocadillos.size() != 0) {
								cache.guardarLastUpdate(this, type, remoteLastUpdate);
								cache.guardarListaBocadillos(this, bocadillos);
							}
						}
					} else if (type.equals("otros")) {
						otros = req2.getOtros();
						if(otros != null) {
							if (otros.size() != 0) {
								cache.guardarLastUpdate(this, type, remoteLastUpdate);
								cache.guardarListaOtros(this, otros);
							}
						}
					} else if (type.equals("ingredientes")) {
						ingredientes = req2.getIngredientes();
						if(ingredientes != null) {
							if (ingredientes.size() != 0) {
								cache.guardarLastUpdate(this, type, remoteLastUpdate);
								cache.guardarListaIngredientes(this, ingredientes);
							}
						}
					}
				} else {
					usarCache = true;
				}
			}
		} else {
			usarCache = true;
		}

		if (usarCache) {
			if (type.equals("bocadillos")) {
				bocadillos = (ArrayList<Bocadillo>) cache.leerListaBocadillos(this);
			} else if (type.equals("otros")) {
				otros = (ArrayList<Otro>) cache.leerListaOtros(this);
			} else if (type.equals("ingredientes")) {
				ingredientes = (ArrayList<Ingrediente>) cache.leerListaIngredientes(this);
			}
		}

		if (type.equals("bocadillos")) {
			this.bocadillos = bocadillos;
		} else if (type.equals("otros")) {
			this.otros = otros;
		} else if (type.equals("ingredientes")) {
			this.ingredientes = ingredientes;
		}
	}
}
