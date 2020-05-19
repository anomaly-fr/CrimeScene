package com.example.crimescene.activities;

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

import com.example.crimescene.R;
import com.example.crimescene.UserInfo;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.internal.ApiExceptionMapper;
import com.google.android.gms.common.internal.ApiExceptionUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    private SignInButton signInButton;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;
    private int RC_SIGN_IN = 1;
    private AlertDialog dialog;
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_DESIGNATION = "Designation";
 public static GoogleSignInAccount account;
 private CoordinatorLayout coordinatorLayout;
 private TextView beginTextView;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

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
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);
        sharedPreferences = getSharedPreferences("Login Shared Preference",MODE_PRIVATE);
        if(sharedPreferences.getBoolean("Is logged in",false)) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));


        }else{

            //  Toast.makeText(this, "No Shared Preference found", Toast.LENGTH_SHORT).show();
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
        if(completedTask.isSuccessful()) {
            try {
                account = completedTask.getResult(ApiException.class);

                // Signed in successfully, show authenticated UI.
                assert account != null;
                FirebaseGoogleAuth(account);


                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Name", account.getDisplayName());
                editor.putString("Email", account.getEmail());
                editor.putBoolean("Is logged in", true);

                editor.apply();


                // updateUI(account);
            } catch (ApiException e) {
                // The ApiException status code indicates the detailed failure reason.
                // Please refer to the GoogleSignInStatusCodes class reference for more information.
                Log.w("Failed to authenticate", "signInResult:failed code=" + e.getStatusCode());
                Toast.makeText(this, "Failed to authenticate", Toast.LENGTH_LONG).show();
                FirebaseGoogleAuth(null);

                //updateUI(null);
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"Failed to authenticate",Toast.LENGTH_LONG).show();
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
                }
                else{
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

//        Map<String,Object> designation = new HashMap<>();
//        designation.put(KEY_EMAIL,UserInfo.getInstance().getEmailID());
//        designation.put(KEY_DESIGNATION,"NOT SET");
//        Designation designation = new Designation(UserInfo.getInstance().getEmailID(),"NOT SET");
//
//        firebaseFirestore.collection("User Designations").add(designation)
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(getApplicationContext(), "Error saving designation", Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//                Toast.makeText(getApplicationContext(), "Designation Saved", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//
//
//        //  Toast.makeText(getApplicationContext(),"Hey, "+personname,Toast.LENGTH_SHORT).show();
        finish();
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
    }


    }
}
