package com.example.crimescene;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


/**
 * A simple {@link Fragment} subclass.
 */
public class CaseNotesFragment extends Fragment {
    TextView notesView;
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    public CaseNotesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_case_notes, container, false);
        notesView = v.findViewById(R.id.case_text);

       // notesView.setText(UserInfo.getInstance().getNote());
//        database.collection("User Cases").document(UserInfo.getInstance().getEmailID()).collection("Cases").get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()){
//                            String text = "";
//                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
//                                 text =  documentSnapshot.get("caseNotes").toString();
//                            }
//
//
//                        }else{
//                            notesView.setText("");
//                        }
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getContext(), "Not found", Toast.LENGTH_SHORT).show();
//
//            }
//        });


        return v;
    }
}
