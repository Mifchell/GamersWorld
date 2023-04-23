package com.project.gamersworld.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.gamersworld.handlers.GroupHandler;
import com.project.gamersworld.models.User;

@Controller
public class GroupController {

    @Autowired
    GroupHandler groupHandler;

    @GetMapping("/creategroup")
    public String viewCreateProfile() {
        return "createprofile";
    }

    @PostMapping("/creategroup")
    public String createProfile(@RequestParam(value = "name") String name,
            @RequestParam(value = "description") String description, HttpServletRequest request) {
        groupHandler.createGroup(name, description, retrieveCurrentUser(request));
        return "redirect:/group";
    }

    private int retrieveCurrentUser(HttpServletRequest request) {
        // Get user session, retrieve uid to get User
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userID");
        return userId;
    }

}
