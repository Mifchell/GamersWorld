package com.project.gamersworld.handlers;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
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
import com.project.gamersworld.models.Profile;
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
    void setUp()
    {
        MockitoAnnotations.openMocks(this);

        messageHandler = new MessageHandler(mockMessageRepo, mockUserRepo, mockGroupRepo);
    }

    @Test
    void testSendUserMessage()
    {
        Message message = new Message();
        User user1 = new User();
        User user2 = new User();

        when(mockUserRepo.findByUid(1)).thenReturn(user1);
        when(mockUserRepo.findByUid(2)).thenReturn(user2);
        when(mockMessageRepo.save(Mockito.any(Message.class))).thenReturn(message);
        messageHandler.sendMessage(1, 2, "Test");
        verify(mockMessageRepo,times(1)).save(Mockito.any(Message.class));
    }

    
}
