package eus.mainu.mainu;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by narciso on 17/02/18.
 * Clase infladora del fragmento de bocadillos
 */

public class MenuFragment extends Fragment{

    private static final String TAG = "MenuFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        return view;
    }

}
