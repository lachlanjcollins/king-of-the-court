package com.lachlan.kingofthecourt.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

@Entity
public class Court implements Parcelable {
    @PrimaryKey
    private String courtId;
    private String locationName;
    private LatLng latLng;
    private ArrayList<Game> gamesList;

    public Court() {
        courtId = "";
        locationName = "";
        latLng = new LatLng(0, 0);
        gamesList = new ArrayList<>();
    }

    public Court(@NonNull String courtId, String locationName, LatLng latLng) {
        this.courtId = courtId;
        this.locationName = locationName;
        this.latLng = latLng;
    }

    public Court(@NonNull String courtId, String locationName, LatLng latLng, ArrayList<Game> gamesList) {
        this.locationName = locationName;
        this.latLng = latLng;
        this.gamesList = gamesList;
    }

    protected Court(Parcel in) {
        courtId = in.readString();
        locationName = in.readString();
        latLng = in.readParcelable(LatLng.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(courtId);
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

    public String getCourtId() {
        return courtId;
    }

    public void setCourtId(String courtId) {
        this.courtId = courtId;
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


