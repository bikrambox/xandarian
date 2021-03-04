package com.xclusive.ParcelTracking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;

public class Saved_orders extends AppCompatActivity {

    private RecyclerView history;
    private Toolbar toolbar3;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_orders);
        init();
    }

    private void init() {
        back = findViewById(R.id.backbtn1);

        history = findViewById(R.id.recyclerview_history);
        toolbar3 = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar3);

        back.setOnClickListener(v->{
                    onBackPressed();
                });
    }
}