package com.project.gamersworld.handlers;

import com.project.gamersworld.models.*;
import com.project.gamersworld.repo.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageHandler {

    @Autowired
    MessageRepo messageRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    GroupRepo groupRepo;

    public MessageHandler(MessageRepo messageRepo,UserRepo userRepo, GroupRepo groupRepo)
    {
        this.messageRepo = messageRepo;
        this.userRepo = userRepo;
        this.groupRepo = groupRepo;
    }

    public void sendMessage(int sender, int receiver, String message)
    {
        User senderU = userRepo.findByUid(sender);
        User receiverU = userRepo.findByUid(receiver);
        Message mess = new Message(senderU, receiverU, message);
        messageRepo.save(mess);
    }

    public void sendGroupMessage(int sender, int groupReceiver, String message)
    {
        User senderU = userRepo.findByUid(sender);
        Group group = groupRepo.findByGroupID(groupReceiver);
        Message mess = new Message(senderU, group, message);
        List<User> list = new ArrayList<User>();
        for(User u: group.getMembers())
            if(u.getUserID() != sender)
                list.add(u);
        mess.setRecievers(list);
        messageRepo.save(mess); 
    }

    public void editMessage(int messageID, String message) {
        Message mess = messageRepo.findByMessageID(messageID);
        mess.setMessage(message);
        messageRepo.save(mess);
    }

    public void deleteMessage(int messageID) {
        Message mess = messageRepo.findByMessageID(messageID);
        messageRepo.delete(mess);
    }
}
