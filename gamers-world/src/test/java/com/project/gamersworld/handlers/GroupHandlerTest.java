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
import com.project.gamersworld.models.Group;
import com.project.gamersworld.models.Profile;
import com.project.gamersworld.models.User;
import com.project.gamersworld.repo.GroupRepo;
import com.project.gamersworld.repo.UserRepo;

public class GroupHandlerTest {

    @Mock
    private static GroupRepo mockGroupRepository;
    @Mock
    private static UserRepo mockUserRepository;
    @InjectMocks
    private static GroupHandler groupHandler;

    ArrayList<Group> list1 = new ArrayList<>();
    ArrayList<Group> list2 = new ArrayList<>();
    ArrayList<Group> list3 = new ArrayList<>();
    ArrayList<Group> list4 = new ArrayList<>();
    User user1;

    @BeforeEach
    void setUp() {
        mockGroupRepository = Mockito.mock(GroupRepo.class);
        mockUserRepository = Mockito.mock(UserRepo.class);
        groupHandler = new GroupHandler(mockGroupRepository, mockUserRepository);

        Profile profile = new Profile("user1", "1234", "test@test.com", "", "");
        user1 = new User(profile);
        Group group1 = new Group("group1", user1, "djshubr");
        Group group2 = new Group("group2", user1, "ksofnshp1");

        list1.add(group1);
        list2.add(group2);
        list2.add(group1);
        list4.add(group2);
    }

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
