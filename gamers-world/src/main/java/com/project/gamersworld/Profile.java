package com.project.gamersworld;

import java.util.*;

public class Profile {
    private String username;
    private String password;
    private String emailAddress;
    private String description;
    private EnumMap<Games, PlayLevel> preferences = new EnumMap<>(Games.class);
    private String preferredTime;

    public Profile() {
    }

    public String getPassword() {
        return password;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return emailAddress;
    }

    public void updateEmail(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getDescription() {
        return description;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public EnumMap<Games, PlayLevel> getPreference() {
        return preferences;
    }

    public void updatePreference(EnumMap<Games, PlayLevel> preferences) {
        this.preferences = preferences;
    }

    public String getTime() {
        return preferredTime;
    }

    public void updateTime(String preferredTime) {
        this.preferredTime = preferredTime;
    }

    
}