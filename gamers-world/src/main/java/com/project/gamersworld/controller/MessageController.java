package com.project.gamersworld.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.gamersworld.handlers.EventHandler;
import com.project.gamersworld.handlers.GroupHandler;
import com.project.gamersworld.handlers.MessageHandler;
import com.project.gamersworld.handlers.UserHandler;
import com.project.gamersworld.models.User;
import com.project.gamersworld.repo.GroupRepo;
import com.project.gamersworld.repo.UserRepo;
import com.project.gamersworld.models.Game;


@Controller
public class MessageController {

    @Autowired
    UserController userController;
    @Autowired
    MessageHandler mHandler;
    @Autowired
    UserHandler uHandler;
    @Autowired
    UserRepo uRepo;
    @Autowired
    GroupRepo gRepo;

    @GetMapping("/messages/user/{uid}")
    public String viewMessages(Model model, HttpServletRequest request,@PathVariable("uid")int uid) {
        model.addAttribute("gamer", uRepo.findByUid(uid));
        model.addAttribute("user", userController.retrieveCurrentUser(request));
        model.addAttribute("messages", uHandler.getConversation(userController.retrieveCurrentUser(request).getUserID(),uRepo.findByUid(uid).getUserID()));
        return "UserMessages";
    }
    @GetMapping("/messages/group/{gid}")
    public String viewGroupMessages(Model model, HttpServletRequest request,@PathVariable("gid")int gid) {
        model.addAttribute("gamer", userController.retrieveCurrentUser(request));
        model.addAttribute("group", gRepo.findByGroupID(gid));
        model.addAttribute("messages",uHandler.getGroupConversation(userController.retrieveCurrentUser(request).getUserID(), gid));
        return "GroupMessages";
    }

    @PostMapping("/message/send")
    public String sendUserMessage(HttpServletRequest request,@RequestParam(value = "message")String message,@RequestParam(value = "receiverID")int userID)
    {
        mHandler.sendMessage(userController.retrieveCurrentUser(request).getUserID(), userID, message);
        return "redirect:/messages/user/"+ userID;
    }

    
    @PostMapping("/message/group/send")
    public String sendGroupMessage(HttpServletRequest request,@RequestParam(value = "message")String message,@RequestParam(value = "receiverID")int gid)
    {
        mHandler.sendGroupMessage(userController.retrieveCurrentUser(request).getUserID(), gid, message);
        return "redirect:/messages/group/"+ gid;
    }
}
