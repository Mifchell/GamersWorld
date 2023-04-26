package com.project.gamersworld.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.gamersworld.models.User;
import com.project.gamersworld.models.Game;

public interface UserRepo extends JpaRepository<User, Long> {

    public User findByProfileEmailAddress(String emailAddress);

    public User findByUid(int uid);

    public User findByProfileUsername(String username);

    public List<User> findByProfilePreferredTimeContains(String preferredTime);

    public List<User> findByProfileDescriptionContains(String description);

    public List<User> findByProfileUsernameContains(String username);

    public List<User> findByProfileGamesContains(Game preferredGame);
}
