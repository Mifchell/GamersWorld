package com.project.gamersworld.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.gamersworld.models.Message;

public interface MessageRepo extends JpaRepository<Message, Long> {

   public Message findByMessageID(int id);

}
