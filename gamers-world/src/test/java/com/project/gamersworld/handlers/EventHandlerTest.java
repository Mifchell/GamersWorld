package com.project.gamersworld.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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
    private User user3;
    private List<Event> result;

    // test - sort events
    @BeforeEach
    void setUp()
    {
        // set up mocks
        mockEventRepository = Mockito.mock(EventRepo.class);
        mockUserRepository = Mockito.mock(UserRepo.class);
        eventHandler = new EventHandler(mockEventRepository, mockUserRepository);

        // set up user1 and games
        Profile profile = new Profile("user1", "1234", "test@test.com", "", "");
        user1 = new User(profile);
        List<Game> games = new ArrayList<Game>();
        games.add(Game.MINECRAFT);
        games.add(Game.FORTNITE);
        games.add(Game.VALORANT);
        user1.getProfile().setGames(games);
        user1.setUserId(1);

        user2 = new User();
        user2.setUserId(2);

        Profile profile2 = new Profile("user3", "1234", "test69@test.com", "", "");
        user3 = new User(profile2);
        user3.setUserId(3);

        // create events
        event1 = new Event(" ", "12/20/2021", " ", "", Game.MINECRAFT, PlayLevel.CASUAL, user1);
        event2 = new Event(" ", "12/24/2023", " ", "", Game.SEKIRO_SHADOWS_DIE_TWICE, PlayLevel.CASUAL, user1);
        event3 = new Event(" ", "11/24/2024", " ", "", Game.FORTNITE, PlayLevel.CASUAL, user1);
        event4 = new Event(" ", "12/24/2024", " ", "", Game.MINECRAFT, PlayLevel.CASUAL, user1);

        event5 = new Event(" ", "12/24/2023", " ", "hi", Game.SEKIRO_SHADOWS_DIE_TWICE, PlayLevel.CASUAL, user1);
        event6 = new Event("hi", "11/24/2024", " ", "", Game.LAST_EPOCH, PlayLevel.CASUAL, user1);
        event7 = new Event(" ", "12/24/2024", " ", "", Game.KENSHI, PlayLevel.CASUAL, user1);

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
    void testEventSearchGameNoMatch() {
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
    @Test
    void testFilterEventHappyPath() {
        events1.add(event5);
        events2.add(event6);

        result.addAll(events1);
        result.addAll(events2);

        // should return all events pertaining to filter
        when(mockEventRepository.findByDescriptionContaining("hi")).thenReturn(events1);
        when(mockEventRepository.findByEventNameContaining("hi")).thenReturn(events2);

        events = eventHandler.filterEvent("hi", user1);

        assertEquals(result, events); 
    }

    @Test
    void testFilterEventNoMatch() {
        //return no events pertaining to filter

        when(mockEventRepository.findByDescriptionContaining("hi")).thenReturn(new ArrayList<Event>());
        when(mockEventRepository.findByEventNameContaining("hi")).thenReturn(new ArrayList<Event>());

        events = eventHandler.filterEvent("hi", user1);

        assertEquals(result, events);
    }

    @Test
    void testFilterEventNoFilter() {
        // add all events sorted
        result.add(event5);
        result.add(event6);
        result.add(event7);

        when(mockEventRepository.findAll()).thenReturn(result);
        
        events = eventHandler.filterEvent("", user1);

        assertEquals(result, events);
    }

    @Test
    void testCreateEventHappyPath() {
        when(mockUserRepository.findByUid(user1.getUserID())).thenReturn(user1);
        when(mockEventRepository.findByEventName("hi")).thenReturn(null);
        Boolean result = eventHandler.createEvent("test", "05/20/2023", "online", "null", "MINECRAFT", "CASUAL", user1.getUserID());
        assertTrue(result);
    }

    @Test
    void testCreateEventExistingName() {
        when(mockEventRepository.findByEventName("hi")).thenReturn(event6);
        Boolean result = eventHandler.createEvent("hi", "05/20/2023", "online", "null", "MINECRAFT", "CASUAL", user1.getUserID());
        assertFalse(result);
    }

    @Test
    void testEditEventHappyPath() {
        when(mockEventRepository.findByEventId(event6.getEventId())).thenReturn(event6);
        when(mockEventRepository.findByEventName("jeff")).thenReturn(event6);
        Boolean result = eventHandler.editEvent(event6.getEventId(), "jeff", "05/20/2023", "online", "yes", "MINECRAFT", "CASUAL");
        assertTrue(result);
    }

    @Test
    void testEditEventExistingName() {
        when(mockEventRepository.findByEventId(event6.getEventId())).thenReturn(event6);
        when(mockEventRepository.findByEventName("jeff")).thenReturn(event1);
        event6.setEventId(16);
        event1.setEventId(11);
        Boolean result = eventHandler.editEvent(event6.getEventId(), "jeff", "05/20/2023", "online", "yes", "MINECRAFT", "CASUAL");
        assertFalse(result);
    }

    @Test
    void testRSVPHappyPath() {
        when(mockEventRepository.findByEventId(event6.getEventId())).thenReturn(event6);
        when(mockUserRepository.findByUid(user2.getUserID())).thenReturn(user2);
        Boolean result = eventHandler.RSVPEvent(user2.getUserID(), event6.getEventId());
        assertTrue(result);
    }

    @Test
    void testRSVPAlreadyAttending() {
        when(mockEventRepository.findByEventId(event6.getEventId())).thenReturn(event6);
        Boolean result = eventHandler.RSVPEvent(user1.getUserID(), event6.getEventId());
        assertFalse(result);
    }

    @Test
    void testRSVPDoesNotExist() {
        when(mockEventRepository.findByEventId(event6.getEventId())).thenReturn(null);
        Boolean result = eventHandler.RSVPEvent(1, event6.getEventId());
        assertFalse(result);
    }

    @Test
    void testDeleteEventHappyPath() {
        when(mockEventRepository.findByEventId(event6.getEventId())).thenReturn(event6);
        Boolean result = eventHandler.deleteEvent(event6.getEventId());
        assertTrue(result);
    }

    @Test
    void testDeleteEventDoesNotExist() {
        when(mockEventRepository.findByEventId(event6.getEventId())).thenReturn(null);
        Boolean result = eventHandler.deleteEvent(event6.getEventId());
        assertFalse(result);
    }

    @Test
    void testCommentEventHappyPath() {
        when(mockEventRepository.findByEventId(event6.getEventId())).thenReturn(event6);
        Boolean result = eventHandler.commentEvent("jeff", event6.getEventId());
        assertTrue(result);
    }

    @Test
    void testCommentEventDoesNotExist() {
        when(mockEventRepository.findByEventId(event6.getEventId())).thenReturn(null);
        Boolean result = eventHandler.commentEvent("jeff", event6.getEventId());
        assertFalse(result);
    }

}

