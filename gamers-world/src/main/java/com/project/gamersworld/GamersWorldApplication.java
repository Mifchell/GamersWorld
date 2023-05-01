package com.project.gamersworld;

import com.project.gamersworld.handlers.FriendHandler;
import com.project.gamersworld.models.Message;
import com.project.gamersworld.models.User;
import com.project.gamersworld.repo.*;

import java.time.LocalDateTime;
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
	MessageRepo mRepo;

	public static void main(String[] args) {
		SpringApplication.run(GamersWorldApplication.class, args);
	}

	@Override
	public void run(String... args) {
		List<User> list = mRepo.findByMessageID(1).getRecievers();

		for(User u: list)
			System.out.println(u.getUserID());
		System.out.println(mRepo.findByMessageID(1).getSender().getUserID());
	}

}
