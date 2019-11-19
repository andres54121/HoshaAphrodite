package com.example.hshaaphrodite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import Mujer.Login;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button hombre, mujer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mujer = findViewById(R.id.btnGirl);
        mujer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent activity = new Intent(this, Login.class);
        startActivity(activity);
    }
}
