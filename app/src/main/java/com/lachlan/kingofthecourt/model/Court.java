package com.lachlan.kingofthecourt.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class Court implements Parcelable {
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

    protected Court(Parcel in) {
        locationName = in.readString();
        latLng = in.readParcelable(LatLng.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(locationName);
        dest.writeParcelable(latLng, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Court> CREATOR = new Creator<Court>() {
        @Override
        public Court createFromParcel(Parcel in) {
            return new Court(in);
        }

        @Override
        public Court[] newArray(int size) {
            return new Court[size];
        }
    };

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


