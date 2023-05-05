package com.project.gamersworld.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "groupes")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int groupID;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "groupList")
    List<User> members;
    int creatorID;
    String description;

    public Group() {
        this.members = new ArrayList<User>();

    }

    public Group(String name, User creator, String description) {
        this.name = name;
        this.members = new ArrayList<User>();
        this.members.add(creator);
        this.creatorID = creator.getUserID();
        this.description = description;
    }

    public Group(Group group) {
        this.groupID = group.getGroupID();
        this.name = group.getName();
        this.members = group.getMembers();
        this.creatorID = group.getCreatorID();
        this.description = group.getDescription();
    }

    public int getGroupID() {
        return this.groupID;
    }

    public void setGroupID(int id) {// for junit
        this.groupID = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getMembers() {
        return this.members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public int getCreatorID() {
        return this.creatorID;
    }

    public void setCreator(int creatorID) {
        this.creatorID = creatorID;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return "groupName: " + this.name;
    }

    public Object thenReturn(Object object) {
        return null;
    }
}
