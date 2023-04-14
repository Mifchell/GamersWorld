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

		// // Delete event from database and hope attendeelist dies with it
		// // Obviously don't have this running at the same time as the above code, this deletes events, and if they ran at the same time, you wouldn't even see any changes to the database
		// // Also, since we can't run above code and delete at same time, we can't keep track of the eventId without manually checking each time a new event is created
		// // You have to manually change this id for deleteEvent each time :(
		// eventHandler.deleteEvent(25);
	}

}
