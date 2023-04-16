package com.project.gamersworld.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.gamersworld.handlers.EventHandler;
import com.project.gamersworld.handlers.UserHandler;
import com.project.gamersworld.models.Event;
import com.project.gamersworld.models.User;

@Controller
public class UserController {

    @Autowired
    UserHandler userHandler;

    @Autowired
    EventHandler eventHandler;

    User user = null;
    
    // show pages
    @GetMapping("/index")
    public String viewHome(Model model){
        List<Event> events = eventHandler.eventSearch(user);
        for (Event event : events)
        {
			System.out.println(event);
			System.out.println("Date: " + event.getDate());
			System.out.println(event.getGame().name());
        }
        model.addAttribute("events", eventHandler.eventSearch(user));
        return "index";
    }

    @GetMapping("/search")
    public String viewSearch(){
        return "search";
    }

    @GetMapping("/profile")
    public String viewProfile(Model model){
        model.addAttribute("profile", user.getProfile());
        return "profile";
    }

    @GetMapping("/edit_profile")
    public String viewEditProfile(Model model){
        model.addAttribute("profile", user.getProfile());
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
    public String login(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password, Model model){
    // authenticate user
    this.user = userHandler.login(email, password, user);
    if(user != null)
    {
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
    public String signUp(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password)
    {   
        // if sign up works, then take them to create profile, else show them error
        user = userHandler.signUp(email, password);
        if(user != null)
        {
            //return viewCreateProfile();
            return "redirect:/createprofile";
        }

        return "redirect:/signup?error";
    }

    // create profile
    @GetMapping("/createprofile")
    public String viewCreateProfile(){
        return "createprofile";
    }

    @PostMapping("/createprofile")
    public String createProfile(@RequestParam(value = "username") String username, @RequestParam(value = "description") String description, @RequestParam(value = "preferredTime") String preferredTime, @RequestParam(value = "game") String game, Model model)
    {   
        userHandler.createProfile(user, username, description, preferredTime, game);
        return "redirect:/profile";
    }


}
