package com.project.gamersworld.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.gamersworld.models.FriendRequest;

public interface FriendRequestRepo extends JpaRepository<FriendRequest, Long> {

   public FriendRequest findByRequestID(int id);

}
