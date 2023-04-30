package com.project.gamersworld.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.gamersworld.handlers.EventHandler;
import com.project.gamersworld.models.User;
import com.project.gamersworld.models.Game;
import com.project.gamersworld.repo.EventRepo;

@Controller
public class EventController {

    @Autowired
    UserController userController;

    @Autowired
    EventHandler eventHandler;

    @Autowired
    EventRepo eventRepo;

    // RSVP to event
    @PostMapping("event/rsvp/{eventId}")
    @ResponseBody
    public void RSVP(@PathVariable("eventId") int id, HttpServletRequest request) {
        User user = userController.retrieveCurrentUser(request);
        eventHandler.RSVPEvent(user.getUserID(), id);
    }

    // Go to edit event page
    @PostMapping("event/editEvent")
    public String editEvent(@RequestParam(value = "id") int Id, Model model) {
        model.addAttribute("event", eventRepo.findByEventId(Id));
        return "editevent";
    }

    @PostMapping("/events")
    public String filterEvents(@RequestParam(value = "filter") String filter, Model model, HttpServletRequest request) {
            model.addAttribute("events", eventHandler.filterEvent(filter, userController.retrieveCurrentUser(request)));
            return "events";
    }

    // Submit event changes
    @GetMapping("/event/editedEvent")
    public String editedEvent(@RequestParam(value = "id") int id, @RequestParam(value = "name") String name, @RequestParam(value = "date") String date, @RequestParam(value = "location") String location, @RequestParam(value = "game") String game, @RequestParam(value = "level") String level, @RequestParam(value = "desc") String desc) {
        eventHandler.editEvent(id, name, date, location, desc, game, level);
        return "redirect:/profile";
    }

    // Remove event from database
    @GetMapping("event/deleteEvent/{eventId}")
    public String deleteEvent(@PathVariable("eventId") int id) {
        eventHandler.deleteEvent(id);
        return "redirect:/profile";
    }

    // Go to new event page
    @GetMapping("event/newEvent/")
    public String viewCreateEvent() {
        return "createevent";
    }

    // Create new event
    @PostMapping("/createevent")
    public String createEvent(HttpServletRequest request, @RequestParam(value = "name") String name, @RequestParam(value = "date") String date, @RequestParam(value = "location") String location, @RequestParam(name = "selectedGame", required = false) List<Game> selectedGame, @RequestParam(value = "level") String level, @RequestParam(value = "desc") String desc){
        int user = userController.retrieveCurrentUser(request).getUserID();
        eventHandler.createEvent(name, date, location, desc, selectedGame.get(0).toString(), level, user);
        return "redirect:/events";
    }

}
