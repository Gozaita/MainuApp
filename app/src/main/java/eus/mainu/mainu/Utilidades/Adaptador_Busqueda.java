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
import eus.mainu.mainu.datalayer.Bocadillo;
import eus.mainu.mainu.R;

public class Adaptador_Busqueda extends BaseAdapter{
    Context mContext;
    LayoutInflater inflater;
    private List<Bocadillo> bocadillosList = null;
    private ArrayList<Bocadillo> arraylist;

    public Adaptador_Busqueda(Context context, List<Bocadillo> animalNamesList) {
        mContext = context;
        this.bocadillosList = animalNamesList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Bocadillo>();
        this.arraylist.addAll(animalNamesList);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return bocadillosList.size();
    }

    @Override
    public Bocadillo getItem(int position) {
        return bocadillosList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
//        final ViewHolder holder;
//        if (view == null) {
//            holder = new ViewHolder();
//            view = inflater.inflate(R.layout.listview_item, null);
//            // Locate the TextViews in listview_item.xml
//            holder.name = (TextView) view.findViewById(R.id.name);
//            view.setTag(holder);
//        } else {
//            holder = (ViewHolder) view.getTag();
//        }
//        // Set the results into TextViews
//        holder.name.setText(bocadillosList.get(position).getNombre() );
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        bocadillosList.clear();
        if (charText.length() == 0) {
            bocadillosList.addAll(arraylist);
        } else {
            for (Bocadillo wp : arraylist) {
                if (wp.getNombre().toLowerCase(Locale.getDefault()).contains(charText)) {
                    bocadillosList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
