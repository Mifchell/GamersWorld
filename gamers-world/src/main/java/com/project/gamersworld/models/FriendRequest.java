package com.project.gamersworld.models;

import javax.persistence.*;

@Entity
@Table(name = "friend_request")
public class FriendRequest {
    
    @Id
    @Column(name = "friendrequestID")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int requestID;

    @ManyToOne
    @JoinColumn(name = "requestsender")
    private User requestSender;

    @ManyToOne
    @JoinColumn(name = "requestreceiver")
    private User requestReceiver;

    public FriendRequest(User sender, User receiver)
    {
        this.requestSender = sender;
        this.requestReceiver = receiver;
    }
    public FriendRequest()
    {
        
    }


    public int getRequestID() {
        return requestID;
    }
    public User getSender() {
        return requestSender;
    }
    public User getReceiver() {
        return requestReceiver;
    }

}
