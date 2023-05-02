package com.project.gamersworld.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


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

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "messages", joinColumns = @JoinColumn(name = "messageID"), inverseJoinColumns = @JoinColumn(name = "receiver"))
    @Fetch(value = FetchMode.SELECT)
    List<User> receivers;
    @Column(name = "date")
    String date;
    @Column(name = "message")
    String message;
    @Column(name = "groupID")
    int groupID;

    public Message()
    {
        this.receivers = new ArrayList<User>();
        this.date = LocalDateTime.now().toString();
    }

    public Message(User sender, User receiver, String message)
    {
        this.receivers = new ArrayList<User>();
        this.receivers.add(receiver);
        this.message = message;
        this.date = LocalDateTime.now().toString();
        this.groupID = -1;

    }

    public Message(Message m)
    {
        this.messageID = m.getMessageID();
        this.sender = m.getSender();
        this.receivers = m.getRecievers();
        this.date = m.getDate();
        this.message = m.getMessage();
        this.groupID = m.getGroupID();
        
    }

    public String getDate()
    {
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

}
