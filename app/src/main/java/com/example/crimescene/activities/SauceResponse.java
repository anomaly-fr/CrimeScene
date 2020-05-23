package com.example.crimescene.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.crimescene.PojoModels.EmergencyModel;
import com.example.crimescene.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.messaging.FirebaseMessaging;

import org.w3c.dom.Document;

import java.io.IOException;

public class SauceResponse extends AppCompatActivity implements OnMapReadyCallback, EventListener<DocumentSnapshot>, GoogleMap.OnMarkerClickListener, View.OnClickListener {

    MapView mapView;
    DocumentReference locationref;
    GoogleMap googleMap;
    MarkerOptions markerOptions;
    Marker victim;
    Polyline path;
    Boolean first=true;

    Button close,navigate;

    //TODO feel free to add a floating fragment here containing stats

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sauce_response);
        locationref=FirebaseFirestore.getInstance().collection("emergencies").document(getIntent().getStringExtra("victimEmail"));
        locationref.addSnapshotListener(this);
        mapView=findViewById(R.id.map);
        findViewById(R.id.close).setOnClickListener(this);
        findViewById(R.id.navigate).setOnClickListener(this);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        markerOptions= new MarkerOptions();
        this.googleMap= googleMap;
        locationref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                GeoPoint geoPoint = task.getResult().getGeoPoint("location");
                try {
                    victim =googleMap.addMarker(markerOptions.position(new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude()))
                            .title("victim")
                            .icon(BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeStream(SauceResponse.this.getAssets().open("police_marker.png")), 100, 100, false)))
                            .draggable(false));
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(new LatLng(geoPoint.getLatitude(),geoPoint.getLongitude()),17)));

                }
                catch (IOException exee){}
                }
        });

        //close with report/without report



    }




    @Override
    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
        if(googleMap!=null){
            try{
                GeoPoint geoPoint= ((GeoPoint) ((EmergencyModel) documentSnapshot.toObject(EmergencyModel.class)).getLocation());
                Toast.makeText(this,"fsd",Toast.LENGTH_LONG).show();
                victim.setPosition(new LatLng(geoPoint.getLatitude(),geoPoint.getLongitude()));
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(new LatLng(geoPoint.getLatitude(),geoPoint.getLongitude()),17)));




            }
            catch (NullPointerException eq){
                Toast.makeText(this,"ikgbkh",Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if(marker.equals(victim)){

        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.navigate:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr="+victim.getPosition().latitude+","+victim.getPosition().longitude)));
                break;
            case R.id.close:
                FirebaseFirestore.getInstance().collection("emergencies").document(getSharedPreferences("s", Context.MODE_PRIVATE).getString("email","null"))
                        .delete();
                break;


        }
    }
}
