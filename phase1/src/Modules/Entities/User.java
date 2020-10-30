package Modules.Entities;

import java.util.ArrayList;

public abstract class User implements Identifiable{

    private String username;
    private String password;
    private String userId;
    private ArrayList<User> friendList;

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

    @Override
    public String getID() {
        return this.userId;
    }

    @Override
    public void setID(String ID) {
        this.userId = ID;
    }

    public ArrayList<User> getFriendList() {
        /*
        Returns a shallow copy of this.friendList
         */
        ArrayList<User> copy = new ArrayList<>();

        for (int i = 0; i < this.friendList.size(); i++) {
            copy.set(i, this.friendList.get(i));
        }

        return copy;
    }

    public void addToFriendList(User user) {
        /*
        Precondition: Integer <userId> is not already an element of <this.friendList>
         */
        this.friendList.add(user);
    }

    public void removeFromFriendList(User user) {
        /*
        Precondition: Integer <userId> is an element of <this.friendList>
         */
        this.friendList.remove(user);
    }

}
