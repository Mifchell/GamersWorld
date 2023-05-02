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

    public void sendMessage(int sender, int receiver, String message)
    {
        User senderU = userRepo.findByUid(sender);
        User receiverU = userRepo.findByUid(receiver);
        Message mess = new Message(senderU, receiverU, message);
        messageRepo.save(mess);
    }

    public void sendGroupMessage(User sender, Group reveiver, String message)
    {

    }
}
