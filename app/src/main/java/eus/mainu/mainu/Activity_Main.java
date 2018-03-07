package eus.mainu.mainu;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import eus.mainu.mainu.Utilidades.AdaptadorDeSeccionesPagerView;

public class Activity_Main extends AppCompatActivity {

    private static final String TAG = "Activity MAIN";
    private TextView titulo;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageButton searchButton;
    private ImageButton backArrow;
    private EditText filter;
    private RelativeLayout layoutblanco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titulo = findViewById(R.id.textViewActividad);
        viewPager = findViewById(R.id.contenedor);
        tabLayout = findViewById(R.id.tabs);
        searchButton = findViewById(R.id.search_button);
        filter = findViewById(R.id.searchFilter);
        backArrow = findViewById(R.id.back_button);
        layoutblanco = findViewById(R.id.barra_blanca);

        setUpTitulo();

        setupViewPager();

        setIntroduceTextoEditText();
        setEnterEditText();

        setBackButton();


    }

    //Responsable de añadir 3 fragmentos: Bocadillos, Menu, Otros
    private void setupViewPager(){

        //Creamos los fragmentos
        AdaptadorDeSeccionesPagerView adapter = new AdaptadorDeSeccionesPagerView(getSupportFragmentManager());
        adapter.addFragment(new Fragment_Menu()); //index 0
        adapter.addFragment(new Fragment_Bocadillos()); //index 1
        adapter.addFragment(new Fragment_Otros()); //index 2

        //Creamos las tabulaciones
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        //Añadimos 3 iconos
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_menu);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_sandwich);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_french_fries);


        searchButton.setOnClickListener(searchClickListener);
    }

    //Metodo que sirve para cambiar el titulo de la toolbar en funcion del fragmento
    private void setUpTitulo(){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Quitamos el teclado si esta abierto
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(viewPager.getWindowToken(), 0);
                //Reseteamos la toolbar
                filter.setVisibility(View.GONE);
                layoutblanco.setVisibility(View.GONE);
                backArrow.setVisibility(View.GONE);
                filter.setText("");

                switch (position) {
                    case 0:
                        titulo.setText(R.string.menuDelDia);
                        searchButton.setVisibility(View.GONE);
                        break;
                    case 1:
                        titulo.setText(R.string.bocadillos);
                        searchButton.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        titulo.setText(R.string.complementos);
                        searchButton.setVisibility(View.VISIBLE);
                        break;
                }
            }
            @Override
            public void onPageSelected(int position) {

            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    View.OnClickListener searchClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (v.equals(searchButton)) {
                //Mostramos el cuadro de texto
                layoutblanco.setVisibility(View.VISIBLE);
                backArrow.setVisibility(View.VISIBLE);
                findViewById(R.id.searchFilter).setVisibility(View.VISIBLE);
                //Focuseamos el cuadro
                filter.requestFocus();
                //Abrimos el teclado en el cuadro de texto
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(filter, InputMethodManager.SHOW_IMPLICIT);
            }
        }
    };

    //Metodo para saber si se ha pulsado el enter, esconder el teclado y el cuadro de texto
    private void setEnterEditText(){
        filter.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //Comprueba si se devuelve un 0
                if (actionId == EditorInfo.IME_NULL) {
                    //Esconde teclado
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    //Esconde texto
                    filter.setVisibility(View.GONE);
                    backArrow.setVisibility(View.GONE);
                    layoutblanco.setVisibility(View.GONE);
                    return true;
                }
                return false;
            }
        });
    }

    //Metodo que gestiona la introduccion del texto en el filtro
    private void setIntroduceTextoEditText(){
        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
    }

    private void setBackButton(){
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutblanco.setVisibility(View.GONE);
                backArrow.setVisibility(View.GONE);
                filter.setVisibility(View.GONE);
                filter.setText("");
                //Esconde teclado
                //Quitamos el teclado si esta abierto
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(viewPager.getWindowToken(), 0);
            }
        });
    }




}
