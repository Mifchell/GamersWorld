package com.project.gamersworld;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

    public List<User> findByProfileEmailAddress(String emailAddress);

    public User findByUid(int uid);
}
