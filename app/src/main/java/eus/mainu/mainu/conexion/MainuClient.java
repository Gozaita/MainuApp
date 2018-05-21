package eus.mainu.mainu.conexion;

import java.util.ArrayList;
import eus.mainu.mainu.data.Bocadillo;
import eus.mainu.mainu.menu.Menu;
import eus.mainu.mainu.data.Otro;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MainuClient {

    @GET("bocadillos")
    Call<ArrayList<Bocadillo>> getBocadillos();

    @GET("eus/mainu/mainu/otros")
    Call<ArrayList<Otro>> getOtros();

    @GET("menu")
    Call<Menu> getMenu();
}
