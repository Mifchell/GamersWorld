package com.project.gamersworld.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.gamersworld.handlers.GroupHandler;
import com.project.gamersworld.handlers.UserHandler;
import com.project.gamersworld.models.User;

@Controller
public class GroupControler {

    @Autowired
    GroupHandler groupHandler;

    @Autowired
    UserHandler userHandler;

    @GetMapping("/creategroup")
    public String viewCreateGroup() {
        return "creategroup";
    }

    @PostMapping("/creategroup")
    public String createGroup(@RequestParam(value = "name") String name,
            @RequestParam(value = "description") String description, HttpServletRequest request, Model model) {

        if (groupHandler.createGroup(name, description, retrieveCurrentUser(request))) {
            return "redirect:/groups";
        }
        model.addAttribute("errorMessage", " Group name is already taken. Please try again with a different username.");
        return "creategroup";
    }

    @PostMapping("/joingroup/{id}")
    public String joinGroup(@PathVariable String id, Model model, HttpServletRequest request) {
        groupHandler.join(Integer.parseInt(id), retrieveCurrentUser(request));

        return "redirect:/groups";
    }

    @PostMapping("/leavegroup/{id}")
    public String leaveGroup(@PathVariable String id, Model model, HttpServletRequest request) {
        groupHandler.leaveGroup(retrieveCurrentUser(request), Integer.parseInt(id));

        return "redirect:/profile";
    }

    @PostMapping("/deletegroup/{id}")
    public String deleteGroup(@PathVariable String id, Model model) {
        groupHandler.deleteGroup(Integer.parseInt(id));

        return "redirect:/profile";
    }

    @GetMapping("/groups")
    public String viewGroups(Model model, HttpServletRequest request) {
        model.addAttribute("groups", groupHandler.groupSearch("", retrieveCurrentUser(request)));
        return "groups";
    }

    @GetMapping("/mygroups")
    public String myGroups(Model model, HttpServletRequest request) {
        model.addAttribute("mygroups", groupHandler.myGroups(retrieveCurrentUser(request)));
        return "profile";
    }

    @PostMapping("/groups")
    public String filterGroup(@RequestParam(value = "filter") String filter, Model model, HttpServletRequest request) {
        model.addAttribute("groups", groupHandler.groupSearch(filter, retrieveCurrentUser(request)));
        return "groups";
    }

    // helper method for this class to retrieve user for each page
    protected User retrieveCurrentUser(HttpServletRequest request) {
        // Get user session, retrieve uid to get User
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userID");
        return userHandler.getUserRepo().findByUid(userId);
    }

}
