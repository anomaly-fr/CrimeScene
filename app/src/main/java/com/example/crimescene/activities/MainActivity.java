package com.example.crimescene.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;

import com.example.crimescene.AssistantService;
import com.example.crimescene.Doms;
import com.example.crimescene.R;
import com.example.crimescene.fragments.EmergenciesFragment;
import com.example.crimescene.fragments.GuideFragment;
import com.example.crimescene.fragments.HomeFragment;
import com.example.crimescene.fragments.InvestigateFragment;
import com.example.crimescene.fragments.TipsFragment;
import com.example.crimescene.services.LocationServiceYo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {
    BottomNavigationView bottomNavigationView;
    MainPagerAdapter pagerAdapter;
    ViewPager mainPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, PackageManager.PERMISSION_GRANTED);
        //startService(new Intent(this, AssistantService.class));
        startService(new Intent(this, LocationServiceYo.class).setAction(Doms.EMERGENCY_START));

        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        //Fragment homeFragment = new HomeFragment();
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,homeFragment).commit();
        FirebaseMessaging.getInstance().subscribeToTopic("all");

        mainPager= findViewById(R.id.pager);
        pagerAdapter= new MainPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.POSITION_UNCHANGED);
        mainPager.setAdapter(pagerAdapter);

        mainPager.setOffscreenPageLimit(4);
        mainPager.addOnPageChangeListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home_item);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
       Intent exit = new Intent(Intent.ACTION_MAIN);
       exit.addCategory(Intent.CATEGORY_HOME);
       exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       startActivity(exit);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {

            case R.id.home_item:
                mainPager.setCurrentItem(0);
                break;

            case R.id.investigate_item:
                mainPager.setCurrentItem(1);
                break;
            case R.id.guide_item:
                mainPager.setCurrentItem(2);
                break;
            case R.id.emergencies:
                mainPager.setCurrentItem(3);
                break;
            case R.id.tips_item:
                mainPager.setCurrentItem(4);
                break;
        }
        return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                bottomNavigationView.setSelectedItemId(R.id.home_item);
                break;
            case 1: bottomNavigationView.setSelectedItemId(R.id.investigate_item);
            break;
            case 2: bottomNavigationView.setSelectedItemId(R.id.guide_item);
            break;
            case 3:
                bottomNavigationView.setSelectedItemId(R.id.emergencies);
                break;
            case 4: bottomNavigationView.setSelectedItemId(R.id.tips_item);
            break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class MainPagerAdapter extends FragmentPagerAdapter{
        public MainPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch(position) {

                case 0:
                    return new HomeFragment();

                case 1:
                    return new InvestigateFragment();

                case 2:
                     return new GuideFragment();
                case 3:
                    return new EmergenciesFragment();
                case 4:
                    return  new TipsFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

}
