package com.project.gamersworld.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.project.gamersworld.handlers.FriendHandler;
import com.project.gamersworld.repo.FriendRequestRepo;
import com.project.gamersworld.models.FriendRequest;


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
    
    @GetMapping("/user/unblock/{user}")
    public String unblockUser(Model model, HttpServletRequest request,@PathVariable("user")int user)
    {
        fHandler.unblockUser(uController.retrieveCurrentUser(request).getUserID(), user);
        return "redirect:/profile";
    }

    @GetMapping("/user/block/{user}")
    public String blockUser(Model model, HttpServletRequest request,@PathVariable("user")int user)
    {
        fHandler.blockUser(uController.retrieveCurrentUser(request).getUserID(), user);
        return "redirect:/messages";
    }
    @GetMapping("/user/remove/{user}")
    public String removeFriend(Model model, HttpServletRequest request,@PathVariable("user")int user)
    {
        fHandler.removeFriend(uController.retrieveCurrentUser(request).getUserID(), user);
        return "redirect:/messages";
    }

}
