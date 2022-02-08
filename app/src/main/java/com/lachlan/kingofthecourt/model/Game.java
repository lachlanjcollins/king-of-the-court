package com.lachlan.kingofthecourt.model;

import java.time.LocalDateTime;

public class Game {
    private Court court;
    private User creator;
    private LocalDateTime time; //@TODO: Confirm the data type for time
    private Team[] teams;

    public Game() {
        court = new Court();
        creator = new User();
        time = LocalDateTime.now();
        teams = new Team[2];
    }

    public Game(Court court, User creator, LocalDateTime time) {
        this.court = court;
        this.creator = creator;
        this.time = time;
        teams = new Team[2];
    }

}
