package com.project.gamersworld.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import com.project.gamersworld.models.Event;
import com.project.gamersworld.models.Game;
import com.project.gamersworld.models.PlayLevel;
import com.project.gamersworld.models.Profile;
import com.project.gamersworld.models.User;
import com.project.gamersworld.repo.EventRepo;
import com.project.gamersworld.repo.UserRepo;

public class EventHandlerTest {

    @Mock
    private static EventRepo mockEventRepository;
    @Mock
    private static UserRepo mockUserRepository;
    @InjectMocks
    private static EventHandler eventHandler;

    private List<Event> events;
    private User user1;
    private List<Event> result;
    ArrayList<Event> list1 = new ArrayList<>();

    // test - sort events
    @BeforeEach
    void setUp()
    {
        mockEventRepository = Mockito.mock(EventRepo.class);
        mockUserRepository = Mockito.mock(UserRepo.class);
        eventHandler = new EventHandler(mockEventRepository, mockUserRepository);
        events = new ArrayList<Event>();
        result = new ArrayList<Event>();
        Profile profile = new Profile("user1", "1234", "test@test.com", "", "");
        user1 = new User(profile);

        Event event1 = new Event("", "12/24/2023", "", "", Game.ACROSS_THE_OBELISK, PlayLevel.CASUAL, user1);
        Event event2 = new Event("", "12/24/2023", "", "", Game.ACROSS_THE_OBELISK, PlayLevel.CASUAL, user1);

        list1.add(event1);
    }

    @Test
    void testUnSortedEvents() {
        events.add(new Event("", "12/24/2023", "", "", Game.ACROSS_THE_OBELISK, PlayLevel.CASUAL, user1));
        events.add(new Event("", "12/24/2024", "", "", Game.ACROSS_THE_OBELISK, PlayLevel.CASUAL, user1));
        events.add(new Event("", "12/25/2023", "", "", Game.ACROSS_THE_OBELISK, PlayLevel.CASUAL, user1));
        events.add(new Event("", "10/25/2020", "", "", Game.ACROSS_THE_OBELISK, PlayLevel.CASUAL, user1));


        result.add(new Event("", "10/25/2020", "", "", Game.ACROSS_THE_OBELISK, PlayLevel.CASUAL, user1));
        result.add(new Event("", "12/24/2023", "", "", Game.ACROSS_THE_OBELISK, PlayLevel.CASUAL, user1));
        result.add(new Event("", "12/25/2023", "", "", Game.ACROSS_THE_OBELISK, PlayLevel.CASUAL, user1));
        result.add(new Event("", "12/24/2024", "", "", Game.ACROSS_THE_OBELISK, PlayLevel.CASUAL, user1));

        events = eventHandler.sortEvents(events);

        assertEquals(events, result);
    }

    // test - eventSearch
    @Test
    void testEventSearchHappyPath() {
        when(mockEventRepository.findAllByGame(user1.getGame())).thenReturn(list2);
        when(mockEventRepository.findByNameContaining("p1")).thenReturn(list1);

        List<Event> events = eventHandler.eventSearch(user1);

        assertEquals(events, list1); 
    }

    @Test
    void testEventSearchNoMatch() {
        when(mockGroupRepository.findByDescriptionContaining("fef")).thenReturn(new ArrayList<Group>());
        when(mockGroupRepository.findByNameContaining("fef")).thenReturn(new ArrayList<Group>());

        List<Group> groups = groupHandler.groupSearch("fef");

        assertEquals(groups, list3); 
    }



}

