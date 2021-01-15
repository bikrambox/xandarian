package com.xclusive.myapplication;
import static maes.tech.intentanim.CustomIntent.customType;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import okhttp3.ResponseBody;import android.content.Intent;
import android.widget.Toast;

import com.bumptech.glide.Glide;


public class Product_tracking extends AppCompatActivity implements SearchView.OnQueryTextListener {




    public static ImageView courierimage;
    public static TextView output;
    private ImageButton qropenbtn,send,donebtn;
    public static Dialog courierdialog;
    public static String Code ="ekart";
    public static int valid = 0;


    private MediaType mediaType = MediaType.parse("application/json");
    private OkHttpClient client = new OkHttpClient();
    private EditText input;
    private RecyclerView crecyclerview;
    private ProgressBar cprogressbar,progressBar2;
    private CarrierAdapter carrierAdapter;
    private ArrayList<CarrierListModel> carrierListModelArrayList = new ArrayList<>();




    Toolbar toolbar;
    MenuItem menuid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_tracking);


        init();

        send.setOnClickListener(V->{


            if (input.getText().toString().isEmpty() | valid==0){
                Toast.makeText(Product_tracking.this, "please select a courier and enter a valid tracking ID", Toast.LENGTH_SHORT).show();
            }
            else {
                progressBar2.setVisibility(View.VISIBLE);

            String in = input.getText().toString();
            RequestBody body = RequestBody.create(mediaType,"{\"tracking_number\":\""+in+"\",\"carrier_code\": \""+Code+"\"}");

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
                //progressBar2.setVisibility(View.GONE);
                Log.e("Product_tracking",e.getMessage());
                Product_tracking.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar2.setVisibility(View.GONE);
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String rep = response.body().string();

                Product_tracking.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Product_tracking",rep);
                        progressBar2.setVisibility(View.GONE);
                        //output.append("-->"+rep);
                        try {
                            JSONObject jsonObject = new JSONObject(rep);

                            JSONObject reader = jsonObject.getJSONObject("data");
                            JSONArray jsonArray = reader.getJSONArray("items");

                            for (int i=0;i<jsonArray.length();i++){

                                JSONObject data1 = jsonArray.getJSONObject(i);

                                String name = data1.getString("carrier_code");
                                String STATUS = data1.getString("status");



                                output.append(name+"\n");
                                output.append(STATUS);




                                //re.append("["+name +i+"], ");

                                //carrierListModelArrayList.add(new CarrierListModel(name,pic));
                               // carrierAdapter.notifyDataSetChanged();


                            }
                           // progressBar.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            progressBar2.setVisibility(View.GONE);
                            e.printStackTrace();

                        }

                    }
                });
            }
        });
            }
        });

        qropenbtn.setOnClickListener(V->{
            Intent intent = new Intent(Product_tracking.this, QR_Code_Scanner.class);
            startActivity(intent);
            finish();
        });


        //todo carrier list fetching code//
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this,2);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        crecyclerview.setLayoutManager(linearLayoutManager);
        cprogressbar.setVisibility(View.VISIBLE);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
//                            .url("https://api.trackingmore.com/v2/carriers/")
//                            .get()
//                            .addHeader("content-type", "application/json")
//                            .addHeader(   "Trackingmore-Api-Key", "49852ed1-3a54-4570-8f69-4c9e9265075b")
//                            .build();

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
                            Log.e("Product_tracking",rep);
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
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            //re.setText(String.valueOf(rep));

                        }

                    });

                }

            }
        });

        //carrierListModelArrayList.add(new CarrierListModel("name","one"));
        carrierAdapter = new CarrierAdapter(carrierListModelArrayList);
        crecyclerview.setAdapter(carrierAdapter);
        //todo/////////////////////////////

        courierimage.setOnClickListener(V->{
            courierdialog.show();
        });
    }
    public void init(){
        input = findViewById(R.id.input);
        output = findViewById(R.id.output);
        send = findViewById(R.id.send);
        qropenbtn = findViewById(R.id.qropenbtn);
        courierimage = findViewById(R.id.courierimage);
        courierimage.bringToFront();
        progressBar2 = findViewById(R.id.progressBar2);



        //todo dialog layout1 items//
        courierdialog = new Dialog(this);
        courierdialog.setContentView(R.layout.courierlist);
        courierdialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        crecyclerview = courierdialog.findViewById(R.id.crecyclerview);
        cprogressbar = courierdialog.findViewById(R.id.cprogressBar);


        toolbar = courierdialog.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("search courier");

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


}