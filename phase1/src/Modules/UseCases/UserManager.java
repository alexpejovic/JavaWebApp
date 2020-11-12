package Modules.UseCases;

import Modules.Entities.User;

import java.util.ArrayList;


/** UseCase for basic management of common user actions specified in {@link Modules.Entities.User}.
 */
public abstract class UserManager {

    /**
     * Checks if the password entered by user matches password on file
     * @param username the username of the account whose password we want to check
     * @param password the password entered that we want to compare to password on file
     * @return true if there is a account with username and entered password matches, false otherwise
     */
    public abstract boolean validatePassword(String password, String username);


    /**
     * Returns whether a particular user is a particular user type
     * @param username the username of the User in question
     * @return true iff a particular speaker is particular user type
     */
    public abstract boolean isUser(String username);

    /**
     * Returns the specific User with username
     * @param username the username we want to check
     * @return the specific User that has the given username
     */
    public abstract User getUser(String username);


    /** Checks if user1 is able to message user2
     *
     * @param user1 User intending to send message
     * @param user2 User to receive message
     * @return Returns true if user2 is in the friends list of user1, false otherwise
     */
    public boolean canMessage(User user1, User user2){

        return user1.getFriendList().contains(user2.getID());
    }

}
