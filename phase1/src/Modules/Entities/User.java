package Modules.Entities;

import Modules.Exceptions.UserNotFoundException;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * An abstract class that all the types of user entities inherit from
 */
public abstract class User implements Serializable {

    private String username;
    private String password;
    private String userId;
    private ArrayList<String> friendList;

    /**
     * Constructor for User
     * @param username the username of this user
     * @param password the password of this user
     * @param userId the unique ID representing this user
     */
    User(String username, String password, String userId) {
        this.username = username;
        this.password = password;
        this.userId = userId;
        this.friendList = new ArrayList<>();
    }

    /**
     * Returns the username of this user
     * @return the username of this user
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Returns the password of this user
     * @return the password of this user
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Returns the unique userID of this user
     * @return the userID of this user
     */
    public String getID() {
        return this.userId;
    }

    /**
     * Returns a shallow copy of the list of userIDs of the users in this user's friendList
     * @return the list of userIDs of the users in this user's friendList
     */
    public ArrayList<String> getFriendList() {
        /*
        Returns a shallow copy of this.friendList
         */
        ArrayList<String> copy = new ArrayList<>();

        for (String userID: this.friendList) {
            copy.add(userID);
        }

        return copy;
    }

    /**
     * Adds the given userID to the list of userIDs in this user's friendList
     * @param userID the userID that is being added
     */
    public void addToFriendList(String userID) {
        /*
        Precondition: String <userId> is not already an element of <this.friendList>
         */
        this.friendList.add(userID);
    }

    /**
     * Removes given userID from this user's friendList
     * @param userID the userID to remove
     * @throws UserNotFoundException if userID is not in this user's friendList
     */
    public void removeFromFriendList(String userID) {
        /*
        Precondition: String <userId> is an element of <this.friendList>
        */
        if (this.friendList.contains(userID)){
            this.friendList.remove(userID);
        }
        else{
            throw new UserNotFoundException();
        }

    }

}
