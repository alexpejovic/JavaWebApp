package Modules.Entities;

import java.util.ArrayList;

public abstract class User {

    private String username;
    private String password;
    private String userId;
    private ArrayList<String> friendList;

    User(String username, String password, String userId) {
        this.username = username;
        this.password = password;
        this.userId = userId;
        this.friendList = new ArrayList<>();
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }


    public String getID() {
        return this.userId;
    }

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

    public void addToFriendList(String userID) {
        /*
        Precondition: String <userId> is not already an element of <this.friendList>
         */
        this.friendList.add(userID);
    }

    public void removeFromFriendList(String userID) {
        /*
        Precondition: String <userId> is an element of <this.friendList>
        */
        this.friendList.remove(userID);
    }

}
