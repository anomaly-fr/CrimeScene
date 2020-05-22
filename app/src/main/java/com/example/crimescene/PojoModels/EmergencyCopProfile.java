package com.example.crimescene.PojoModels;

import com.google.firebase.firestore.GeoPoint;

public class EmergencyCopProfile {
    GeoPoint geoPoint;

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public EmergencyCopProfile() {
    }

    public EmergencyCopProfile(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }
}
