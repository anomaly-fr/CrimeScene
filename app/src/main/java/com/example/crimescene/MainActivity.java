package com.example.crimescene;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        Fragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,homeFragment).commit();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch(item.getItemId()) {

                    case R.id.home_item:
                       selectedFragment = new HomeFragment();
                       break;

                    case R.id.investigate_item:
                        selectedFragment = new InvestigateFragment();
                        break;
                    case R.id.guide_item:
                        selectedFragment = new GuideFragment();
                        break;
                    case R.id.tips_item:
                        selectedFragment = new TipsFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                return true;
            }
        });
    }
}
