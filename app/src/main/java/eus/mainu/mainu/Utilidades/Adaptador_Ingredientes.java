package eus.mainu.mainu.Utilidades;
import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import java.util.ArrayList;
import eus.mainu.mainu.Activity_Main;
import eus.mainu.mainu.R;

public class Adaptador_Ingredientes extends RecyclerView.Adapter<Adaptador_Ingredientes.ViewHolder> {

    //Globales
    private final String TAG = "Adaptador_Ingredientes";

    //Variables
    private Context mContext;
    private ArrayList<String> arrayIngredientes;


    public Adaptador_Ingredientes(ArrayList<String> array, Context context) {
        this.arrayIngredientes = array;
        mContext = context;
    }


    //Metodo que se utiliza para "Inflar" el contexto
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclingview_ingredientes, parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG,"onBindViewHolder: called."); //Para debuguear

        holder.checkbox.setText( arrayIngredientes.get(position) );

        //Accion que se ejecuta al pulsar en un objeto de la lista
        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String z = ((AppCompatCheckBox) v).getText().toString();
                Activity_Main a = (Activity_Main) mContext;
                a.filterByIngredient(z);
            }
        });
    }

    //Le dice al adaptador cuantos objetos tenemos en la lista, si devolvemos 0, no muestra ninguno
    @Override
    public int getItemCount() {
        return arrayIngredientes.size();
    }

    /********************************************************************************************/
    class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {

        CheckBox checkbox;
        LinearLayout ingredientes_Layout;

        private ViewHolder(View itemView) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.ingredienteCheckBox);
            ingredientes_Layout = itemView.findViewById(R.id.ingredientes_Layout);
        }
    }

}
