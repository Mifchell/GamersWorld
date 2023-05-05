package com.project.gamersworld.handlers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.gamersworld.models.Group;
import com.project.gamersworld.models.User;
import com.project.gamersworld.repo.GroupRepo;
import com.project.gamersworld.repo.UserRepo;

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
    public List<Group> groupSearch(String filter, User user) {

        Set<Group> returnList = new HashSet<Group>();

        List<Group> userList = new ArrayList<Group>();
        if (user.getGroupList() != null) {
            userList = user.getGroupList();
        }

        if (filter.equals("")) {
            returnList = groupRepository.findAll().stream().collect(Collectors.toSet());
        } else {
            returnList.addAll(groupRepository.findByDescriptionContaining(filter));
            returnList.addAll(groupRepository.findByNameContaining(filter));
        }
        for (Group groups : userList) {
            returnList.remove(groups);
        }

        return returnList.stream().collect(Collectors.toList());
    }

    public List<Group> myGroups(User user) {
        List<Group> mygroups = new ArrayList<Group>();
        if (user.getGroupList() != null) {
            mygroups = user.getGroupList();
        }
        return mygroups;
    }

    public List<Group> groupOwned(User user) {
        List<Group> groupOwned = new ArrayList<Group>();
        for (Group group : myGroups(user)) {
            if (group.getCreatorID() == user.getUserID()) {
                groupOwned.add(group);
            }
        }
        return groupOwned;
    }

    public boolean createGroup(String name, String description, User user) {

        User creator = new User(user);

        if (groupRepository.findByName(name) != null) {
            return false;
        }

        Group group = new Group(name, creator, description);

        List<Group> groupList = new ArrayList<Group>();
        if (creator.getGroupList() != null) {
            groupList = creator.getGroupList();
        }
        groupList.add(group);
        List<User> members = new ArrayList<User>();
        members.add(creator);

        group.setMembers(members);

        userRepository.save(creator);

        // groupRepository.save(group);

        return true;

    }

    public Group editGroup(int groupId, String name, String description) {

        if (groupRepository.findByName(name) != null && groupRepository.findByGroupID(groupId).getName() != name) {
            return null;
        }

        Group group = new Group(groupRepository.findByGroupID(groupId));
        group.setName(name);
        group.setDescription(description);

        groupRepository.save(group);
        return group;
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
        List<User> groupMembers = group.getMembers();
        for (int i = 0; i < groupMembers.size(); i++) {
            User user = groupMembers.get(i);
            user.getGroupList().remove(group);
            groupMembers.remove(i);
            userRepository.save(user);

        }

        groupRepository.delete(group);
    }

    public User leaveGroup(User user, int groupID) {

        Group group = new Group(groupRepository.findByGroupID(groupID));
        List<User> memberList = group.getMembers();
        memberList.remove(user);
        group.setMembers(memberList);
        if (group.getCreatorID() == user.getUserID()) {
            if (memberList.isEmpty()) {
                deleteGroup(groupID);
                return user;
            } else {
                group.setCreator(memberList.get(0).getUserID());
            }
        }
        List<Group> groupList = user.getGroupList();
        for (int i = 0; i < groupList.size(); i++) {
            if (groupList.get(i).getGroupID() == group.getGroupID()) {
                groupList.remove(i);
            }
        }
        user.setGroupList(groupList);
        userRepository.save(user);
        groupRepository.save(group);

        return user;

    }

}
