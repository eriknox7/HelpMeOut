package com.example.helpmeoutappuser;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CredentialsActivity extends AppCompatActivity {
    Button registerBtn, loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credentials);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        registerBtn = findViewById(R.id.register);
        loginBtn = findViewById(R.id.loginBtn);

        registerBtn.setOnClickListener(view -> {
            Intent i = new Intent(CredentialsActivity.this, RegisterActivity.class);
            startActivity(i);
        });

        loginBtn.setOnClickListener(view -> {
            Intent i = new Intent(CredentialsActivity.this, LoginActivity.class);
            startActivity(i);
        });
    }
}