package com.project.gamersworld.models;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "friends")
public class Friends {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "uid")
    int uid;
    int user_friend_uid;

    public int getUser_friend_uid() {
        return user_friend_uid;
    }

    public Friends()
    {

    }

    public Friends(int user1, int user2)
    {
        this.uid = user1;
        this.user_friend_uid = user2;
    }
}
