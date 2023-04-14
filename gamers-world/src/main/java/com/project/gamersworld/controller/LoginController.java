package com.project.gamersworld.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.gamersworld.handlers.UserHandler;
import com.project.gamersworld.repo.UserRepo;

@Controller
public class LoginController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    UserHandler userHandler;

    @GetMapping("")
    public String viewHomePage(){
        return "index";
    }

    // log in
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password){
      // authenticate user
      if(userHandler.login(email, password))
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
