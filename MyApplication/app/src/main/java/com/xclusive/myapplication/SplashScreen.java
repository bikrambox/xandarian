package com.xclusive.myapplication;
import static maes.tech.intentanim.CustomIntent.customType;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SplashScreen extends AppCompatActivity {
    private FloatingActionButton refreshbtn;
    public ConnectivityManager connectivityManager;
    public NetworkInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
         refreshbtn = findViewById(R.id.refreshbtn);

        connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        info = connectivityManager.getActiveNetworkInfo();
if (info==null){

    AlertDialog alertDialog = new AlertDialog.Builder(this)
            .setTitle("Network Status")
            .setMessage("please check your network and try again.")

            .setIcon(R.drawable.network)
            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    refreshbtn.setVisibility(View.VISIBLE);
                    dialog.cancel();
                }
            }).create();

    alertDialog.show();
}
else{
    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
        @Override
        public void run() {
            Intent main1 = new Intent(SplashScreen.this, Product_tracking.class);
            startActivity(main1);
            customType(SplashScreen.this,"bottom-to-up");
            finish();
        }
    },2000);
}

    }


    @Override
    protected void onResume() {
        super.onResume();

        info = connectivityManager.getActiveNetworkInfo();
        if (info!=null){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent main1 = new Intent(SplashScreen.this,  Product_tracking.class);
                    startActivity(main1);
                    customType(SplashScreen.this,"bottom-to-up");
                    finish();
                }
            },2000);

        }
    }

    public void refresh(View view) {

        info = connectivityManager.getActiveNetworkInfo();
        if (info!=null){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent main1 = new Intent(SplashScreen.this,  Product_tracking.class);
                    startActivity(main1);
                    customType(SplashScreen.this,"bottom-to-up");
                    finish();
                }
            },2000);

        }
    }
}