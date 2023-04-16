package com.project.gamersworld.models;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.gamersworld.repo.UserRepo;

public class Friendship {
 
    private User owner;

    public Friendship(User owner)
    {
        this.owner = owner;
    }

    public void addFriend(User user)
    {
        this.owner.friendsList.add(user);
        user.friendsList.add(owner); 
    }

    public void removeFriend(User user)
    {
        this.owner.friendsList.remove(user);
        user.friendsList.remove(owner);
    }

    public void blockUser(User user)
    {

    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }



    
}
