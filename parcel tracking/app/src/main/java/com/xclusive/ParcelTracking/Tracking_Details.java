package com.xclusive.ParcelTracking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kofigyan.stateprogressbar.StateProgressBar;
//import com.shuhart.stepview.StepView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Tracking_Details extends AppCompatActivity  implements OnMapReadyCallback {

    private TextView track1,trackid,textView5,textView4,sc, pp,prh,pt,pd;
    private TextView scn, ppn,prhn,ptn,pdn;
    private TextView scd, ppd,prhd,ptd,pdd,  date1,date2;
    private StateProgressBar stateprogressbar;
    private Button backbtn,savebtn;
    ImageView carrier;
    GoogleMap map;
    private RecyclerView recyclerviewstatus;
    private ArrayList<statusModel> statuslist = new ArrayList<statusModel>();
    private statusAdapter statusAdapter;


    public static String tracking_number1,carrier_code1,status1,itemTimeLength1,ItemReceived1,
            DestinationArrived1,lastEvent1,icon_url1;

    public static ArrayList<String >description1 = new ArrayList<>();
    public static ArrayList<String >locationtransit1 = new ArrayList<>();
    public static ArrayList<String >checkpoint_status1= new ArrayList<>();
    public static ArrayList<String >dates1= new ArrayList<>();

    //todo sqllite//////////////////////////////////////////////////////////////////
    sqlDB dataBaseLite;
    ////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tracking__details);
        dataBaseLite = new sqlDB(this);
        init();

        description1 = getIntent().getStringArrayListExtra("description");
        locationtransit1 = getIntent().getStringArrayListExtra("locationtransit");
        checkpoint_status1 = getIntent().getStringArrayListExtra("checkpoint_status");
        dates1 = getIntent().getStringArrayListExtra("dates");
        tracking_number1 = getIntent().getStringExtra("tracking_number");
        carrier_code1 = getIntent().getStringExtra("carrier_code");
        status1 = getIntent().getStringExtra("status");
        itemTimeLength1 = getIntent().getStringExtra("itemTimeLength");
        ItemReceived1 = getIntent().getStringExtra("ItemReceived");
        DestinationArrived1 = getIntent().getStringExtra("DestinationArrived");
        lastEvent1 = getIntent().getStringExtra("lastEvent");
        icon_url1 = getIntent().getStringExtra("icon_url");


        MapFragment fragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map1);
        fragment.getMapAsync(this);

        Glide.with(this).load(icon_url1).placeholder(R.drawable.location_icon).into(carrier);
        trackid.setText(tracking_number1);
        track1.setText(locationtransit1.get(0));
        if (!lastEvent1.contains("Delivered")){

            String d =dates1.get(1);
            String dd = d.replaceAll("[-:]","");

            String ddd = dd.substring(0,6);
            int up = Integer.valueOf(dd.substring(6,8));

            textView5.setText(ddd+String.valueOf(up+5)+"days");
        }
        else{
            textView4.setText(R.string.Delivered1);
            textView5.setText(dates1.get(1));
        }




// todo: state progressbar//

        statepro();
        recyclerview();

// todo: save order details in SQL Lite//
        savebtn.setOnClickListener(V->{
            savedorders();
        });


 }




    private void recyclerview() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerviewstatus.setLayoutManager(linearLayoutManager);
        for (int i=0;i< description1.size();i++){
            statuslist.add(new statusModel(description1.get(i),dates1.get(i)));
        }


        statusAdapter= new statusAdapter(statuslist);
        recyclerviewstatus.setAdapter(statusAdapter);
        statusAdapter.notifyDataSetChanged();


    }

    private void statepro() {
        String[] descriptionData = {
                getResources().getString(R.string.shipc),
                getResources().getString(R.string.packageup),
                getResources().getString(R.string.transit),
                getResources().getString(R.string.Delivered)
        };
        stateprogressbar.setStateDescriptionData(descriptionData);
        //stateprogressbar.setStateDescriptionTypeface(String.valueOf(R.font.roboto_light));
        stateprogressbar.enableAnimationToCurrentState(true);
        stateprogressbar.setStateSize(30f);
        stateprogressbar.setStateNumberTextSize(15f);
        stateprogressbar.setStateLineThickness(5f);
        stateprogressbar.setStateDescriptionSize(12f);
        stateprogressbar.setMaxDescriptionLine(5);
        stateprogressbar.setJustifyMultilineDescription(true);
        stateprogressbar.setDescriptionLinesSpacing(5f);
        //app:spb_currentStateNumber="two"


        if (status1.contains("transit")){
            stateprogressbar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
        }
        if(status1.contains("pickup")){
            stateprogressbar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
        }
       if (lastEvent1.contains("Delivered")){
           stateprogressbar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
        }
       else {
           stateprogressbar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
       }





    }
    private void init() {
        backbtn = findViewById(R.id.backbtn);
        savebtn = findViewById(R.id.savebtn);
        carrier =findViewById(R.id.carrierimage);
        trackid =findViewById(R.id.trackid);
        track1 =findViewById(R.id.track1);
        textView5 =findViewById(R.id.textView5);
        textView4 =findViewById(R.id.textView4);
        recyclerviewstatus =findViewById(R.id.recyclerviewstatus);



        stateprogressbar = findViewById(R.id.stateprogressbar);

        backbtn.setOnClickListener(V->{
            onBackPressed();
            finish();
        });

    }
    private void savedorders() {
        int res = dataBaseLite.insertdata(tracking_number1,carrier_code1,icon_url1);
        if (res==1){
            Toast.makeText(Tracking_Details.this, "Saved", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        String loc = locationtransit1.get(0);
        List<Address> addresses = null;
        MarkerOptions options = new MarkerOptions();

        if (loc != null) {
            Geocoder geocoder = new Geocoder(Tracking_Details.this);
            try {
                addresses = geocoder.getFromLocationName(loc, 1);
                Log.e("----------->asd", addresses.get(0).toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            Address address = addresses.get(0);

            map = googleMap;
            LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("sydney"));

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            //googleMap.animateCamera(CameraUpdateFactory.zoomBy(1));
            googleMap.addCircle(new CircleOptions().center(latLng));


        }


    }
}