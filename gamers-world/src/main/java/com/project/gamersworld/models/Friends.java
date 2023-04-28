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
    @Column(name = "uid")
    int uid;

    @Column(name = "user_friend_uid")
    int user_friend_uid;

    public int getUser_friend_uid() {
        return user_friend_uid;
    }

    public int getUID()
    {
        return this.uid;
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
