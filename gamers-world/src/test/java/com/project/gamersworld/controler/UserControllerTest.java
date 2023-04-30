package com.project.gamersworld.controler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;
import com.project.gamersworld.controller.UserController;
import com.project.gamersworld.handlers.UserHandler;
import com.project.gamersworld.models.User;
import com.project.gamersworld.repo.UserRepo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import com.project.gamersworld.models.Profile;

public class UserControllerTest {

    @InjectMocks
    private static UserController userController;

    @Mock
    private static UserHandler userHandler;
    
    @Mock
    private static HttpServletRequest request;

    @Mock
    private static UserRepo mockUserRepository;

    @Mock
    private static Model model;

    private User user;
    HttpSession session;
    private Profile profile;

    @BeforeEach
    public void setUp() {
        profile = new Profile(null, "password", "test@example.com", null, null);
        user = new User(profile);
        user.setUserID(123);
        session = Mockito.mock(HttpSession.class);
        request = Mockito.mock(HttpServletRequest.class);
        model = Mockito.mock(Model.class);
        mockUserRepository = Mockito.mock(UserRepo.class);
        userHandler = new UserHandler(mockUserRepository);
        userController = new UserController();



    }

    @Test
    public void testSignUpHappyPath()
    {

     
    }

    @Test
    public void testSignUpFailure() {

    }


}