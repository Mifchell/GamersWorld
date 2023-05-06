package com.project.gamersworld.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @Column(name = "messageID")
    @GeneratedValue(strategy = GenerationType.TABLE)
    int messageID;

    @ManyToOne
    @JoinColumn(name = "sender", referencedColumnName = "UID")
    User sender;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "message_receivers", joinColumns = @JoinColumn(name = "message_id"), inverseJoinColumns = @JoinColumn(name = "receiverID"))
    List<User> receivers;

    @Column(name = "date")
    String date;
    @Column(name = "message")
    String message;
    @Column(name = "groupID")
    int groupID;
    @Column(name = "num_of_likes")
    int numLikes = 0;

    public Message() {
        this.receivers = new ArrayList<User>();
        this.date = LocalDateTime.now().toString();
    }

    public Message(Message m) {
        this.messageID = m.getMessageID();
        this.sender = m.getSender();
        this.receivers = m.getRecievers();
        this.date = m.getDate();
        this.message = m.getMessage();
        this.groupID = m.getGroupID();
        this.numLikes = m.getLikes();

    }

    public Message(User sender, User receiver, String message) {
        this.sender = sender;
        List<User> list = new ArrayList<User>();
        list.add(receiver);
        this.receivers = list;
        this.message = message;
        this.date = LocalDateTime.now().toString();
        this.groupID = -1;
        this.numLikes = 0;

    }

    public Message(User sender, Group group, String message) {
        this.sender = sender;
        this.message = message;
        this.date = LocalDateTime.now().toString();
        this.groupID = group.getGroupID();
        this.numLikes = 0;
    }

    public String getDate() {
        return this.date;
    }

    public String getMessage() {
        return message;
    }

    public int getMessageID() {
        return messageID;
    }

    public List<User> getRecievers() {
        return receivers;
    }

    public User getSender() {
        return sender;
    }

    public void setRecievers(List<User> recievers) {
        this.receivers = recievers;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

   public void setNumLikes(int numLikes) {
       this.numLikes = numLikes;
   }

    public int getLikes() {
        return numLikes;
    }
}
