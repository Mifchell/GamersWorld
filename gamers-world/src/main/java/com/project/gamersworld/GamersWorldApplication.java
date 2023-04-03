package com.project.gamersworld;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import antlr.collections.List;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class GamersWorldApplication implements CommandLineRunner {
	@Autowired
	private UserRepo userRepository;

	public static void main(String[] args) {
		SpringApplication.run(GamersWorldApplication.class, args);
	}

	@Override
	public void run(String... args) {

		// Insert a new user in the database
		Profile profile = new Profile("test", "test1234", "test@test.com", "", "");
		User user = new User(profile);

		userRepository.save(user);

		System.out.println("saved");

		System.out.println(userRepository.findByUid(1));

		ArrayList<User> users = (ArrayList<User>) (userRepository.findByProfileEmailAddress("test@test.com"));

		for (int i = 0; i < users.size(); i++) {
			System.out.println(users.get(i));
		}

		System.out.println("query");

	}

}
