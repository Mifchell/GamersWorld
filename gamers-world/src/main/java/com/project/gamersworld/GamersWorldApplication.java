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

		// int newEventId = eventHandler.createEvent("event3", "today", "here", "yes", "minecraft", "expert");
		// Event eventTest = new Event(eventRepository.findByEventId(newEventId));
		// System.out.println(eventTest);
		// System.out.println(eventTest.getDate());

		// eventHandler.deleteEvent(40);

		// Event random = new Event("idk","yesterday", "bed", "idfk", Game.MINECRAFT, PlayLevel.CASUAL, userRepository.findByUid(2));
		// System.out.println(random);
		// System.out.println(random.getDate());
		// System.out.println(random.getAttendeeList().get(0));
		// eventRepository.save(random);
		// Event random2 = new Event(eventRepository.findByEventName("idk"));
		// System.out.println(random2);
		// System.out.println(random2.getDate());
		// //System.out.println(random2.getAttendeeList().get(0));

		// User user = new User(userRepository.findByUid(1));
		// eventHandler.RSVPEvent(user.getUserID(), eventTest.getEventId());
		// Event eventTest2 = new Event(eventRepository.findByEventId(eventTest.getEventId()));
		// for(User attendee: eventTest2.getAttendeeList()) {
		// 	System.out.println(attendee);
		// }

		// eventHandler.editEvent(newEventId, "event4", eventTest2.getDate(), eventTest2.getLocation(), eventTest2.getDescription(), eventTest2.getGame().toString(), eventTest2.getPlayLevel().toString(), eventTest2.getComments(), eventTest2.getAttendeeList());
		// Event eventTest3 = new Event(eventRepository.findByEventId(newEventId));
		// System.out.println(eventTest3);
	}

}
