package com.project.gamersworld.handlers;

import com.project.gamersworld.models.*;
import com.project.gamersworld.repo.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventHandler {
    @Autowired
    private EventRepo eventRepo;

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
                for (int j = 0; j < user.getProfile().getGames().size()-1; j++) {
                    if (events.get(i).getGame().equals(user.getProfile().getGames().get(i))) {
                        // store event in return list if not there
                        if (!returnEvents.contains(events.get(i)))
                            returnEvents.add(events.get(i));
                    }
                }
            }
        } else {
            // retrieve all events sorted chronologically
            returnEvents = events;

        }

        return returnEvents;
    }

    /*
     * do a filter search on the repositary
     */
    public void filterEvent() {

    }
}
