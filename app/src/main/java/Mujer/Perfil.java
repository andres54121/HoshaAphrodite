package Mujer;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.hshaaphrodite.EditPerfil;
import com.example.hshaaphrodite.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class Perfil extends Fragment implements View.OnClickListener{
    TextView nombre, txtuid, email;
    Button edit;
    ImageView foto;
    Uri photoUrl;
    View view;
    String nombreU, apeU, correoU;

    FirebaseUser user;
    private DatabaseReference mDatabase;

    public Perfil() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_perfil, container, false);

        txtuid = view.findViewById(R.id.txtuid);
        nombre = view.findViewById(R.id.txtNomUsua);
        email = view.findViewById(R.id.txtCorreo);
        foto = view.findViewById(R.id.imgPerfil);
        edit = view.findViewById(R.id.btnEditProfile);
        datosPerfil();

        foto.setOnClickListener(this);
        edit.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnEditProfile:
                Intent activity = new Intent(getActivity(), EditPerfil.class);
                startActivity(activity);
                break;
        }


    }

    private void datosPerfil(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        txtuid.setText(user.getPhotoUrl()+"");
        nombre.setText(user.getDisplayName());
        email.setText(user.getEmail());

        photoUrl = user.getPhotoUrl();

        if(photoUrl!=null){
            photoUrl = user.getPhotoUrl();
            Glide.with(getActivity()).load(photoUrl).into(foto);
        }
    }


}
