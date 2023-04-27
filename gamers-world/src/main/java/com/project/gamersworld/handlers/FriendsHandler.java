package com.project.gamersworld.handlers;

import com.project.gamersworld.models.*;
import com.project.gamersworld.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendsHandler {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FriendsRepo friendsRepo;

    public void addFriend(int user1, int user2)
    {
        boolean check = false;
        User person1 = userRepo.findByUid(user1);
        User person2 = userRepo.findByUid(user2);

        for(Friends friend: person1.friendsList)
            if(friend.getUser_friend_uid() == person2.getUserID())
                check = true;

        if(!check)
        {
            Friends person1F = new Friends(user1,user2);
            Friends person2F = new Friends(user2,user1);
            
            System.out.println(person1F.getUID() + "\n" + person2F.getUID());

        }
    }


    
}
