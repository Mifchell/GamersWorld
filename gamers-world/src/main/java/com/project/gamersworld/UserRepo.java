package com.project.gamersworld;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

    public User findByProfileEmailAddress(String emailAddress);

    public User findByUid(int uid);

}
