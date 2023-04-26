package com.project.gamersworld.handlers;

import com.project.gamersworld.models.*;
import com.project.gamersworld.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendsHandler {

    @Autowired
    private UserRepo userRepo;

    public void addFriend(int user1, int user2)
    {
        User person1 = userRepo.findByUid(user1);
        System.out.println(person1.getUserID());
    }
    

    
}
