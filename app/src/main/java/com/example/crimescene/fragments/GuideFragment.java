package com.example.crimescene.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.crimescene.R;
import com.pdftron.pdf.config.ViewerConfig;
import com.pdftron.pdf.controls.DocumentActivity;

import android.os.Bundle;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuideFragment extends Fragment {
    private Button constitutionButton,secondButton,thirdButton,fourthButton;


    public GuideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.guide_layout,container,false);
        constitutionButton = view.findViewById(R.id.constitution_button);
        secondButton = view.findViewById(R.id.button_2);
        thirdButton = view.findViewById(R.id.button_3);
        fourthButton = view.findViewById(R.id.button_4);
        fourthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        thirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

            }
        });
        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        constitutionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(),ConstitutionActivity.class);
//                startActivity(intent);
                ViewerConfig config = new ViewerConfig.Builder().openUrlCachePath(getActivity().getCacheDir().getAbsolutePath()).build();
                DocumentActivity.openDocument(getActivity(),R.raw.coi_part_full,config);

            }
        });
        return view;
    }

}
