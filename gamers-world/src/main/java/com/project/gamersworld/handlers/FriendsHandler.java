package com.project.gamersworld.handlers;

import com.project.gamersworld.models.*;
import com.project.gamersworld.repo.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendsHandler 
{

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
            
            List<Friends> p1List = person1.getFriendList();
            List<Friends> p2List = person2.getFriendList();

            p1List.add(person2F);
            p2List.add(person1F);
            person1.setFriendList(p1List);
            person2.setFriendList(p2List);
            userRepo.save(person1);
        }
    }

    public void removeFriend(int user1, int user2)
    {
        
        User person1 = userRepo.findByUid(user1);
        User person2 = userRepo.findByUid(user2);

        List<Friends> person1List = person1.getFriendList();
        List<Friends> person2List = person2.getFriendList();

        for(Friends f: person1List)
            System.out.println(f.getUID());
        System.out.println("_____________________");
        System.out.println(person2List.size());
        
        // int index = -1;
        // for(int i = 0; i < person1List.size();i++)
        // {
        //     Friends f = person1List.get(i);
        //     if(f.getUID() == user2)
        //         index = i;
        // }
        // person1List.remove(index);
        // index = -1;
        // for(int i = 0; i < person2List.size();i++)
        // {
        //     Friends f = person2List.get(i);
        //     if(f.getUID() == user1)
        //         index = i;
        // }
        // person2List.remove(index);

        // person1.setFriendList(person1List);
        // person2.setFriendList(person2List);
        // userRepo.save(person1);
        // userRepo.save(person2);
    }
}
