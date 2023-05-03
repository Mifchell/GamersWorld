package com.project.gamersworld;

import com.project.gamersworld.handlers.*;
import com.project.gamersworld.repo.*;
import com.project.gamersworld.models.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class GamersWorldApplication implements CommandLineRunner {
	@Autowired
	EventHandler eventHandler;

	@Autowired
	UserHandler userHandler;

	@Autowired
	GroupHandler groupHandler;

	@Autowired
	UserRepo userRepo;

	@Autowired
	GroupRepo groupRepo;

	public static void main(String[] args) {
		SpringApplication.run(GamersWorldApplication.class, args);
	}

	@Override
	public void run(String... args) {

		List<User> users = userHandler.userSearch(new String[] { "test" });
		for (User user : users) {
			System.out.println(user);
		}
		User user = userRepo.findByProfileUsername("test");
		groupHandler.createGroup("test", "test", )

		User user = userRepo.findByProfileUsername("test");
		Group group = groupRepo.findByGroupID(5);
		List<User> userList = users;
		for (User u : userList)
			groupHandler.join(5, u);

		groupHandler.deleteGroup(5);

		for (User u : userList)
			System.out.println(u.getGroupList());

	}

}
