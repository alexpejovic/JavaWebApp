package Modules.Entities;

import java.util.ArrayList;

public abstract class User {

    private String username;
    private String password;
    private Integer userId;
    private ArrayList<Integer> friendList;

    public User(String username, String password, Integer userId) {
        this.username = username;
        this.password = password;
        this.userId = userId;
        this.friendList = new ArrayList<Integer>();
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public ArrayList<Integer> getFriendList() {
        /*
        Returns a shallow copy of this.friendList
         */
        ArrayList<Integer> copy = new ArrayList<Integer>();

        for (int i = 0; i < this.friendList.size(); i++) {
            copy.set(i, this.friendList.get(i));
        }

        return copy;
    }

    public void addToFriendList(Integer userId) {
        /*
        Precondition: Integer <userId> is not already an element of <this.friendList>
         */
        this.friendList.add(userId);
    }

    public void removeFromFriendList(Integer userId) {
        /*
        Precondition: Integer <userId> is an element of <this.friendList>
         */
        this.friendList.remove(userId);
    }

}
