package com.example.baroad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.go_sign_btn).setOnClickListener(v-> {
            Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
            startActivity(intent);
        });
    }


}