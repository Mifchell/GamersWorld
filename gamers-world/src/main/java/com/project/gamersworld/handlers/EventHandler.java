package com.project.gamersworld.handlers;

import com.project.gamersworld.models.*;
import com.project.gamersworld.repo.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
                    if (events.get(i).getGame() != null && events.get(i).getGame().equals(user.getProfile().getGames().get(j))) {
                        // store event in return list if not there
                        if (!returnEvents.contains(events.get(i)))
                            returnEvents.add(events.get(i));
                    }
                }
            }
        }

        // check if there are matched events, sort, then return
        if (!returnEvents.isEmpty())
        {
            return sortEvents(returnEvents);
        }
        else{
            // return all events sorted chronologically
            return sortEvents(events);
        }

    }

    /*
     * do a filter search on the repositary
     */
    public List<Event> filterEvent(String filter) {
        List<Event> returnList = new ArrayList<Event>();
        
        if (filter.equals("")) {
            returnList = eventRepo.findAll();
        } else {
            returnList.addAll(eventRepo.findByDescriptionContaining(filter));
            returnList.addAll(eventRepo.findByEventNameContaining(filter));
        }

        return sortEvents(returnList);
    }

    /*
     * Create an event
     */
    public int createEvent(String name, String date, String location, String description, String game, String playLevel, int user) {
        //Retreive Game and PlayLevel objects
        Game gameObject = Game.valueOf(game.toUpperCase());
        PlayLevel playLevelObject = PlayLevel.valueOf(playLevel.toUpperCase());
        //find active user info: id, name, object, idc
        User creator = new User(userRepo.findByUid(user));
        //create new attendeelist to quickly add to created event
        List<User> attendeeList1 = new ArrayList<User>();
        attendeeList1.add(creator);
        Event event = new Event(name, date, location, description, gameObject, playLevelObject, creator);
        event.setAttendeeList(attendeeList1);
        //MUST add event to users eventlist and save both for many to many relation to work
        creator.getEventList().add(event);
        eventRepo.save(event);
        userRepo.save(creator);
        return event.getEventId();
    }

    /*
     * Edit event 
     */
    public void editEvent(int ID, String name, String date, String location, String description, String game, String playLevel) {
        //Retreive Game and PlayLevel objects
        Game gameObject = Game.valueOf(game.toUpperCase());
        PlayLevel playLevelObject = PlayLevel.valueOf(playLevel.toUpperCase());
        Event oldVersion = new Event(eventRepo.findByEventId(ID));
        //Create new event based on updated data
        Event updatedEvent = new Event(name, date, location, description, gameObject, playLevelObject, oldVersion.getAttendeeList().get(0));
        //Keep same ID and set attendeelist and comments
        updatedEvent.setEventId(ID);
        //Comments and attendeelist not edited by creator, so retreived from old event version
        updatedEvent.setAttendeeList(oldVersion.getAttendeeList());
        updatedEvent.setComments(oldVersion.getComments());
        eventRepo.save(updatedEvent);
    }

    /*
     * Delete event from database
     */
    @Transactional // Don't remember why this was needed, but it stopped errors from occuring
    public void deleteEvent(int ID) {
        Event event = new Event(eventRepo.findByEventId(ID));
        for(User attendee: event.getAttendeeList()) {
            for(Event event2: attendee.getEventList()) {
                if(event2.getEventId() == ID){
                    attendee.getEventList().remove(ID);
                    break;
                }
            }
            userRepo.save(attendee);
        }
        eventRepo.deleteByEventId(ID);
    }

    /*
     * Mark user as attending event
     */
    public void RSVPEvent(int ID, int eventID){
        //Retreive event and user and add each other to each other's lists
        Event event = new Event(eventRepo.findByEventId(eventID));
		List<User> attenList = event.getAttendeeList();
        User user = new User(userRepo.findByUid(ID));
		attenList.add(user);
		event.setAttendeeList(attenList);
        user.getEventList().add(event);
        userRepo.save(user);
        eventRepo.save(event);
    }

    /*
     * User leaves a comment on the post
     */
    public void commentEvent(String comment, int eventID){
        // Retreive existing comment list
        Event event = new Event(eventRepo.findByEventId(eventID));
		List<String> comments = event.getComments();
        // Add comment to list and save
		comments.add(comment);
		event.setComments(comments);
		eventRepo.save(event);
    }


    public List<Event> getEventsSorted()
    {
        List<Event> events = eventRepo.findAll();
        return sortEvents(events);
    }

    private List<Event> sortEvents(List<Event> events)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        Comparator<Event> byDate = Comparator.comparing(event -> LocalDate.parse(event.getDate(), formatter));
        Collections.sort(events, byDate);

        return events;
    }
}
