package com.project.gamersworld.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

import com.project.gamersworld.models.Game;
import com.project.gamersworld.models.Profile;
import com.project.gamersworld.models.Message;
import com.project.gamersworld.models.User;
import com.project.gamersworld.repo.UserRepo;

@Service
public class UserHandler {
    @Autowired
    private UserRepo userRepo;

    public UserHandler(UserRepo userRepository) {
        this.userRepo = userRepository;
    }

    public User login(String email, String password) {
        // if user exists, check if password matches saved password
        if (userRepo.findByProfileEmailAddress(email) != null) {
            User user = new User(userRepo.findByProfileEmailAddress(email));
            if (user.getProfile().getPassword().equals(password)) {
                return user;
            }
        }

        return null;
    }

    public User signUp(String email, String password) {
        // check if there is already an existing email
        if (userRepo.findByProfileEmailAddress(email) != null) {
            return null;
        } else {
            // create new profile and user
            Profile profile = new Profile("", password, email, "", "");
            User user = new User(profile);
            userRepo.save(user);
            return user;
        }
    }

    public boolean deleteAccount(User user) {
        if (user != null) {
            userRepo.delete(user);
            return true;
        } else {
            return false;
        }
    }

    public List<User> userSearch(String filter) {
        ArrayList<User> returnList = new ArrayList<User>();
        // make sure no duplicate users are added
        HashSet<Integer> addedUsers = new HashSet<Integer>();

        if (filter.equals("")) {
            returnList = (ArrayList<User>) userRepo.findAll();

        } else {
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

    public boolean createProfile(User user, String username, String description, String preferredTime,
            List<Game> selectedGames) {
        // check if username is unique
        if (userRepo.findByProfileUsername(username) != null) {
            return false;
        }

        user.getProfile().setUsername(username);
        user.getProfile().setDescription(description);
        user.getProfile().setTime(preferredTime);

        user.getProfile().setGames(selectedGames);

        // save all
        userRepo.save(user);

        return true;
    }

    public boolean editProfile(User user, String username, String description, String preferredTime,
            List<Game> selectedGames, String email, String password) {

        if ((userRepo.findByProfileUsername(username) != null && !userRepo.findByProfileUsername(username).equals(user))
                || ((userRepo.findByProfileEmailAddress(email) != null)
                        && !userRepo.findByProfileEmailAddress(email).equals(user))) {
            return false;
        }

        user.getProfile().setUsername(username); // username has to be unique
        user.getProfile().setDescription(description);
        user.getProfile().setTime(preferredTime);
        user.getProfile().setEmail(email);
        user.getProfile().setPassword(password);
        user.getProfile().setGames(selectedGames);

        // save all
        userRepo.save(user);

        return true;
    }

    public List<Game> displayTrends() {
        List<Game> games = new ArrayList<>();
        HashMap<Game, Integer> numGames = new HashMap<>();
        for (User user : userRepo.findAll()) {
            for (Game game : user.getProfile().getGames()) {
                if (numGames.containsKey(game)) {
                    numGames.put(game, numGames.get(game) + 1);
                } else {
                    numGames.put(game, 1);
                }
            }
        }

        List<HashMap.Entry<Game, Integer>> gamesSorted = new ArrayList<>(numGames.entrySet());
        gamesSorted.sort(Collections.reverseOrder(HashMap.Entry.comparingByValue()));

        int counter = 0;
        for (HashMap.Entry<Game, Integer> entry : gamesSorted) {
            games.add(entry.getKey());
            counter++;
            if (counter == 3) {
                break;
            }
        }

        return games;
    }

    public List<Message> getConversation(int UID, int otherUID) {
        User user = userRepo.findByUid(UID);
        List<Message> finalMessages = new ArrayList<Message>();

        for (Message m : user.getSentMessages())
            for (User u : m.getRecievers())
                if (u.getUserID() == otherUID && m.getGroupID() == -1)
                    finalMessages.add(m);
        for (Message m : user.getReceivedMessages())
            if (m.getSender().getUserID() == otherUID && m.getGroupID() == -1)
                finalMessages.add(m);

        // sort here
        Comparator<Message> comp = new Comparator<Message>() {
            public int compare(Message m1, Message m2) {
                return m1.getDate().compareTo(m2.getDate());
            }
        };
        Collections.sort(finalMessages, comp);
        // end sort

        return finalMessages;
    }

    public List<Message> getGroupConversation(int UID, int groupID) {
        User user = userRepo.findByUid(UID);
        List<Message> messageList = new ArrayList<Message>();

        for (Message m : user.getSentMessages())
            if (m.getGroupID() == groupID)
                messageList.add(m);
        for (Message m : user.getReceivedMessages())
            if (m.getGroupID() == groupID)
                messageList.add(m);

        Comparator<Message> comp = new Comparator<Message>() {
            public int compare(Message m1, Message m2) {
                return m1.getDate().compareTo(m2.getDate());
            }
        };
        Collections.sort(messageList, comp);
        return messageList;
    }

    public UserRepo getUserRepo() {
        return userRepo;
    }
}
