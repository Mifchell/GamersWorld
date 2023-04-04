package com.project.gamersworld;

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
