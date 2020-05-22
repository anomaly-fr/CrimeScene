package com.example.crimescene.PojoModels;

import com.google.firebase.firestore.GeoPoint;

public class EmergencyModel {
    Case mCase;
    GeoPoint location;
    boolean assigned,open;
    String leadOfficerId;

    public EmergencyModel() {
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public EmergencyModel(Case mCase, GeoPoint location, boolean assigned, boolean open, String leadOfficerId) {
        this.mCase = mCase;
        this.location = location;
        this.assigned = assigned;
        this.open = open;
        this.leadOfficerId = leadOfficerId;
    }

    public Case getmCase() {
        return mCase;
    }

    public void setmCase(Case mCase) {
        this.mCase = mCase;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    public String getLeadOfficerId() {
        return leadOfficerId;
    }

    public void setLeadOfficerId(String leadOfficerId) {
        this.leadOfficerId = leadOfficerId;
    }
}
