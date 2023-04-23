package com.project.gamersworld.controller;

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

import ch.qos.logback.core.joran.conditional.ElseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;


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
    public String viewHome(Model model, HttpServletRequest request){
        model.addAttribute("events", eventHandler.eventSearch(retrieveCurrentUser(request)));
        model.addAttribute("groups", groupHandler.groupSearch(""));
        return "index";
    }

    @GetMapping("/events")
    public String viewEvents(Model model, HttpServletRequest request){
        model.addAttribute("events", eventHandler.eventSearch(retrieveCurrentUser(request)));
        return "events";
    }

    @PostMapping("/events")
    public String filterEvents(@RequestParam(value = "filter") String filter, Model model, HttpServletRequest request)
    {   
        model.addAttribute("events", eventHandler.filterEvent(filter));
        return "events";
    }

    @GetMapping("/groups")
    public String viewGroups(Model model, HttpServletRequest request){
        return "groups";
    }

    @GetMapping("/gamers")
    public String viewGamers(Model model, HttpServletRequest request){
        return "gamers";
    }

    @GetMapping("/messages")
    public String viewMessages(Model model, HttpServletRequest request){
        return "messages";
    }

    @GetMapping("/profile")
    public String viewProfile(Model model, HttpServletRequest request){
        model.addAttribute("profile", retrieveCurrentUser(request).getProfile());
        return "profile";
    }

    @GetMapping("/edit_profile")
    public String viewEditProfile(Model model, HttpServletRequest request){
        model.addAttribute("profile", retrieveCurrentUser(request).getProfile());
        return "editprofile";
    }

    @GetMapping("/event")
    public String viewEvent(){
        return "event";
    }

    // log in
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password, HttpServletRequest request){
    // authenticate user
    User user = userHandler.login(email, password);
    if(user != null)
    {
        // store info about user's session
        HttpSession session = request.getSession();
        session.setAttribute("userID", user.getUserID());

        // show home page
        return "redirect:/index";
    }
    
    // show error message
    // return "redirect:/login";
    return "redirect:/login?error";
    }


    // sign up
    @GetMapping("/signup")
    public String showSignUp()
    {
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password, HttpServletRequest request)
    {   
        // if sign up works, then take them to create profile, else show them error
        User user = userHandler.signUp(email, password);
        if(user != null)
        {
            // store info about user's session
            HttpSession session = request.getSession();
            session.setAttribute("userID", user.getUserID());

            return "redirect:/createprofile";
        }

        return "redirect:/signup?error";
    }

    //log out
    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null)
        {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        
        return "redirect:/login?logout"; //go back to login page after log out

    }

    //delect account
    @PostMapping("/delete")
    public String deleteAccount(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password) // must enter password to delete account
    {

        //if password matches then delete
        if(userHandler.deleteAccount(email, password))
        {
            return "redirect:/signup";
            
        }
        
        
        return "redirect:/login"; //delete account button in login page?

    }

    // create profile
    @GetMapping("/createprofile")
    public String viewCreateProfile(){
        return "createprofile";
    }

    @PostMapping("/createprofile")
    public String createProfile(@RequestParam(value = "username") String username, @RequestParam(value = "description") String description, @RequestParam(value = "preferredTime") String preferredTime, @RequestParam(value = "game") String game, HttpServletRequest request)
    {   
        userHandler.createProfile(retrieveCurrentUser(request), username, description, preferredTime, game);
        return "redirect:/profile";
    }


    // helper method for this class to retrieve user for each page
    private User retrieveCurrentUser(HttpServletRequest request)
    {
        // Get user session, retrieve uid to get User
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userID");
        return userHandler.getUserRepo().findByUid(userId);
    }

}
