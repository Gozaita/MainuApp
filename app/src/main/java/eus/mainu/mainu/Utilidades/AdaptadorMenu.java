package eus.mainu.mainu.Utilidades;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.TreeSet;

import eus.mainu.mainu.ActivityElemento;
import eus.mainu.mainu.R;
import eus.mainu.mainu.datalayer.Plato;

public class AdaptadorMenu extends BaseAdapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

    private ArrayList<Plato> mData = new ArrayList<Plato>();
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();

    private LayoutInflater mInflater;

    public AdaptadorMenu(Context context) {
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(final Plato item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void addSectionHeaderItem(final String item) {
        Plato plato = new Plato(item);
        mData.add(plato);
        sectionHeader.add(mData.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Plato getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        int rowType = getItemViewType(position);

        if (convertView == null) {
            holder = new ViewHolder();
            switch (rowType) {
                case TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.listview_platos, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.nombreTextView);
                    holder.estrellitas = convertView.findViewById(R.id.ratingBarEstrellitas);
                    holder.textView.setText(mData.get(position).getNombre());
                    holder.estrellitas.setRating((float) mData.get(position).getPuntuacion());
                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Decimos que queremos navegar a la clase Elemento
                            Intent intent = new Intent(mInflater.getContext(), ActivityElemento.class);
                            //Le pasamos la informacion que necesita la clase
                            intent.putExtra("Plato",mData.get(position));
                            //Iniciamos la actividad
                            mInflater.getContext().startActivity(intent);
                        }
                    });
                    break;
                case TYPE_SEPARATOR:
                    convertView = mInflater.inflate(R.layout.listview_titulo, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.header);
                    holder.textView.setText(mData.get(position).getNombre());
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    public static class ViewHolder {
        public TextView textView;
        public RatingBar estrellitas;
    }

}