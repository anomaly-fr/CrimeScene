package com.example.crimescene;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import static com.google.android.material.snackbar.Snackbar.*;

public class LoginActivity extends AppCompatActivity {
    private SignInButton signInButton;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;
    private int RC_SIGN_IN = 1;
    private AlertDialog dialog;
 private CoordinatorLayout coordinatorLayout;
 private TextView beginTextView;
 SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        coordinatorLayout = findViewById(R.id.coordinator_layout);
        firebaseAuth = FirebaseAuth.getInstance();
     //   beginTextView = findViewById(R.id.begin_TV);
        signInButton = findViewById(R.id.google_signin);
        GoogleSignInOptions googleSignInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);
        sharedPreferences = getSharedPreferences("Logged in",MODE_PRIVATE);
        if(sharedPreferences.getBoolean("Logged in",false)){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SignIn();
            }
        });


    }
    public void SignIn() {

        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent,RC_SIGN_IN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

        }

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            FirebaseGoogleAuth(account);

           // updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Failed to authenticate", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this,"Failed to authenticate",Toast.LENGTH_LONG).show();
            FirebaseGoogleAuth(null);

            //updateUI(null);
        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount account) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    View contextView = findViewById(R.id.google_signin);
                   // Toast.makeText(getApplicationContext(), "Sucessfully signed in", Toast.LENGTH_LONG).show();
//                   Snackbar snackbar = make(findViewById(android.R.id.content),"Successfully signed in", Snackbar.LENGTH_SHORT).show();
//                   Snackbar = Snackbar.make(findViewById(android.R.id.content),"Ok",Snackbar.LENGTH_SHORT).show();
                   FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                    UpdateUI(currentUser);
                }else{
                    Toast.makeText(getApplicationContext(),"Failed to authenticate",Toast.LENGTH_LONG).show();
                    UpdateUI(null);


                }
            }
        });
    }
    public void UpdateUI(FirebaseUser currentUser) {
    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
    if(account != null) {
        String fullname = account.getDisplayName();
        String username = account.getGivenName();
        String email = account.getEmail();
        Uri image = account.getPhotoUrl();
        UserInfo userInfo = UserInfo.getInstance();
        userInfo.setFullName(fullname);
        userInfo.setNickName(username);
        userInfo.setEmailID(email);
        userInfo.setDisplayPicture(image);

        sharedPreferences.edit().putBoolean("Logged in",true).apply();


      //  Toast.makeText(getApplicationContext(),"Hey, "+personname,Toast.LENGTH_SHORT).show();
        finish();
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
    }


    }
}
