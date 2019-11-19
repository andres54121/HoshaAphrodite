package com.example.hshaaphrodite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Mujer.Home;
import Mujer.Modelos.Usuario;

public class crearUsua extends AppCompatActivity implements View.OnClickListener{
    Button resgitro;
    EditText txtCorreo, txtContrasena, txtNombre, txtApe, txtVeriContra;
    String correo, contrasena, nombre, apellidos, veriContra;

    String uid;

    FirebaseAuth firebaseAuth;
    //Base de Datos
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usua);
        resgitro = findViewById(R.id.btnUpPerfil);
        txtCorreo = findViewById(R.id.txtNewCorreo);
        txtContrasena = findViewById(R.id.txtNewContra1);
        txtNombre = findViewById(R.id.txtUpNom);
        txtApe = findViewById(R.id.txtUpApe);
        txtVeriContra = findViewById(R.id.txtNewContra2);

        initialFreebase();
        resgitro.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        correo = txtCorreo.getText().toString().trim();
        contrasena = txtContrasena.getText().toString().trim();
        veriContra = txtVeriContra.getText().toString().trim();
        nombre = txtNombre.getText().toString().trim();
        apellidos = txtApe.getText().toString().trim();
        if(validate()==true){
            Toast.makeText(getApplicationContext(), "vas bien", Toast.LENGTH_SHORT).show();

            firebaseAuth.createUserWithEmailAndPassword(correo,contrasena)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                firebaseAuth.signInWithEmailAndPassword(correo,contrasena)
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    // Sign in success, update UI with the signed-in user's information
                                                    user = firebaseAuth.getCurrentUser();
                                                    uid = user.getUid();

                                                    Usuario x = new Usuario();
                                                    x.setUid(uid);
                                                    x.setNombre(nombre);
                                                    x.setApellidos(apellidos);
                                                    x.setCorreo(correo);
                                                    x.setContraseña(contrasena);

                                                    databaseReference.child("Usuario").child(x.getUid()).setValue(x);
                                                    Toast.makeText(getApplicationContext(), "El usuario se registro con éxito :"+ uid, Toast.LENGTH_SHORT).show();

                                                    Intent activity = new Intent(getApplicationContext(), Home.class);
                                                    Toast.makeText(getApplicationContext(), "Acceso correcto", Toast.LENGTH_SHORT).show();
                                                    startActivity(activity);
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Acceso incorrecto", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                            }else{
                                Toast.makeText(getApplicationContext(), "El usuario no se registro:"+ uid, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }


    private void initialFreebase() {
        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    private boolean validate() {
        if(correo.equals(""))
            txtCorreo.setError("Campo Obligatorio");
        if(contrasena.equals(""))
            txtContrasena.setError("Campo Obligatorio");
        if(nombre.equals(""))
            txtNombre.setError("Campo Obligatorio");
        if(apellidos.equals(""))
            txtApe.setError("Campo Obligatorio");
        if(!veriContra.equals(contrasena))
            txtVeriContra.setError("Las contraseñas no Coinciden");
        else{
            return true;
        }
        return false;
    }
}
