package com.project.gamersworld;

import com.project.gamersworld.handlers.EventHandler;
import com.project.gamersworld.handlers.GroupHandler;
import com.project.gamersworld.models.*;
import com.project.gamersworld.repo.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

// import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class GamersWorldApplication implements CommandLineRunner {
	@Autowired
	private UserRepo userRepository;

	// @Autowired
	// private GroupRepo groupRepository;

	// @Autowired
	// private EventRepo eventRepository;

	@Autowired
	EventHandler eventHandler;

	@Autowired
	GroupHandler groupHandler;

	public static void main(String[] args) {
		SpringApplication.run(GamersWorldApplication.class, args);
	}

	@Override
	public void run(String... args) {
		User user1 = userRepository.findByProfileEmailAddress("test1@test.com");

		List<Event> events = eventHandler.eventSearch(user1);

		for (Event event : events) {
			System.out.println(event);
		}

		List<Group> groups = groupHandler.groupSearch("");
		for (Group group : groups) {
			System.out.println(group);
		}
	}

}
