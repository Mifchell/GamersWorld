package com.project.gamersworld.handlers;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
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

    private Event event1;
    private Event event2;
    private Event event3;
    private Event event4;

    private Event event5;
    private Event event6;
    private Event event7;

    private List<Event> events;
    private List<Event> events1;
    private List<Event> events2;
    private List<Event> events3;
    private User user1;
    private User user2;
    private List<Event> result;

    // test - sort events
    @BeforeEach
    void setUp()
    {
        // set up mocks
        mockEventRepository = Mockito.mock(EventRepo.class);
        mockUserRepository = Mockito.mock(UserRepo.class);
        eventHandler = new EventHandler(mockEventRepository, mockUserRepository);

        // create events
        event1 = new Event(" ", "12/20/2021", " ", "", Game.MINECRAFT, PlayLevel.CASUAL, user1);
        event2 = new Event(" ", "12/24/2023", " ", "", Game.SEKIRO_SHADOWS_DIE_TWICE, PlayLevel.CASUAL, user1);
        event3 = new Event(" ", "11/24/2024", " ", "", Game.FORTNITE, PlayLevel.CASUAL, user1);
        event4 = new Event(" ", "12/24/2024", " ", "", Game.MINECRAFT, PlayLevel.CASUAL, user1);

        event5 = new Event(" ", "12/24/2023", " ", "", Game.SEKIRO_SHADOWS_DIE_TWICE, PlayLevel.CASUAL, user1);
        event6 = new Event(" ", "11/24/2024", " ", "", Game.LAST_EPOCH, PlayLevel.CASUAL, user1);
        event7 = new Event(" ", "12/24/2024", " ", "", Game.KENSHI, PlayLevel.CASUAL, user1);

        // set up user1 and games
        Profile profile = new Profile("user1", "1234", "test@test.com", "", "");
        user1 = new User(profile);
        List<Game> games = new ArrayList<Game>();
        games.add(Game.MINECRAFT);
        games.add(Game.FORTNITE);
        games.add(Game.VALORANT);
        user1.getProfile().setGames(games);

        user2 = new User();

        // instantiations
        events = new ArrayList<Event>();
        events1 = new ArrayList<Event>();
        events2 = new ArrayList<Event>();
        events3 = new ArrayList<Event>();
        result = new ArrayList<Event>();

    }

    // sort events - eventhandler
    @Test
    void testSortedEvents() {
        // should return a sorted list
        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);

        result.add(event1);
        result.add(event2);
        result.add(event3);
        result.add(event4);

        events = eventHandler.sortEvents(events);

       org.junit.jupiter.api.Assertions.assertIterableEquals(events,result);
    }


    @Test
    void testUnSortedEvents() {
        // should return a sorted list
        events.add(event1);
        events.add(event4);
        events.add(event3);
        events.add(event2);

        result.add(event1);
        result.add(event2);
        result.add(event3);
        result.add(event4);

        events = eventHandler.sortEvents(events);

       org.junit.jupiter.api.Assertions.assertIterableEquals(events,result);
    }
    
    // test - eventSearch
    @Test
    void testEventSearchHappyPath() {
        // create correct list - events that matched and are sorted
        result.add(event1);
        result.add(event3);
        result.add(event4);

        // add matching games to corresponding events lists
        events1.add(event1); // add minecraft events
        events1.add(event4);
        events2.add(event3); // add fornite

        // should return a list of matched events with the same game according to the user's games
        when(mockEventRepository.findAllByGame(user1.getProfile().getGames().get(0))).thenReturn(events1); // minecraft
        when(mockEventRepository.findAllByGame(user1.getProfile().getGames().get(1))).thenReturn(events2); // fornite
        when(mockEventRepository.findAllByGame(user1.getProfile().getGames().get(2))).thenReturn(events3); // valorant (empty list)

        events = eventHandler.eventSearch(user1);

        assertEquals(events, result); 
    }

    @Test
    void testEventSearchNoMatch() {
        // should return all events sorted
        // add all events sorted
        result.add(event5);
        result.add(event6);
        result.add(event7);

        events1.add(event5);
        events1.add(event6);
        events1.add(event7);

        // should return a list of matched events with the same game according to the user's games
        when(mockEventRepository.findAllByGame(user1.getProfile().getGames().get(0))).thenReturn(events1);
        when(mockEventRepository.findAllByGame(user1.getProfile().getGames().get(1))).thenReturn(events2);
        when(mockEventRepository.findAllByGame(user1.getProfile().getGames().get(2))).thenReturn(events3);

        when(mockEventRepository.findAll()).thenReturn(events1);

        events = eventHandler.eventSearch(user1);

        assertEquals(result, events); 
    }

    // filter event
    

    



}

