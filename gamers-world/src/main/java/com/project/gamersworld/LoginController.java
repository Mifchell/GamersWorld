package com.project.gamersworld;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }   

    // @PostMapping("/login")
    // public String login(@RequestParam String email, @RequestParam String password) {
    //     // Authenticate user

    //     return "redirect:/index.html";
    // }

    
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model) {
        // Authenticate user
        
        // Check if email and password are valid
        if (email.equals("test@test.com") && password.equals("password")) {
            // Login successful, redirect to home page
            return "redirect:/index";
        } else {
            // Login failed, show error message
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }

    //necessary?
    @GetMapping("/")
    public String showHomePage() {
        return "redirect:/index";
    }

}



