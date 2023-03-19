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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EnumMap<Games, PlayLevel> getPreferences() {
        return preferences;
    }

    public void setPreferences(EnumMap<Games, PlayLevel> preferences) {
        this.preferences = preferences;
    }

    public String getPreferredTime() {
        return preferredTime;
    }

    public void setPreferredTime(String preferredTime) {
        this.preferredTime = preferredTime;
    }

    
}