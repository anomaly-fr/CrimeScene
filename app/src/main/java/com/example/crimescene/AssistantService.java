package com.example.crimescene;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.example.crimescene.activities.Sauce;

import java.util.ArrayList;
import java.util.Locale;

public class AssistantService extends Service {

    SpeechRecognizer speechRecognizer;
    Intent intentRecognizer;
    Handler handler;
    Runnable runnable;
    private static int LOOP_TIME = 4000;
    private boolean sosStatus = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initiateSpeech();
        speechRecognizer.startListening(intentRecognizer);
        Log.i("SOS", "Speech Start Here");
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                Log.i("SOS", "Speech Done");
                speechRecognizer.startListening(intentRecognizer);
                handler.postDelayed(this, LOOP_TIME);
            }
        };
        runnable.run();
        return super.onStartCommand(intent, flags, startId);
    }

    private void initiateSpeech() {
        intentRecognizer = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intentRecognizer.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intentRecognizer.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());


        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {
                Log.i("SOS", "Error " + error);
            }

            @Override
            public void onResults(Bundle results) {
                onGettingResults(results);
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
                onGettingResults(partialResults);
            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });
    }

    private void onGettingResults(Bundle bundle) {
        ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String string;
        if (matches!=null) {
            string = matches.get(0);

            Log.i("SOS", "Bool " + sosStatus);
            Log.i("SOS", "Message " + string);

            if (string.contains("stop voice") || string.contains("cancel voice")) {
                Log.i("SOS", "Stop voice");
                callAToast("Voice recognition deactivated");
                speechRecognizer.destroy();
                handler.removeCallbacks(runnable);
                return;
            }

            if (!sosStatus && (string.contains("trouble") ||
                    string.contains("help") ||
                    string.contains("start sos") ||
                    string.contains("leave"))) {

                if (this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    callAToast("GeoLocation Permission not granted");
                    callAToast("Grant those permissions to call SOS");
                }

                Log.i("SOS", "Trouble");
                sosStatus = true;
                callAToast("SOS activated");
                Intent i = new Intent(AssistantService.this, LocationServiceYo.class);
                i.setAction(Doms.EMERGENCY_START);
                startService(i);

            } else if (sosStatus && (string.contains("stop service") || string.contains("cancel service"))) {
                Log.i("SOS", "Stop SOS");
                sosStatus = false;
                callAToast("SOS deactivated");
                Intent i = new Intent(AssistantService.this, LocationServiceYo.class);
                i.setAction(Doms.EMERGENCY_STOP);
                startService(i);
            }
        }
    }

    private void callAToast(String a) {
        Toast.makeText(this, a, Toast.LENGTH_SHORT).show();
    }

}
