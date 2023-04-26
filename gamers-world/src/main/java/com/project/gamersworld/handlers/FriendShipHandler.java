package com.project.gamersworld.handlers;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.gamersworld.models.User;
import com.project.gamersworld.repo.UserRepo;

@Service
public class FriendShipHandler {
    @Autowired
    UserRepo userRepo;

    public void addFriend(User owner,User user)
    {
        owner.friendsList.add(user.getUserID());
        user.friendsList.add(owner.getUserID()); 
        userRepo.save(owner);
        userRepo.save(user);
    }
    
    public void removeFriend(User owner, User user)
    {
        owner.friendsList.remove(user.getUserID());
        user.friendsList.remove(owner.getUserID());
        userRepo.save(owner);
        userRepo.save(user);
    }

    public void blockUser(User user)
    {

    }
    
}

