package com.project.gamersworld.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.gamersworld.handlers.UserHandler;

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

}
