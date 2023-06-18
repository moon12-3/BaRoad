package com.example.baroad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.baroad.post.TripPost1;
import com.example.baroad.post.TripPost2;
import com.example.baroad.post.TripPost3;
import com.example.baroad.post.TripPost4;
import com.example.baroad.post.TripPost5;
import com.example.baroad.post.TripPost6;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottom;
    OnBackPressedListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottom = findViewById(R.id.navigationView);
        getSupportFragmentManager().beginTransaction().add(R.id.frame, new MainFragment()).commit();

        bottom.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new MainFragment()).commit();
                        break;
                    case R.id.around:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new LookAround()).commit();
                        break;
                    case R.id.my_list:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new MyPlan()).commit();
                        break;
                    case R.id.my_page:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Mypage()).commit();
                        break;
                }

                return true;
            }
        });
    }

    public void changeFragment(int index) {
        switch (index) {
            case 1:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, new MyPlan())
                        .commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, new Myplan_map())
                        .commit();
                break;
            case 3 :
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, new LookAround())
                        .commit();
            case 4 :
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, new MainFragment())
                        .commit();

        }
    }

    public void setOnBackPressedListener(OnBackPressedListener listener){
        this.listener = listener;
    }

    @Override
    public void onBackPressed() {
        if(listener!=null){
            listener.onBackPressed();
        }else{
            super.onBackPressed();
        }
    }

    public void endActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public FragmentManager getFragmentMana() {
        return getSupportFragmentManager();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void replacePost(int index) {
        Fragment fragment = TripPost1.newInstance();
        switch (index) {
            case 2:
                fragment = TripPost2.newInstance();
                break;
            case 3:
                fragment = TripPost3.newInstance();
                break;
            case 4:
                fragment = TripPost4.newInstance();
                break;
            case 5:
                fragment = TripPost5.newInstance();
                break;
            case 6:
                fragment = TripPost6.newInstance();
                break;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}