package com.project.gamersworld;

import com.project.gamersworld.handlers.EventHandler;
import com.project.gamersworld.handlers.FriendHandler;
import com.project.gamersworld.models.User;
import com.project.gamersworld.repo.*;

import java.util.List;

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

		User u1 = userRepo.findByUid(1);

		if(u1.blockedUsers.isEmpty())
			System.out.println("True");
		for(User u : u1.getBlockedUsers())
			System.out.println(u.getUserID());

		System.out.println("HERE_______________");

	}

}
