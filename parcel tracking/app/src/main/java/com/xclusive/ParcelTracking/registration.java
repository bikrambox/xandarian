package com.xclusive.ParcelTracking;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class registration extends AppCompatActivity {
    private EditText uname,uemail,password,cpassword;
    private ImageButton rdonebtn;
    private ProgressBar progressBar3;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();

        mAuth = FirebaseAuth.getInstance();


        rdonebtn.setOnClickListener(v->{
            if (!validatename() | !validateemail()  | !validatecpass() )
            {
                return;
            }
            else{
                progressBar3.setVisibility(View.VISIBLE);
                Registration();
            }

        });


    }

    private void Registration() {
        mAuth.createUserWithEmailAndPassword(uemail.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                       String user = mAuth.getUid().toString();
                        register(user);

                    } else {
                        // If sign in fails, display a message to the user.
                        progressBar3.setVisibility(View.GONE);

                    }


                });
    }

    private void init() {
        uname =findViewById(R.id.uname1);
        uemail =findViewById(R.id.uemail);
        password =findViewById(R.id.password1);
        cpassword =findViewById(R.id.password2);
        rdonebtn =findViewById(R.id.rdonebtn);
        progressBar3 =findViewById(R.id.progressBar3);
    }

    private void register(String user) {
        Map<Object,String> data = new HashMap<>();
        data.put("FullName",uname.getText().toString());
        data.put("Email",uemail.getText().toString());
        data.put("Password",password.getText().toString());

        db.collection("USERS").document(user)
                .set(data)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        progressBar3.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Registration Successfully Done",Toast.LENGTH_SHORT).show();
                         onBackPressed();
                         startActivity(new Intent(getApplicationContext(),login.class));
                         finish();

                    }
                    else {
                        progressBar3.setVisibility(View.GONE);
                    }
                });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
       // FirebaseUser currentUser = mAuth.getCurrentUser();
      //updateUI(currentUser);
    }


    //todo validation edit text views/
    private boolean validatename() {
        String val =uname.getText().toString();

        if(val.isEmpty()){
            uname.setError("Field cannot be empty");
            return false;
        }
        else if(val.length()>=50 || val.length() < 2)
        {
            uname.setError("name cannot be more than 50 letters!");
            uname.setText("");
            return false;
        }
        else
        {
            uname.setError(null);
            uname.setEnabled(false);

            return true;
        }

    }
    private boolean validateemail() {
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

    private boolean validatepass() {

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
    private boolean validatecpass() {
        validatepass();
        String val =password.getText().toString();
        String val1 =cpassword.getText().toString();

        if(val.isEmpty()){
            cpassword.setError("Field cannot be empty");
            return false;
        }
        else if(!val.matches(val1))
        {
            cpassword.setError("password  not matched!");
            cpassword.setText("");
            return false;


        }
        else
        {
            cpassword.setError(null);
            cpassword.setEnabled(false);

            return true;
        }

    }
}

