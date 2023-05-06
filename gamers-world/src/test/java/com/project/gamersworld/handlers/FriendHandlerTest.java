package com.project.gamersworld.handlers;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.project.gamersworld.models.FriendRequest;
import com.project.gamersworld.models.Group;
import com.project.gamersworld.models.Message;
import com.project.gamersworld.models.User;
import com.project.gamersworld.repo.FriendRequestRepo;
import com.project.gamersworld.repo.GroupRepo;
import com.project.gamersworld.repo.MessageRepo;
import com.project.gamersworld.repo.UserRepo;

public class FriendHandlerTest {
    
    @Mock
    UserRepo mockUserRepo;
    @Mock
    FriendRequestRepo mockFriendRequestRepo;
    @InjectMocks
    FriendHandler mockFriendHandler;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);

        mockFriendHandler = new FriendHandler(mockUserRepo,mockFriendRequestRepo);
    }

    @Test
    void TestAddFriend()
    {
        User user1 = new User();
        user1.setUserId(1);
        User user2 = new User();
        user2.setUserId(2);

        when(mockUserRepo.findByUid(1)).thenReturn(user1);
        when(mockUserRepo.findByUid(2)).thenReturn(user2);
        mockFriendHandler.addFriend(1, 2);
        verify(mockUserRepo,times(2)).save(Mockito.any(User.class));
        assertTrue(user1.getFriendList().contains(user2));
        assertTrue(user2.getFriendList().contains(user1));
    }

    @Test
    void TestRemoveFriend()
    {
        User user1 = new User();
        user1.setUserId(1);
        List<User> user1Friends = new ArrayList<User>();
        User user2 = new User();
        user2.setUserId(2);
        List<User> user2Friends = new ArrayList<User>();

        user1Friends.add(user2);
        user2Friends.add(user1);

        user1.setFriendList(user1Friends);
        user2.setFriendList(user2Friends);

        assertTrue(user1.getFriendList().contains(user2));
        assertTrue(user2.getFriendList().contains(user1));

        when(mockUserRepo.findByUid(1)).thenReturn(user1);
        when(mockUserRepo.findByUid(2)).thenReturn(user2);
        mockFriendHandler.removeFriend(1, 2);
        verify(mockUserRepo,times(2)).save(Mockito.any(User.class));
        assertFalse(user1.getFriendList().contains(user2));
        assertFalse(user2.getFriendList().contains(user1));
        
    }

    @Test
    void TestBlockUserInFriendsList()
    {
        User user1 = new User();
        user1.setUserId(1);

        User user2 = new User();
        user2.setUserId(2);
        
        List<User> user1Friends = new ArrayList<User>();
        List<User> user2Friends = new ArrayList<User>();

        user1Friends.add(user2);
        user2Friends.add(user1);

        user1.setFriendList(user1Friends);
        user2.setFriendList(user2Friends);

        assertTrue(user1.getFriendList().contains(user2));
        assertTrue(user2.getFriendList().contains(user1));

        when(mockUserRepo.findByUid(1)).thenReturn(user1);
        when(mockUserRepo.findByUid(2)).thenReturn(user2);
        mockFriendHandler.blockUser(1, 2);
        verify(mockUserRepo,times(3)).save(Mockito.any(User.class));//1 time in block user and 2 times in removing of friend
        assertFalse(user1.getFriendList().contains(user2));
        assertFalse(user2.getFriendList().contains(user1));
        assertTrue(user1.getBlockedUsers().contains(user2));
    }

    @Test
    void TestBlockUserNotInFriendsList()
    {
        User user1 = new User();
        user1.setUserId(1);

        User user2 = new User();
        user2.setUserId(2);

        assertFalse(user1.getFriendList().contains(user2));
        assertFalse(user2.getFriendList().contains(user1));

        when(mockUserRepo.findByUid(1)).thenReturn(user1);
        when(mockUserRepo.findByUid(2)).thenReturn(user2);
        mockFriendHandler.blockUser(1, 2);
        verify(mockUserRepo,times(1)).save(Mockito.any(User.class));//1 time in block user, if more than it ran as if they were already friends
        assertFalse(user1.getFriendList().contains(user2));
        assertFalse(user2.getFriendList().contains(user1));
        assertTrue(user1.getBlockedUsers().contains(user2));
    }
    
    @Test
    void sendFriendRequestToUserWhoBlockedSender()
    {
        User user1 = new User();
        user1.setUserId(1);

        User user2 = new User();
        user2.setUserId(2);

        List<User> blocked = new ArrayList<User>();
        blocked.add(user2);
        user1.setBlockedUsers(blocked);

        assertFalse(user1.getFriendList().contains(user2));
        assertFalse(user2.getFriendList().contains(user1));
        assertTrue(user1.getBlockedUsers().contains(user2));

        when(mockUserRepo.findByUid(1)).thenReturn(user1);
        when(mockUserRepo.findByUid(2)).thenReturn(user2);

        mockFriendHandler.sendFriendRequest(2, 1);
        verify(mockFriendRequestRepo,times(0)).save(Mockito.any(FriendRequest.class));
        
        assertTrue(user1.getreceivedFriendRequest().isEmpty());
        assertTrue(user2.getSentFriendRequest().isEmpty());
    }

    @Test
    void sendFriendRequestToUserWhoIsNotBlocked()
    {
        User user1 = new User();
        user1.setUserId(1);

        User user2 = new User();
        user2.setUserId(2);

        assertFalse(user1.getFriendList().contains(user2));
        assertFalse(user2.getFriendList().contains(user1));
        assertTrue(user1.getreceivedFriendRequest().isEmpty());
        assertTrue(user2.getSentFriendRequest().isEmpty());

        when(mockUserRepo.findByUid(1)).thenReturn(user1);
        when(mockUserRepo.findByUid(2)).thenReturn(user2);

        mockFriendHandler.sendFriendRequest(1, 2);
        verify(mockFriendRequestRepo,times(1)).save(Mockito.any(FriendRequest.class));
        
    }

    @Test
    void sendFriendRequestToUserWhoAlreadySentAFriendRequestToSender()
    {
        User user1 = new User();
        user1.setUserId(1);

        User user2 = new User();
        user2.setUserId(2);

        List<FriendRequest> requests = new ArrayList<FriendRequest>();
        requests.add(new FriendRequest(user2,user1));
        user1.setReceivedFriendRequest(requests);

        when(mockUserRepo.findByUid(1)).thenReturn(user1);
        when(mockUserRepo.findByUid(2)).thenReturn(user2);

        mockFriendHandler.sendFriendRequest(1, 2);
        verify(mockFriendRequestRepo,times(0)).save(Mockito.any(FriendRequest.class));
        verify(mockUserRepo,times(2)).save(Mockito.any(User.class)); 

        assertTrue(user1.getFriendList().contains(user2));
        assertTrue(user2.getFriendList().contains(user1));
    }

    @Test
    void sendFriendToSelf()
    {
        User user1 = new User();
        user1.setUserId(1);
        when(mockUserRepo.findByUid(1)).thenReturn(user1);
        mockFriendHandler.sendFriendRequest(1, 1);
        verify(mockFriendRequestRepo,times(0)).save(Mockito.any(FriendRequest.class));
    }

    @Test
    void testAcceptFriendRequest()
    {
        User user1 = new User();
        user1.setUserId(1);

        User user2 = new User();
        user2.setUserId(2);

        when(mockUserRepo.findByUid(1)).thenReturn(user1);
        when(mockUserRepo.findByUid(2)).thenReturn(user2);

        FriendRequest fr = new FriendRequest(user1,user2);
        mockFriendHandler.acceptFriendRequest(fr);
        verify(mockFriendRequestRepo,times(1)).delete(Mockito.any(FriendRequest.class));
        verify(mockUserRepo,times(2)).save(Mockito.any(User.class));
    }
    
    @Test
    void testDeclinetFriendRequest()
    {
        User user1 = new User();
        user1.setUserId(1);

        User user2 = new User();
        user2.setUserId(2);

        FriendRequest fr = new FriendRequest(user1,user2);
        mockFriendHandler.declineFriendRequest(fr);
        verify(mockFriendRequestRepo,times(1)).delete(Mockito.any(FriendRequest.class));
    }

    @Test
    void testGetRequestReceivedUsers()
    {
        User user1 = new User();
        user1.setUserId(1);

        User user2 = new User();
        user2.setUserId(2);

        User user3 = new User();
        user3.setUserId(3);

        User user4 = new User();
        user4.setUserId(4);

        FriendRequest fr1 = new FriendRequest(user2,user1);
        FriendRequest fr2 = new FriendRequest(user3,user1);
        FriendRequest fr3 = new FriendRequest(user4,user1);

        List<FriendRequest> list = new ArrayList<FriendRequest>();

        list.add(fr1);
        list.add(fr2);
        list.add(fr3);

        user1.setReceivedFriendRequest(list);
        when(mockUserRepo.findByUid(1)).thenReturn(user1);
        List<User> userList = mockFriendHandler.getRequestReceivedUsers(1);

        for(int i = 0; i < userList.size();i++)
            assertTrue(userList.get(i).getUserID() == (i+1));
    }

    @Test
    void testGetRequestSentUsers()
    {
        User user1 = new User();
        user1.setUserId(1);

        User user2 = new User();
        user2.setUserId(2);

        User user3 = new User();
        user3.setUserId(3);

        User user4 = new User();
        user4.setUserId(4);

        FriendRequest fr1 = new FriendRequest(user1,user2);
        FriendRequest fr2 = new FriendRequest(user1,user3);
        FriendRequest fr3 = new FriendRequest(user1,user4);

        List<FriendRequest> list = new ArrayList<FriendRequest>();

        list.add(fr1);
        list.add(fr2);
        list.add(fr3);

        user1.setSentFriendRequest(list);
        when(mockUserRepo.findByUid(1)).thenReturn(user1);
        List<User> userList = mockFriendHandler.getRequestSentUsers(1);

        for(int i = 0; i < userList.size();i++)
            assertTrue(userList.get(i).getUserID() == (i+1));
    }

    

}
