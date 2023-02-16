package com.example.baroad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseAuth auth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser != null) {
            // 로그인된 사용자가 있는 경우 필요한 작업 진행
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        EditText IDText = findViewById(R.id.id_text);
        EditText PassText = findViewById(R.id.pass_text);

        findViewById(R.id.go_main_btn).setOnClickListener(v -> {
            if(!IDText.getText().toString().equals("") && !PassText.getText().toString().equals("")) {
                auth.signInWithEmailAndPassword(IDText.getText().toString(), PassText.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "로그인 완료!", Toast.LENGTH_SHORT).show();

                                    auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                                        @Override
                                        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                                            FirebaseUser user = firebaseAuth.getCurrentUser();
                                            if(user != null) {
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
                                    });
                                }
                            }
                        });
            }
            else {
                Toast.makeText(LoginActivity.this, "계정과 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.go_sign_btn).setOnClickListener(v-> {
            Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
            startActivity(intent);
        });
    }


}