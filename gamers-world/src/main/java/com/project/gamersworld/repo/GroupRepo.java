package com.project.gamersworld.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.gamersworld.models.Group;

public interface GroupRepo extends JpaRepository<Group, Long> {

    public Group findByGroupID(int groupID);

    public Group findByName(String name);

    public List<Group> findByDescriptionContaining(String description);

    public List<Group> findByNameContaining(String name);

}
