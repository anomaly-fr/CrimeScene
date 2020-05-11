package com.example.crimescene;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AddFileActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout chooseMurder, chooseFraud, chooseMissing, chooseAssault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_file);
        chooseMurder = findViewById(R.id.choose_murder);
        chooseFraud = findViewById(R.id.choose_fraud);
        chooseMissing = findViewById(R.id.choose_missing);
        chooseAssault = findViewById(R.id.choose_horrid);
        chooseMurder.setOnClickListener(this);
        chooseFraud.setOnClickListener(this);
        chooseMissing.setOnClickListener(this);
        chooseAssault.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_murder:
                updateMurderUI();
                break;
            case R.id.choose_fraud:
                updateFraudUI();
                break;
            case R.id.choose_missing:
                updateMissingUI();
                break;
            case R.id.choose_horrid:
                updateAssaultUI();
                break;
        }

    }

    private void updateMurderUI() {
        chooseMurder.setElevation(8);
        chooseAssault.setElevation(0);
        chooseMissing.setElevation(0);
        chooseFraud.setElevation(0);

       RelativeLayout main = findViewById(R.id.main_ll);
        main.setElevation(8);
        main.setBackgroundColor(getColor(R.color.murderous_red));

        LinearLayout circle1 = findViewById(R.id.addpic_icon);
        LinearLayout circle2 = findViewById(R.id.record_icon);
        LinearLayout circle3 = findViewById(R.id.other_icon);

        circle1.setBackgroundColor(getColor(R.color.murderous_red));
        circle2.setBackgroundColor(getColor(R.color.murderous_red));
        circle3.setBackgroundColor(getColor(R.color.murderous_red));

        TextView desc = findViewById(R.id.title_desc);
        desc.setText("Murder, kidnapping or similar case.");
        desc.setTextColor(getColor(R.color.murderous_red));

    }

    private void updateFraudUI() {
        chooseMurder.setElevation(0);

        chooseAssault.setElevation(0);
        chooseMissing.setElevation(0);
        chooseFraud.setElevation(8);


        RelativeLayout main = findViewById(R.id.main_ll);
        main.setBackgroundColor(getColor(R.color.fraudulent_green));
        main.setElevation(8);

        LinearLayout circle1 = findViewById(R.id.addpic_icon);
        LinearLayout circle2 = findViewById(R.id.record_icon);
        LinearLayout circle3 = findViewById(R.id.other_icon);

        circle1.setBackgroundColor(getColor(R.color.fraudulent_green));
        circle2.setBackgroundColor(getColor(R.color.fraudulent_green));
        circle3.setBackgroundColor(getColor(R.color.fraudulent_green));

        TextView desc = findViewById(R.id.title_desc);
        desc.setText("Fraud, bribery or similar case");
        desc.setTextColor(getColor(R.color.fraudulent_green));

    }

    private void updateMissingUI() {
        chooseMurder.setElevation(0);
        chooseAssault.setElevation(0);
        chooseMissing.setElevation(8);
        chooseFraud.setElevation(0);

      RelativeLayout main = findViewById(R.id.main_ll);
        main.setBackgroundColor(getColor(R.color.treachurous_yellow));
        main.setElevation(8);

        LinearLayout circle1 = findViewById(R.id.addpic_icon);
        LinearLayout circle2 = findViewById(R.id.record_icon);
        LinearLayout circle3 = findViewById(R.id.other_icon);

        circle1.setBackgroundColor(getColor(R.color.treachurous_yellow));
        circle2.setBackgroundColor(getColor(R.color.treachurous_yellow));
        circle3.setBackgroundColor(getColor(R.color.treachurous_yellow));

        TextView desc = findViewById(R.id.title_desc);
        desc.setText("Missing person/artifact or similar case");
        desc.setTextColor(getColor(R.color.treachurous_yellow));

    }

    private void updateAssaultUI() {
        chooseMurder.setElevation(0);
        chooseAssault.setElevation(8);
        chooseMissing.setElevation(0);
        chooseFraud.setElevation(0);


        RelativeLayout main = findViewById(R.id.main_ll);
        main.setBackgroundColor(getColor(R.color.horrid_pink));
        main.setElevation(8);

        LinearLayout circle1 = findViewById(R.id.addpic_icon);
        LinearLayout circle2 = findViewById(R.id.record_icon);
        LinearLayout circle3 = findViewById(R.id.other_icon);

        circle1.setBackgroundColor(getColor(R.color.horrid_pink));
        circle2.setBackgroundColor(getColor(R.color.horrid_pink));
        circle3.setBackgroundColor(getColor(R.color.horrid_pink));

        TextView desc = findViewById(R.id.title_desc);
        desc.setText("Sexual offense, rights violation or similar case.");
        desc.setTextColor(getColor(R.color.horrid_pink));

    }


}
