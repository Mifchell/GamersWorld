package com.project.gamersworld.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.project.gamersworld.controller.GroupControler;
import com.project.gamersworld.handlers.GroupHandler;
import com.project.gamersworld.handlers.UserHandler;
import com.project.gamersworld.models.Group;
import com.project.gamersworld.models.User;
import com.project.gamersworld.repo.GroupRepo;
import com.project.gamersworld.repo.UserRepo;

public class GroupControlerTest {

    @Mock
    GroupHandler mockGroupHandler;
    @Mock
    UserHandler mockUserHandler;
    @Mock
    UserRepo mockUserRepo;
    @Mock
    GroupRepo mockGroupRepo;
    @Mock
    HttpSession session;
    @Mock
    HttpServletRequest request;

    @Mock
    Model model;

    @InjectMocks
    GroupControler groupControler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        groupControler = new GroupControler(mockGroupHandler, mockUserHandler);
    }

    @Test
    void testCreateGroup_HappyResult() {
        User user = new User();

        when(mockGroupHandler.createGroup("", "", user)).thenReturn(true);
        when(session.getAttribute(any())).thenReturn(1);
        when(mockUserHandler.getUserRepo()).thenReturn(mockUserRepo);
        when(mockUserRepo.findByUid(1)).thenReturn(user);
        when(request.getSession()).thenReturn(session);

        String value = groupControler.createGroup("", "", request, model);

        assertEquals(value, "redirect:/groups");
    }

    @Test
    void testCreateGroup_BadResult() {
        User user = new User();

        when(mockGroupHandler.createGroup("", "", user)).thenReturn(false);
        when(session.getAttribute(any())).thenReturn(1);
        when(mockUserHandler.getUserRepo()).thenReturn(mockUserRepo);
        when(mockUserRepo.findByUid(1)).thenReturn(user);
        when(request.getSession()).thenReturn(session);

        String value = groupControler.createGroup("", "", request, model);

        assertEquals(value, "creategroup");
    }

    @Test
    void testEditGroup_Happypath() {
        Group group = new Group();

        when(mockGroupHandler.editGroup(1, "", "")).thenReturn(group);

        String value = groupControler.editGroup(1, "", "", request, model);

        assertEquals(value, "redirect:/profile");
    }

    @Test
    void testEditGroup_badRequest() {
        Group group = new Group();

        when(mockGroupHandler.editGroup(1, "", "")).thenReturn(null);
        when(model.addAttribute(anyString(), anyString())).thenReturn(model);
        when(mockGroupHandler.getGroupRepository()).thenReturn(mockGroupRepo);
        when(mockGroupRepo.findByGroupID(1)).thenReturn(group);

        String value = groupControler.editGroup(1, "", "", request, model);

        assertEquals(value, "managegroup");
    }

    @Test
    void testAddMember_Happypath() {
        User user = new User();

        when(mockUserHandler.getUserRepo()).thenReturn(mockUserRepo);
        when(mockUserRepo.findByProfileUsername("")).thenReturn(user);
        when(mockGroupHandler.join(1, user)).thenReturn(new Group());

        String value = groupControler.addMember(1, "", request, model);

        assertEquals(value, "redirect:/profile");
    }

    @Test
    void testAddMember_badRequest() {

        when(mockUserHandler.getUserRepo()).thenReturn(mockUserRepo);
        when(mockUserRepo.findByProfileUsername("")).thenReturn(null);
        when(mockGroupHandler.editGroup(1, "", "")).thenReturn(null);
        when(model.addAttribute(anyString(), anyString())).thenReturn(model);
        when(mockGroupHandler.getGroupRepository()).thenReturn(mockGroupRepo);
        when(mockGroupRepo.findByGroupID(1)).thenReturn(new Group());

        String value = groupControler.editGroup(1, "", "", request, model);

        assertEquals(value, "managegroup");
    }

}
