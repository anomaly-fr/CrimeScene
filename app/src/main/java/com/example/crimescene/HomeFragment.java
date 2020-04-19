package com.example.crimescene;


import android.content.Intent;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.mikhaellopez.circularimageview.CircularImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private Button signoutButton;
    private TextView emailTextView,nicknameTextView,tagTextView;
    private CircularImageView profilepictureImageView;

    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_layout,container,false);

        firebaseAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions googleSignInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(getContext(),googleSignInOptions);
        signoutButton = view.findViewById(R.id.signout_button);
        emailTextView = view.findViewById(R.id.email_TV);
        nicknameTextView = view.findViewById(R.id.greeting_TV);
        tagTextView = view.findViewById(R.id.tag_TV);
        profilepictureImageView = view.findViewById(R.id.displaypic_IV);
        signoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //  FirebaseAuth.getInstance().signOut();
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getActivity(),"Signed out successfully",Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                        Intent intent = new Intent(getActivity(),LoginActivity.class);
                        startActivity(intent);
                    }
                });


            }
        });
        UserInfo userInfo = UserInfo.getInstance();
        emailTextView.setText(userInfo.getEmailID());
        nicknameTextView.setText(userInfo.getFullName());

        Glide.with(getActivity()).load(userInfo.getDisplayPicture()).into(profilepictureImageView);




        return view;
    }

}
