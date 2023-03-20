package com.project.gamersworld;


abstract class Message{
    private int messageID;
    Date date;
    User sender;
    List<User> reciever;
    public void send();
    public void delete();
    public void edit();
    public void react();

}
