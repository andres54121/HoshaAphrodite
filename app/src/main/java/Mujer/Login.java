package Mujer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hshaaphrodite.R;
import com.example.hshaaphrodite.crearUsua;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity implements View.OnClickListener {
    Button logIn;
    EditText txtCorreo, txtContrasena;
    TextView resgitro;
    String correo, contrasena, nombre, apellidos;

    FirebaseAuth firebaseAuth;
    //Base de Datos
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private String mCustomToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        resgitro = findViewById(R.id.btnRegistro);
        logIn = findViewById(R.id.btnLogin);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtContrasena =findViewById(R.id.txtContraseña);

        iniciarFirbabase();

        resgitro.setOnClickListener(this);
        logIn.setOnClickListener(this);
    }

    private void iniciarFirbabase() {
        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public void onClick(View view) {
        correo = txtCorreo.getText().toString().trim();
        contrasena = txtContrasena.getText().toString().trim();

        switch (view.getId()){
            case R.id.btnLogin:{
                if(correo.equals("") || contrasena.equals("")){
                    validation();
                }
                else {
                    firebaseAuth.signInWithEmailAndPassword(correo, contrasena)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Intent activity = new Intent(getApplicationContext(), Home.class);
                                        Toast.makeText(getApplicationContext(), "Acceso correcto", Toast.LENGTH_SHORT).show();
                                        startActivity(activity);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Acceso incorrecto", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                break;
            }
            case R.id.btnRegistro:{
                    /*
/*
                    firebaseAuth.createUserWithEmailAndPassword(correo,contrasena)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "El usuario se registro con éxito", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getApplicationContext(), "El usuario no se registro.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });*/
                Intent activity = new Intent(getApplicationContext(), crearUsua.class);
                Toast.makeText(getApplicationContext(), "Acceso correcto", Toast.LENGTH_SHORT).show();
                startActivity(activity);
                break;
            }
        }

    }

    private void validation() {
        if(correo.equals(""))
            txtCorreo.setError("Campo Obligatorio");
        if(contrasena.equals(""))
            txtContrasena.setError("Campo Obligatorio");
    }
}
