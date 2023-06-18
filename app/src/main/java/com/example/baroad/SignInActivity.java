package com.example.baroad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.baroad.Model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        auth = auth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();

        EditText email = findViewById(R.id.email_text);
        EditText pass = findViewById(R.id.pass_text);
        EditText name = findViewById(R.id.id_nickname);

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

                                    FirebaseUser user = auth.getCurrentUser();
                                    String uid = user.getUid();   // 이메일로 저장
                                    UserModel userModel = new UserModel(name.getText().toString(), "imgsrc", "없음");
                                    addUserToDatabase(userModel, uid);
                                    finish();
                                    Log.d("mytag", "회원 가입(=유저 생성) 성공 " + uid);

                                    Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    if(pass.getText().toString().length() < 6) Toast.makeText(SignInActivity.this, "비밀번호를 6자리 이상으로 설정해주세요.", Toast.LENGTH_SHORT).show();
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

    private void addUserToDatabase(UserModel userModel, String uId) {
        db.child("users").child(uId).setValue(userModel);
    }
}