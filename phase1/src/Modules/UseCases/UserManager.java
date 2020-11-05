package Modules.UseCases;

import Modules.Entities.User;


/** UseCase for basic management of common user actions specified in {@link Modules.Entities.User}.
 */
public abstract class UserManager {

    /** Validates password of user
     *
     * @param password String representation of a password
     * @param user User whose password we wish to validate
     * @return Returns true if the user's password is password, false otherwise
     */
    public boolean validatePassword(String password, User user){

        return password.equals(user.getPassword());
    }

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
