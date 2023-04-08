package com.project.gamersworld.handlers;

import com.project.gamersworld.models.Group;
import com.project.gamersworld.repo.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupHandler {
    @Autowired
    private GroupRepo groupRepository;

    public GroupRepo getGroupRepository() {
        return this.groupRepository;
    }

    /*
     * Does a group search based on description containing a filter
     */
    public List<Group> groupSearch(String filter) {

        ArrayList<Group> returnList = new ArrayList<Group>();

        if (filter.equals("")) {
            returnList = (ArrayList<Group>) groupRepository.findAll();
        } else {
            returnList = (ArrayList<Group>) groupRepository.findByDescriptionContaining(filter);
        }

        return returnList;
    }

    public void createGroup() {

    }

}
