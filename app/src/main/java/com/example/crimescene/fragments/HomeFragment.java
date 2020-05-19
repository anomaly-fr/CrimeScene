package com.example.crimescene.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.crimescene.Case;
import com.example.crimescene.activities.AddFileActivity;
import com.example.crimescene.activities.LoginActivity;
import com.example.crimescene.R;
import com.example.crimescene.UserInfo;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private Button signoutButton;
    float h=0,m=0,l=0,c=0;
    PieChart pieChart;
    private TextView emailTextView,nicknameTextView,tagTextView;
    private CircularImageView profilepictureImageView;
    private FirebaseFirestore userDatabase = FirebaseFirestore.getInstance();
    private CollectionReference reference = userDatabase.collection("Users");
//    private CollectionReference ref = userDatabase.collection("UserCases").document(UserInfo.getInstance().getEmailID()).collection("Cases");
    SharedPreferences sharedPreferences;


    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();


    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_layout, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions googleSignInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(getContext(), googleSignInOptions);
        signoutButton = view.findViewById(R.id.signout_button);
        emailTextView = view.findViewById(R.id.email_TV);
        pieChart = view.findViewById(R.id.pie_chart);
        nicknameTextView = view.findViewById(R.id.greeting_TV);
        tagTextView = view.findViewById(R.id.tag_TV);

        profilepictureImageView = view.findViewById(R.id.displaypic_IV);
        sharedPreferences = getActivity().getSharedPreferences("Login Shared Preference", Context.MODE_PRIVATE);
        UserInfo.getInstance().setFullName(sharedPreferences.getString("Name",null));
        UserInfo.getInstance().setEmailID(sharedPreferences.getString("Email",null));

        UserInfo userInfo = UserInfo.getInstance();
        emailTextView.setText(userInfo.getEmailID());
        nicknameTextView.setText(userInfo.getFullName());




        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
        if(account != null){
            Uri image;
            image = account.getPhotoUrl();
            UserInfo.getInstance().setDisplayPicture(image);
            Glide.with(getActivity()).load(userInfo.getDisplayPicture()).into(profilepictureImageView);
        }


        signoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  FirebaseAuth.getInstance().signOut();
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getActivity(), "Signed out successfully", Toast.LENGTH_SHORT).show();
                       sharedPreferences.edit().putBoolean("Is logged in",false).apply();

                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });


            }
        });
        setUpPieChart();

        return view;
    }

    private void setUpPieChart() {

        firebaseFirestore =FirebaseFirestore.getInstance();

        firebaseFirestore.collection("UserCases").document(UserInfo.getInstance().getEmailID()).collection("Cases")

                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {


                    for (QueryDocumentSnapshot cases : queryDocumentSnapshots) {
                        Case currentCase = cases.toObject(Case.class);
                        switch (currentCase.getPriority()){
                            case 1:
                                h++;
                                break;
                            case 2:
                                m++;
                                break;
                            case 3:
                                l++;
                                break;
                            case 4:
                                c++;
                                break;

                        }

                    }


                }




            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });




        List<PieEntry> entries = new ArrayList<>();

      //  Toast.makeText(getContext(), c.toString()+" " +h.toString(), Toast.LENGTH_SHORT).show();
        entries.add(new PieEntry(c,"Closed"));
        entries.add(new PieEntry(l,"Low"));
        entries.add(new PieEntry(m,"Medium"));
        entries.add(new PieEntry(h,"High"));


        PieDataSet dataSet = new PieDataSet(entries,"");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData data = new PieData(dataSet);
        pieChart.setData(data);

        pieChart.animateY(1000);
        pieChart.invalidate();
    }

 //   @Override
//    public void onResume() {
//        super.onResume();
//
//
//        setUpPieChart();
//
//
//    }

}




