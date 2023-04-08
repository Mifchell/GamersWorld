package com.project.gamersworld;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        // Authenticate user

        return "redirect:/index.html";
    }

    //necessary?
    @GetMapping("/")
    public String showHomePage() {
        return "redirect:/index.html";
    }

}
