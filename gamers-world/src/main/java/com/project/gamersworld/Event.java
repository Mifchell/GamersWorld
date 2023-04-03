package com.project.gamersworld;

import java.util.*;

import javax.persistence.*;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int eventId;

    private Date date;

    private String location;

    private Game game;

    private PlayLevel playLevel;

    private String description;

    private List<String> comments;

    // @ManyToMany(mappedBy = "eventList")
    private List<User> attendeeList;

    public Event() {
    }

    public Event(int eventId, Date date, String location, String description, Game game, PlayLevel playLevel,
            List<String> comments, List<User> attendeeList) {
        this.eventId = eventId;
        this.date = date;
        this.location = location;
        this.description = description;
        this.game = game;
        this.playLevel = playLevel;
        this.comments = comments;
        this.attendeeList = attendeeList;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public PlayLevel getPlayLevel() {
        return playLevel;
    }

    public void setAttendeeList(List<User> attendeeList) {
        this.attendeeList = attendeeList;
    }

    public List<User> getAttendeeList() {
        return attendeeList;
    }

    public void setPlayLevel(PlayLevel playLevel) {
        this.playLevel = playLevel;
    }

    public List<String> getComments() {
        return comments;
    }

    /*
     * @param the comment to be added to the eventList
     * add comment to the list and display new comment list
     */
    public void commentEvent(String message) {

    }
}
