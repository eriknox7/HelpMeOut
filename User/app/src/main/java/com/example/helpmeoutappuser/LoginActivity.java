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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    EditText e_email, e_password;
    Button signinBtn;
    TextView signup_link,fgtbtn;
    String s_email, s_password;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        e_email = findViewById(R.id.e_email);
        e_password = findViewById(R.id.e_password);

        fgtbtn = findViewById(R.id.forgotpass);

        fgtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ResetPasswordActivity.class));
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        progressbar = findViewById(R.id.progressBar);

        signup_link = findViewById(R.id.signup_link);
        signup_link.setOnClickListener(view -> {
            Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(i);
        });

        signinBtn = findViewById(R.id.signin_btn);
        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }

            private void loginUser() {
                s_email = e_email.getText().toString().trim();
                s_password = e_password.getText().toString().trim();

                // validations for input email and password
                if (TextUtils.isEmpty(s_email)) {
                    Toast.makeText(getApplicationContext(), "Please enter email!!", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(s_password)) {
                    Toast.makeText(getApplicationContext(), "Please enter password!!", Toast.LENGTH_LONG).show();
                    return;
                }

                // signin existing user
                firebaseAuth.signInWithEmailAndPassword(s_email, s_password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Login successful!!", Toast.LENGTH_LONG).show();

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                assert user != null;

                                progressbar.setVisibility(View.GONE);;
                                Intent intent2 = new Intent(getApplicationContext(), HomeScreen.class);
                                startActivity(intent2);
                                finishAffinity();


                            } else {

                                // sign-in failed
                                Toast.makeText(getApplicationContext(), "Login failed!!", Toast.LENGTH_LONG).show();

                                // hide the progress bar
                                progressbar.setVisibility(View.GONE);
                            }
                        });
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {

        if (currentUser != null) {
            // User is signed in
            Intent i = new Intent(LoginActivity.this, HomeScreen.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            // User is signed out

        }

    }

}