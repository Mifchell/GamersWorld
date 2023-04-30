package com.project.gamersworld.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verifyNoInteractions;
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

    private ArrayList<Event> list1;
    private ArrayList<Event> list2;
    private ArrayList<Event> list3;
    private ArrayList<Event> list4;
    private User user1;

    @BeforeEach
    void setUp() {
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();
        list4 = new ArrayList<>();
        mockEventRepository = Mockito.mock(EventRepo.class);
        mockUserRepository = Mockito.mock(UserRepo.class);
        eventHandler = new EventHandler(mockEventRepository, mockUserRepository);

        Profile profile = new Profile("user1", "1234", "test@test.com", "", "");
        user1 = new User(profile);
        Event event1 = new Event("MC", "12/23/2024", "Esports", "come play", Game.MINECRAFT, PlayLevel.CASUAL, user1);
        Event event2 = new Event("FORTNITE", "12/19/2023", "Esports", "let's play", Game.FORTNITE, PlayLevel.EXPERT, user1);

        list1.add(event1);
        list2.add(event2);
        list3.add(event1);
        list4.add(event2);
    }

    // test - filter event
    //
    @Test
    void testSearchGroupNoFilter() {
        when(mockGroupRepository.findAll()).thenReturn(list2);
        
        List<Group> groups = groupHandler.groupSearch("");

        assertEquals(groups, list2);

        
    }

    @Test
    void testSearchGroupHappyPath() {
        when(mockGroupRepository.findByDescriptionContaining("p1")).thenReturn(list4);
        when(mockGroupRepository.findByNameContaining("p1")).thenReturn(list1);

        List<Group> groups = groupHandler.groupSearch("p1");

        assertEquals(groups, list2); 
    }

    @Test
    void testSearchGroupNoMatch() {
        when(mockGroupRepository.findByDescriptionContaining("fef")).thenReturn(new ArrayList<Group>());
        when(mockGroupRepository.findByNameContaining("fef")).thenReturn(new ArrayList<Group>());

        List<Group> groups = groupHandler.groupSearch("fef");

        assertEquals(groups, list3); 
    }

    @Test
    void testCreateGroupHappyPath() {
        when(mockGroupRepository.findByName("")).thenReturn(null);
        when(mockUserRepository.save(Mockito.any(User.class))).thenReturn(new User());
        when(mockGroupRepository.save(Mockito.any(Group.class))).thenReturn(new Group());

        String name = groupHandler.createGroup("", "", user1);

        assertEquals(name,"");
    }

    @Test
    void testCreateGroupBadName() {
        when(mockGroupRepository.findByName("")).thenReturn(new Group());

        String name = groupHandler.createGroup("", "", user1);

        verifyNoInteractions(mockUserRepository);

        assertNull(name);
    }
}
