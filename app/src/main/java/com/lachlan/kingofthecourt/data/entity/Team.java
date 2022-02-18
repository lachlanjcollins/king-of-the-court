package com.lachlan.kingofthecourt.data.entity;

import java.util.ArrayList;

public class Team {
    private final ArrayList<User> players;
    private int score;

    public Team() {
        players = new ArrayList<>();
        score = 0;
    }

    public Team(ArrayList<User> players) {
        this.players = players;
    }


}
