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

    /*
     * do we need this? or should we just have databases representing them? Like
     * eventRegistration class
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "friends", joinColumns = @JoinColumn(name = "uid"), inverseJoinColumns = @JoinColumn(name = "user_friend_uid"))
    @Fetch(value = FetchMode.SELECT)
    public List<User> friendsList;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(name = "blocked", joinColumns = @JoinColumn(name = "UID"), inverseJoinColumns = @JoinColumn(name = "blocked_friend_uid"))
    @Fetch(value = FetchMode.SELECT)
    public List<User> blockedUsers;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @Fetch(value = FetchMode.SELECT)
    @JoinTable(name = "group_registration", joinColumns = { @JoinColumn(name = "uid") }, inverseJoinColumns = {
            @JoinColumn(name = "groupID") })
    public List<Group> groupList;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SELECT)
    @JoinTable(name = "event_registration", joinColumns = @JoinColumn(name = "uid"), inverseJoinColumns = @JoinColumn(name = "eventID"))
    List<Event> eventList;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<Message> sentMessages;

    @ManyToMany(mappedBy = "receivers")
    private List<Message> receivedMessages;

    @OneToMany(mappedBy = "requestReceiver", cascade = CascadeType.ALL)
    private List<FriendRequest> receivedFriendRequest;

    @OneToMany(mappedBy = "requestSender", cascade = CascadeType.ALL)
    private List<FriendRequest> sentFriendRequest;

    public User() {
        this.friendsList = new ArrayList<User>();
        this.groupList = new ArrayList<Group>();
        this.blockedUsers = new ArrayList<User>();
        this.eventList = new ArrayList<Event>();
        this.sentMessages = new ArrayList<Message>();
        this.receivedMessages = new ArrayList<Message>();
        this.receivedFriendRequest = new ArrayList<FriendRequest>();
        this.sentFriendRequest = new ArrayList<FriendRequest>();
    }

    public User(User user) {
        this.uid = user.getUserID();
        this.profile = user.getProfile();
        this.friendsList = user.getFriendList();
        this.blockedUsers = user.getBlockedUsers();
        this.groupList = user.getGroupList();
        this.eventList = user.getEventList();
        this.receivedMessages = user.getReceivedMessages();
        this.sentMessages = user.getSentMessages();
        this.receivedFriendRequest = user.getreceivedFriendRequest();
        this.sentFriendRequest = user.getSentFriendRequest();
    }

    public User(Profile profile) {

        this.profile = profile;
        this.friendsList = new ArrayList<User>();
        this.groupList = new ArrayList<Group>();
        this.blockedUsers = new ArrayList<User>();
        this.eventList = new ArrayList<Event>();
        this.sentMessages = new ArrayList<Message>();
        this.receivedMessages = new ArrayList<Message>();
        this.receivedFriendRequest = new ArrayList<FriendRequest>();
        this.sentFriendRequest = new ArrayList<FriendRequest>();
    }

    public int getUserID() {
        return this.uid;
    }

    // For Test Purposes ONLY
    public void setUserId(int id) {
        this.uid = id;
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

    public String toString() {
        return "username: " + this.profile.getUsername() + " ID: " + getUserID();
    }

    public Object thenReturn(User user1) {
        return null;
    }

    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }
    public void setReceivedMessages(List<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    public List<Message> getSentMessages() {
        return sentMessages;
    }
    public void setSentMessages(List<Message> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public List<FriendRequest> getreceivedFriendRequest() {
        return receivedFriendRequest;
    }
    public void setReceivedFriendRequest(List<FriendRequest> receivedFriendRequest) {
        this.receivedFriendRequest = receivedFriendRequest;
    }
    public void setSentFriendRequest(List<FriendRequest> sentFriendRequest) {
        this.sentFriendRequest = sentFriendRequest;
    }
    public List<FriendRequest> getSentFriendRequest() {
        return sentFriendRequest;
    }

    // for login test only
    @Override
    public boolean equals(Object obj) {
        return this.uid == ((User) obj).uid;
    }

}
