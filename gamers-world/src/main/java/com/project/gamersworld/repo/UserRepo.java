package com.project.gamersworld.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.gamersworld.models.User;

public interface UserRepo extends JpaRepository<User, Long> {

    public User findByProfileEmailAddress(String emailAddress);

    public User findByUid(int uid);

    public List<User> findByProfilePreferredTimeContains(String preferredTime);

}
