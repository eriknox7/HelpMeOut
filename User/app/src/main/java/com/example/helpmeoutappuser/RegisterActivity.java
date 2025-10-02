package com.example.helpmeoutappuser;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText name,email,pass,repass,phone;      //Declaration
    Button register;
    TextView login;

    ProgressBar buttonProgress;
    boolean passwordVisible;

    Model userData;

    //-------------- Firebase ---------------
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;

    // ------------- Progress Dialog ----------


    private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        pass = (EditText) findViewById(R.id.password);
        repass = (EditText) findViewById(R.id.repassword);
        register = (Button) findViewById(R.id.login_button);
        login = (TextView) findViewById(R.id.login);
        progressbar = findViewById(R.id.progressbar);

        firebaseAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to LoginActivity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to LoginActivity
                SetValidation();
            }
        });



    }


    public void SetValidation() {

        userData = new Model();

        progressbar.setVisibility(View.VISIBLE);

        //Local Variable to store Password

        String password = pass.getText().toString().trim();
        String repassword = repass.getText().toString().trim();

        // Profile Data Store

        userData.setName(name.getText().toString());
        userData.setEmail(email.getText().toString());
        userData.setMno(phone.getText().toString());
        userData.setImageurl("https://firebasestorage.googleapis.com/v0/b/womensafety-fa171.appspot.com/o/profile_nav_logo.png?alt=media&token=8dc14aa7-7075-499f-bf47-cb5821271c98");

        // Firebase Database Reference

        database= FirebaseDatabase.getInstance();
        myRef=database.getReference();


        // Validate Data

        // Full name

        if (TextUtils.isEmpty(userData.name)) {

            Toast.makeText(RegisterActivity.this, "Please Enter Full Name ", Toast.LENGTH_SHORT).show();
            progressbar.setVisibility(View.GONE);
            return;
        }


        // Email
        if (TextUtils.isEmpty(userData.email)) {

            Toast.makeText(RegisterActivity.this, "Please Enter Email ", Toast.LENGTH_SHORT).show();
            progressbar.setVisibility(View.GONE);
            return;
        }

        // password

        if (TextUtils.isEmpty(password)) {

            Toast.makeText(RegisterActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            progressbar.setVisibility(View.GONE);
            return;
        }

        // repassword

        if (TextUtils.isEmpty(repassword)) {

            Toast.makeText(RegisterActivity.this, "Please Enter RePassword", Toast.LENGTH_SHORT).show();
            progressbar.setVisibility(View.GONE);
            return;
        }

        if (password.length() < 6) {

            Toast.makeText(RegisterActivity.this, "Password Must be more than 6 digit & less than 1 digit", Toast.LENGTH_SHORT).show();
        }


        // Mobile Number

        if (TextUtils.isEmpty(userData.mno)) {

            Toast.makeText(RegisterActivity.this, "Please Enter Mobile Number ", Toast.LENGTH_SHORT).show();
            progressbar.setVisibility(View.GONE);
            return;
        }

        if (userData.mno.length() < 10) {

            Toast.makeText(RegisterActivity.this, "Mobile no. must be 10 digit number! Enter Valid number. ", Toast.LENGTH_SHORT).show();
            progressbar.setVisibility(View.GONE);
        }



        // Validation Done !!


        if (password.equals(repassword) && userData.mno.length() == 10 ) {

//            buttonProgress.setVisibility(View.VISIBLE);
            //      CreateAccount_Text.setVisibility(View.GONE);
            firebaseAuth.createUserWithEmailAndPassword(userData.email, password)
                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                myRef.child("Users").child(firebaseAuth.getCurrentUser().getUid()).setValue(userData);
                                progressbar.setVisibility(View.GONE);
                                Toast.makeText(RegisterActivity.this, "Account Created...", Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(getApplicationContext(), Login.class));
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                finish();

                            } else {
                                String msg = task.getException().toString();
                                Toast.makeText(RegisterActivity.this, "Error:" + msg, Toast.LENGTH_SHORT).show();
                                progressbar.setVisibility(View.GONE);
                            }

                            // ...
                        }


                    });

        }

    }



}
