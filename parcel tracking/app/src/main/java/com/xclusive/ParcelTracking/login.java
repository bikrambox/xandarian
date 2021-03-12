package com.xclusive.ParcelTracking;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
private ImageButton donelogin;
private ProgressBar progressBar1;
private EditText uemail,password;
private FirebaseAuth user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        donelogin = findViewById(R.id.rdonebtn);

        donelogin.setOnClickListener(v->{
          if (!validemail() | !validpassword()){
              return;
          }
          else {
              login_user();

          }

        });
    }



    private void login_user() {
        progressBar1.setVisibility(View.VISIBLE);
        user = FirebaseAuth.getInstance();
        user.signInWithEmailAndPassword(uemail.getText().toString(),password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()){
                             progressBar1.setVisibility(View.GONE);
                             Intent  main1= new Intent(getApplicationContext(), Product_tracking.class);

                             main1.putExtra("ID","");
                             main1.putExtra("CID","");
                             main1.putExtra("URL","");
                             startActivity(main1);
                                         finish();
                         }
                         else {
                             progressBar1.setVisibility(View.GONE);
                             Toast.makeText(login.this, "Email Or Password incorrect...", Toast.LENGTH_SHORT).show();
                         }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar1.setVisibility(View.GONE);
            }
        });



    }

    private void init() {
        progressBar1 = findViewById(R.id.progressBar1);
        uemail =findViewById(R.id.uname1);
        password  = findViewById(R.id.password);

    }


    private boolean validpassword() {
        String val =password.getText().toString();
        String passpattern ="^" +
                "(?=.*[a-zA-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$)" + ".{4,}" + "$";

        if(val.isEmpty()){
            password.setError("Field cannot be empty");
            return false;
        }
        else if (!val.matches(passpattern))
        {
            password.setError("Field should contain at least one symbol ,character ,upper and lower case letters ");
            password.setText("");
            return false;
        }

        else
        {
            password.setError(null);
            password.setEnabled(false);

            return true;
        }
    }
    private boolean validemail() {
        String val =uemail.getText().toString();
        String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(val.isEmpty()){
            uemail.setError("Field cannot be empty");

            return false;
        }
        else if(!val.matches(emailpattern))
        {
            uemail.setError("Invalid email address");
            uemail.setText("");
            return false;
        }
        else
        {
            uemail.setError(null);
            uemail.setEnabled(false);
            return true;
        }
    }
}