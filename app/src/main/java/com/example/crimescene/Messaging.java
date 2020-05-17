package com.example.crimescene;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

public class Messaging extends FirebaseMessagingService {
    NotificationManager notificationManager;

    Map<String,String> data= new HashMap<>();

    public Messaging() {

    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Log.e("comes","here");
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
                            .setContentTitle(getString(R.string.incoming_call))
                            .setContentText(getString(R.string.incoming_call_text))
                            .addAction(new Notification.Action.Builder(null,"Respond",PendingIntent.getActivity(getApplicationContext(),100,new Intent("sauce"),PendingIntent.FLAG_ONE_SHOT))
                                .build())
                            .addAction(new Notification.Action.Builder(null,"Busy",PendingIntent.getForegroundService(getApplicationContext(),69,new Intent("busy"),PendingIntent.FLAG_ONE_SHOT))
                                .build())
                            .setSmallIcon(R.drawable.ic_daymode_icon) //TODO ICON AND BEAUTIFY THIS
                            .setContentIntent(PendingIntent.getActivity(getApplicationContext(),69,new Intent("sauce"),PendingIntent.FLAG_ONE_SHOT))
                            .build();
                    notificationManager.notify((int)System.currentTimeMillis(),notification);
                    Log.e("but idk", "if here");

                }
                else{
                    Notification notification = new NotificationCompat.Builder(getApplicationContext())
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setSound(Uri.parse("android.resource://com.example.crimescene/raw/sirens"))
                            .setContentIntent(PendingIntent.getActivity(getApplicationContext(),0,new Intent(getApplicationContext(),SauceResponse.class),0))
                            .build();
                    notificationManager.notify((int)System.currentTimeMillis(),notification);

                }


        }


    }
}
