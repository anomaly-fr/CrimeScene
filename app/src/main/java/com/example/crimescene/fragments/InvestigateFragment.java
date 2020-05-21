package com.example.crimescene.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.crimescene.activities.AddFileActivity;
import com.example.crimescene.PojoModels.Case;
import com.example.crimescene.adapters.CaseAdapter;
import com.example.crimescene.FileView;
import com.example.crimescene.R;
import com.example.crimescene.PojoModels.UserInfo;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

/**
 * A simple {@link Fragment} subclass.
 */
public class InvestigateFragment extends Fragment {
    FloatingActionButton addFileButton;
    private RecyclerView casesRecylerView;

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CaseAdapter caseAdapter;



    public InvestigateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.investigate_layout, container, false);
        addFileButton = view.findViewById(R.id.add_file);
        casesRecylerView = view.findViewById(R.id.case_RV);
        addFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddFileActivity.class));
            }
        });
        Query query =firebaseFirestore.collection("UserCases")
                .document(UserInfo.getInstance().getEmailID())
                .collection("Cases");
        query.orderBy("priority", Query.Direction.DESCENDING);


        FirestoreRecyclerOptions<Case>options = new FirestoreRecyclerOptions.Builder<Case>().setQuery(query,Case.class).build();
        caseAdapter = new CaseAdapter(options);
        GridLayoutManager manager = new GridLayoutManager(getContext(),2);
        casesRecylerView.setLayoutManager(manager);
        casesRecylerView.setAdapter(caseAdapter);

        caseAdapter.setOnCaseClickListener(new CaseAdapter.onCaseClickListener() {
            @Override
            public void onCaseClick(DocumentSnapshot snapshot, int position) {

              UserInfo.getInstance().setCurrentFile(snapshot.getString("caseName"));
             //  UserInfo.getInstance().setNote(snapshot.getString("caseNotes"));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FileView fileView = new FileView();
                        fileView.show(getChildFragmentManager(),"BottomSheet");

                    }
                },1000);


            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        caseAdapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        caseAdapter.stopListening();
    }
}
