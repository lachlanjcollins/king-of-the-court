package com.lachlan.kingofthecourt.data.entity;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;

@Entity
public class Game implements Parcelable {
    @PrimaryKey
    private String gameId;
    private User creator;
    private Date dateTime;
    private ArrayList<User> players;

    public Game() {
        gameId = "";
        creator = new User();
        dateTime = new Date();
        players = new ArrayList<>();
    }

    public Game(Date dateTime) {
        creator = new User();
        this.dateTime = dateTime;
        players = new ArrayList<>();
    }

    public Game(User creator, Date dateTime) {
        this.creator = creator;
        this.dateTime = dateTime;
        players = new ArrayList<>();
    }

    public Game(User creator, Date dateTime, ArrayList<User> players) {
        this.creator = creator;
        this.dateTime = dateTime;
        this.players = players;
    }

    public Game(String gameId, User creator, Date dateTime, ArrayList<User> players) {
        this.gameId = gameId;
        this.creator = creator;
        this.dateTime = dateTime;
        this.players = players;
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

    public ArrayList<User> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<User> players) {
        this.players = players;
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
