package com.project.gamersworld.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.gamersworld.handlers.EventHandler;
import com.project.gamersworld.handlers.GroupHandler;
import com.project.gamersworld.handlers.UserHandler;
import com.project.gamersworld.models.Game;
import com.project.gamersworld.models.User;

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
        model.addAttribute("gamers", userHandler.recommendGamer(retrieveCurrentUser(request).getUserID()));
        
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
    public String signUp(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password, HttpServletRequest request, Model model)
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


        model.addAttribute("errorMessage", "Email is already taken.");

        return "signup";
    }

    //log out
    @PostMapping("/logout")
    public String logout(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        session.removeAttribute("userID");
        
        return "redirect:/login?logout"; //go back to login page after log out

    }

    //delect account
    @PostMapping("/delete")
    public String deleteAccount(HttpServletRequest request) // must enter password to delete account
    { 
        //if password matches then delete
        if(userHandler.deleteAccount(retrieveCurrentUser(request)))
        {
            HttpSession session = request.getSession();
            session.removeAttribute("userID");
            return "redirect:/login";
            
        }
       
        return "redirect:/profile"; 

    }

    // create profile
    @GetMapping("/createprofile")
    public String viewCreateProfile(){
        return "createprofile";
    }

    @PostMapping("/createprofile")
    public String createProfile(@RequestParam(value = "username") String username, @RequestParam(value = "description") String description, @RequestParam(value = "preferredTime") String preferredTime, @RequestParam(name = "selectedGames", required = false) List<Game> selectedGames, HttpServletRequest request)
    {   
        userHandler.createProfile(retrieveCurrentUser(request), username, description, preferredTime, selectedGames); 
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
