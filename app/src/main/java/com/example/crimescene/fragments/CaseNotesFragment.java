package com.example.crimescene.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crimescene.Case;
import com.example.crimescene.R;
import com.example.crimescene.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class CaseNotesFragment extends Fragment {
    TextView notesView;
    LinearLayout addNotes;
    EditText addNewNote;
    String fetchNotes;

    ImageButton saveNote,cancelSave;
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
        addNotes = v.findViewById(R.id.add_notes_button);
        addNewNote = v.findViewById(R.id.notes_case);
        saveNote = v.findViewById(R.id.save_note);
        cancelSave = v.findViewById(R.id.cancel_save);
        addNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewNote();
            }


        });

        database.collection("UserCases").document(UserInfo.getInstance().getEmailID()).collection("Cases")
                .whereEqualTo("caseName",UserInfo.getInstance().getCurrentFile())
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                  
                    fetchNotes = null;
                    for (QueryDocumentSnapshot cases : queryDocumentSnapshots) {
                        Case currentCase = cases.toObject(Case.class);
                        fetchNotes = currentCase.getCaseNotes();

                    }

                    if(!fetchNotes.isEmpty())
                        notesView.setText(fetchNotes);

                  else  notesView.setText(R.string.no_notes_added);
                }
                    
                    
                       

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });






        return v;
    }
    private void addNewNote() {
        addNewNote.setVisibility(View.VISIBLE);
        saveNote.setVisibility(View.VISIBLE);
        cancelSave.setVisibility(View.VISIBLE);
        addNotes.setVisibility(View.GONE);



        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String newNote = addNewNote.getText().toString();
                if(!newNote.isEmpty()){
                    String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    String time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                    final String updatedNote = newNote + "\n -" + date + " at "+time +"\n\n" + fetchNotes;
                    database.collection("UserCases").document(UserInfo.getInstance().getEmailID())
                            .collection("Cases")
                            .document(UserInfo.getInstance().getCurrentFile())
                            .update("caseNotes",updatedNote).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            notesView.setText(updatedNote);
                            addNewNote.setText("");
                            addNewNote.setVisibility(View.GONE);
                            saveNote.setVisibility(View.GONE);
                            cancelSave.setVisibility(View.GONE);
                            fetchNotes = updatedNote;
                            addNotes.setVisibility(View.VISIBLE);


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Failed to add new note.", Toast.LENGTH_SHORT).show();
                        }
                    });


                }

            }
        });
        cancelSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelSave.setVisibility(View.GONE);
                saveNote.setVisibility(View.GONE);
                addNewNote.setVisibility(View.GONE);
                addNotes.setVisibility(View.VISIBLE);
            }
        });

    }
}
