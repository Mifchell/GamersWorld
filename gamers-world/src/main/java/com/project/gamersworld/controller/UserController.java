package com.project.gamersworld.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.gamersworld.handlers.UserHandler;

@Controller
public class UserController {

    @Autowired
    UserHandler userHandler;

    // show pages
    @GetMapping("/index")
    public String viewHome(){
        return "index";
    }

    @GetMapping("/search")
    public String viewSearch(){
        return "search";
    }

    @GetMapping("/profile")
    public String viewProfile(){
        return "profile";
    }

    @GetMapping("/edit_profile")
    public String viewEditProfile(){
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
      if(userHandler.login(email, password, model))
      {
        return "index";
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
        if(userHandler.signUp(email, password))
        {
            return "editprofile";
        }

        return "redirect:/signup?error";
    }

}
