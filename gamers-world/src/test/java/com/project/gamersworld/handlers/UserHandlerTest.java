package com.project.gamersworld.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.project.gamersworld.models.Game;
import com.project.gamersworld.models.Group;
import com.project.gamersworld.models.Profile;
import com.project.gamersworld.models.User;
import com.project.gamersworld.repo.GroupRepo;
import com.project.gamersworld.repo.UserRepo;

public class UserHandlerTest {

    @Mock
    private static UserRepo mockUserRepository;
    @InjectMocks
    private static UserHandler userHandler;

    ArrayList<User> userList;
    ArrayList<User> expected;

    User user1;
    User user2;
    User user3;
    User user4;

    String filter;

    @BeforeEach
    void setUp() {
        mockUserRepository = Mockito.mock(UserRepo.class);
        userHandler = new UserHandler(mockUserRepository);

        userList = new ArrayList<>();
        expected = new ArrayList<>();

        user1 = new User(new Profile("user1", "1234", "test1@test.com", "minecraft asd", "10:30PM"));
        user2 = new User(new Profile("user2", "1234", "test2@test.com", "testz fgh", "1:00AM"));
        user3 = new User(new Profile("user3", "1234", "test3@test.com", "I am jkl", "12:00PM"));
        user4 = new User(new Profile("usser4", "1234", "test4@test.com", "qwe zxcmine", "10:30PM"));
        user1.setUserId(0);
        user2.setUserId(1);
        user3.setUserId(2);
        user4.setUserId(3);
        user1.profile.setGames(Arrays.asList(Game.valueOf("MINECRAFT"), Game.valueOf("VALORANT")));
        user2.profile.setGames(Arrays.asList(Game.valueOf("XCOM_2"), Game.valueOf("VALORANT")));
        user3.profile.setGames(Arrays.asList(Game.valueOf("FORTNITE")));
        user4.profile.setGames(Arrays.asList(Game.valueOf("MINECRAFT")));
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);

    }

    @Test
    void testUserSearchNoFilter() {
        when(mockUserRepository.findAll()).thenReturn(userList);

        List<User> users = userHandler.userSearch("");
        //users.add(new User( new Profile("user5", "1234", "", "", "")));

        assertThat(users).containsExactlyInAnyOrderElementsOf(userList);
    }

    @Test
    void testUserSearchFilterUserName() {
        filter = "user2";
        when(mockUserRepository.findAll()).thenReturn(userList);
        when(mockUserRepository.findByProfileDescriptionContains(filter))
                .thenReturn(new ArrayList<>(Arrays.asList()));
        when(mockUserRepository.findByProfilePreferredTimeContains(filter))
                .thenReturn(new ArrayList<>(Arrays.asList()));
        when(mockUserRepository.findByProfileUsernameContains(filter))
                .thenReturn(new ArrayList<>(Arrays.asList(user2)));

        List<User> users = new ArrayList<User>(userHandler.userSearch(filter));
        expected.add(user2);

        assertThat(users).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void testUserSearchFilterPrefferedTime() {
        filter = "1:00";
        when(mockUserRepository.findAll()).thenReturn(userList);
        when(mockUserRepository.findByProfileDescriptionContains(filter))
                .thenReturn(new ArrayList<>());
        when(mockUserRepository.findByProfilePreferredTimeContains(filter))
                .thenReturn(new ArrayList<>(Arrays.asList(user2)));
        when(mockUserRepository.findByProfileUsernameContains(filter))
                .thenReturn(new ArrayList<>());

        List<User> users = userHandler.userSearch(filter);
        expected.add(user2);

        assertThat(users).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void testUserSearchFilterDescription() {
        filter = "minecraft";
        when(mockUserRepository.findAll()).thenReturn(userList);
        when(mockUserRepository.findByProfileDescriptionContains(filter))
                .thenReturn(new ArrayList<>(Arrays.asList(user1)));
        when(mockUserRepository.findByProfilePreferredTimeContains(filter))
                .thenReturn(new ArrayList<>());
        when(mockUserRepository.findByProfileUsernameContains(filter))
                .thenReturn(new ArrayList<>());

        List<User> users = userHandler.userSearch(filter);
        expected.add(user1);

        assertThat(users).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void testSearchUserFilterNoMatch() {
        filter = "user5";
        when(mockUserRepository.findAll()).thenReturn(userList);
        when(mockUserRepository.findByProfileDescriptionContains(filter))
                .thenReturn(new ArrayList<>());
        when(mockUserRepository.findByProfilePreferredTimeContains(filter))
                .thenReturn(new ArrayList<>());
        when(mockUserRepository.findByProfileUsernameContains(filter))
                .thenReturn(new ArrayList<>());

        List<User> users = userHandler.userSearch(filter);

        assertEquals(expected, users);
    }

    @Test
    void testSearchUserFilterSpaces() {
        String filter = " ";
        when(mockUserRepository.findAll()).thenReturn(userList);
        when(mockUserRepository.findByProfileDescriptionContains(filter))
                .thenReturn(userList);
        when(mockUserRepository.findByProfilePreferredTimeContains(filter))
                .thenReturn(new ArrayList<>());
        when(mockUserRepository.findByProfileUsernameContains(filter))
                .thenReturn(new ArrayList<>());

        List<User> users = userHandler.userSearch(filter);

        assertThat(users).containsExactlyInAnyOrderElementsOf(userList);
    }

    @Test
    void testSearchMultipleReturnsUserSearch() {
        String filter = "user";
        when(mockUserRepository.findAll()).thenReturn(userList);
        when(mockUserRepository.findByProfileDescriptionContains(filter))
                .thenReturn(new ArrayList<>());
        when(mockUserRepository.findByProfilePreferredTimeContains(filter))
                .thenReturn(new ArrayList<>());
        when(mockUserRepository.findByProfileUsernameContains(filter))
                .thenReturn(new ArrayList<>(Arrays.asList(user1, user2, user3)));

        List<User> users = userHandler.userSearch(filter);
        expected.add(user1);
        expected.add(user2);
        expected.add(user3);

        assertThat(users).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void testUserSearchFilterMultiplePrefferedTimeSearch() {
        filter = "10:30PM";
        when(mockUserRepository.findAll()).thenReturn(userList);
        when(mockUserRepository.findByProfileDescriptionContains(filter))
                .thenReturn(new ArrayList<>());
        when(mockUserRepository.findByProfilePreferredTimeContains(filter))
                .thenReturn(new ArrayList<>(Arrays.asList(user1, user4)));
        when(mockUserRepository.findByProfileUsernameContains(filter))
                .thenReturn(new ArrayList<>());

        List<User> users = userHandler.userSearch(filter);
        expected.add(user1);
        expected.add(user4);

        assertThat(users).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void testSearchMultipleReturnsDescriptionSearch() {
        String filter = "mine";
        when(mockUserRepository.findAll()).thenReturn(userList);
        when(mockUserRepository.findByProfileDescriptionContains(filter))
                .thenReturn(Arrays.asList(user1, user4));
        when(mockUserRepository.findByProfilePreferredTimeContains(filter))
                .thenReturn(new ArrayList<>());
        when(mockUserRepository.findByProfileUsernameContains(filter))
                .thenReturn(new ArrayList<>());

        List<User> users = userHandler.userSearch(filter);
        expected.add(user1);
        expected.add(user4);

        assertThat(users).containsExactlyInAnyOrderElementsOf(expected);
    }

}