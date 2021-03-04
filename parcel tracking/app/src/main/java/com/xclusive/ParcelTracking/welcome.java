package com.xclusive.ParcelTracking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.content.Intent;

public class welcome extends AppCompatActivity {
private ImageView img;

private ImageButton login,registrationbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        img = findViewById(R.id.imageView9);
        login=findViewById(R.id.loginbtn);
        registrationbtn=findViewById(R.id.registrationbtn);
        img.setAlpha(60);

        login.setOnClickListener(v -> {
            Intent loginintent = new Intent(getApplicationContext(),login.class);
            startActivity(loginintent);

        });
        registrationbtn.setOnClickListener(v -> {
            Intent registerintent = new Intent(getApplicationContext(),registration.class);
            startActivity(registerintent);

        });
    }
}