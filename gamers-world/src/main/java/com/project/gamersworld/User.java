package com.project.gamersworld;

import java.util.ArrayList;
import java.util.List;

public class User {

    int userID;
    Profile profile;
    List<User> friendsList;
    List<Group> groupList;
    List<User> blockedUsers;
    List<Event> eventList;

    void User()
    {
        this.userID = 0000;//place holder
        //what approch to gen new user ID?
    }
    void addFriend(User newFriend)
    {
        friendsList.add(newFriend);
    }

    //create group and event belong here?
    void removeFriend(User toBeRemoved)
    {
        friendsList.remove(toBeRemoved);
    }

    void block(User toBeBlocked)
    {
        if(friendsList.contains(toBeBlocked))
            friendsList.remove(toBeBlocked);
        blockedUsers.add(toBeBlocked);
    }
}

