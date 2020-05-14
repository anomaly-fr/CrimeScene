package com.example.crimescene;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {
    BottomNavigationView bottomNavigationView;
    MainPagerAdapter pagerAdapter;
    ViewPager mainPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        //Fragment homeFragment = new HomeFragment();
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,homeFragment).commit();

        mainPager= findViewById(R.id.pager);
        pagerAdapter= new MainPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.POSITION_UNCHANGED);
        mainPager.setAdapter(pagerAdapter);

        mainPager.setOffscreenPageLimit(3);
        mainPager.addOnPageChangeListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home_item);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
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
            case R.id.tips_item:
                mainPager.setCurrentItem(3);
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
            case 3: bottomNavigationView.setSelectedItemId(R.id.tips_item);
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
                    return  new TipsFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
