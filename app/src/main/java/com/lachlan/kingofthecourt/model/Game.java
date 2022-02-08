package com.lachlan.kingofthecourt.model;


import java.util.Date;

public class Game {
    private User creator;
    private Date dateTime; //@TODO: Confirm the data type for time
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
}
