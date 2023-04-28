package com.project.gamersworld;

import com.project.gamersworld.handlers.EventHandler;
import com.project.gamersworld.handlers.FriendHandler;
import com.project.gamersworld.models.User;
import com.project.gamersworld.repo.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class GamersWorldApplication implements CommandLineRunner {
	@Autowired
	FriendHandler handler;

	@Autowired
	UserRepo userRepo;

	public static void main(String[] args) {
		SpringApplication.run(GamersWorldApplication.class, args);
	}

	@Override
	public void run(String... args) {

		handler.removeFriend(1, 3);

	}

}
