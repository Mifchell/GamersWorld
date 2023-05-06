package com.project.gamersworld.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.description;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import com.project.gamersworld.models.Message;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Assertions;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.project.gamersworld.controller.MessageController;
import com.project.gamersworld.models.Event;
import com.project.gamersworld.models.Game;
import com.project.gamersworld.models.Group;
import com.project.gamersworld.models.Message;
import com.project.gamersworld.models.Profile;
import com.project.gamersworld.models.User;
import com.project.gamersworld.repo.GroupRepo;
import com.project.gamersworld.repo.EventRepo;
import com.project.gamersworld.repo.MessageRepo;
import com.project.gamersworld.repo.UserRepo;

public class UserHandlerTest {

        @Mock
        private static UserRepo mockUserRepository;
        @InjectMocks
        private static UserHandler userHandler;

    

    @Mock
    private MessageRepo messageRepository;

    @InjectMocks
    private MessageController messageController;

    private User user1;
    private User user2;
    private boolean usernameGood;
    private boolean emailGood;
        private ArrayList<User> userList;
        private ArrayList<User> expected;
        private User user3;
        private User user4;
        private User user5;
        private User user6;
        private User user7;

      

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

                user3 = new User(new Profile("user3", "1234", "test1@test.com", "minecraft asd", "10:30PM"));
                user4 = new User(new Profile("user4", "1234", "test2@test.com", "testz fgh", "1:00AM"));
                user5 = new User(new Profile("user5", "1234", "test3@test.com", "I am jkl", "12:00PM"));
                user6 = new User(new Profile("usser6", "1234", "test4@test.com", "qwe zxcmine", "10:30PM"));
                user7 = new User(new Profile("user7", "1234", "test5@test.com", "ee zxce user", "11:30PM"));
                user3.setUserId(2);
                user4.setUserId(3);
                user5.setUserId(4);
                user6.setUserId(5);
                user7.setUserId(6);
                user3.profile.setGames(Arrays.asList(Game.valueOf("MINECRAFT"), Game.valueOf("VALORANT")));
                user4.profile.setGames(Arrays.asList(Game.valueOf("XCOM_2"), Game.valueOf("VALORANT")));
                user5.profile.setGames(Arrays.asList(Game.valueOf("FORTNITE")));
                user6.profile.setGames(Arrays.asList(Game.valueOf("MINECRAFT")));
                user7.profile.setGames(Arrays.asList(Game.valueOf("MINECRAFT"), Game.valueOf("VALORANT")));
                userList.add(user3);
                userList.add(user4);
                userList.add(user5);
                userList.add(user6);
        }

        // test - login
    @Test
    void testLoginHappyPath() 
    {
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
        }
    void testEditProfile() {
    
    User user1 = new User(new Profile("user1", "password", "John Doe", null, null));

   
    UserRepo mockUserRepository = Mockito.mock(UserRepo.class);
    when(mockUserRepository.findByProfileUsername("user1")).thenReturn(user1);

    UserHandler userHandler = new UserHandler(mockUserRepository);


    boolean success = userHandler.editProfile(user1, "user1", "newpassword", "Jane Doe", new ArrayList<Game>(), "newemail@example.com", "newbio");


    assertTrue(success);

    assertEquals(user1.getProfile().getUsername(), "user1");
   
    assertEquals(user1.getProfile().getEmail(), "newemail@example.com");
    
}



    //deleteAccount
    @Test
void deleteAccountWrongPassword(){
    UserRepo mockUserRepository = Mockito.mock(UserRepo.class);
    UserHandler userHandler = new UserHandler(mockUserRepository);

    HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockSession = Mockito.mock(HttpSession.class);
    when(mockRequest.getSession()).thenReturn(mockSession);
    when(mockSession.getAttribute("userID")).thenReturn(1L);

    User mockUser = new User();

    when(mockUserRepository.findById(1L)).thenReturn(Optional.of(mockUser));
    when(mockRequest.getParameter("password")).thenReturn("wrongpassword");

    assertTrue(userHandler.deleteAccount(mockUser));
} 

    @Test
    void deleteAccountUserNotFound() {
    UserRepo mockUserRepository = Mockito.mock(UserRepo.class);
    UserHandler userHandler = new UserHandler(mockUserRepository);

    HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockSession = Mockito.mock(HttpSession.class);
    when(mockRequest.getSession()).thenReturn(mockSession);
    when(mockSession.getAttribute("userID")).thenReturn(1L);

    when(mockUserRepository.findById(1L)).thenReturn(Optional.empty());

    assertFalse(userHandler.deleteAccount(null));

    verify(mockUserRepository, times(0)).delete(any());
    verify(mockSession, times(0)).removeAttribute("userID");
}


//Display trends
@Test
void testDisplayTrendsNotEnoughGames() {
    List<User> userList = new ArrayList<>();
    User user1 = new User(new Profile("user1", "password", "John Doe", null, null));
    
    userList.add(user1);
    
    UserRepo mockUserRepository = Mockito.mock(UserRepo.class);
    when(mockUserRepository.findAll()).thenReturn(userList);

    
    UserHandler userHandler = new UserHandler(mockUserRepository);

  
    List<Game> games = userHandler.displayTrends();
    assertTrue(games.isEmpty());
}
    

@Test
void testDisplayTrendsNoUsers() {
    
    UserRepo mockUserRepository = Mockito.mock(UserRepo.class);
    when(mockUserRepository.findAll()).thenReturn(new ArrayList<>());

  
    UserHandler userHandler = new UserHandler(mockUserRepository);

    List<Game> games = userHandler.displayTrends();
    assertTrue(games.isEmpty());
}

                //assertThat(users).containsExactlyInAnyOrderElementsOf(expected);
        

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
        void testUserSearchMultipleReturnsUserSearch() {
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
        void testUserSearchMultipleReturnsDescriptionSearch() {
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

        @Test
        void testUserSearchNoDuplicates() {
                String filter = "user";

                when(mockUserRepository.findAll()).thenReturn(userList);
                when(mockUserRepository.findByProfileDescriptionContains(filter))
                                .thenReturn(new ArrayList<>());
                when(mockUserRepository.findByProfilePreferredTimeContains(filter))
                                .thenReturn(new ArrayList<>());
                when(mockUserRepository.findByProfileUsernameContains(filter))
                                .thenReturn(new ArrayList<>(Arrays.asList(user3, user4, user5, user7)));

                List<User> users = userHandler.userSearch(filter);
                expected.add(user3);
                expected.add(user4);
                expected.add(user5);
                expected.add(user7);

                assertThat(users).containsExactlyInAnyOrderElementsOf(expected);
        }

        // recommendGamer
        @Test
        void  testRecommendGamerNoUserRecommended() {
                when(mockUserRepository.findAll()).thenReturn(userList);
                when(mockUserRepository.findByUid(user5.getUserID())).thenReturn(user5);
                when(mockUserRepository.findByProfileGamesContains(Game.FORTNITE)).thenReturn(new ArrayList<>());

                List<User> users = userHandler.recommendGamer(user5.getUserID());

                assertThat(users).containsExactlyInAnyOrderElementsOf(expected);
        }

        @Test
        void  testRecommendGamerOneUserRecommended()
        {
                when(mockUserRepository.findAll()).thenReturn(userList);
                when(mockUserRepository.findByUid(user3.getUserID())).thenReturn(user3);
                when(mockUserRepository.findByProfileGamesContains(Game.MINECRAFT)).thenReturn(new ArrayList<>(Arrays.asList(user6)));

                List<User> users = userHandler.recommendGamer(user3.getUserID());
                expected.add(user6);

                assertThat(users).containsExactlyInAnyOrderElementsOf(expected);
        }

        @Test
        void  testRecommendGamerMultipleUsersRecommended()
        {
                when(mockUserRepository.findAll()).thenReturn(userList);
                when(mockUserRepository.findByUid(user3.getUserID())).thenReturn(user3);
                when(mockUserRepository.findByProfileGamesContains(Game.VALORANT)).thenReturn(new ArrayList<>(Arrays.asList(user4)));
                when(mockUserRepository.findByProfileGamesContains(Game.MINECRAFT)).thenReturn(new ArrayList<>(Arrays.asList(user6)));

                List<User> users = userHandler.recommendGamer(user3.getUserID());
                expected.add(user4);
                expected.add(user6);

                assertThat(users).containsExactlyInAnyOrderElementsOf(expected);
        }

        @Test
        void  testRecommendGamerNoDuplicates()
        {
                when(mockUserRepository.findAll()).thenReturn(userList);
                when(mockUserRepository.findByUid(user3.getUserID())).thenReturn(user3);
                when(mockUserRepository.findByProfileGamesContains(Game.VALORANT)).thenReturn(new ArrayList<>(Arrays.asList(user4,user7)));
                when(mockUserRepository.findByProfileGamesContains(Game.MINECRAFT)).thenReturn(new ArrayList<>(Arrays.asList(user6)));

                List<User> users = userHandler.recommendGamer(user3.getUserID());
                expected.add(user4);
                expected.add(user6);
                expected.add(user7);

                assertThat(users).containsExactlyInAnyOrderElementsOf(expected);
        }

        @Test
        void testGetConversation() 
        {
                Message m1 = new Message();
                Message m2 = new Message();
                Message m3 = new Message();
                Message m4 = new Message();
                List<User> receiverU1 = new ArrayList<User>();
                List<User> receiverU2 = new ArrayList<User>();

                receiverU1.add(user1);
                receiverU2.add(user2);

                m1.setSender(user1);
                m1.setRecievers(receiverU2);
                m2.setSender(user2);
                m2.setRecievers(receiverU1);
                m3.setSender(user2);
                m3.setRecievers(receiverU1);
                m4.setSender(user1);
                m4.setRecievers(receiverU2);
                
                List<Message> u1Sent = new ArrayList<Message>();
                u1Sent.add(m4);
                u1Sent.add(m1);
                List<Message> u1Received = new ArrayList<Message>();
                u1Received.add(m3);
                u1Received.add(m2);

                user1.setSentMessages(u1Sent);
                user1.setReceivedMessages(u1Received);
                user2.setSentMessages(u1Received);
                user2.setReceivedMessages(u1Sent);


                List<Message> orderedList = new ArrayList<Message>();
                orderedList.add(m1);
                orderedList.add(m2);
                orderedList.add(m3);
                orderedList.add(m4);

                when(mockUserRepository.findByUid(user1.getUserID())).thenReturn(user1);
                when(mockUserRepository.findByUid(user2.getUserID())).thenReturn(user2);
                List<Message> test = userHandler.getConversation(user1.getUserID(), user2.getUserID());
                for(int i = 0 ; i < test.size(); i++)
                        assertEquals(test.get(i).getDate(),orderedList.get(i).getDate());
        }

        @Test
        void testGetGroupConversation() 
        {
                List<Group> groupList = new ArrayList<Group>();
                Group group = new Group("TestGroup", user1, "Test Group");
                group.setGroupID(100);
                groupList.add(group);

                user1.setGroupList(groupList);
                user2.setGroupList(groupList);
                user3.setGroupList(groupList);
                user4.setGroupList(groupList);
                
                List<User> groupMembers = new ArrayList<User>();
                groupMembers.add(user1);
                groupMembers.add(user2);
                groupMembers.add(user3);
                groupMembers.add(user4);
                group.setMembers(groupMembers);

                Message m1 = new Message(user1,group,"Test Message");
                List<Message> sent1 = user1.getSentMessages();
                sent1.add(m1);
                user1.setSentMessages(sent1);
                Message m2 = new Message(user2,group,"Test2");
                List<Message> sent2 = user2.getSentMessages();
                sent2.add(m2);
                user2.setSentMessages(sent2);
                Message m3 = new Message(user3,group,"Test3");
                List<Message> sent3 = user3.getSentMessages();
                sent3.add(m3);
                user3.setSentMessages(sent3);
                Message m4 = new Message(user4,group,"Test4");
                List<Message> sent4 = user4.getSentMessages();
                sent4.add(m4);
                user4.setSentMessages(sent4);


                List<Message> u1Received = new ArrayList<Message>();
                u1Received.add(m2);
                u1Received.add(m4);
                u1Received.add(m3);
                List<Message> u2Received = new ArrayList<Message>();
                u2Received.add(m4);
                u2Received.add(m3);
                u2Received.add(m1);
                List<Message> u3Received = new ArrayList<Message>();
                u3Received.add(m1);
                u3Received.add(m4);
                u3Received.add(m3);
                List<Message> u4Received = new ArrayList<Message>();
                u4Received.add(m1);
                u4Received.add(m3);
                u4Received.add(m2);

                user1.setReceivedMessages(u1Received);
                user2.setReceivedMessages(u2Received);
                user3.setReceivedMessages(u3Received);
                user4.setReceivedMessages(u4Received);

                List<Message> orderedList = new ArrayList<Message>();
                orderedList.add(m1);
                orderedList.add(m2);
                orderedList.add(m3);
                orderedList.add(m4);

                when(mockUserRepository.findByUid(user1.getUserID())).thenReturn(user1);
                when(mockUserRepository.findByUid(user2.getUserID())).thenReturn(user2);
                when(mockUserRepository.findByUid(user3.getUserID())).thenReturn(user3);
                when(mockUserRepository.findByUid(user4.getUserID())).thenReturn(user4);
                List<Message> test = userHandler.getGroupConversation(user1.getUserID(), group.getGroupID());
                for(int i = 0 ; i < test.size(); i++)
                        assertEquals(test.get(i).getDate(),orderedList.get(i).getDate());
        }
}
