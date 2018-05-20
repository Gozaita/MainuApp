package eus.mainu.mainu;

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

import eus.mainu.mainu.Utilidades.CacheAdmin;
import eus.mainu.mainu.Utilidades.HttpGetRequest;
import eus.mainu.mainu.Utilidades.Menu;
import eus.mainu.mainu.datalayer.Bocadillo;
import eus.mainu.mainu.datalayer.Complemento;

public class ActivityInicio extends AppCompatActivity {

	private final String TAG = "Inicio";

	private static final long SPLASH_SCREEN_DELAY = 50;

	private Menu menu;
	private ArrayList<Bocadillo> bocadillos;
	private ArrayList<Complemento> otros;

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
		 * 	-	Lista de otros productos
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

		Log.d(TAG, "onCreate: Se han obtenido los datos");
	}

	private void manageCache(String type, boolean conectado) {
		/**
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
		ArrayList<Complemento> otros = new ArrayList<>();

		CacheAdmin cache = new CacheAdmin();
		boolean usarCache = false;

		if (conectado) {
			String remoteLastUpdate = req1.getLastUpdate(type);
			String localLastUpdate = cache.leerLastUpdate(this, type);
			if (!remoteLastUpdate.equalsIgnoreCase(localLastUpdate)) {
				if (type.equals("bocadillos")) {
					bocadillos = req2.getBocadillos();
					if (bocadillos.size() != 0) {
						cache.guardarLastUpdate(this, type, remoteLastUpdate);
						cache.guardarListaBocadillos(this, bocadillos);
					}
				} else if (type.equals("otros")) {
					otros = req2.getOtros();
					if (otros.size() != 0) {
						cache.guardarLastUpdate(this, type, remoteLastUpdate);
						cache.guardarListaOtros(this, otros);
					}
				}
			} else {
				usarCache = true;
			}
		} else {
			usarCache = true;
		}

		if (usarCache) {
			if (type.equals("bocadillos")) {
				bocadillos = (ArrayList<Bocadillo>) cache.leerListaBocadillos(this);
			} else if (type.equals("otros")) {
				otros = (ArrayList<Complemento>) cache.leerListaOtros(this);
			}
		}

		if (type.equals("bocadillos")) {
			this.bocadillos = bocadillos;
		} else if (type.equals("otros")) {
			this.otros = otros;
		}
	}
}
