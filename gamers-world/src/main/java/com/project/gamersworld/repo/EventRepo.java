package com.project.gamersworld.repo;

import com.project.gamersworld.models.Event;
import com.project.gamersworld.models.Game;
import com.project.gamersworld.models.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepo extends JpaRepository<Event, Long> {

    public Event findByEventId(int eventId);

    public Event findByEventName(String eventName);

    public Event findByGame(Game game);

    public List<Event> findAllByGame(Game game);

    public List<Event> findByDescriptionContaining(String description);

    public List<Event> findByEventNameContaining(String name);

    public List<Event> findByAttendeeList(User user);

    public void deleteByEventId(int eventId);
}
