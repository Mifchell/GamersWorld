package com.project.gamersworld.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.gamersworld.models.Profile;
import com.project.gamersworld.models.User;
import com.project.gamersworld.repo.UserRepo;

@Service
public class UserHandler {
    @Autowired
    private UserRepo userRepo;

    public boolean login(String email, String password)
    {
        boolean check = false;

        // if user exists, check if password matches saved password
        if (userRepo.findByProfileEmailAddress(email) != null){
            User user = new User(userRepo.findByProfileEmailAddress(email));
            check = user.getProfile().getPassword().equals(password);
        }

        return check;
    }

    public boolean signUp(String email, String password)
    {
        
        boolean check = false;

        // check if there is already an existing email
        if (userRepo.findByProfileEmailAddress(email) != null){
            return check;
        }
        else{
            // create new profile and user
            Profile profile = new Profile("", password, email, "", "");
            User user = new User(profile);
            userRepo.save(user);
            
            return true;
        }
    }
}
