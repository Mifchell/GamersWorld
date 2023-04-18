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
import com.project.gamersworld.handlers.UserHandler;
import com.project.gamersworld.models.User;

@Controller
public class UserController {
    
    @Autowired
    UserHandler userHandler;

    @Autowired
    EventHandler eventHandler;
    
    // show pages
    @GetMapping("/index")
    public String viewHome(Model model, HttpServletRequest request){
        // add group and user column here
        model.addAttribute("events", eventHandler.eventSearch(retrieveCurrentUser(request)));
        return "index";
    }

    @GetMapping("/search")
    public String viewSearch(){
        return "search";
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
