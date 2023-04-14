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

		// Event event1 = new Event(eventRepository.findByEventId(7));

		// List<String> comments = event1.getComments();
		// comments.add("agdftef");
		// event1.setComments(comments);

		// eventRepository.save(event1);

		//New Event
		int newEventId = eventHandler.createEvent("event3", "today", "here", "yes", "minecraft", "expert", 2);
		Event eventTest = new Event(eventRepository.findByEventId(newEventId));
		System.out.println(eventTest);
		System.out.println(eventTest.getDate());
		System.out.println(eventTest.getAttendeeList());

		//Adds another user to attendeeList
		User user = new User(userRepository.findByUid(1));
		eventHandler.RSVPEvent(user.getUserID(), newEventId);
		Event eventTest2 = new Event(eventRepository.findByEventId(newEventId));
		System.out.println(eventTest2.getAttendeeList());

		//Tests EditEvent...
		eventHandler.editEvent(newEventId, "event4", eventTest.getDate(), eventTest.getLocation(), eventTest.getDescription(), eventTest.getGame().toString(), eventTest.getPlayLevel().toString());
		Event eventTest3 = new Event(eventRepository.findByEventId(newEventId));
		System.out.println(eventTest3);
		System.out.println(eventTest3.getDate());
		System.out.println(eventTest3.getAttendeeList());

		// Event random = new Event("idk","yesterday", "bed", "idfk", Game.MINECRAFT, PlayLevel.CASUAL, userRepository.findByUid(2));
		// System.out.println(random);
		// System.out.println(random.getDate());
		// System.out.println(random.getAttendeeList().get(0));
		// eventRepository.save(random);
		// Event random2 = new Event(eventRepository.findByEventName("idk"));
		// System.out.println(random2);
		// System.out.println(random2.getDate());
		// //System.out.println(random2.getAttendeeList().get(0));

		
	}

}
