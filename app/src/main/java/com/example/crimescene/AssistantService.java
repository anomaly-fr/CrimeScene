package com.example.crimescene;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Locale;

public class AssistantService extends Service {

    SpeechRecognizer speechRecognizer;
    Intent intentRecognizer;
    Handler handler;
    private static int LOOP_TIME = 5000;

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
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.i("SOS", "Speech Done");
                speechRecognizer.startListening(intentRecognizer);
                handler.postDelayed(this, LOOP_TIME);
            }
        };
        handler.postDelayed(runnable, LOOP_TIME);
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
            Log.i("SOS", "Whole String " + string);
            if (string.contains("trouble") || string.contains("help")) {
                Log.i("SOS", "Successful");
                speechRecognizer.stopListening();
                Intent i = new Intent(AssistantService.this, Sauce.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                speechRecognizer.destroy();
                handler.removeMessages(0);
            }
            Log.i("SOS", "Messages " + handler.obtainMessage());
        }
    }

}
