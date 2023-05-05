package com.project.gamersworld.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import com.project.gamersworld.models.Game;
import com.project.gamersworld.models.Profile;
import com.project.gamersworld.models.User;
import com.project.gamersworld.repo.UserRepo;

public class UserHandlerTest {

    @Mock
    private static UserRepo mockUserRepository;

    @InjectMocks
    private static UserHandler userHandler;

    private User user1;
    private User user2;
    private boolean usernameGood;

    @BeforeEach
    void setUp() {
        // set up mocks
        mockUserRepository = Mockito.mock(UserRepo.class);
        userHandler = new UserHandler(mockUserRepository);

        // set up users
        Profile profile = new Profile("", "1234", "test1@test.com", "", "");
        user1 = new User(profile);
        user2 = new User();

        usernameGood = false;

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

    //Logout
    @Test
    void testLogoutHappyPath(){
        /*Has no handler */


    }
    
    //editProfile
    @Test
    void testEditProfileHappyPath(){
        when(mockUserRepository.findByProfileUsername("user1")).thenReturn(null);

        usernameGood = userHandler.editProfile(user1, "user1", "", "", new ArrayList<Game>(), "", "");
        assertTrue(usernameGood);
    }

    @Test
    void testEditProfileExistingEmail(){
        user1.getProfile().setEmail("user1");
        when(mockUserRepository.findByProfileEmailAddress("user1")).thenReturn(user1);

        usernameGood = userHandler.editProfile(user1, "user1", "", "", new ArrayList<Game>(), "email1", "");
        assertFalse(usernameGood);

    }

    //deleteAccount
    




}