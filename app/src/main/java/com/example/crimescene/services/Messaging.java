package com.example.crimescene.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.crimescene.R;
import com.example.crimescene.activities.SauceResponse;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

public class Messaging extends FirebaseMessagingService {
    NotificationManager notificationManager;

    FirebaseFirestore firestore;

    Map<String,String> data= new HashMap<>();

    public Messaging() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        firestore= FirebaseFirestore.getInstance();
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        if(GoogleSignIn.getLastSignedInAccount(this).getEmail()!=null)
        FirebaseFirestore.getInstance().collection("tokens")
                .document(GoogleSignIn.getLastSignedInAccount(this).getEmail())
                .set(s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        data = remoteMessage.getData();
        switch(data.get("Type")){
            case "SOS":
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    NotificationChannel notificationChannel = new NotificationChannel("sos","SOSCalls", NotificationManager.IMPORTANCE_HIGH);
                    notificationChannel.setVibrationPattern(new long[]{100,300,200,500,100,400});
                    notificationChannel.setSound(Uri.parse("android.resource://com.example.crimescene/raw/sirens"),new  AudioAttributes.Builder()
                    .setAllowedCapturePolicy(AudioAttributes.ALLOW_CAPTURE_BY_ALL)
                    .build());
                    notificationManager.createNotificationChannel(notificationChannel);

                    Notification notification = new Notification.Builder(getApplicationContext(),"sos")
                            .setContentTitle("SOS Call")
                            .setContentText("Please Respond")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentIntent(PendingIntent.getActivity(getApplicationContext(),69,new Intent(getApplicationContext(), SauceResponse.class),PendingIntent.FLAG_ONE_SHOT))
                            .build();
                    notificationManager.notify((int)System.currentTimeMillis(),notification);

                }
                else{
                    NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext())
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            //.setSound(Uri.parse("android.resource://com.example.crimescene/raw/sirens"))
                            .setContentIntent(PendingIntent.getActivity(getApplicationContext(),0,new Intent(getApplicationContext(),SauceResponse.class),0));
                    if(getSharedPreferences("Login Shared Preference", Context.MODE_PRIVATE).getBoolean("onDuty",false))
                        notification.setSound(Uri.parse("android.resource://com.example.crimescene/raw/sirens"));
                    notificationManager.notify((int)System.currentTimeMillis(),notification.build());
                }
                break;



        }


    }
}
