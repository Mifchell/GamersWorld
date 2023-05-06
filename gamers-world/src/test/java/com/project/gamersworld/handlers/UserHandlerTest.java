package com.project.gamersworld.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    private User user1;
    private User user2;
    private boolean usernameGood;

    private ArrayList<User> userList;
    private ArrayList<User> expected;

    private User user3;
    private User user4;
    private User user5;
    private User user6;

    private String filter;

    @BeforeEach
    void setUp() {
        mockUserRepository = Mockito.mock(UserRepo.class);
        userHandler = new UserHandler(mockUserRepository);

        Profile profile = new Profile("", "1234", "test1@test.com", "", "");
        user1 = new User(profile);
        user2 = new User();

        usernameGood = false;

        // users for testing filter/recommend user
        userList = new ArrayList<>();
        expected = new ArrayList<>();

        user3 = new User(new Profile("user1", "1234", "test1@test.com", "minecraft asd", "10:30PM"));
        user4 = new User(new Profile("user2", "1234", "test2@test.com", "testz fgh", "1:00AM"));
        user5 = new User(new Profile("user3", "1234", "test3@test.com", "I am jkl", "12:00PM"));
        user6 = new User(new Profile("usser4", "1234", "test4@test.com", "qwe zxcmine", "10:30PM"));
        user3.setUserId(0);
        user4.setUserId(1);
        user5.setUserId(2);
        user6.setUserId(3);
        user3.profile.setGames(Arrays.asList(Game.valueOf("MINECRAFT"), Game.valueOf("VALORANT")));
        user4.profile.setGames(Arrays.asList(Game.valueOf("XCOM_2"), Game.valueOf("VALORANT")));
        user5.profile.setGames(Arrays.asList(Game.valueOf("FORTNITE")));
        user6.profile.setGames(Arrays.asList(Game.valueOf("MINECRAFT")));
        userList.add(user3);
        userList.add(user4);
        userList.add(user5);
        userList.add(user6);
    }

    // test - login
    @Test
    void testLoginHappyPath() {
        // should return user
        when(mockUserRepository.findByProfileEmailAddress("test1@test.com")).thenReturn(user1);

        user2 = userHandler.login("test1@test.com", "1234");

        assertEquals(user1, user2);
    }

    @Test
    void testLoginIncorrectPassword() {

        user1.getProfile().setPassword("1235");
        // should return null
        when(mockUserRepository.findByProfileEmailAddress("test1@test.com")).thenReturn(user1);

        user2 = userHandler.login("test1@test.com", "1234");

        assertNull(user2);
    }

    @Test
    void testLoginIncorrectEmail() {

        user1.getProfile().setEmail("test2@test.com");
        // should return null
        when(mockUserRepository.findByProfileEmailAddress("test1@test.com")).thenReturn(null);

        user2 = userHandler.login("test1@test.com", "1234");

        assertNull(user2);
    }

    // test sign up
    @Test
    void testSignUpHappyPath() {
        // should return user
        when(mockUserRepository.findByProfileEmailAddress("test1@test.com")).thenReturn(null);

        user2 = userHandler.signUp("test1@test.com", "1234");

        assertEquals(user1, user2);
    }

    @Test
    void testSignUpExistingEmail() {
        // should return user
        when(mockUserRepository.findByProfileEmailAddress("test1@test.com")).thenReturn(user1);

        user2 = userHandler.signUp("test1@test.com", "1234");

        assertNull(user2);
    }

    // test create profile
    @Test
    void testCreateProfileHappyPath() {
        // should return true
        when(mockUserRepository.findByProfileUsername("user1")).thenReturn(null);

        usernameGood = userHandler.createProfile(user1, "user1", "", "",
        new ArrayList<Game>());

        assertTrue(usernameGood);
    }

    @Test
    void testCreateProfileExistingUsername() {
        // should return false
        user1.getProfile().setUsername("user1");
        when(mockUserRepository.findByProfileUsername("user1")).thenReturn(user1);

        usernameGood = userHandler.createProfile(user1, "user1", "", "",
                new ArrayList<Game>());

        assertFalse(usernameGood);
    }

    // Logout
    @Test
    void testLogoutHappyPath() {
        /* Has no handler */

    }

    // editProfile
    @Test
    void testEditProfileHappyPath(){
        when(mockUserRepository.findByProfileUsername("user1")).thenReturn(null);

        usernameGood = userHandler.editProfile(user1, "user1", "", "", new ArrayList<Game>(), "", "");
        assertTrue(usernameGood);
    }

    @Test
    void testEditProfileExistingEmail() {
        user1.getProfile().setEmail("user1");
        when(mockUserRepository.findByProfileEmailAddress("user1")).thenReturn(user1);

        usernameGood = userHandler.editProfile(user1, "user1", "", "", new ArrayList<Game>(), "email1", "");
        assertFalse(usernameGood);

    }

    // deleteAccount

    // userSearch (filterGamer)
    @Test
    void testUserSearchNoFilter() {
        when(mockUserRepository.findAll()).thenReturn(userList);

        List<User> users = userHandler.userSearch("");

        assertThat(users).containsExactlyInAnyOrderElementsOf(userList);
    }

    @Test
    void testUserSearchFilterUserName() {
        filter = "user4";
        when(mockUserRepository.findAll()).thenReturn(userList);
        when(mockUserRepository.findByProfileDescriptionContains(filter))
                .thenReturn(new ArrayList<>(Arrays.asList()));
        when(mockUserRepository.findByProfilePreferredTimeContains(filter))
                .thenReturn(new ArrayList<>(Arrays.asList()));
        when(mockUserRepository.findByProfileUsernameContains(filter))
                .thenReturn(new ArrayList<>(Arrays.asList(user4)));

        List<User> users = new ArrayList<User>(userHandler.userSearch(filter));
        expected.add(user4);

        assertThat(users).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void testUserSearchFilterPrefferedTime() {
        filter = "1:00";
        when(mockUserRepository.findAll()).thenReturn(userList);
        when(mockUserRepository.findByProfileDescriptionContains(filter))
                .thenReturn(new ArrayList<>());
        when(mockUserRepository.findByProfilePreferredTimeContains(filter))
                .thenReturn(new ArrayList<>(Arrays.asList(user4)));
        when(mockUserRepository.findByProfileUsernameContains(filter))
                .thenReturn(new ArrayList<>());

        List<User> users = userHandler.userSearch(filter);
        expected.add(user4);

        assertThat(users).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void testUserSearchFilterDescription() {
        filter = "minecraft";
        when(mockUserRepository.findAll()).thenReturn(userList);
        when(mockUserRepository.findByProfileDescriptionContains(filter))
                .thenReturn(new ArrayList<>(Arrays.asList(user3)));
        when(mockUserRepository.findByProfilePreferredTimeContains(filter))
                .thenReturn(new ArrayList<>());
        when(mockUserRepository.findByProfileUsernameContains(filter))
                .thenReturn(new ArrayList<>());

        List<User> users = userHandler.userSearch(filter);
        expected.add(user3);

        assertThat(users).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void testSearchUserFilterNoMatch() {
        filter = "user7";
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
                .thenReturn(new ArrayList<>(Arrays.asList(user3, user4, user5)));

        List<User> users = userHandler.userSearch(filter);
        expected.add(user3);
        expected.add(user4);
        expected.add(user5);

        assertThat(users).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void testUserSearchFilterMultiplePrefferedTimeSearch() {
        filter = "10:30PM";
        when(mockUserRepository.findAll()).thenReturn(userList);
        when(mockUserRepository.findByProfileDescriptionContains(filter))
                .thenReturn(new ArrayList<>());
        when(mockUserRepository.findByProfilePreferredTimeContains(filter))
                .thenReturn(new ArrayList<>(Arrays.asList(user3, user6)));
        when(mockUserRepository.findByProfileUsernameContains(filter))
                .thenReturn(new ArrayList<>());

        List<User> users = userHandler.userSearch(filter);
        expected.add(user3);
        expected.add(user6);

        assertThat(users).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void testSearchMultipleReturnsDescriptionSearch() {
        String filter = "mine";
        when(mockUserRepository.findAll()).thenReturn(userList);
        when(mockUserRepository.findByProfileDescriptionContains(filter))
                .thenReturn(Arrays.asList(user3, user6));
        when(mockUserRepository.findByProfilePreferredTimeContains(filter))
                .thenReturn(new ArrayList<>());
        when(mockUserRepository.findByProfileUsernameContains(filter))
                .thenReturn(new ArrayList<>());

        List<User> users = userHandler.userSearch(filter);
        expected.add(user3);
        expected.add(user6);

        assertThat(users).containsExactlyInAnyOrderElementsOf(expected);
    }

    // recommend user
    @Test
    

}