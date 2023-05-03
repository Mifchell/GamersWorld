package com.project.gamersworld.handlers;
import com.project.gamersworld.models.*;
import com.project.gamersworld.repo.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendHandler {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private FriendRequestRepo friendRequestRepo;

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
        for(int i = 0; i < ownerList.size(); i++)
            if(ownerList.get(i).getUserID() == user)
                ownerList.remove(i);
        for(int i = 0; i < userList.size(); i++)
            if(userList.get(i).getUserID() == owner)
                userList.remove(i);
        ownerU.setFriendList(ownerList);
        userU.setFriendList(userList);
        userRepo.save(ownerU);
        userRepo.save(userU);

    }

    public void blockUser(int user, int blocked)
    {
        boolean check = true;
        User userU = userRepo.findByUid(user);
        List<User> userBlockedList = userU.getBlockedUsers();
        User blockedU = userRepo.findByUid(blocked);
        for(User u: userBlockedList)
            if(u.getUserID() == blocked)
                check = false;
        if(check)
        {
            userBlockedList.add(blockedU);
            userU.setBlockedUsers(userBlockedList);
            userRepo.save(userU);   
        }
    }

    public void unblockUser(int user, int blocked)
    {
        User userU = userRepo.findByUid(user);
        List<User> userBlockedList = userU.getBlockedUsers();
        for(int i = 0; i < userBlockedList.size(); i++)
            if(userBlockedList.get(i).getUserID() == blocked)
                userBlockedList.remove(i);
        userRepo.save(userU);
    }

    public void sendFriendRequest(int sender, int receiver)
    {
        User senderU = userRepo.findByUid(sender);
        User receiverU = userRepo.findByUid(receiver);

        FriendRequest request = new FriendRequest(senderU, receiverU);
        friendRequestRepo.save(request);
    }
    public void declineFriendRequest(FriendRequest request)
    {
        friendRequestRepo.delete(request);
    }
 
}
