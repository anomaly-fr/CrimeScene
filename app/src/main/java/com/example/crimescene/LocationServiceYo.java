package com.example.crimescene;

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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.HashMap;
import java.util.Map;

public class LocationServiceYo extends Service implements LocationListener, OnCompleteListener<Void> {

    LocationManager locationManager;
    GeoPoint geoPoint;
    Map<String,Object> map= new HashMap<>();



    public LocationServiceYo() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent.getAction().equals(Doms.EMERGENCY_START)){

            locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 100, this);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 100, this);
                locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 1000, 100, this);
            }

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                NotificationChannel notificationChannel= new NotificationChannel("emergency","Emergency obviously", NotificationManager.IMPORTANCE_LOW);
                notificationChannel.setVibrationPattern(new long[]{1000L, 1000L, 1000L}); //TODO same, beautify notif
                ((NotificationManager)getSystemService(NOTIFICATION_SERVICE)).createNotificationChannel(notificationChannel);
                Notification notification= new Notification.Builder(this,"emergency")  // TODO beautify notif
                        .setContentTitle("trackin")
                        .setContentText("backup in no time")
                        .setContentIntent(PendingIntent.getActivity(this,0,new Intent(this,Sauce.class),PendingIntent.FLAG_ONE_SHOT))//pls Don't remove this
                        .build();
                startForeground(69,notification);
            }

        }
        else{
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
            Toast.makeText(this, "dayum", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        geoPoint = new GeoPoint(location.getLatitude(),location.getLongitude());
        map.put(Timestamp.now().toString(),geoPoint);

        FirebaseFirestore.getInstance().collection("emergencies").document(GoogleSignIn.getLastSignedInAccount(this).getEmail())
                .update(map);
        FirebaseFirestore.getInstance().collection("emergencies").document(GoogleSignIn.getLastSignedInAccount(this).getEmail())
                .collection("latest")
                .document("location")
                .set(new Err(geoPoint));

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
