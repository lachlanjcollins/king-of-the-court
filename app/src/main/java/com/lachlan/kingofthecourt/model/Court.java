package com.lachlan.kingofthecourt.model;

import com.google.android.gms.maps.model.LatLng;

public class Court {
    private String locationName;
    private LatLng latLng;

    public Court() {
        locationName = "";
        latLng = new LatLng(0, 0);
    }

    public Court(String locationName, LatLng latLng) {
        this.locationName = locationName;
        this.latLng = latLng;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}


