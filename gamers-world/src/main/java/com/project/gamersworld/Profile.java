package com.project.gamersworld;

//import java.util.*;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

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
    private String preferredTime;

    public Profile() {
    }

    Profile(String username, String password, String emailAdress, String description, String preferredTime) {
        this.username = username;
        this.password = password;
        this.emailAddress = emailAdress;
        this.description = description;
        // this.preferences = preferences;
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

    // public EnumMap<Game, PlayLevel> getPreference() {
    // return preferences;
    // }

    // public void setPreferences(EnumMap<Game, PlayLevel> preferences) {
    // this.preferences = preferences;
    // }

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