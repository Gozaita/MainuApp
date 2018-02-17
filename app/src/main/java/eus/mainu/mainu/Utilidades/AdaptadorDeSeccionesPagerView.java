package eus.mainu.mainu.Utilidades;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by narciso on 17/02/18.
 * Clase para adaptar los diferentes fragments al PagerView
 */

public class AdaptadorDeSeccionesPagerView extends FragmentPagerAdapter {

    private static final String TAG = "SectionsPagerAdapter";

    private final List<Fragment> mFragmentList = new ArrayList<>();


    public AdaptadorDeSeccionesPagerView(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }


    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment){
        mFragmentList.add(fragment);
    }
}
