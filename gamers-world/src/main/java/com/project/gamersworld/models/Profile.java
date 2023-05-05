package com.project.gamersworld.models;

//import java.util.*;
import javax.persistence.Embeddable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Embeddable
public class Profile {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String emailAddress;
    private String description;
    // private EnumMap<Game, PlayLevel> preferences = new EnumMap<>(Game.class);

    @Fetch(value = FetchMode.SELECT)
    @ElementCollection(targetClass = Game.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "Games", joinColumns = @JoinColumn(name = "uid"))
    @Enumerated(EnumType.STRING)
    @Column(name = "game_name")
    private List<Game> games;

    private String preferredTime;

    public Profile() {
    }

    public Profile(String username, String password, String emailAdress, String description, String preferredTime) {
        this.username = username;
        this.password = password;
        this.emailAddress = emailAdress;
        this.description = description;
        this.games = new ArrayList<Game>();
        this.preferredTime = preferredTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return emailAddress;
    }

    public void setEmail(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Game> getGames() {
        return this.games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public String getTime() {
        return preferredTime;
    }

    public void setTime(String preferredTime) {
        this.preferredTime = preferredTime;
    }

}