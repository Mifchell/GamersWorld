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

    public void addFriend(int owner, int user)
    {
        User ownerU = userRepo.findByUid(owner);
        List<User> ownerList = ownerU.getFriendList();
        User userU = userRepo.findByUid(user);
        ownerList.add(userU);
        List<User> userList = userU.getFriendList();
        userList.add(ownerU);
        userU.setFriendList(userList);
        userRepo.save(userU);

    }
    
    public void removeFriend(int owner,int user)
    {
        User ownerU = userRepo.findByUid(owner);
        User userU = userRepo.findByUid(user);
        List<User> ownerList = ownerU.getFriendList();
        List<User> userList = userU.getFriendList();
        if(ownerList.contains(userU))
            ownerList.remove(userU);
        if(userList.contains(ownerU))
            userList.remove(owner);
        
        userRepo.save(userU);

    }

    public void blockUser(User user)
    {

    }
 
}
