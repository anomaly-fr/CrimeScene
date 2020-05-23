package com.example.crimescene.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.crimescene.PojoModels.EmergencyModel;
import com.example.crimescene.R;
import com.example.crimescene.activities.SauceResponse;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmergencyDialogAcceptanceFragment extends BottomSheetDialogFragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, EventListener<DocumentSnapshot> {

    MapView mapView;
    DocumentReference locationref;
    GoogleMap googleMap;
    MarkerOptions markerOptions;
    Marker victim;
    Polyline path;
    Boolean first=true;

    String victimEmail;
    GeoPoint geoPoint;


    Button button;

    public EmergencyDialogAcceptanceFragment(String victimEmail,GeoPoint init){
        this.victimEmail=victimEmail;
        this.geoPoint= init;
    }




    public EmergencyDialogAcceptanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_emergency_dialog_acceptance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        locationref= FirebaseFirestore.getInstance().collection("emergencies").document(victimEmail);
        Toast.makeText(getContext(),victimEmail,Toast.LENGTH_LONG).show();
        locationref.addSnapshotListener(this);
        mapView= (MapView) v.findViewById(R.id.map);
        mapView.getMapAsync(this);
        button=v.findViewById(R.id.accept);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SauceResponse.class).putExtra("victimEmail",victimEmail));
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //mapView.onResume();
        markerOptions= new MarkerOptions();
        this.googleMap= googleMap;
        try {
            markerOptions.position(new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude()))
                    .title("victim")
                    .icon(BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeStream(getContext().getAssets().open("police_marker.png")), 100, 100, false)))
                    .draggable(false);
        }
        catch (IOException exe){}
        victim = googleMap.addMarker(markerOptions);
        first=false;
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(new LatLng(geoPoint.getLatitude(),geoPoint.getLongitude()),16)));

        //close with report/without report




    }




    @Override
    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
        if(googleMap!=null){
            try{
                GeoPoint geoPoint= ((GeoPoint) ((EmergencyModel) documentSnapshot.toObject(EmergencyModel.class)).getLocation());
                Toast.makeText(getContext(),"fsd",Toast.LENGTH_LONG).show();
                    victim.setPosition(new LatLng(geoPoint.getLatitude(),geoPoint.getLongitude()));
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(new LatLng(geoPoint.getLatitude(),geoPoint.getLongitude()),17)));




            }
            catch (NullPointerException eq){
                Toast.makeText(getContext(),"ikgbkh",Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
       // mapView.onPause();
        //mapView.on
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapView.onCreate(savedInstanceState);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if(marker.equals(victim)){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr="+victim.getPosition().latitude+","+victim.getPosition().longitude)));
        }
        return true;
    }
}
