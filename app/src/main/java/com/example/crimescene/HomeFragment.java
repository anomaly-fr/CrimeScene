package com.example.crimescene;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.Document;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private Button signoutButton;
    private TextView emailTextView,nicknameTextView,tagTextView;
    private CircularImageView profilepictureImageView;
    private FirebaseFirestore userDatabase = FirebaseFirestore.getInstance();
    private CollectionReference reference = userDatabase.collection("Users");
    SharedPreferences sharedPreferences;
    private static final String COP = " Cop";
    private static final String CIVILIAN = "Civilian";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_DESIGNATION = "Designation";

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
        String desig;
//        firebaseFirestore.collection("Designations")
//                .whereEqualTo("KEY_EMAIL",UserInfo.getInstance().getEmailID()).limit(1)
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        for(QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
//                            Designation check = snapshot.toObject(Designation.class);
//                            String email = check.getKEY_EMAIL();
//                            String desig = check.getKEY_DESIGNATION();
//
//                            if(desig.equals("NOT SET")){
//                                Toast.makeText(getContext(), email + " Designation not set", Toast.LENGTH_SHORT).show();
//                            }
//
//                        }
//
//
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                Toast.makeText(getContext(), "fail", Toast.LENGTH_SHORT).show();
//
//                                            }
//                                        });


//
//    if(sharedPreferences.getString("First time","Not set").equals("Cop")){
//        tagTextView.setText(COP);
//    }else if(sharedPreferences.getString("First time","Not set").equals("Civilian")){
//        tagTextView.setText(CIVILIAN);
//    }else{
//        // Toast.makeText(getContext(), "Not set", Toast.LENGTH_SHORT).show();
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setTitle("Are you a part of the police force?").setPositiveButton("Yes, I'm a cop", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                sharedPreferences.edit().putString("First time","Cop").apply();
//
//                dialog.dismiss();
//                tagTextView.setText(COP);
//
//            }
//        }).setNegativeButton("No, I'm a civilian", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                sharedPreferences.edit().putString("First time","Civilian").apply();
//                dialog.dismiss();
//                tagTextView.setText(CIVILIAN);
//            }
//        });
//
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }
        return view;
    }










    }


