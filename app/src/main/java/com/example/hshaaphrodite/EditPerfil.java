package com.example.hshaaphrodite;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import Mujer.Modelos.Usuario;
import Mujer.Perfil;

public class EditPerfil extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_IMAGE = 100;
    Uri imageUri, imgPer;
    EditText Nombre, Apellidos;
    Button actualizar;
    ImageView foto_gallery;

    String nombreU, apeU;

    //Base de Datos
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user;
    //almacenamiento de archivos
    FirebaseStorage storage;
    StorageReference storageRef, perfilImagesRef;
    String nameIMG, uid;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_perfil);
        //firebase xd

        foto_gallery = findViewById(R.id.imgUpFoto);
        Nombre = findViewById(R.id.txtUpNom);
        Apellidos = findViewById(R.id.txtUpApe);
        actualizar = findViewById(R.id.btnUpPerfil);

        initialFreebase();
        datosPerfil();


        foto_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        actualizar.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnUpPerfil:
                nombreU = Nombre.getText().toString();
                apeU = Apellidos.getText().toString();
                updatePerfil();
                break;
        }
    }
    //*************************
    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            foto_gallery.setImageURI(imageUri);
        }
    }

    //*************************
    private void updatePerfil(){
        //Actualizar datos en la BD
        databaseReference.child("Usuario").child(uid).child("nombre").setValue(nombreU);
        databaseReference.child("Usuario").child(uid).child("apellidos").setValue(apeU);

        //Actualizar foto de perfil
        // Get the data from an ImageView as bytes

        perfilImagesRef = storageRef.child("img/"+uid+".jpg");

        foto_gallery.setDrawingCacheEnabled(true);
        foto_gallery.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) foto_gallery.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = perfilImagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                updateAuth();
                Toast.makeText(getApplicationContext(), "Cambios Guardados" + perfilImagesRef, Toast.LENGTH_SHORT).show();

                Intent activity = new Intent(getApplicationContext(), Perfil.class);
                startActivity(activity);


            }
        });
    }

    private void updateAuth(){
        //Cambia el nombre del usuario y su foto de perfil

        perfilImagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(nombreU + " " + apeU)
                        .setPhotoUri(uri)
                        .build();

                user.updateProfile(profileUpdates);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    private void initialFreebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        imgPer = user.getPhotoUrl();
    }

    private void datosPerfil(){
        if(imgPer==null)
            Glide.with(getApplicationContext()).load(imgPer).into(foto_gallery);

        DatabaseReference perfilDB = FirebaseDatabase.getInstance().getReference().child("Usuario").child(uid).child("nombre");
        perfilDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                //Nombre.setText(usuario.getNombre());
                //Apellidos.setText(usuario.getApellidos());
                //dataSnapshot.getValue()

                Toast.makeText(getApplicationContext(), dataSnapshot.getValue() +"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
