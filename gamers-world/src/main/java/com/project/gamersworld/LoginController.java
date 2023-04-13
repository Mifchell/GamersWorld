package com.project.gamersworld;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("")
    public String viewHomePage(){
        return "index";
    }

    // log in
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        List<User> users = userRepo.findAll();
        for(User u:users)
        System.out.println(u.getProfile().getEmail());
        model.addAttribute("user", users);
        return "login";
    }
// error is that user is null
    @PostMapping("/login")
    public String login(@ModelAttribute User user) {
      // authenticate user
      // traverse the list!
      // if ("email".equals(users.get(i)) && "password".equals(user.getProfile().getPassword())) {
      //   return "redirect:/index";
      // } else {
        return "redirect:/login?error";
      // }
    }


    // sign up
    @GetMapping("/signup")
    public String showSignUp(Model model)
    {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/process_signup")
    public String processSignUp(User user)
    {
        // save User
        userRepo.save(user);
        return "editprofile";
    }

}
