package com.example.crimescene.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.crimescene.Doms;
import com.example.crimescene.LocationServiceYo;
import com.example.crimescene.R;
import com.google.firebase.firestore.GeoPoint;

public class Sauce extends AppCompatActivity implements View.OnClickListener {

    Location location;
    GeoPoint geoPoint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sauce);

        findViewById(R.id.stop_emergency).setOnClickListener(this);

        if (this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            askPermissions();
        } else {
            initLocationService();
        }

    }

    //use this to stop it on a button click
    public void stopLocationService() {
        startService(new Intent(this, LocationServiceYo.class).setAction(Doms.EMERGENCY_STOP));
    }

    public void initLocationService() {
        startService(new Intent(this, LocationServiceYo.class).setAction(Doms.EMERGENCY_START));
    }

    public void askPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 68);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 68:
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Please enable location services", Toast.LENGTH_LONG).show();
                    askPermissions();
                } else
                    initLocationService();

        }
    }

    @Override
    public void onClick(View v){
        stopLocationService();
        finish();
    }
}
