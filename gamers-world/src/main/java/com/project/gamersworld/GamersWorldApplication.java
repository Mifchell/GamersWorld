package com.project.gamersworld;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

		System.out.println(userRepository.findByEmail("test@test.com"));
	}

}
