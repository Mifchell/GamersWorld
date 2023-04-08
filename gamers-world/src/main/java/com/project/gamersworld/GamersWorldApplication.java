package com.project.gamersworld;

import com.project.gamersworld.handlers.EventHandler;
import com.project.gamersworld.repo.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

	public static void main(String[] args) {
		SpringApplication.run(GamersWorldApplication.class, args);
	}

	@Override
	public void run(String... args) {
		System.out.println(userRepository.findAll());

	}

}
