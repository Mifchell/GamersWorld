package com.project.gamersworld.handlers;

import com.project.gamersworld.models.*;
import com.project.gamersworld.repo.*;

import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.ReverbType;

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

    public void sendMessage(int sender, int receiver, String message) {
        User senderU = userRepo.findByUid(sender);
        User receiverU = userRepo.findByUid(receiver);
        Message mess = new Message(senderU, receiverU, message);
        messageRepo.save(mess);
    }

    public void sendGroupMessage(int sender, int groupReceiver, String message) {
        User senderU = userRepo.findByUid(sender);
        Group group = groupRepo.findByGroupID(groupReceiver);
        Message mess = new Message(senderU, group, message);

        // overide receivers
        User u1 = userRepo.findByUid(2);
        User u2 = userRepo.findByUid(3);
        User u3 = userRepo.findByUid(4);
        List<User> list = new ArrayList<User>();
        list.add(u1);
        list.add(u2);
        list.add(u3);
        mess.setRecievers(list);
        /// end overide

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
