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

public class UserHandlerTest {

    @Mock
    private static UserRepo mockUserRepository;

    @InjectMocks
    private static UserHandler userHandler;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp()
    {
        // set up mocks
        mockUserRepository = Mockito.mock(UserRepo.class);
        userHandler = new UserHandler(mockUserRepository);

        // set up users
        Profile profile = new Profile("user1", "1234", "test1@test.com", "", "");
        user1 = new User(profile);
        user2 = new User();

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
        when(mockUserRepository.findByProfileEmailAddress("test1@test.com")).thenReturn(user1);

        user2 = userHandler.login("test1@test.com", "1234");

        assertEquals(user1, user2);
    }

    @Test
    void testSignUpExistingEmail() {
        // should return user
        when(mockUserRepository.findByProfileEmailAddress("test1@test.com")).thenReturn(user1);

        user2 = userHandler.login("test1@test.com", "1234");

        assertEquals(user1, user2);
    }

}