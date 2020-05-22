package com.example.crimescene.services;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.crimescene.Doms;
import com.example.crimescene.activities.Sauce;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.HashMap;
import java.util.Map;

public class LocationServiceYo extends Service implements LocationListener, OnCompleteListener<Void> {

    LocationManager locationManager;
    GeoPoint geoPoint;
    Map<String,Object> map= new HashMap<>();
    int serviceType=1;



    public LocationServiceYo() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void initLocation(){
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 100, this);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 100, this);
            locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 1000, 100, this);
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent.getAction().equals(Doms.EMERGENCY_START)){
            serviceType=1;
            initLocation();
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                NotificationChannel notificationChannel= new NotificationChannel("emergency","Emergency obviously", NotificationManager.IMPORTANCE_LOW);
                notificationChannel.setVibrationPattern(new long[]{1000L, 1000L, 1000L}); //TODO same, beautify notif
                ((NotificationManager)getSystemService(NOTIFICATION_SERVICE)).createNotificationChannel(notificationChannel);
                Notification notification= new Notification.Builder(this,"emergency")  // TODO beautify notif
                        .setContentTitle("trackin")
                        .setContentText("backup in no time")
                        .setContentIntent(PendingIntent.getActivity(this,0,new Intent(this, Sauce.class),PendingIntent.FLAG_ONE_SHOT))//pls Don't remove this
                        .build();
                startForeground(69,notification);
            }

        }
        else if(intent.getAction().equals(Doms.EMERGENCY_STOP)){
            stopForeground(true);
            stopSelf();
        }
        else if(intent.getAction().equals(Doms.COP_ONLINE)){
            serviceType=2;
            initLocation();
            if(Build.VERSION.SDK_INT> Build.VERSION_CODES.O) {
                Notification notification = new Notification.Builder(this,"emergency")
                        .setContentText("Eh")
                        .setContentTitle("meh") //TODO make pretty
                        .setContentIntent(PendingIntent.getActivity(this,0,new Intent(this,Sauce.class),PendingIntent.FLAG_ONE_SHOT))
                        .build();
                startForeground(68, notification);
            }
        }
        else if(intent.getAction().equals(Doms.COP_OFFLINE)){
            map= new HashMap<>();
            map.put(FirebaseAuth.getInstance().getUid(),
                    geoPoint);
            FirebaseFirestore.getInstance().collection("OnlineList").document("OnlineList")
                    .update(map);
            stopForeground(true);
            stopSelf();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FirebaseFirestore.getInstance().collection("emergencies").document(GoogleSignIn.getLastSignedInAccount(this).getEmail())
                .delete()
                .addOnCompleteListener(this);
    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        if(task.isSuccessful())
            Toast.makeText(this, "SOS mode exited", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        geoPoint = new GeoPoint(location.getLatitude(),location.getLongitude());
        map.put(Timestamp.now().toString(),geoPoint);
        if(serviceType==1) {
            FirebaseFirestore.getInstance().collection("emergencies").document(GoogleSignIn.getLastSignedInAccount(this).getEmail())
                    .update(map);
            FirebaseFirestore.getInstance().collection("emergencies").document(GoogleSignIn.getLastSignedInAccount(this).getEmail())
                    .collection("latest")
                    .document("location")
                    .set(new Err(geoPoint));
        }
        else{
            FirebaseFirestore.getInstance().collection("OnlineList")
                    .document("OnlineList")
                    .update(FirebaseAuth.getInstance().getUid(),geoPoint);
        }


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    class Err{
        GeoPoint geoPoint;

        public Err() {
        }

        public Err(GeoPoint geoPoint) {
            this.geoPoint = geoPoint;
        }

        public GeoPoint getGeoPoint() {
            return geoPoint;
        }

        public void setGeoPoint(GeoPoint geoPoint) {
            this.geoPoint = geoPoint;
        }

        //TODO emergency Pojo
    }
}
