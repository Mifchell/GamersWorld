package com.project.gamersworld;

import java.util.List;

public class User {

    int userID;
    Profile profile;
    List<User> friendsList;
    List<Group> groupList;
    List<User> blockedUsers;
    List<Event> eventList;

    public User() {
        this.userID = 0000;// place holder
        // what approch to gen new user ID?
    }

    public int getUserID() {
        return this.userID;
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

    public void setFriendList(List<User> friendList) {
        this.friendsList = friendList;
    }

    public List<Group> getGroupList() {
        return this.groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    public List<User> getBlockedUsers() {
        return this.blockedUsers;
    }

    public void setBlockedUsers(List<User> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    public List<Event> getEventList() {
        return this.eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    // removed the addFriend, removeFriend and blockUser as they are in the
    // firendship class now

    // create group and event belong here?
    void createGroup() {
        // create the group
    }

    void cerateEvent() {
        // create the Event
    }
}
