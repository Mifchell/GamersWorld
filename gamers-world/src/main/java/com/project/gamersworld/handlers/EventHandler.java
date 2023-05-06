package com.project.gamersworld.handlers;

import com.project.gamersworld.models.*;
import com.project.gamersworld.repo.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public EventHandler(EventRepo eventRepository, UserRepo userRepo) {
        this.eventRepo = eventRepository;
        this.userRepo = userRepo;
    }

    public EventRepo getEventRepository() {
        return this.eventRepo;
    }

    /*
     * Does a event search based on user preference
     */
    public List<Event> eventSearch(User user) {

        List<Event> returnEvents = new ArrayList<Event>();
        List<Event> curEvents = null;
        List<Event> userEventList = null;
        List<Game> userGames = null;

        if (user.getEventList() != null)
            userEventList = user.getEventList();
        if (user.getProfile().getGames() != null)
            userGames = user.getProfile().getGames();

        if (userGames != null && !userGames.isEmpty()) {

            // match event game to user's games, if it's not already in there
            for (int i = 0; i < userGames.size(); i++) {
                // Multiple events can share the same game, so it can return 2+ events
                curEvents = eventRepo.findAllByGame(userGames.get(i));
                // Adds each event returned individually
                for (Event event : curEvents) {
                    if (event != null && !returnEvents.contains(event))
                        returnEvents.add(event);
                }
            }

            // if there are matched events, sort and return them
            if (!returnEvents.isEmpty()) {
                if (userEventList != null) {
                    for (Event events : userEventList) {
                        returnEvents.remove(events);
                    }
                }
                return sortEvents(returnEvents);
            }

        }

        // if there are no matched events after going thru, then return all events
        // sorted
        // OR if user does not have a game then return all sorted

        returnEvents = eventRepo.findAll();
        if (userEventList != null) {
            for (Event events : userEventList) {
                returnEvents.remove(events);
            }
        }

        return sortEvents(returnEvents);
    }

    public List<Event> myEvents(User user) {
        List<Event> myevents = new ArrayList<Event>();
        if (user.getEventList() != null) {
            myevents = user.getEventList();
        }
        return myevents;
    }

    public List<Event> eventOwned(User user) {
        List<Event> eventList = new ArrayList<Event>();
        for (Event event : myEvents(user)) {
            if (event.getCreatorID() == user.getUserID()) {
                eventList.add(event);
            }
        }
        return eventList;
    }

    /*
     * do a filter search on the repositary
     */
    public List<Event> filterEvent(String filter, User user) {
        Set<Event> returnList = new HashSet<Event>();

        if (filter.equals("")) {
            returnList = eventRepo.findAll().stream().collect(Collectors.toSet());
        } else {
            returnList.addAll(eventRepo.findByDescriptionContaining(filter));
            returnList.addAll(eventRepo.findByEventNameContaining(filter));
        }

        List<Event> userList = new ArrayList<Event>();
        if (user.getEventList() != null) {
            userList = user.getEventList();
        }

        for (Event events : userList) {
            returnList.remove(events);
        }

        return sortEvents(returnList.stream().collect(Collectors.toList()));
    }

    /*
     * Create an event
     */
    public boolean createEvent(String name, String date, String location, String description, String game,
            String playLevel, int user) {
        // check if event name is unique
        if (eventRepo.findByEventName(name) != null) {
            return false;
        }

        // Retreive Game and PlayLevel objects
        Game gameObject = Game.valueOf(game.toUpperCase());
        PlayLevel playLevelObject = PlayLevel.valueOf(playLevel.toUpperCase());
        // find active user info: id, name, object, idc
        User creator = new User(userRepo.findByUid(user));

        Event event = new Event(name, date, location, description, gameObject, playLevelObject, creator);
        // MUST add event to users eventlist and save both for many to many relation to
        // work
        creator.getEventList().add(event);
        eventRepo.save(event);
        userRepo.save(creator);
        return true;
    }

    /*
     * Edit event
     */
    public boolean editEvent(int ID, String name, String date, String location, String description, String game,
            String playLevel) {
        // check if event name is unique
        Event checkEvent = eventRepo.findByEventName(name);
        if ((checkEvent != null) && (checkEvent.getEventId() != ID)) {
            return false;
        }

        // Retreive Game and PlayLevel objects
        Game gameObject = Game.valueOf(game.toUpperCase());
        PlayLevel playLevelObject = PlayLevel.valueOf(playLevel.toUpperCase());
        Event oldVersion = new Event(eventRepo.findByEventId(ID));

        // Create new event based on updated data
        User creatoor = new User(userRepo.findByUid(oldVersion.getCreatorID()));
        Event updatedEvent = new Event(name, date, location, description, gameObject, playLevelObject,
                creatoor);
        // Keep same ID and set attendeelist and comments
        updatedEvent.setEventId(ID);
        // Comments and attendeelist not edited by creator, so retreived from old event
        // version
        updatedEvent.setAttendeeList(oldVersion.getAttendeeList());
        updatedEvent.setComments(oldVersion.getComments());
        eventRepo.save(updatedEvent);
        return true;
    }

    /*
     * Delete event from database
     */
    @Transactional // Don't remember why this was needed, but it stopped errors from occuring

    public boolean deleteEvent(int ID) {
        Event eventTemp = eventRepo.findByEventId(ID);
        if (eventTemp == null) {
            return false;
        }
        Event event = new Event(eventTemp);
        for (User attendee : event.getAttendeeList()) {
            for (Event event2 : attendee.getEventList()) {
                if (event2.getEventId() == ID) {
                    attendee.getEventList().remove(event2);
                    break;
                }
            }
            userRepo.save(attendee);
        }
        eventRepo.deleteByEventId(ID);
        return true;
    }

    /*
     * Mark user as attending event
     */
    public boolean RSVPEvent(int ID, int eventID) {
        // Retreive event and user and add each other to each other's lists
        Event temp = eventRepo.findByEventId(eventID);
        if (temp == null) {
            return false;
        }
        Event event = new Event(temp);
        List<User> attenList = event.getAttendeeList();
        for (User user : attenList) {
            if (user.getUserID() == ID) {
                return false;
            }
        }
        User user = new User(userRepo.findByUid(ID));
        attenList.add(user);
        event.setAttendeeList(attenList);
        user.getEventList().add(event);
        userRepo.save(user);
        eventRepo.save(event);
        return true;
    }

    /*
     * Clear all RSVP related to given user
     */
    public void ClearRSVP(int userId) {
        User user = new User(userRepo.findByUid(userId));
        user.getEventList().clear();
        userRepo.save(user);
    }

    /*
     * User leaves a comment on the post
     */
    public boolean commentEvent(String comment, int eventID) {
        // Retreive existing comment list
        Event temp = eventRepo.findByEventId(eventID);
        if (temp == null) {
            return false;
        }
        Event event = new Event(temp);
        List<String> comments = event.getComments();
        // Add comment to list and save
        comments.add(comment);
        event.setComments(comments);
        eventRepo.save(event);
        return true;
    }

    public List<Event> getEventsSorted() {
        List<Event> events = eventRepo.findAll();
        return sortEvents(events);
    }

    protected List<Event> sortEvents(List<Event> events) {
        if (events != null && !events.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            Comparator<Event> byDate = Comparator.comparing(event -> LocalDate.parse(event.getDate(), formatter));
            Collections.sort(events, byDate);
        }

        return events;
    }
}
