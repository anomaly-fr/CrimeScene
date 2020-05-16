package com.example.crimescene.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.crimescene.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;

import org.w3c.dom.Document;

public class SauceResponse extends AppCompatActivity implements OnMapReadyCallback, EventListener<DocumentSnapshot>, GoogleMap.OnMarkerClickListener {

    MapView mapView;
    DocumentReference locationref;
    GoogleMap googleMap;
    MarkerOptions markerOptions;
    Marker victim;
    Polyline path;
    Boolean first=true;

    //TODO feel free to add a floating fragment here containing stats

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sauce_response);
        locationref=FirebaseFirestore.getInstance().document(getIntent().getExtras().getString("path"));
        locationref.addSnapshotListener(this);
        mapView=findViewById(R.id.map);

        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapView.onResume();
        markerOptions= new MarkerOptions();
        this.googleMap= googleMap;



    }




    @Override
    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
        if(googleMap!=null){
            try{
                GeoPoint geoPoint= (GeoPoint) documentSnapshot.toObject(GeoPoint.class);
                if(first) {
                    markerOptions.position(new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude()))
                            .title("victim")
                            .icon(BitmapDescriptorFactory.fromAsset("police_marker.png"))
                            .draggable(false);
                    victim = googleMap.addMarker(markerOptions);
                    first=false;
                }
                else{
                    victim.setPosition(new LatLng(geoPoint.getLatitude(),geoPoint.getLongitude()));

                }



            }
            catch (NullPointerException eq){}

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
    public boolean onMarkerClick(Marker marker) {
        if(marker.equals(victim)){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr="+victim.getPosition().latitude+","+victim.getPosition().longitude)));
        }
        return true;
    }
}
