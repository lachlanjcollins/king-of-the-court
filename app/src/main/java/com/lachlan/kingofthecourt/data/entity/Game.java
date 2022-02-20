package com.lachlan.kingofthecourt.data.entity;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * Entity class which represents basketball game.
 *
 * @author Lachlan Collins
 * @version 20 February 2022
 */
@Entity
public class Game implements Parcelable {

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
    @PrimaryKey
    @NonNull
    private String gameId;
    private String creatorId;
    private Date dateTime;
    private String locationId;

    public Game() {
        gameId = "";
        creatorId = "";
        dateTime = new Date();
        locationId = "";
    }

    public Game(String gameId, String creatorId, Date dateTime, String locationId) {
        this.gameId = gameId;
        this.creatorId = creatorId;
        this.dateTime = dateTime;
        this.locationId = locationId;
    }

    protected Game(Parcel in) {
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date time) {
        this.dateTime = time;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
