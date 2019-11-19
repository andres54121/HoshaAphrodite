package Mujer;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.hshaaphrodite.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class clinicas extends Fragment {

    ListView listaMenu;
    List<String> opciones;

    public clinicas() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clinicas, container, false);


        //1.- conectar buestro listview con el elemento visual
        listaMenu= view.findViewById(R.id.listClinicas);
        //2. Agregamos la lista de flores
        opciones = new ArrayList<>();
        opciones.add("Info");
        opciones.add("Flores");
        //3. Creamos el adaptador
        //@params1 el actividad actual
        //@params2 el layout que esta usando
        //@params3 los valores
        ArrayAdapter AdaptadorMenu = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, opciones);
        //4. Enlazar adaptador -lisview
        listaMenu.setAdapter(AdaptadorMenu);
        return view;
    }

}
