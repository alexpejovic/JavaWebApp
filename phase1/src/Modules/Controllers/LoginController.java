package Modules.Controllers;

import Modules.Entities.Attendee;
import Modules.Entities.User;
import Modules.Exceptions.UserNotFoundException;
import Modules.UseCases.AttendeeManager;
import Modules.UseCases.OrganizerManager;
import Modules.UseCases.SpeakerManager;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Controller that handles Logging in and Signing up for User accounts
 */
public class LoginController {

    private AttendeeManager attendeeManager;
    private OrganizerManager organizerManager;
    private SpeakerManager speakerManager;

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

    /**
     * Returns whether or not there is a matching username and password combination
     * within all the Users in this conference
     * @param username the username we want to check
     * @param password the password we want to check
     * @return true iff there is a User with the matching username and password.
     */
    public boolean validateUsernamePassword(String username, String password){
        //TODO: need the user managers to implement way to check password otherwise accessing
        // entities would be violation of clean architecture

        // checking if this user is an attendee
        if (attendeeManager.isUser(username)){
            return attendeeManager.validatePassword(password, username);
        }
        // checking if this user is an organizer
        else if (organizerManager.isUser(username)){
            return attendeeManager.validatePassword(password, username);
        }
        // checking if this user is an speaker
        else if (speakerManager.isUser(username)){
            return attendeeManager.validatePassword(password, username);
        }

        throw new UserNotFoundException();

    }

    /**
     * Returns the User with this username
     * Precondition: there is a User in this conference with the given username
     * @param username the username of the User we want
     * @return the User we are looking for
     */
    public User returnUser(String username){
        // checking if this user is an attendee
        if (attendeeManager.isUser(username)){
            return attendeeManager.getUser(username);
        }
        // checking if this user is an organizer
        else if (organizerManager.isUser(username)){
            return organizerManager.getUser(username);
        }
        // checking if this user is an speaker
        else if (speakerManager.isUser(username)){
            return organizerManager.getUser(username);
        }
        //throws a exception if there is no user with the given username
        throw new UserNotFoundException();
    }


}
