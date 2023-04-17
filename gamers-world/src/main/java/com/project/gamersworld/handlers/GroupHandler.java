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

    @Autowired
    UserRepo userRepo;

    public GroupHandler(GroupRepo groupRepository) {
        this.groupRepository = groupRepository;
    }

    public GroupRepo getGroupRepository() {
        return this.groupRepository;
    }

    /*
     * Does a group search based on description or name containing a filter
     */
    public List<Group> groupSearch(String filter) {

        ArrayList<Group> returnList = new ArrayList<Group>();

        if (filter.equals("")) {
            returnList = (ArrayList<Group>) groupRepository.findAll();
        } else {
            returnList = (ArrayList<Group>) groupRepository.findByDescriptionContaining(filter);
            returnList.addAll(groupRepository.findByNameContaining(filter));
        }

        if (returnList.isEmpty()) {
            // do something
        }

        return returnList;
    }

    public void createGroup(String name, String description, int creatorID) {

        try {

            Group group = new Group(name, userRepo.findByUid(creatorID), description);

            groupRepository.save(group);

        } catch (Exception e) {
            e.toString();
        }

    }

    /*
     * @param the User to add
     * add the given User to the member List
     */
    public boolean addMember() {
        return false;
    }

    /*
     * @param the User to remove
     * Remove the given User from the member List
     */
    public boolean removeMember() {
        return false;
    }

    /*
     * @param the User joining
     * 
     * think of deleting this? kinda the same as add member? or putting it in
     * another class to call addMethod
     */
    public boolean join() {
        return false;
    }

    /*
     * @param the group to delete
     * delete the group from the DB
     */
    public boolean deleteGroup() {
        return false;
    }

}
