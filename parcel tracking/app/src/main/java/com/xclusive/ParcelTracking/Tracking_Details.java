package com.xclusive.ParcelTracking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.List;

public class Tracking_Details extends AppCompatActivity {
    private ImageButton backbtn,anotherbtn;
    private TextView sc, pp,prh,pt,pd;
    private TextView scn, ppn,prhn,ptn,pdn;
    private TextView scd, ppd,prhd,ptd,pdd,  date1,date2;

    StepView step_view;

//    private BottomSheetBehavior bottomSheetBehavior;
//    private FrameLayout frameLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking__details);
//        frameLayout = findViewById(R.id.container_bottom_sheet);
//        bottomSheetBehavior = BottomSheetBehavior.from(frameLayout);
//        bottomSheetBehavior.setPeekHeight(200);
//        //now you can set the states:
//        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        init();
        step_view = findViewById(R.id.step_view);

        step_view.getState()
                .selectedTextColor(ContextCompat.getColor(this, R.color.white))
                .animationType(StepView.ANIMATION_CIRCLE)
                .selectedCircleColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .selectedCircleRadius(getResources().getDimensionPixelSize(R.dimen.dp14))
                .selectedStepNumberColor(ContextCompat.getColor(this, R.color.white))
                // You should specify only stepsNumber or steps array of strings.
                // In case you specify both steps array is chosen.
                .steps(new ArrayList<String>() {{
                    add("Shipment Created");
                    add("Package Picked up");
                    add("Package Reached HUB");
                    add("Package In Transit");
                    add("Package Delivered");
                }})
                // You should specify only steps number or steps array of strings.
                // In case you specify both steps array is chosen.
                .stepsNumber(5)
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .stepLineWidth(getResources().getDimensionPixelSize(R.dimen.dp1))
                .textSize(getResources().getDimensionPixelSize(R.dimen.sp14))
                .stepNumberTextSize(getResources().getDimensionPixelSize(R.dimen.sp16))
                .typeface(ResourcesCompat.getFont(getApplicationContext(), R.font.roboto))
                // other state methods are equal to the corresponding xml attributes
                .commit();


        step_view.go(0, true);
        step_view.done(true);
        //step_view.done(false);
        step_view.setOnStepClickListener(new StepView.OnStepClickListener() {
            @Override
            public void onStepClick(int step) {
                // 0 is the first step
            }
        });



//        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                switch (newState) {
//                    case BottomSheetBehavior.STATE_DRAGGING:
//                    case BottomSheetBehavior.STATE_EXPANDED:
//                        break;
//                    case BottomSheetBehavior.STATE_COLLAPSED:
//                    default:
//
//                }
//            }
//
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//
//            }
//        });
 }

    private void init() {
        backbtn = findViewById(R.id.backbtn);
        anotherbtn = findViewById(R.id.track_anotherbtn);

        sc = findViewById(R.id.ship_date);    //todo:
        pp = findViewById(R.id.package_date);
        prh= findViewById(R.id.package_reach_hub);
        pt = findViewById(R.id.transit_date);
        pd = findViewById(R.id.delivery_date);

        scn = findViewById(R.id.pack_loc);    //todo:
        ppn = findViewById(R.id.pack_p_loc);
        prhn= findViewById(R.id.pr_loc);
        ptn = findViewById(R.id.pt_loc);
        pdn = findViewById(R.id.pd_loc);

        scd = findViewById(R.id.e_time);      //todo:
        ppd = findViewById(R.id.pp_time);
        prhd = findViewById(R.id.pr_time);
        ptd = findViewById(R.id.pt_time);
        pdd = findViewById(R.id.pd_time);

        date1 = findViewById(R.id.date1);     //todo:
        date2 = findViewById(R.id.date2);

        backbtn.setOnClickListener(V->{
            onBackPressed();
            finish();
        });
        anotherbtn.setOnClickListener(v->{
            Intent in = new Intent(getApplicationContext(),Product_tracking.class);
            startActivity(in);
            finish();
        });
    }
}