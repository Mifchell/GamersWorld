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
import com.project.gamersworld.handlers.FriendHandler;
import com.project.gamersworld.handlers.GroupHandler;
import com.project.gamersworld.handlers.UserHandler;
import com.project.gamersworld.models.User;
import com.project.gamersworld.repo.FriendRequestRepo;
import com.project.gamersworld.models.FriendRequest;
import com.project.gamersworld.models.Game;


@Controller
public class FriendshipControler {
    @Autowired
    FriendRequestRepo fRReop;
    @Autowired
    FriendHandler fHandler;
    @Autowired
    UserController uController;
    @GetMapping("/user/accept/{request}")
    public String acceptFriend(Model model, HttpServletRequest request,@PathVariable("request")int fRequest)
    {
        FriendRequest theRequest = fRReop.findByRequestID(fRequest);
        fHandler.acceptFriendRequest(theRequest);
        return "redirect:/profile";
    }

    
    @GetMapping("/user/decline/{request}")
    public String declineFriend(Model model, HttpServletRequest request,@PathVariable("request")int fRequest)
    {
        FriendRequest theRequest = fRReop.findByRequestID(fRequest);
        fHandler.declineFriendRequest(theRequest);
        return "redirect:/profile";
    }

    @GetMapping("/index/send/{id}")
    public String sendFriendRequest(Model model, HttpServletRequest request,@PathVariable("id")int id)
    {
        fHandler.sendFriendRequest(uController.retrieveCurrentUser(request).getUserID(), id);
        return "redirect:/index";
    }
}
