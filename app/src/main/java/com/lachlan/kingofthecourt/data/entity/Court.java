package com.lachlan.kingofthecourt.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Court implements Parcelable {

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
    @PrimaryKey
    @NonNull
    private String courtId;
    private String locationName;
    @Embedded
    private Location location;

    public Court() {
        courtId = "";
        locationName = "";
        location = new Location(0, 0);
    }

    public Court(@NonNull String courtId, String locationName, Location location) {
        this.courtId = courtId;
        this.locationName = locationName;
        this.location = location;
    }

    protected Court(Parcel in) {
        courtId = in.readString();
        locationName = in.readString();
    }

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


