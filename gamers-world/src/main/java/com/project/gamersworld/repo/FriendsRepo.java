package com.project.gamersworld.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.gamersworld.models.Friends;

public interface FriendsRepo extends JpaRepository<Friends, Long> {

   public Friends findByUid(int uid);
}
