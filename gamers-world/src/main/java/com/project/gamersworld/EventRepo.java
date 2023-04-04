package com.project.gamersworld;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepo extends JpaRepository<Event, Long> {

    public Event findByEventId(int eventId);

    public Event findByEventName(String eventName);

    List<Event> findByAttendeeList(User user);

}
