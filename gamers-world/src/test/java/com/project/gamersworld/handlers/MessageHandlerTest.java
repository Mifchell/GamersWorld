package com.project.gamersworld.handlers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.project.gamersworld.models.Group;
import com.project.gamersworld.models.Message;
import com.project.gamersworld.models.User;
import com.project.gamersworld.repo.GroupRepo;
import com.project.gamersworld.repo.MessageRepo;
import com.project.gamersworld.repo.UserRepo;

public class MessageHandlerTest {

    @Mock
    UserRepo mockUserRepo;
    @Mock
    GroupRepo mockGroupRepo;
    @Mock
    MessageRepo mockMessageRepo;
    @InjectMocks
    MessageHandler messageHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        messageHandler = new MessageHandler(mockMessageRepo, mockUserRepo, mockGroupRepo);
    }

    @Test
    void testSendUserMessage() {
        Message message = new Message();
        User user1 = new User();
        User user2 = new User();

        when(mockUserRepo.findByUid(1)).thenReturn(user1);
        when(mockUserRepo.findByUid(2)).thenReturn(user2);
        when(mockMessageRepo.save(Mockito.any(Message.class))).thenReturn(message);
        messageHandler.sendMessage(1, 2, "Test");
        verify(mockMessageRepo, times(1)).save(Mockito.any(Message.class));
    }

    @Test
    void testEditMessage() {
        Message message = new Message();
        User user1 = new User();
        User user2 = new User();
        user1.setUserId(1);
        user2.setUserId(2);
        when(mockUserRepo.findByUid(1)).thenReturn(user1);
        when(mockUserRepo.findByUid(2)).thenReturn(user2);

        when(mockMessageRepo.findByMessageID(0)).thenReturn(message);
        when(mockMessageRepo.save(Mockito.any(Message.class))).thenReturn(message);

        messageHandler.editMessage(message.getMessageID(), "Test");

        verify(mockMessageRepo).save(message);
    }

    @Test
    void testDeleteMessage() {
        Message message = new Message();
        User user1 = new User();
        User user2 = new User();
        user1.setUserId(1);
        user2.setUserId(2);

        when(mockUserRepo.findByUid(1)).thenReturn(user1);
        when(mockUserRepo.findByUid(2)).thenReturn(user2);
        when(mockMessageRepo.findByMessageID(0)).thenReturn(message);

        messageHandler.deleteMessage(message.getMessageID());

        verify(mockMessageRepo).delete(message);
    }

    @Test
    void testSendGroupMessage() {
        Message message = new Message();
        User user1 = new User();
        Group group = new Group();
        List<User> userList = new ArrayList<User>();

        userList.add(new User());
        userList.add(new User());
        group.setMembers(userList);

        userList.add(new User());
        when(mockUserRepo.findByUid(1)).thenReturn(user1);
        when(mockGroupRepo.findByGroupID(5)).thenReturn(group);
        when(mockMessageRepo.save(Mockito.any(Message.class))).thenReturn(message);
        messageHandler.sendMessage(1, 5, "Test");
        verify(mockMessageRepo, times(1)).save(Mockito.any(Message.class));
    }

}
