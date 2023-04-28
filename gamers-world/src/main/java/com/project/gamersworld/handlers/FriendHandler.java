package com.project.gamersworld.handlers;
import com.project.gamersworld.models.*;
import com.project.gamersworld.repo.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FriendHandler {

    @Autowired
    private UserRepo userRepo;

    public void addFriend(User owner, User user)
    {
        owner.friendsList.add(user);
        user.friendsList.add(owner); 

        userRepo.save(owner);
    }

    public void removeFriend(int user1,int user2)
    {
        boolean check =false;
        User owner = new User(userRepo.findByUid(user1));
        User user = new User(userRepo.findByUid(user2));

        List<User> ownerList = owner.getFriendList();

        for(User us: ownerList)
            if(us.getUserID() == user2)
                check = true;
        if(check)
        {
            System.out.print("IN\n\n");
            ownerList.remove(user);
            owner.setFriendList(ownerList);
            List<User> userList = user.getFriendList();
            userList.remove(user1);
            user.setFriendList(userList);
            userRepo.save(owner);
            userRepo.save(user);
        }
    }

    public void blockUser(User user)
    {

    }
 
}
