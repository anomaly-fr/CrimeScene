package com.example.crimescene.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crimescene.Case;
import com.example.crimescene.R;
import com.example.crimescene.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddFileActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout chooseMurder, chooseFraud, chooseMissing, chooseAssault;
    private Button saveCase;
    private EditText fileName,fileNotes;
    int caseType = 1;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_file);
        chooseMurder = findViewById(R.id.choose_murder);
        chooseFraud = findViewById(R.id.choose_fraud);
        chooseMissing = findViewById(R.id.choose_missing);
        chooseAssault = findViewById(R.id.choose_horrid);
        saveCase = findViewById(R.id.save_case);
        fileName = findViewById(R.id.file_name);
        fileNotes = findViewById(R.id.case_note_ET);
        chooseMurder.setOnClickListener(this);
        chooseFraud.setOnClickListener(this);
        chooseMissing.setOnClickListener(this);
        chooseAssault.setOnClickListener(this);
        saveCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fileName.getText().toString().isEmpty()){
                    Toast.makeText(AddFileActivity.this, "Enter a name for the file", Toast.LENGTH_SHORT).show();
                    return;
                }
                saveNewCase();

            }

            private void saveNewCase() {
                final String caseTitle = fileName.getText().toString().trim();
                String caseNotes = fileNotes.getText().toString().trim();
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                String time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                String time1 = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                String order = date.concat(time1);

                Case newCase = new Case(caseTitle,caseType,order);
//                if(!caseNotes.isEmpty()){
//                    newCase.setCaseInfo(caseNotes);
//                //    UserInfo.getInstance().setNote(caseNotes);
//                }else{
//                    newCase.setCaseInfo("");
//                  //  UserInfo.getInstance().setNote("");
//                }

                CollectionReference reference = db.collection("User Cases")
                        .document(UserInfo.getInstance().getEmailID()).collection("Cases");
                reference.add(newCase)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(AddFileActivity.this, String.format("Case %s added", caseTitle), Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddFileActivity.this, "Failed to add case", Toast.LENGTH_SHORT).show();

                    }
                });

                finish();


            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_murder:
                caseType = 1;
                updateMurderUI();
                break;
            case R.id.choose_fraud:
                caseType = 2;
                updateFraudUI();
                break;
            case R.id.choose_missing:
                caseType = 3;
                updateMissingUI();
                break;
            case R.id.choose_horrid:
                caseType = 4;
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
