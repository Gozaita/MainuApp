package eus.mainu.mainu.Utilidades;

import java.util.ArrayList;
import eus.mainu.mainu.datalayer.Bocadillo;
import eus.mainu.mainu.datalayer.Complemento;
import eus.mainu.mainu.datalayer.Plato;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MainuClient {

    @GET("bocadillos")
    Call<ArrayList<Bocadillo>> getBocadillos();

    @GET("otros")
    Call<ArrayList<Complemento>> getOtros();

    @GET("menu")
    Call<Menu> getMenu();
}
