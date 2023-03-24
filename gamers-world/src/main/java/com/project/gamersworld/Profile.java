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

    public EnumMap<Games, PlayLevel> getPreference() {
        return preferences;
    }

    public void setPreferences(EnumMap<Games, PlayLevel> preferences) {
        this.preferences = preferences;
    }

    public String getTime() {
        return preferredTime;
    }

    public void setTime(String preferredTime) {
        this.preferredTime = preferredTime;
    }

    /*
     * This function handle the process of getting the new password
     * Not a setter*
     */
    public void updatePassword() {

    }

    /*
     * This function handle the process of getting the new emailAdress
     * Not a setter*
     */
    public void updateEmail() {

    }

    /*
     * This function handle the process of getting the new description
     * Not a setter*
     */
    public void updateDescription() {

    }

    /*
     * This function handle the process of getting the new preferences
     * Not a setter*
     */
    public void updatePreference() {

    }

    /*
     * This function handle the process of getting the new preferred time
     * Not a setter*
     */
    public void updateTime() {

    }

}