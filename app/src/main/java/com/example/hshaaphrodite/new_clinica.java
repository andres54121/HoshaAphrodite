package com.example.hshaaphrodite;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Mujer.Home;
import Mujer.Modelos.Clinica;
import Mujer.Modelos.Usuario;


/**
 * A simple {@link Fragment} subclass.
 */
public class new_clinica extends Fragment implements View.OnClickListener{
    View view;
    EditText nombre, resumen, descrip, altitud, longitud;
    String nom, re, dess, altt, longg;
    Button xd ;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public new_clinica() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_clinica, container, false);
        initialFreebase();

        nombre = view.findViewById(R.id.txtnomClinica);
        resumen = view.findViewById(R.id.txtResumen);
        descrip = view.findViewById(R.id.txtDesc);
        altitud = view.findViewById(R.id.txtAltitud);
        longitud = view.findViewById(R.id.txtLongitud);

        xd = view.findViewById(R.id.btnNEWclinica);
        xd.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        nom = nombre.getText().toString().trim();
        re = resumen.getText().toString().trim();
        dess = descrip.getText().toString().trim();
        altt = altitud.getText().toString().trim();
        longg = longitud.getText().toString().trim();

        int idd= (int) Math.floor(Math.random()*(1-1000+1)+1000);

        Clinica x = new Clinica();
        x.setId(idd);
        x.setNombre(nom);
        x.setResumen(re);
        x.setDescripcion(dess);
        x.setAltitud(altt);
        x.setLongitud(longg);

        databaseReference.child("Clinicas").child(x.getId()+"").setValue(x);
        Toast.makeText(getActivity(), "La clinica se registro con éxito :", Toast.LENGTH_SHORT).show();

    }
    private void initialFreebase() {

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}
