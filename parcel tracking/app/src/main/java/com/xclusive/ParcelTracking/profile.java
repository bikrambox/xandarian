package com.xclusive.ParcelTracking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static com.xclusive.ParcelTracking.SplashScreen.currentuser;

public class profile extends AppCompatActivity {
private ImageButton backbtn;
private TextView username,email;
private ImageView profile1;
private FirebaseFirestore db = FirebaseFirestore.getInstance();
private ProgressBar progressBar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
        progressBar3.setVisibility(View.VISIBLE);
        loaddata();

    }

    private void init() {
        username = findViewById(R.id.username);
        email = findViewById(R.id.useremail);
        profile1 = findViewById(R.id.userprofile);
        progressBar3 = findViewById(R.id.progressBar3);
        findViewById(R.id.backbtn).setOnClickListener(v->{
            onBackPressed();
        });

    }
    private void loaddata() {
        FirebaseAuth usr = FirebaseAuth.getInstance();
         db.collection("USERS").document(usr.getUid())
                 .get()
                 .addOnCompleteListener( task -> {
                     if (task.isSuccessful()) {
                         String n = task.getResult().get("FullName").toString();
                         String e = task.getResult().get("Email").toString();
                         username.setText(n);
                         email.setText(e);
                         progressBar3.setVisibility(View.GONE);
                     } else {
                      progressBar3.setVisibility(View.GONE);
                     }
                 }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 progressBar3.setVisibility(View.GONE);
             }
         });


    }
}