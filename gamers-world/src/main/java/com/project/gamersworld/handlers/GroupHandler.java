package com.project.gamersworld.handlers;

import com.project.gamersworld.models.*;

import com.project.gamersworld.repo.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupHandler {

    @Autowired
    private GroupRepo groupRepository;

    @Autowired
    private UserRepo userRepository;

    public GroupHandler(GroupRepo groupRepository, UserRepo userRepo) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepo;
    }

    public GroupRepo getGroupRepository() {
        return this.groupRepository;
    }

    /*
     * Does a group search based on description or name containing a filter
     */
    public List<Group> groupSearch(String filter) {

        Set<Group> returnList = new HashSet<Group>();

        if (filter.equals("")) {
            returnList = groupRepository.findAll().stream().collect(Collectors.toSet());
        } else {
            returnList.addAll(groupRepository.findByDescriptionContaining(filter).stream().collect(Collectors.toSet()));
            returnList.addAll(groupRepository.findByNameContaining(filter));
        }

        if (returnList.isEmpty()) {
            // do something
        }

        return returnList.stream().collect(Collectors.toList());
    }

    public String createGroup(String name, String description, User user) {

        User creator = new User(user);

        if (groupRepository.findByName(name) != null) {
            return null;
        }

        Group group = new Group(name, creator, description);

        List<Group> groupList = new ArrayList<Group>();
        if (creator.getGroupList() != null) {
            groupList = creator.getGroupList();
        }
        groupList.add(group);

        creator.setGroupList(groupList);

        groupRepository.save(group);
        userRepository.save(creator);

        return name;

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
     * @param the groupId of the group to join
     *
     */
    public Group join(int groupId, User user) {

        Group group = new Group(groupRepository.findByGroupID(groupId));
        List<Group> groupList = new ArrayList<Group>();
        if (user.getGroupList() != null) {
            groupList = user.getGroupList();
        }
        boolean flag = false;
        for (Group groups : groupList) {
            if (groups.getGroupID() == group.getGroupID()) {
                flag = true;
            }
        }
        if (!flag) {
            groupList.add(group);
            user.setGroupList(groupList);
            userRepository.save(user);
            groupRepository.save(group);
        }

        return group;
    }

    /*
     * @param the group to delete
     * delete the group from the DB
     */
    public void deleteGroup(int groupID) {
        Group group = groupRepository.findByGroupID(groupID);

        for (User member : group.getMembers()) {
            member.getGroupList().remove(group);
            userRepository.save(member);
        }

        groupRepository.delete(group);
    }

    public User leaveGroup(User user, int groupID) {

        Group group = new Group(groupRepository.findByGroupID(groupID));
        List<User> memberList = group.getMembers();
        if (memberList.contains(user)) {
            memberList.remove(user);
            List<Group> groupList = user.getGroupList();
            groupList.remove(group);
            user.setGroupList(groupList);
            userRepository.save(user);
            groupRepository.save(group);
            return user;
        }
        return null;

    }

}
