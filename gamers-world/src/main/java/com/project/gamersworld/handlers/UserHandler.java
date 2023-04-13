package com.project.gamersworld.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.gamersworld.models.User;
import com.project.gamersworld.repo.UserRepo;

@Service
public class UserHandler {
    @Autowired
    private UserRepo userRepo;

    public boolean login(String email, String password)
    {
        boolean check = false;

        if (userRepo.findByProfileEmailAddress(email) != null){
            User user = new User(userRepo.findByProfileEmailAddress(email));
            check = user.getProfile().getPassword().equals(password);
        }

        return check;
    }

    public boolean signup(String email, String password)
    {
        // check if there is already an existing email
        boolean check = false;

        if (userRepo.findByProfileEmailAddress(email) != null)

        // create new profile - pass in email and password ""
        //rofile(String username, String password, String emailAdress, String description, String preferredTime)
        return false;
    }
}
