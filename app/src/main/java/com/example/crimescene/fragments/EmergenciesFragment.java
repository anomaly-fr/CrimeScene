package com.example.crimescene.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.crimescene.FileView;
import com.example.crimescene.PojoModels.Case;
import com.example.crimescene.PojoModels.EmergencyModel;
import com.example.crimescene.PojoModels.UserInfo;
import com.example.crimescene.R;
import com.example.crimescene.adapters.CaseAdapter;
import com.example.crimescene.adapters.EmergenciesAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmergenciesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmergenciesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    EmergenciesAdapter adapter;
    RecyclerView recycler;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EmergenciesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmergenciesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmergenciesFragment newInstance(String param1, String param2) {
        EmergenciesFragment fragment = new EmergenciesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_emergencies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        recycler = v.findViewById(R.id.recycler)
        Query query = FirebaseFirestore.getInstance().collection("emergencies");
        FirestoreRecyclerOptions<EmergencyModel> options = new FirestoreRecyclerOptions.Builder<Case>().setQuery(query, EmergencyModel.class).build();
        adapter = new EmergenciesAdapter(options);
        GridLayoutManager manager = new GridLayoutManager(getContext(),2);

        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);

        adapter.setOnEmergencyClickListener(new EmergenciesAdapter.onEmergencyClickListener() {
            @Override
            public void onEmergencyClick(DocumentSnapshot snapshot, int position) {
                new EmergencyDialogAcceptanceFragment().show(getChildFragmentManager(),"tag");

            }
        });
}
