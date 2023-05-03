package com.project.gamersworld;

import com.project.gamersworld.handlers.EventHandler;
import com.project.gamersworld.handlers.UserHandler;
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
	UserRepo userRepo;

	public static void main(String[] args) {
		SpringApplication.run(GamersWorldApplication.class, args);
	}

	@Override
	public void run(String... args) {

		List<User> users = userHandler.userSearch(new String[] { "test" });
		for (User user : users) {
			System.out.println(user);
		}
	}

}
