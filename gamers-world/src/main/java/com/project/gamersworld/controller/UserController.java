package com.project.gamersworld.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.gamersworld.handlers.EventHandler;
import com.project.gamersworld.handlers.GroupHandler;
import com.project.gamersworld.handlers.UserHandler;
import com.project.gamersworld.models.User;
import com.project.gamersworld.models.Game;

@Controller
public class UserController {

    @Autowired
    UserHandler userHandler;

    @Autowired
    EventHandler eventHandler;

    @Autowired
    GroupHandler groupHandler;

    // show pages
    @GetMapping("/index")
    public String viewHome(Model model, HttpServletRequest request) {
        model.addAttribute("events", eventHandler.eventSearch(retrieveCurrentUser(request)));
        model.addAttribute("groups", groupHandler.groupSearch("", retrieveCurrentUser(request)));
        model.addAttribute("gamers", userHandler.recommendGamer(retrieveCurrentUser(request).getUserID()));
        model.addAttribute("user", retrieveCurrentUser(request));

        return "index";
    }

    @GetMapping("/events")
    public String viewEvents(Model model, HttpServletRequest request) {
        model.addAttribute("events", eventHandler.eventSearch(retrieveCurrentUser(request)));
        return "events";
    }

    @GetMapping("/gamers")
    public String viewGamers(Model model, HttpServletRequest request) {
        return "gamers";
    }

    @GetMapping("/messages")
    public String viewMessages(Model model, HttpServletRequest request) {
        return "messages";
    }

    @GetMapping("/profile")
    public String viewProfile(Model model, HttpServletRequest request) {
        model.addAttribute("profile", retrieveCurrentUser(request).getProfile());
        model.addAttribute("mygroups", groupHandler.myGroups(retrieveCurrentUser(request)));
        model.addAttribute("events", eventHandler.myEvents(retrieveCurrentUser(request)));
        return "profile";
    }

    @GetMapping("/editprofile")
    public String viewEditProfile(Model model, HttpServletRequest request) {
        model.addAttribute("profile", retrieveCurrentUser(request).getProfile());
        return "editprofile";
    }

    // log in
    @GetMapping({ "/login", "", "/" })
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password,
            HttpServletRequest request, Model model) {
        // authenticate user
        User user = userHandler.login(email, password);
        if (user != null) {
            // store info about user's session
            HttpSession session = request.getSession();
            session.setAttribute("userID", user.getUserID());

            // show home page
            return "redirect:/index";
        }

        // show error
        model.addAttribute("errorMessage", "Email and/or password invalid. Please try again.");
        return "login";
    }

    // sign up
    @GetMapping("/signup")
    public String showSignUp() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password,
            HttpServletRequest request, Model model) {
        // if sign up works, then take them to create profile
        User user = userHandler.signUp(email, password);
        if (user != null) {
            // store info about user's session
            HttpSession session = request.getSession();
            session.setAttribute("userID", user.getUserID());

            return "redirect:/createprofile";
        }

        // show error
        model.addAttribute("errorMessage", "Email is already taken. Please try again with a different email.");
        return "signup";
    }

    // log out
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("userID");

        return "redirect:/login?logout"; // go back to login page after log out

    }

    // delect account
    @PostMapping("/delete")
    public String deleteAccount(HttpServletRequest request) // must enter password to delete account
    {
        // if password matches then delete
        if (userHandler.deleteAccount(retrieveCurrentUser(request))) {
            HttpSession session = request.getSession();
            session.removeAttribute("userID");
            return "redirect:/login";

        }

        return "redirect:/profile";

    }

    //Edit profile
    @PostMapping("/editprofile")
    public String editProfile(@RequestParam(value = "username") String username,
        @RequestParam(value = "description") String description,
        @RequestParam(value = "preferredTime") String preferredTime,
        @RequestParam(name = "selectedGames", required = false) List<Game> selectedGames,
        @RequestParam(value = "email") String email, @RequestParam(value = "password") String password,
        HttpServletRequest request, Model model){
            
            if(userHandler.editProfile(retrieveCurrentUser(request), username, description, preferredTime, selectedGames, email, password)){
                
                return "redirect:/profile";
            }
            model.addAttribute("errorMessage", "Username or email is already taken. Please try again.");
            model.addAttribute("profile", retrieveCurrentUser(request).getProfile());
            
            return "editprofile";
        }

    // create profile
    @GetMapping("/createprofile")
    public String viewCreateProfile() {
        return "createprofile";
    }

    @PostMapping("/createprofile")
    public String createProfile(@RequestParam(value = "username") String username,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "preferredTime") String preferredTime,
            @RequestParam(name = "selectedGames", required = false) List<Game> selectedGames,
            HttpServletRequest request, Model model) {
        // check if new username is valid
        if (userHandler.createProfile(retrieveCurrentUser(request), username, description, preferredTime,
                selectedGames)) {
            return "redirect:/profile";
        }

        // show error
        model.addAttribute("errorMessage", "Username is already taken. Please try again with a different username.");
        return "createprofile";
    }

    // helper method for this class to retrieve user for each page
    protected User retrieveCurrentUser(HttpServletRequest request) {
        // Get user session, retrieve uid to get User
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userID");
        return userHandler.getUserRepo().findByUid(userId);
    }

}
