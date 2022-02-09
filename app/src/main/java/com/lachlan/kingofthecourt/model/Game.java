package com.lachlan.kingofthecourt.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Game implements Parcelable {
    private User creator;
    private Date dateTime;
    private Team[] teams;

    public Game() {
        creator = new User();
        dateTime = new Date();
        teams = new Team[2];
    }

    public Game(Date dateTime) {
        creator = new User();
        this.dateTime = dateTime;
        teams = new Team[2];
    }

    public Game(User creator, Date dateTime) {
        this.creator = creator;
        this.dateTime = dateTime;
        teams = new Team[2];
    }

    protected Game(Parcel in) {
    }

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

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date time) {
        this.dateTime = time;
    }

    public Team[] getTeams() {
        return teams;
    }

    public void setTeams(Team[] teams) {
        this.teams = teams;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
