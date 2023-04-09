package com.project.gamersworld;

import com.project.gamersworld.handlers.EventHandler;
import com.project.gamersworld.handlers.GroupHandler;
import com.project.gamersworld.models.*;
import com.project.gamersworld.repo.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

// import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class GamersWorldApplication implements CommandLineRunner {
	// @Autowired
	// private UserRepo userRepository;

	// @Autowired
	// private GroupRepo groupRepository;

	@Autowired
	private EventRepo eventRepository;

	@Autowired
	EventHandler eventHandler;

	@Autowired
	GroupHandler groupHandler;

	public static void main(String[] args) {
		SpringApplication.run(GamersWorldApplication.class, args);
	}

	@Override
	public void run(String... args) {

		Event event1 = new Event(eventRepository.findByEventId(7));

		List<String> comments = event1.getComments();
		comments.add("agdftef");
		event1.setComments(comments);

		eventRepository.save(event1);

	}

}
