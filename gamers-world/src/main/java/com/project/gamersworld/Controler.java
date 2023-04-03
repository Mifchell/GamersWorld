package com.project.gamersworld;

import org.springframework.beans.factory.annotation.Autowired;

public class Controler {

    @Autowired
    private UserRepo userRepository;

    public void test() {
        // Insert a new user in the database
        Profile profile = new Profile("test", "test1234", "test@test.com", "", "");
        User user = new User(profile);

        userRepository.save(user);

        System.out.println(userRepository.findByEmail("test@test.com"));
    }
}
