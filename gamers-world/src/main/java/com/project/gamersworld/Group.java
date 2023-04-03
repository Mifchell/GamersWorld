package com.project.gamersworld;

import java.util.ArrayList;

import javax.persistence.*;

@Entity
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int groupID;

    // @ManyToMany(mappedBy = "groupList")
    ArrayList<User> members;
    User creator;
    String description;

    public Group() {

    }

    public int getGroupID() {
        return this.groupID;
    }

    public ArrayList<User> getMembers() {
        return this.members;
    }

    public void setMembers(ArrayList<User> members) {
        this.members = members;
    }

    public User getCreator() {
        return this.creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
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
