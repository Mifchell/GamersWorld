package com.project.gamersworld;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

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

	public static void main(String[] args) {
		SpringApplication.run(GamersWorldApplication.class, args);
	}

	@Override
	public void run(String... args) {

		// // setting up new users
		// Profile profile1 = new Profile("test1", "1234", "test1@test.com", "", "");
		// Profile profile2 = new Profile("test2", "1234", "test2@test.com", "", "");
		// Profile profile3 = new Profile("test3", "1234", "test3@test.com", "", "");
		// Profile profile4 = new Profile("test4", "1234", "test4@test.com", "", "");

		// User user1 = new User(profile1);
		// User user2 = new User(profile2);
		// User user3 = new User(profile3);
		// User user4 = new User(profile4);

		// userRepository.save(user1);
		// userRepository.save(user2);
		// userRepository.save(user3);
		// userRepository.save(user4);

		// // friendship creation
		// // user1 frinds with user 2 and 3

		User user1 = new User(userRepository.findByProfileEmailAddress("test1@test.com"));
		User user2 = new User(userRepository.findByProfileEmailAddress("test2@test.com"));
		User user3 = new User(userRepository.findByProfileEmailAddress("test3@test.com"));
		User user4 = new User(userRepository.findByProfileEmailAddress("test4@test.com"));

		user1.profile.setGames(new ArrayList<Game>());
		user2.profile.setGames(new ArrayList<Game>());
		user3.profile.setGames(new ArrayList<Game>());
		user4.profile.setGames(new ArrayList<Game>());

		ArrayList<Game> games = (ArrayList<Game>) user1.profile.getGames();
		games.add(Game.FORTNITE);
		games.add(Game.MINECRAFT);

		user1.profile.setGames(games);
		user2.profile.setGames(games);

		user1.friendsList.add(user2);
		user1.friendsList.add(user3);
		user2.friendsList.add(user1);
		user3.friendsList.add(user1);

		userRepository.save(user1);
		userRepository.save(user2);
		userRepository.save(user3);
		userRepository.save(user4);

		// user4 is blocked by user 1
		// user1 = new User(userRepository.findByProfileEmailAddress("test1@test.com"));
		// user2 = new User(userRepository.findByProfileEmailAddress("test2@test.com"));
		// user1.blockedUsers.add(user4);
		// user2.blockedUsers.add(user4);

		// userRepository.save(user1);
		// userRepository.save(user2);

		// user1 creates a group
		// Group group = new Group("group1", user1, "");

		// groupRepository.save(group);

		// // user2 join group
		// group = new Group(group);
		// group.members.add(user2);

		// groupRepository.save(group);

		// // user3 and user 2 cerate an event
		// PlayLevel playLevel = PlayLevel.CASUAL;
		// Game game = Game.MINECRAFT;

		// Event event1 = new Event("event1", "", "", null, game, playLevel, user3);
		// Event event2 = new Event("event2", "", "", null, game, playLevel, user2);

		// eventRepository.save(event1);
		// eventRepository.save(event2);

		// // user2 attend event
		// event1 = new Event(eventRepository.findByEventName("event1"));
		// List<User> attenList = event1.getAttendeeList();
		// attenList.add(user2);
		// event1.setAttendeeList(attenList);

	}

}
