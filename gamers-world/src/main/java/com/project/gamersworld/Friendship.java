// package com.project.gamersworld;

// public class Friendship {
//     User user;

//     public Friendship(User user) {
//         this.user = user;
//     }

//     public User getUser() {
//         return this.user;
//     }

//     public void setUser(User user) {
//         this.user = user;
//     }

//     /*
//      * @param the user to add to the friendList
//      * Add the given user to the friendList
//      */
//     public void addFriend(User userToAdd) {
//         user.friendsList.add(userToAdd);
//     }

//     /*
//      * @param the user to remove from the friendList
//      * Remove the given user from the friendList
//      */
//     public void removeFriend(User userToRemove) {
//         user.friendsList.remove(userToRemove);
//     }

//     /*
//      * @param the user to remove from the friendList
//      * Add the given user to the BlockedList,
//      * The User won't receive any more mess
//      */
//     public void blockUser(User userToBlock) {
//         if (user.friendsList.contains(userToBlock))
//             user.friendsList.remove(userToBlock);
//         user.blockedUsers.add(userToBlock);
//     }

// }
