package Modules.Entities;

import java.util.ArrayList;
import java.util.UUID;

public abstract class User {

    private String username;
    private String password;
    private UUID userId;
    private ArrayList<UUID> friendList;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.userId = UUID.randomUUID();
        this.friendList = new ArrayList<UUID>();
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public UUID getUserId() {
        return this.userId;
    }

    public ArrayList<UUID> getFriendList() {
        /*
        Returns a shallow copy of this.friendList
         */
        ArrayList<UUID> copy = new ArrayList<UUID>();

        for (int i = 0; i < this.friendList.size(); i++) {
            copy.set(i, this.friendList.get(i));
        }

        return copy;
    }

    public void addToFriendList(UUID userId) {
        /*
        Precondition: UUID <userId> is not already an element of <this.friendList>
         */
        this.friendList.add(userId);
    }

    public void removeFromFriendList(UUID userId) {
        /*
        Precondition: String <userId> is an element of <this.friendList>
         */
        this.friendList.remove(userId);
    }

}
