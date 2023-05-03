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

    @BeforeEach
    void setUp() {
        mockUserRepository = Mockito.mock(UserRepo.class);
        userHandler = new UserHandler(mockUserRepository);

        userList = new ArrayList<>();
        expected = new ArrayList<>();

        user1 = new User(new Profile("user1", "1234", "test1@test.com", "minecraft asd", "10:30PM"));
        user2 = new User(new Profile("user2", "1234", "test2@test.com", "testz fgh", "1:00AM"));
        user3 = new User(new Profile("user3", "1234", "test3@test.com", "I am jkl", "12:00PM"));
        user4 = new User(new Profile("user4", "1234", "test4@test.com", "qwe zxc", "10:30PM"));
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
    void testSearchUserNoFilter() {
        when(mockUserRepository.findAll()).thenReturn(userList);

        List<User> users = userHandler.userSearch(new String[0]);
        //users.add(new User( new Profile("user5", "1234", "", "", "")));

        assertThat(users).containsExactlyInAnyOrderElementsOf(userList);
    }

    @Test
    void testSearchUserFilterUserName() {
        when(mockUserRepository.findAll()).thenReturn(userList);

        List<User> users = userHandler.userSearch(new  String[] { "user2" });
        expected.add(user2);

        assertThat(users).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void testSearchUserFilterPrefferedTime() {
        when(mockUserRepository.findAll()).thenReturn(userList);

        List<User> users = userHandler.userSearch(new  String[] { "10:30PM" });
        expected.add(user1);
        expected.add(user4);

        assertThat(users).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void testSearchUserFilterNoMatch() {
        when(mockUserRepository.findAll()).thenReturn(userList);

        List<User> users = userHandler.userSearch(new String[] { "user5" });

        assertEquals(expected, users);
    }

    @Test
    void testSearchUserFilterNull() {
        when(mockUserRepository.findAll()).thenReturn(userList);

        List<User> users = userHandler.userSearch(new String[] { null });

        assertThat(users).containsExactlyInAnyOrderElementsOf(userList);
    }

    @Test
    void testSearchUserFilterEmpty() {
        when(mockUserRepository.findAll()).thenReturn(userList);

        List<User> users = userHandler.userSearch(new String[] { "" });

        assertThat(users).containsExactlyInAnyOrderElementsOf(userList);
    }

    @Test
    void testSearchUserFilterSpaces() {
        when(mockUserRepository.findAll()).thenReturn(userList);

        List<User> users = userHandler.userSearch( new String[] { " " });

        assertThat(users).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void testSearchUserFilterMultiple() {
        when(mockUserRepository.findAll()).thenReturn(userList);

        List<User> users = userHandler.userSearch(new String[] { "user1", "user2" });
        expected.add(user1);
        expected.add(user2);

        assertThat(users).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void testSearchUserFilterMultipleNoMatch() {
        when(mockUserRepository.findAll()).thenReturn(userList);

        List<User> users = userHandler.userSearch(new String[] { "user1", "user5" });

        assertEquals(users, userList);
    }

    @Test
    void testSearchUserFilterMultipleNull() {
        when(mockUserRepository.findAll()).thenReturn(userList);

        List<User> users = userHandler.userSearch(new String[] { null, null });

        assertEquals(users, userList);
    }

    @Test
    void testSearchUserFilterMultipleEmpty() {
        when(mockUserRepository.findAll()).thenReturn(userList);

        List<User> users = userHandler.userSearch(new String[] { "", "" });

        assertEquals(users, userList);
    }

    @Test
    void testSearchUserFilterMultipleSpaces() {
        when(mockUserRepository.findAll()).thenReturn(userList);

        List<User> users = userHandler.userSearch(new String[] { " ", " " });

        assertEquals(expected, users);
    }

    @Test
    void testSearchUserFilterMultipleSpacesAndNull() {
        when(mockUserRepository.findAll()).thenReturn(userList);

        List<User> users = userHandler.userSearch(new String[] { " ", null });

        assertEquals(expected, users);
    }

    @Test
    void testSearchUserFilterMultipleSpacesAndEmpty() {
        when(mockUserRepository.findAll()).thenReturn(userList);

        List<User> users = userHandler.userSearch(new String[] { " ", "" });

        assertEquals(expected, users);
    }
}
