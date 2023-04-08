package com.project.gamersworld;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepo extends JpaRepository<Group, Long> {

    public List<Group> findByGroupID(int groupID);

    public List<Group> findByDescriptionContaining(String description);

}
