package com.project.gamersworld;

import com.project.gamersworld.handlers.EventHandler;
import com.project.gamersworld.handlers.GroupHandler;
import com.project.gamersworld.models.*;
import com.project.gamersworld.repo.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class GamersWorldApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(GamersWorldApplication.class, args);
	}

	@Override
	public void run(String... args) {

	}

}
