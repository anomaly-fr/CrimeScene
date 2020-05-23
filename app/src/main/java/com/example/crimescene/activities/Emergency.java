package com.example.crimescene.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.crimescene.PojoModels.EmergencyModel;
import com.example.crimescene.R;
import com.example.crimescene.adapters.EmergenciesAdapter;
import com.example.crimescene.fragments.EmergencyDialogAcceptanceFragment;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;

public class Emergency extends AppCompatActivity {

    EmergenciesAdapter adapter;
    RecyclerView recycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        recycler = findViewById(R.id.recycler);
        Query query = FirebaseFirestore.getInstance().collection("emergencies")
                .orderBy("assigned", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<EmergencyModel> options = new FirestoreRecyclerOptions.Builder<EmergencyModel>().setQuery(query, EmergencyModel.class)
                .build();
        adapter = new EmergenciesAdapter(options);
        GridLayoutManager manager = new GridLayoutManager(this, 2);

        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);

        adapter.setOnEmergencyClickListener(new EmergenciesAdapter.onEmergencyClickListener() {
            @Override
            public void onEmergencyClick(DocumentSnapshot snapshot, int position) {
                new EmergencyDialogAcceptanceFragment(snapshot.getId(),snapshot.getGeoPoint("location")).show(getSupportFragmentManager(), "tag");

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
