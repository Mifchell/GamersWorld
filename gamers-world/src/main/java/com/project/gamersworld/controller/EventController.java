package com.project.gamersworld.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.gamersworld.handlers.EventHandler;
import com.project.gamersworld.models.User;

@Controller
public class EventController {

    @Autowired
    UserController userController;

    @Autowired
    EventHandler eventHandler;

    @RequestMapping(value = "event/rsvp/{eventId}", method = RequestMethod.POST)
    @ResponseBody
    public void RSVP(@PathVariable("eventId") int id, HttpServletRequest request) {
        User user = userController.retrieveCurrentUser(request);
        eventHandler.RSVPEvent(user.getUserID(), id);
    }

}
