package eus.mainu.mainu.Utilidades;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import eus.mainu.mainu.R;
import eus.mainu.mainu.datalayer.Bocadillo;

public class Adaptador_Busqueda extends BaseAdapter{
    Context mContext;
    LayoutInflater inflater;
    private ArrayList<Bocadillo> arraylist;

    public Adaptador_Busqueda(Context context, ArrayList<Bocadillo> bocadillos) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = bocadillos;

        //this.arraylist = new ArrayList<Bocadillo>();
        //this.arraylist.addAll(bocadillos);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public Bocadillo getItem(int position) {
        return arraylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_testtt, null);
            // Locate the TextViews in listview_item.xml
            //holder.name = (TextView) view.findViewById(R.id.listview_testtt);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(arraylist.get(position).getNombre() );
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        arraylist.clear();
        if (charText.length() == 0) {
            arraylist.addAll(arraylist);
        } else {
            for (Bocadillo wp : arraylist) {
                if (wp.getNombre().toLowerCase(Locale.getDefault()).contains(charText)) {
                    arraylist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
