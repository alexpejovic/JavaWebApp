package modules.usecases;

import modules.entities.User;
import modules.exceptions.NonUniqueIdException;

import java.util.ArrayList;


/** UseCase for basic management of common user actions specified in {@link modules.entities.User}.
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
     * Returns the specific userID matching a username
     * @param username the username we want to check
     * @return the specific userID of a User that has the given username
     */
    public abstract String getUserID(String username);


    /** Checks if user1 is able to message user2
     *
     * @param user1 User intending to send message
     * @param user2 User to receive message
     * @return Returns true if user2 is in the friends list of user1, false otherwise
     */
    public boolean canMessage(User user1, User user2){

        return user1.getFriendList().contains(user2.getID());
    }

    /**
     * Helper method to check if a given user ID is unique
     * @param users the list of users of a specific type to check
     * @return true if and only if the userID is unique
     * @throws NonUniqueIdException if there is already a user with that ID in users
     */
     boolean isUniqueID(ArrayList users, String userID){
         for (int i = 0; i < users.size(); i++) {
            User user = (User)users.get(i);
            if (user.getID().equals(userID)){
                throw new NonUniqueIdException();
            }
        }
        return true;
    }

    /**
     * Helper method for creating accounts to check if a given username is unique
     * @param users the list of users of a specific type to check
     * @return true if and only if the userID is unique
     */
    public boolean isUniqueUsername(ArrayList users, String username){
        for (int i = 0; i < users.size(); i++) {
            User user = (User)users.get(i);
            if (user.getUsername().equals(username)){
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the specific username matching a user ID
     * @param userId the user ID we want to check
     * @return the specific username of a User that has the given user ID
     */
    public abstract String getUsername(String userId);

}
