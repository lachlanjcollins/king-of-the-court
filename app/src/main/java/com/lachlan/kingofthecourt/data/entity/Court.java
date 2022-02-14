package com.lachlan.kingofthecourt.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

@Entity
public class Court implements Parcelable {

    @PrimaryKey
    @NonNull
    private String courtId;

    private String locationName;

    @Embedded
    private Location location;

//    private ArrayList<Game> gamesList; @TODO: Fix this

    public Court() {
        courtId = "";
        locationName = "";
        location = new Location(0, 0);
//        gamesList = new ArrayList<>();
    }

    public Court(@NonNull String courtId, String locationName, Location location) {
        this.courtId = courtId;
        this.locationName = locationName;
        this.location = location;
    }

    public Court(@NonNull String courtId, String locationName, LatLng latLng, ArrayList<Game> gamesList) {
        this.locationName = locationName;
        this.location = new Location(latLng.latitude, latLng.longitude);
//        this.gamesList = gamesList;
    }

    protected Court(Parcel in) {
        courtId = in.readString();
        locationName = in.readString();
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(courtId);
        parcel.writeString(locationName);
    }
}


