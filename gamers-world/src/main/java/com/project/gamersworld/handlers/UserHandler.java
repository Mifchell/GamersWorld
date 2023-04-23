package com.project.gamersworld.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

import com.project.gamersworld.models.Profile;
import com.project.gamersworld.models.User;
import com.project.gamersworld.models.Game;
import com.project.gamersworld.repo.UserRepo;

@Service
public class UserHandler {
    @Autowired
    private UserRepo userRepo;

    public boolean login(String email, String password) {
        boolean check = false;

        // if user exists, check if password matches saved password
        if (userRepo.findByProfileEmailAddress(email) != null) {
            User user = new User(userRepo.findByProfileEmailAddress(email));
            check = user.getProfile().getPassword().equals(password);
        }

        return check;
    }

    public boolean signUp(String email, String password) {

        boolean check = false;

        // check if there is already an existing email
        if (userRepo.findByProfileEmailAddress(email) != null) {
            return check;
        } else {
            // create new profile and user
            Profile profile = new Profile("", password, email, "", "");
            User user = new User(profile);
            userRepo.save(user);

            return true;
        }
    }

    public List<User> userSearch(String[] filters) {
        ArrayList<User> returnList = new ArrayList<User>();
        // make sure no duplicate users are added
        HashSet<Integer> addedUsers = new HashSet<Integer>();

        if (filters.length == 0 || filters[0].equals("")) {
            returnList = (ArrayList<User>) userRepo.findAll();
        } else {
            for (String filter : filters) {
                for (User user : userRepo.findByProfilePreferredTimeContains(filter)) {
                    if (addedUsers.add(user.getUserID())) {
                        returnList.add(user);
                    }
                }
                for (User user : userRepo.findByProfileDescriptionContains(filter)) {
                    if (addedUsers.add(user.getUserID())) {
                        returnList.add(user);
                    }
                }
                for (User user : userRepo.findByProfileUsernameContains(filter)) {
                    if (addedUsers.add(user.getUserID())) {
                        returnList.add(user);
                    }
                }
            }
        }
        return returnList;
    }

    // method to recommend gamer based on game
    public List<User> recommendGamer(int UID) {
        ArrayList<User> returnList = new ArrayList<User>();
        User user = userRepo.findByUid(UID);
        List<Game> userGames = user.getProfile().getGames();
        HashSet<Integer> addedUsers = new HashSet<Integer>();

        for (Game game : userGames)
            for (User gamer : userRepo.findByProfileGamesContains(game))
                if (gamer.getUserID() != UID && addedUsers.add(gamer.getUserID()))
                    returnList.add(gamer);

        return returnList;
    }

}
