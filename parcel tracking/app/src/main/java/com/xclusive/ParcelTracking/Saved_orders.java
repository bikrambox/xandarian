package com.xclusive.ParcelTracking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Saved_orders extends AppCompatActivity {

    private RecyclerView history;
    private Toolbar toolbar3;
    private FloatingActionButton back;
    private saveAdapter saveAdapter;
    private ArrayList<savedmodel> savedmodels = new ArrayList<>();
    private static Cursor cursor;
    private static sqlDB dataBaseLite;

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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        history.setLayoutManager(linearLayoutManager);
        dataBaseLite = new sqlDB(this);
        cursor = dataBaseLite.getAlldata();
        if (cursor.getCount()!=0){
            while (cursor.moveToNext()){
                savedmodels.add(new savedmodel(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2)));
            }
        }



        saveAdapter = new saveAdapter(savedmodels);
        history.setAdapter(saveAdapter);
        saveAdapter.notifyDataSetChanged();



        back.setOnClickListener(v->{
                    onBackPressed();
                });
    }
}