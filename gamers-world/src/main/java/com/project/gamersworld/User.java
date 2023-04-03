package com.project.gamersworld;

// import java.util.ArrayList;
// import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int uid;

    @Embedded
    Profile profile;

    // /*
    // * do we need this? or should we just have databases representing them? Like
    // * eventRegistration class
    // */
    // @ElementCollection(fetch = FetchType.EAGER)
    // @CollectionTable(name = "friends", joinColumns = @JoinColumn(name = "uid"))
    // List<User> friendsList;

    // @ManyToMany
    // @JoinTable(name = "group_registration", joinColumns = @JoinColumn(name =
    // "uid"), inverseJoinColumns = @JoinColumn(name = "groupID"))
    // List<Group> groupList;

    // @ElementCollection(fetch = FetchType.EAGER)
    // @CollectionTable(name = "Blocked", joinColumns = @JoinColumn(name = "uid"))
    // List<User> blockedUsers;

    // @ManyToMany
    // @JoinTable(name = "event_registration", joinColumns = @JoinColumn(name =
    // "uid"), inverseJoinColumns = @JoinColumn(name = "userID"))
    // List<Event> eventList;

    public User() {
        // this.friendsList = new ArrayList<User>();
        // this.groupList = new ArrayList<Group>();
        // this.blockedUsers = new ArrayList<User>();
        // this.eventList = new ArrayList<Event>();
    }

    public User(Profile profile) {
        this.profile = profile;
        // this.friendsList = new ArrayList<User>();
        // this.groupList = new ArrayList<Group>();
        // this.blockedUsers = new ArrayList<User>();
        // this.eventList = new ArrayList<Event>();
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

    // public List<User> getFriendList() {
    // return this.friendsList;
    // }

    // public void setFriendList(List<User> friendList) {
    // this.friendsList = friendList;
    // }

    // public List<Group> getGroupList() {
    // return this.groupList;
    // }

    // public void setGroupList(List<Group> groupList) {
    // this.groupList = groupList;
    // }

    // public List<User> getBlockedUsers() {
    // return this.blockedUsers;
    // }

    // public void setBlockedUsers(List<User> blockedUsers) {
    // this.blockedUsers = blockedUsers;
    // }

    // public List<Event> getEventList() {
    // return this.eventList;
    // }

    // public void setEventList(List<Event> eventList) {
    // this.eventList = eventList;
    // }

    // removed the addFriend, removeFriend and blockUser as they are in the
    // firendship class now

    // create group and event belong here?
    void createGroup() {
        // create the group
    }

    void createEvent() {
        // create the Event
    }

    public String toString() {
        return "username: " + this.profile.getUsername();
    }
}
