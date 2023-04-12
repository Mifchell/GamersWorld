package com.project.gamersworld.handlers;

import com.project.gamersworld.models.*;
import com.project.gamersworld.repo.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventHandler {
    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private UserRepo userRepo;

    public EventHandler() {

    }

    public EventRepo getEventRepository() {
        return this.eventRepo;
    }

    /*
     * Does a event search based on user preference
     */
    public List<Event> eventSearch(User user) {
        // all events
        List<Event> events = eventRepo.findAll();
        // create empty events list
        List<Event> returnEvents = new ArrayList<Event>();

        // check if user has a preference
        if (!user.getProfile().getGames().isEmpty()) {
            // retrieve events with matching user preferences
            for (int i = 0; i < events.size(); i++) {
                for (int j = 0; j < user.getProfile().getGames().size(); j++) {
                    if (events.get(i).getGame().equals(user.getProfile().getGames().get(i))) {
                        // store event in return list
                        returnEvents.add(events.get(i));
                    }
                }
            }
        } else {
            // retrieve all events sorted chronologically
            returnEvents = events;

        }

        return events;
    }

    /*
     * do a filter search on the repositary
     */
    public void filterEvent() {

    }

    /*
     * Create an event
     */
    public int createEvent(String name, String date, String location, String description, String game, String playLevel) {
        //Retreive Game and PlayLevel objects
        Game gameObject = Game.valueOf(game.toUpperCase());
        PlayLevel playLevelObject = PlayLevel.valueOf(playLevel.toUpperCase());
        //find active user info: id, name, object, idc
        User creator = new User(userRepo.findByUid(2));
        Event event = new Event(name, date, location, description, gameObject, playLevelObject, creator);
        eventRepo.save(event);
        return event.getEventId();
    }

    /*
     * Edit event 
     */
    public void editEvent(int ID, String name, String date, String location, String description, String game, String playLevel,
    List<String> comments, List<User> attendeeList) {
        //Retreive Game and PlayLevel objects
        Game gameObject = Game.valueOf(game.toUpperCase());
        PlayLevel playLevelObject = PlayLevel.valueOf(playLevel.toUpperCase());
        //Create new event based on updated data
        Event updatedEvent = new Event(name, date, location, description, gameObject, playLevelObject, eventRepo.findByEventId(ID).getAttendeeList().get(0));
        //Keep same ID and set attendeelist and comments
        updatedEvent.setEventId(ID);
        updatedEvent.setAttendeeList(attendeeList);
        updatedEvent.setComments(comments);
        eventRepo.save(updatedEvent);
    }

    /*
     * Delete event from database
     */
    @Transactional
    public void deleteEvent(int ID) {
        eventRepo.deleteByEventId(ID);
    }

    /*
     * Mark user as attending event
     */
    public void RSVPEvent(int ID, int eventID){
        //Retreive event and add user to event's attendee list
        Event event = new Event(eventRepo.findByEventId(eventID));
		List<User> attenList = event.getAttendeeList();
		attenList.add(userRepo.findByUid(ID));
		event.setAttendeeList(attenList);
        eventRepo.save(event);
    }
}
