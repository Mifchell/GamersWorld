package com.project.gamersworld.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "user")
public class User {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.TABLE)
    int uid;

    @Embedded
    public Profile profile;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "friends", joinColumns = @JoinColumn(name = "uid"), inverseJoinColumns = @JoinColumn(name = "user_friend_uid"))
    @Fetch(value = FetchMode.SELECT)
    public List<User> friendsList;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
    @JoinTable(name = "group_registration", joinColumns = { @JoinColumn(name = "uid") }, inverseJoinColumns = {
            @JoinColumn(name = "groupID") })
    public List<Group> groupList;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SELECT)
    @JoinTable(name = "event_registration", joinColumns = @JoinColumn(name = "uid"), inverseJoinColumns = @JoinColumn(name = "eventID"))
    List<Event> eventList;

    public User() {
        this.friendsList = new ArrayList<User>();
        this.groupList = new ArrayList<Group>();
        this.eventList = new ArrayList<Event>();
    }

    public User(User user) {
        this.uid = user.getUserID();
        this.profile = user.getProfile();
        this.friendsList = user.getFriendList();
        this.groupList = user.getGroupList();
        this.eventList = user.getEventList();
    }

    public User(Profile profile) {

        this.profile = profile;
        this.friendsList = new ArrayList<User>();
        this.groupList = new ArrayList<Group>();
        this.eventList = new ArrayList<Event>();
    }

    public int getUserID() {
        return this.uid;
    }

    public Profile getProfile() {
        return this.profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<User> getFriendList() {
        return this.friendsList;
    }

    public void setFriendList(ArrayList<User> friendList) {
        this.friendsList = friendList;
    }

    public List<Group> getGroupList() {
        return this.groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    public List<Event> getEventList() {
        return this.eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    public String toString() {
        return "username: " + this.profile.getUsername() + " ID: " + getUserID();
    }

    public Object thenReturn(User user1) {
        return null;
    }

    // for login test only
    @Override
    public boolean equals(Object obj)
    {
        return this.uid == ((User) obj).uid;
    }
}
