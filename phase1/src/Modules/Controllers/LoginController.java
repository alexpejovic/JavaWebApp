package Modules.Controllers;

import Modules.Entities.User;
import Modules.Exceptions.UserNotFoundException;
import Modules.UseCases.AttendeeManager;
import Modules.UseCases.OrganizerManager;
import Modules.UseCases.SpeakerManager;

/**
 * Controller that handles Logging in and Signing up for User accounts
 */
public class LoginController {

    private AttendeeManager attendeeManager;
    private OrganizerManager organizerManager;
    private SpeakerManager speakerManager;

    /** the unique userID of the User logged into this program*/
    private String loggedUser;

    /**
     * Constructor for LoginController
     * @param attendeeManager the AttendeeManager use case for this conference
     * @param organizerManager the OrganizerManager use case for this conference
     * @param speakerManager the SpeakerManager use case for this conference
     */
    public LoginController(AttendeeManager attendeeManager,
                           OrganizerManager organizerManager,
                           SpeakerManager speakerManager){
        this.attendeeManager = attendeeManager;
        this.organizerManager = organizerManager;
        this.speakerManager = speakerManager;
    }

    /** Logs in a user into their account. If log-in is successful, User information is stored in loggedUser.
     *
     * @param username Username of user intending to log-in
     * @param password Password of user intending to log-in
     * @return True if log-in is successful, false otherwise
     */
    public boolean logIn(String username, String password){

        if(validateUsernamePassword(username, password)) {
            loggedUser = returnUserID(username);
            return true;
        }
        else
            return false;
    }

    /** Getter for userID of User who is currently logged-in
     *
     * @return The userID of User currently logged-in
     */
    public String getLoggedUser() {
        return loggedUser;
    }

    /**
     * Returns whether or not there is a matching username and password combination
     * within all the Users in this conference
     * @param username the username we want to check
     * @param password the password we want to check
     * @return true iff there is a User with the matching username and password.
     */
    public boolean validateUsernamePassword(String username, String password){

        // checking if this user is an attendee
        if (attendeeManager.isUser(username)){
            return attendeeManager.validatePassword(username, password);
        }
        // checking if this user is an organizer
        else if (organizerManager.isUser(username)){
            return organizerManager.validatePassword(username, password);
        }
        // checking if this user is an speaker
        else if (speakerManager.isUser(username)){
            return speakerManager.validatePassword(username, password);
        }
        //user is not found
        return false;
    }

    /**
     * Returns the unique userID of the User with this username
     * Precondition: there is a User in this conference with the given username
     * @param username the username of the User we want
     * @return the userID of the User we are looking for
     */
    private String returnUserID(String username){
        // checking if this user is an attendee
        if (attendeeManager.isUser(username)){
            return attendeeManager.getUserID(username);
        }
        // checking if this user is an organizer
        else if (organizerManager.isUser(username)){
            return organizerManager.getUserID(username);
        }
        // checking if this user is an speaker
        else if (speakerManager.isUser(username)){
            return speakerManager.getUserID(username);
        }
        //throws a exception if there is no user with the given username
        throw new UserNotFoundException();
    }


}
