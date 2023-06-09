package com.example.baroad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottom;

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
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.frame, new Myplan_map())
//                        .commit();
                break;
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
}