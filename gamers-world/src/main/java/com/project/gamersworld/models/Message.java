package com.project.gamersworld.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int messageID;

    @ManyToOne
    int sender;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "message", joinColumns = @JoinColumn(name = "UID"), inverseJoinColumns = @JoinColumn(name = "receiver"))
    @Fetch(value = FetchMode.SELECT)
    List<User> receivers;
    
    String date;
    String message;
    int groupID;

    public Message()
    {
        this.receivers = new ArrayList<User>();
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
    public int getSender() {
        return sender;
    }
    public void setRecievers(List<User> recievers) {
        this.receivers = recievers;
    }
    public int getGroupID() {
        return groupID;
    }
}
