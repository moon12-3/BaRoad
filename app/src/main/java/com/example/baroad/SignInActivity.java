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

public class SignInActivity extends AppCompatActivity {
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        auth = auth.getInstance();

        EditText email = findViewById(R.id.email_text);
        EditText pass = findViewById(R.id.pass_text);

        findViewById(R.id.join_btn).setOnClickListener(v->{
            if(!email.getText().toString().equals("") && !pass.getText().toString().equals("")) {
                // 이메일과 비밀번호가 공백이 아닌 경우
                auth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // 회원가입 성공시
                                    Toast.makeText(SignInActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // 계정이 중복된 경우
                                    Toast.makeText(SignInActivity.this, "이미 존재하는 계정입니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            else {
                // 이메일과 비밀번호가 공백인 경우
                Toast.makeText(this, "계정과 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
            }
        });
    }
}