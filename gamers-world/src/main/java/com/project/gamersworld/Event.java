package com.project.gamersworld;

import java.util.*;

public class Event {
    private String eventId;
    private Date date;
    private String location;
    private String description;
    private Games game;
    private String playLevel;
    private List<User> attendeeList;

    public Event() {
    }

    public Event(String eventId, Date date, String location, String description, Games game, String playLevel,
            List<User> attendeeList) {
        this.eventId = eventId;
        this.date = date;
        this.location = location;
        this.description = description;
        this.game = game;
        this.playLevel = playLevel;
        this.attendeeList = attendeeList;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Games getGame() {
        return game;
    }

    public void setGame(Games game) {
        this.game = game;
    }

    public String getPlayLevel() {
        return playLevel;
    }

    public void setPlayLevel(String playLevel) {
        this.playLevel = playLevel;
    }

    public List<User> getAttendeeList() {
        return attendeeList;
    }

    public void setAttendeeList(List<User> attendeeList) {
        this.attendeeList = attendeeList;
    }

    public void searchEvent(Date date, String location, Games game, String playLevel) {
        // search for event by date, location, game, play level?

    }

    public void filterEvent(Date date, String location, Games game, String playLevel) {
        // filter event by date, location, game, play level?

    }
}
