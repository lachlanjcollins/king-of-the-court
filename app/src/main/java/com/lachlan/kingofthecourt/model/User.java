package com.lachlan.kingofthecourt.model;

public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String position;
    private int wins;
    private int losses;

    public User() {
        id = "";
        firstName = "";
        lastName = "";
        email = "";
        position = "";
        wins = 0;
        losses = 0;
    }

    public User(String id) {
        this.id = id;
        firstName = "";
        lastName = "";
        email = "";
        position = "";
        wins = 0;
        losses = 0;
    }

    public User(String id, String firstName, String lastName, String email, String position) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.position = position;
        wins = 0;
        losses = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
