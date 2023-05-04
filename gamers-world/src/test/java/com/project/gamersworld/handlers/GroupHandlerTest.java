package com.project.gamersworld.handlers;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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
    User user2;
    Group group1;

    @BeforeEach
    void setUp() {
        mockGroupRepository = Mockito.mock(GroupRepo.class);
        mockUserRepository = Mockito.mock(UserRepo.class);
        groupHandler = new GroupHandler(mockGroupRepository, mockUserRepository);

        Profile profile = new Profile("user1", "1234", "test@test.com", "", "");
        user1 = new User(profile);
        user1.setUserId(1);
        user2 = new User(profile);
        user2.setUserId(2);
        group1 = new Group("group1", user1, "djshubr");
        Group group2 = new Group("group2", user2, "ksofnshp1");

        list1.add(group1);

        list2.add(group2);
        list2.add(group1);
        list4.add(group2);
    }

    @Test
    void testSearchGroupNoFilter() {
        when(mockGroupRepository.findAll()).thenReturn(list2);
        
        List<Group> groups = groupHandler.groupSearch("",user1);

        assertTrue(groups.size() == list2.size() && groups.containsAll(list2) && list2.containsAll(groups));

        
    }

    @Test
    void testSearchGroupHappyPath() {
        when(mockGroupRepository.findByDescriptionContaining("p1")).thenReturn(list4);
        when(mockGroupRepository.findByNameContaining("p1")).thenReturn(list1);

        List<Group> groups = groupHandler.groupSearch("p1",user1);

        assertTrue(groups.size() == list2.size() && groups.containsAll(list2) && list2.containsAll(groups));
        //assertEquals(groups, list2); 
    }

    @Test
    void testSearchGroupNoMatch() {
        when(mockGroupRepository.findByDescriptionContaining("fef")).thenReturn(new ArrayList<Group>());
        when(mockGroupRepository.findByNameContaining("fef")).thenReturn(new ArrayList<Group>());

        List<Group> groups = groupHandler.groupSearch("fef", user1);

        assertTrue(groups.size() == list3.size() && groups.containsAll(list3) && list3.containsAll(groups));
    }

    @Test
    void testCreateGroupHappyPath() {
        when(mockGroupRepository.findByName("")).thenReturn(null);
        when(mockUserRepository.save(Mockito.any(User.class))).thenReturn(new User());
        when(mockGroupRepository.save(Mockito.any(Group.class))).thenReturn(new Group());

        boolean name = groupHandler.createGroup("", "", user1);

        assertTrue(name);
    }

    @Test
    void testCreateGroupBadName() {
        when(mockGroupRepository.findByName("")).thenReturn(new Group());

        boolean name = groupHandler.createGroup("", "", user1);

        verifyNoInteractions(mockUserRepository);

        assertFalse(name);
    }

    @Test
    void testMyGroups_emptyList() {

        List<Group> mygroups = groupHandler.myGroups(user1);
        assertTrue(mygroups.isEmpty());
    }

    @Test
    void testMyGroups_notEmpty() {
        user1.setGroupList(list2);
        List<Group> mygroups = groupHandler.myGroups(user1);
        assertEquals(list2, mygroups);
    }

    @Test
    void testGroupOwned_Empty() {
        List<Group> mygroups = groupHandler.groupOwned(user1);
        assertTrue(mygroups.isEmpty());
    }

    @Test
    void testGroupOwned_NotEmpty() {
        user1.setGroupList(list2);
        List<Group> mygroups = groupHandler.groupOwned(user1);
        assertEquals(list1, mygroups);
    }

    @Test
    void testEditGroup_HappyPath(){
        when(mockGroupRepository.findByName(any())).thenReturn(null);
        when(mockGroupRepository.findByGroupID(0)).thenReturn(group1);

        Group group = groupHandler.editGroup(0, "group1", "");
        assertEquals(group.getGroupID(), group1.getGroupID());
    }

    @Test
    void testEditGroup_nameTaken(){
        when(mockGroupRepository.findByName(any())).thenReturn(group1);
        when(mockGroupRepository.findByGroupID(0)).thenReturn(group1);

        Group group = groupHandler.editGroup(0, "", "");
        assertNull(group);
    }

    @Test
    void testEditGroup_NoChangeName(){
        when(mockGroupRepository.findByName(any())).thenReturn(group1);
        when(mockGroupRepository.findByGroupID(0)).thenReturn(group1);

        Group group = groupHandler.editGroup(0, "group1", "");
        assertEquals(group.getGroupID(), group1.getGroupID());
    }

}
