package eus.mainu.mainu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import eus.mainu.mainu.Utilidades.RecyclingViewCardAdapter;

public class Fragment_Otros extends Fragment{

    private static final String TAG = "Fragment_Otros";
    private static final int NUM_COLUMNS = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_otros, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_otros);

        RecyclingViewCardAdapter recyclingViewCardAdapter = new RecyclingViewCardAdapter(getActivity());
        StaggeredGridLayoutManager recyclingViewCardAdapterManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(recyclingViewCardAdapterManager);
        recyclerView.setAdapter(recyclingViewCardAdapter);



        return view;
    }

}
