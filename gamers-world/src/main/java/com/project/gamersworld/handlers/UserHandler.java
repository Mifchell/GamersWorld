package com.project.gamersworld.handlers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.gamersworld.models.Game;
import com.project.gamersworld.models.Profile;
import com.project.gamersworld.models.User;
import com.project.gamersworld.repo.UserRepo;

@Service
public class UserHandler {
    @Autowired
    private UserRepo userRepo;

    public User login(String email, String password)
    {
        // if user exists, check if password matches saved password
        if (userRepo.findByProfileEmailAddress(email) != null){
            User user = new User(userRepo.findByProfileEmailAddress(email));
            if(user.getProfile().getPassword().equals(password))
            {
                return user;
            }
        }

        return null;
    }

    public User signUp(String email, String password)
    {
        // check if there is already an existing email
        if (userRepo.findByProfileEmailAddress(email) != null){
            return null;
        }
        else{
            // create new profile and user
            Profile profile = new Profile("", password, email, "", "");
            User user = new User(profile);
            userRepo.save(user);
            
            return user;
        }
    }

    public void createProfile(User user, String username, String description, String preferredTime, String game)
    {
        user.getProfile().setUsername(username);
        user.getProfile().setDescription(description);
        user.getProfile().setTime(preferredTime);
        
        // set one game for now
        List<Game> games = new ArrayList<Game>();
        games.add(Game.valueOf(game));
        user.getProfile().setGames(games);

        // save all
        userRepo.save(user);
    }

    public UserRepo getUserRepo()
    {
        return userRepo;
    }
}
