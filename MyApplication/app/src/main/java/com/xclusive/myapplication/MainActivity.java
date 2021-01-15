package com.xclusive.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static maes.tech.intentanim.CustomIntent.customType;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CarrierAdapter carrierAdapter;
    private ArrayList<CarrierListModel> carrierListModelArrayList = new ArrayList<>();
    private ProgressBar progressBar;

    private MaterialToolbar toolbar;
    private ImageButton backbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        backbtn = findViewById(R.id.backbtn);



        recyclerView = findViewById(R.id.recyclercarrier);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//
////                            .url("https://api.trackingmore.com/v2/carriers/")
////                            .get()
////                            .addHeader("content-type", "application/json")
////                            .addHeader(   "Trackingmore-Api-Key", "49852ed1-3a54-4570-8f69-4c9e9265075b")
////                            .build();
//
//        	.url("https://order-tracking.p.rapidapi.com/carriers")
//                .get()
//                .addHeader("content-type", "application/json")
//                .addHeader("x-rapidapi-key", "a99baae0fcmsh06e4b728572c2e0p157a47jsn483516a43cf5")
//                .addHeader("x-rapidapi-host", "order-tracking.p.rapidapi.com")
//                .build();
//
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e("MainActivity","response--->"+e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()){
//                    //Log.e("MainActivity","response--->"+ String.valueOf(response.body()));
//                    final String rep = response.body().string();
//                    //Toast.makeText(MainActivity.this, rep, Toast.LENGTH_SHORT).show();
//                    MainActivity.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            //Toast.makeText(MainActivity.this, rep, Toast.LENGTH_SHORT).show();
//                            //Log.e("MainActivity",rep);
//                            try {
//                                JSONObject jsonObject = new JSONObject(rep);
//                                JSONArray jsonArray = jsonObject.getJSONArray("data");
//                                for (int i=1;i<100;i++){
//
//                                    JSONObject data1 = jsonArray.getJSONObject(i);
//
//                                    String name = data1.getString("name");
//                                    String pic = data1.getString("picture");
//
//
//
//                                        //re.append("["+name +i+"], ");
//
//                                       carrierListModelArrayList.add(new CarrierListModel(name,pic));
//                                      carrierAdapter.notifyDataSetChanged();
//
//
//                                }
//                                progressBar.setVisibility(View.GONE);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                            //re.setText(String.valueOf(rep));
//
//                        }
//
//                    });
//
//                }
//
//            }
//        });
//
//        //carrierListModelArrayList.add(new CarrierListModel("name","one"));
//        carrierAdapter = new CarrierAdapter(carrierListModelArrayList);
//        recyclerView.setAdapter(carrierAdapter);


        backbtn.setOnClickListener(V->{
            onBackPressed();
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        customType(MainActivity.this,"fadein-to-fadeout");
        finish();
    }
}