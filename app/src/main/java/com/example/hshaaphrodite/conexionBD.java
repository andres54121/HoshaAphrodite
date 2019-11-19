package com.example.hshaaphrodite;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class conexionBD {
    Boolean exito;
    FirebaseUser user;

    public boolean conexion(String correo, String contrasena, FirebaseAuth firebaseAuth){
        firebaseAuth.signInWithEmailAndPassword(correo,contrasena)
                .addOnCompleteListener((Executor) this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            exito = true;
                        } else {
                            exito = false;
                        }
                    }
                });
        return exito;
    }

    public void usuario(){
        if(user!=null){

        }
    }
}
