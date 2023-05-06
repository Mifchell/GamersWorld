package com.project.gamersworld.models;

import java.util.*;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int eventId;

    private int creatorID;

    private String eventName;

    private String date;

    private String location;

    private Game game;

    private PlayLevel playLevel;

    private String description;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "event_comments", joinColumns = @JoinColumn(name = "eventID"))
    @Column(name = "comments")
    private List<String> comments;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "eventList")
    private List<User> attendeeList;

    public Event() {
    }

    public Event(Event event) {
        this.eventId = event.getEventId();
        this.creatorID = event.getCreatorID();
        this.eventName = event.getEventName();
        this.date = event.getDate();
        this.location = event.getLocation();
        this.game = event.getGame();
        this.playLevel = event.getPlayLevel();
        this.description = event.getDescription();
        this.attendeeList = event.getAttendeeList();
        this.comments = event.getComments();
    }

    public Event(String name, String date, String location, String description, Game game, PlayLevel playLevel,
            User creator) {
        this.eventName = name;
        this.date = date;
        this.location = location;
        this.description = description;
        this.attendeeList = new ArrayList<User>();
        this.attendeeList.add(creator);
        this.creatorID = creator.getUserID();
        this.playLevel = playLevel;
        this.game = game;
        this.comments = new ArrayList<String>();
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(int creatorID) {
        this.creatorID = creatorID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public String toString() {
        return "EventName: " + this.eventName;
    }

    // for event search test only
    @Override
    public boolean equals(Object obj)
    {
        return this.eventName.equals(((Event) obj).eventName) && this.location.equals(((Event) obj).location)  && this.description.equals(((Event) obj).description) && this.playLevel.equals(((Event) obj).playLevel) && this.game.equals(((Event) obj).game) && this.date.equals(((Event) obj).date) && this.eventId == ((Event) obj).eventId;
    }

}
