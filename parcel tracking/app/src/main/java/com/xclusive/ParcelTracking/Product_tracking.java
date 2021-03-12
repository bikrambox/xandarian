package com.xclusive.ParcelTracking;

import static maes.tech.intentanim.CustomIntent.customType;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class Product_tracking extends AppCompatActivity implements SearchView.OnQueryTextListener, NavigationView.OnNavigationItemSelectedListener {

/**Your tracking info was not found, Possible reasons:
1: The carrier hasn't accepted your package yet.
2: The carrier hasn't scanned and entered your package tracking information into their system yet.
3: Your tracking number is incorrect or invalid.
4: Your tracking number has expired and removed from carrier's system */


    public static ImageView courierimage;
    public static TextView output;
    private ImageButton qropenbtn,send,donebtn,micbtn;
    public static Dialog courierdialog;
    public static String Code1 ="";
    public static int valid = 0;
    public static String tracking_number,carrier_code,status,itemTimeLength,ItemReceived,
                         DestinationArrived,lastEvent,icon_url;

    public static ArrayList<String >description = new ArrayList<>();
    public static ArrayList<String >locationtransit = new ArrayList<>();
    public static ArrayList<String >checkpoint_status= new ArrayList<>();
    public static ArrayList<String >dates= new ArrayList<>();



    private MediaType mediaType = MediaType.parse("application/json");
    private OkHttpClient client = new OkHttpClient();
    public static  EditText input;
    private RecyclerView crecyclerview;
    private ProgressBar cprogressbar,progressBar20;
    private CarrierAdapter carrierAdapter;
    private ArrayList<CarrierListModel> carrierListModelArrayList = new ArrayList<>();

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;


    Toolbar toolbar,toolbar1;
    MenuItem menuid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_tracking);


        init();
        input.setText(getIntent().getStringExtra("ID"));
        Code1 = getIntent().getStringExtra("CID");
        Glide.with(this).load(getIntent().getStringExtra("URL")).placeholder(R.drawable.imageloading).into(courierimage);
        if (Code1.length()!=0){
            valid = 1;
//            Toast.makeText(getApplicationContext(), carrier_code, Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(),getIntent().getStringExtra("ID"), Toast.LENGTH_SHORT).show();
        }
        send.setOnClickListener(V->{
            if (input.getText().toString().isEmpty() | valid==0){
                Toast.makeText(Product_tracking.this, "please select a courier and enter a valid tracking ID", Toast.LENGTH_SHORT).show();
            }
            else {
            progressBar20.setVisibility(View.VISIBLE);

            String in = input.getText().toString();
            tracking_number = in;
            RequestBody body = RequestBody.create(mediaType,"{\"tracking_number\":\""+in+"\",\"carrier_code\": \""+ Code1 +"\"}");

            Request request = new Request.Builder()
                    .url("https://order-tracking.p.rapidapi.com/trackings/realtime")
                    .post(body)
                    .addHeader("content-type", "application/json")
                    .addHeader("x-rapidapi-key", "a99baae0fcmsh06e4b728572c2e0p157a47jsn483516a43cf5")
                    .addHeader("x-rapidapi-host", "order-tracking.p.rapidapi.com")
                    .build();


            client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Product_tracking.this.runOnUiThread(() -> progressBar20.setVisibility(View.GONE));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String rep = response.body().string();
                Product_tracking.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Product_tracking",rep);
                        progressBar20.setVisibility(View.GONE);
                        //output.append("-->"+rep);
                        try {
                            JSONArray  trackinfo;
                            trackinfo  = new JSONArray();
                            JSONObject jsonObject = new JSONObject(rep);
                            JSONObject reader = jsonObject.getJSONObject("data");
                            JSONArray jsonArray = reader.getJSONArray("items");
                            JSONObject origin = null;


                            for (int i=0;i<jsonArray.length();i++){

                                JSONObject data1 = jsonArray.getJSONObject(i);
                                lastEvent = data1.getString("lastEvent");
                                carrier_code = data1.getString("carrier_code");
                                status= data1.getString("status");
                                DestinationArrived  = data1.getString("destination_country");
                                itemTimeLength = data1.getString("itemTimeLength");

                                origin =  (JSONObject)data1.get("origin_info");
                            }

                            ItemReceived = origin.getString("ItemReceived");

                            trackinfo = (JSONArray) origin.get("trackinfo");

                            for (int t=0;t<trackinfo.length();t++){
                                JSONObject dd = (JSONObject) trackinfo.get(t);


                                description.add(dd.get("StatusDescription").toString());
                                locationtransit.add(dd.get("Details").toString());
                                checkpoint_status.add(dd.get("checkpoint_status").toString());
                                dates.add(dd.get("Date").toString());


                            }

//                            Log.e("st",description.toString());
//                            Log.e("lo",locationtransit.toString());
//                            Log.e("ck", checkpoint_status.toString());
//                            Log.e("dats", dates.toString());





                        } catch (JSONException e) {
                            progressBar20.setVisibility(View.GONE);
                            e.printStackTrace();

                        }
                     details();
                    }
                });
            }




            });
            }
        });
        qropenbtn.setOnClickListener(V->{
            Intent intent = new Intent(Product_tracking.this, QR_Code_Scanner.class);
            startActivity(intent);

        });


        //todo carrier list fetching code//
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this,2);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        crecyclerview.setLayoutManager(linearLayoutManager);
        cprogressbar.setVisibility(View.VISIBLE);


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://order-tracking.p.rapidapi.com/carriers")
                .get()
                .addHeader("content-type", "application/json")
                .addHeader("x-rapidapi-key", "a99baae0fcmsh06e4b728572c2e0p157a47jsn483516a43cf5")
                .addHeader("x-rapidapi-host", "order-tracking.p.rapidapi.com")
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Product_tracking","response--->"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    //Log.e("MainActivity","response--->"+ String.valueOf(response.body()));
                    final String rep = response.body().string();
                    //Toast.makeText(MainActivity.this, rep, Toast.LENGTH_SHORT).show();
                    Product_tracking.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(MainActivity.this, rep, Toast.LENGTH_SHORT).show();
                           // Log.e("Product_tracking",rep);
                            try {
                                JSONObject jsonObject = new JSONObject(rep);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i=1;i<jsonArray.length();i++){

                                    JSONObject data1 = jsonArray.getJSONObject(i);

                                    String name = data1.getString("name");
                                    String pic = data1.getString("picture");
                                    String code = data1.getString("code");

                                    //re.append("["+name +i+"], ");
                                    carrierListModelArrayList.add(new CarrierListModel(name,code,pic));
                                    carrierAdapter.notifyDataSetChanged();


                                }
                                cprogressbar.setVisibility(View.GONE);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    });

                }

            }
        });
        carrierAdapter = new CarrierAdapter(carrierListModelArrayList);
        crecyclerview.setAdapter(carrierAdapter);
        //todo/////////////////////////////

        courierimage.setOnClickListener(V->{
            courierdialog.show();
        });


        //todo:mic btn function for translating speech to text using google translator//
        micbtn.setOnClickListener(v->{

            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
            try {
                startActivityForResult(intent, 1);
                input.setText("");
            }catch (ActivityNotFoundException e){
                Toast.makeText(getApplicationContext(),"Your Device doesn't Support this Feature",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

        });
        /*-----------------------------------------------------------------------*/

    }
    private void details() {
        Intent intent = new Intent(Product_tracking.this, Tracking_Details.class);
        intent.putStringArrayListExtra("description",description);
        intent.putStringArrayListExtra("locationtransit",locationtransit);
        intent.putStringArrayListExtra("checkpoint_status",checkpoint_status);
        intent.putStringArrayListExtra("dates",dates);



        intent.putExtra("tracking_number", tracking_number);
        intent.putExtra("carrier_code", carrier_code);
        intent.putExtra("status",status);
        intent.putExtra("itemTimeLength",itemTimeLength);
        intent.putExtra("ItemReceived",ItemReceived);
        intent.putExtra(" DestinationArrived",  DestinationArrived);
        intent.putExtra("lastEvent", lastEvent);
        intent.putExtra("icon_url",icon_url);
        startActivity(intent);
    }
    public void init(){

        drawerLayout =findViewById(R.id.drawer_layout1);
        navigationView = findViewById(R.id.nav1);
        micbtn = findViewById(R.id.micbtn);
        toolbar1 =  findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);

        /*todo setting navigation view*/
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        navigationView.bringToFront();

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar1,R.string.draweropen,R.string.drawerclose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_b);

        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        /*----------------------------*/

        input = findViewById(R.id.input);
        output = findViewById(R.id.output);
        send = findViewById(R.id.send);
        qropenbtn = findViewById(R.id.qropenbtn);
        courierimage = findViewById(R.id.courierimage);
        courierimage.bringToFront();
        progressBar20 = findViewById(R.id.progressBar20);




        //todo dialog layout1 items//
        courierdialog = new Dialog(this);
        courierdialog.setContentView(R.layout.courierlist);
        courierdialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        crecyclerview = courierdialog.findViewById(R.id.crecyclerview);
        cprogressbar = courierdialog.findViewById(R.id.cprogressBar);
        toolbar = courierdialog.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if (resultCode== RESULT_OK && data !=null){
                ArrayList<String> info = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                input.setText(info.get(0).replaceAll("\\s", "").toUpperCase());
                }
            break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        menuid =  menu.findItem(R.id.searchbtn);
        SearchView searchView = (SearchView) menuid.getActionView();
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        customType(Product_tracking.this,"fadein-to-fadeout");
    }

    /**
     * Called when the user submits the query. This could be due to a key press on the
     * keyboard or due to pressing a submit button.
     * The listener can override the standard behavior by returning true
     * to indicate that it has handled the submit request. Otherwise return false to
     * let the SearchView handle the submission by launching any associated intent.
     *
     * @param query the query text that is to be submitted
     * @return true if the query has been handled by the listener, false to let the
     * SearchView perform the default action.
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    /**
     * Called when the query text is changed by the user.
     *
     * @param newText the new content of the query text field.
     * @return false if the SearchView should perform the default action of showing any
     * suggestions if available, true if the action was handled by the listener.
     */
    @Override
    public boolean onQueryTextChange(String newText) {

        String searchinput = newText.toLowerCase();
        ArrayList<CarrierListModel> searchlist = new ArrayList<>();

        for (CarrierListModel name: carrierListModelArrayList){

            if (name.getName().toLowerCase().contains(searchinput)){
                searchlist.add(name);


            }

        }
        carrierAdapter.updatelist(searchlist);
        return true;
    }


    /**
     * Called when an item in the navigation menu is selected.
     *
     * @param item The selected item
     * @return true to display the item as the selected item
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.home:
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.profile:
                startActivity(new Intent(getApplicationContext(), profile.class));
                break;

            case R.id.savedfile:
                startActivity(new Intent(getApplicationContext(), Saved_orders.class));
                break;

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), welcome.class));
                finish();
                break;
        }
        return true;
    }


}