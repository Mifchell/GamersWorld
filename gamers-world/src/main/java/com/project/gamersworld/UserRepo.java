package com.project.gamersworld;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

    public User findByProfileEmailAddress(String emailAddress);

    public User findByUid(int uid);

    public List<User> findByProfilePreferredTimeContains(String preferredTime);
    

    //list of other users that blocked the user
    public List<User> findAllByBlockedUsers(User user);

}
