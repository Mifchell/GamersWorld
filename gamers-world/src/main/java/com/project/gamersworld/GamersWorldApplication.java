package com.project.gamersworld;

import com.project.gamersworld.handlers.EventHandler;
import com.project.gamersworld.handlers.GroupHandler;
import com.project.gamersworld.models.*;
import com.project.gamersworld.repo.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class GamersWorldApplication implements CommandLineRunner {
	@Autowired
	EventHandler eventHandler;

	@Autowired
	UserRepo userRepo;

	public static void main(String[] args) {
		SpringApplication.run(GamersWorldApplication.class, args);
	}

	@Override
	public void run(String... args){

		// // test with user1
		// User user = new User(userRepo.findByProfileEmailAddress("test1@test.com"));

		// List<Event> events = eventHandler.eventSearch(user);



		// for (Event event : events)
		// {
		// 	System.out.println(event);
		// 	System.out.println("Date: " + event.getDate());
		// 	System.out.println(event.getGame().name());
		// }

	}

}
