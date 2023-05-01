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

    private List<Event> events;
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
        result = new ArrayList<Event>();

    }

    // sort events - eventhandler
    @Test
    void testUnSortedEvents() {
        // should return a sorted list
        events.add(new Event(" ", "12/24/2023", " ", "", Game.ACROSS_THE_OBELISK, PlayLevel.CASUAL, user1));
        events.add(new Event(" ", "12/24/2024", " ", "", Game.ACROSS_THE_OBELISK, PlayLevel.CASUAL, user1));
        events.add(new Event(" ", "12/25/2023", " ", "", Game.ACROSS_THE_OBELISK, PlayLevel.CASUAL, user1));
        events.add(new Event(" ", "10/25/2020", " ", "", Game.ACROSS_THE_OBELISK, PlayLevel.CASUAL, user1));

        result.add(new Event(" ", "10/25/2020", " ", " ", Game.ACROSS_THE_OBELISK, PlayLevel.CASUAL, user1));
        result.add(new Event(" ", "12/24/2023", " ", "", Game.ACROSS_THE_OBELISK, PlayLevel.CASUAL, user1));
        result.add(new Event(" ", "12/25/2023", " ", "", Game.ACROSS_THE_OBELISK, PlayLevel.CASUAL, user1));
        result.add(new Event(" ", "12/24/2024", " ", "", Game.ACROSS_THE_OBELISK, PlayLevel.CASUAL, user1));

        events = eventHandler.sortEvents(events);

       org.junit.jupiter.api.Assertions.assertIterableEquals(events,result);
    }

    // test - eventSearch
    @Test
    void testEventSearchHappyPath() {
        events.add(new Event(" ", "12/24/2023", " ", "", Game.FORTNITE, PlayLevel.CASUAL, user2));
        

        // should return a list of matched events with the same game according to the user's games
        for (int i = 0; i < user1.getProfile().getGames().size(); i++)
            when(mockEventRepository.findAllByGame(user1.getProfile().getGames().get(i))).thenReturn(events);
    result = eventHandler.eventSearch(user1);
    assertEquals(result, events); 
    }

    @Test
    void testEventSearchNoMatch() {
        // should return empty list

        // should return a list of matched events with the same game according to the user's games
        when(mockEventRepository.findAllByGame(user1.getProfile().getGames().get(0))).thenReturn(events);

        result = eventHandler.eventSearch(user1);

        assertEquals(result, events); 
    }





}

