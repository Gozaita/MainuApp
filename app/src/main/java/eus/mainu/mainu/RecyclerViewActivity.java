package eus.mainu.mainu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

public class RecyclerViewActivity extends AppCompatActivity {

    private static final String TAG = "RecyclerView";

    //vars
    private ArrayList<String> mNombres = new ArrayList<>();
    private ArrayList<String> mImagenesUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_recycler_view);

        initImageBitmaps();


    }

    private void initImageBitmaps() {
        Log.d(TAG,"initImageBitmaps: preparing bitmaps.");

        //Ejemplos de imagenes que añadimos a los arrays
        mImagenesUrls.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
        mNombres.add("Havasu Falls");

        mImagenesUrls.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mNombres.add("Trondheim");

        mImagenesUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mNombres.add("Portugal");

        initRecyclerView();

    }

    private void initRecyclerView() {
        Log.d(TAG,"initImageBitmaps: recyclerView");

        //android.support.v7.widget.RecyclerView recyclerView = findViewById(R.id.recycler_view);
        //RecyclerViewAdapter adapter = new RecyclerViewAdapter(mImagenesUrls, mNombres, this);
        //recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

}
