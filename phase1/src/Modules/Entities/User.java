package Modules.Entities;

import java.util.ArrayList;

public abstract class User implements Identifiable{

    private String username;
    private String password;
    private String userId;
    private ArrayList<String> friendList;

    public User(String username, String password, String userId) {
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

    @Override
    public String getID() {
        return this.userId;
    }

    @Override
    public void setID(String ID) {
        this.userId = ID;
    }

    public ArrayList<String> getFriendList() {
        /*
        Returns a shallow copy of this.friendList
         */
        ArrayList<String> copy = new ArrayList<>();

        for (int i = 0; i < this.friendList.size(); i++) {
            copy.set(i, this.friendList.get(i));
        }

        return copy;
    }

    public void addToFriendList(String userId) {
        /*
        Precondition: Integer <userId> is not already an element of <this.friendList>
         */
        this.friendList.add(userId);
    }

    public void removeFromFriendList(String userId) {
        /*
        Precondition: Integer <userId> is an element of <this.friendList>
         */
        this.friendList.remove(userId);
    }

}
