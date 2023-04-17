package com.project.gamersworld.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.project.gamersworld.models.Group;
import com.project.gamersworld.models.Profile;
import com.project.gamersworld.models.User;
import com.project.gamersworld.repo.GroupRepo;

public class GroupHandlerTest {

    @Mock
    private static GroupRepo mockGroupRepository;
    @InjectMocks
    private static GroupHandler groupHandler;
    ArrayList<Group> list1 = new ArrayList<>();
    ArrayList<Group> list2 = new ArrayList<>();

    @BeforeEach
    void setUp() {
        mockGroupRepository = Mockito.mock(GroupRepo.class);

        Profile profile = new Profile("user1", "1234", "test@test.com", "", "");
        User user1 = new User(profile);
        Group group1 = new Group("group1", user1, "djshubr");
        Group group2 = new Group("group2", user1, "ksofnsh");

        list1.add(group1);
        list2.add(group1);
        list2.add(group2);
    }

    @Test
    void testSearchGroupNoMatch() {
        when(mockGroupRepository.findAll()).thenReturn(list2);
        groupHandler = new GroupHandler(mockGroupRepository);
        List<Group> groups = groupHandler.groupSearch("");

        assertEquals(groups, list2);

        
    }
}
