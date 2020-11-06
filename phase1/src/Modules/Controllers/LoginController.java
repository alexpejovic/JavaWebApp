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


    public boolean validateUsernamePassword(String username, String password){
        //TODO: need the user managers to implement way to check password otherwise accessing
        // entities would be violation of clean architecture

//        // checking if this user is an attendee
//        if (attendeeManager.isUser(username)){
//            return attendeeManager.validatePassword(username);
//        }
//        // checking if this user is an organizer
//        else if (organizerManager.isUser(username)){
//            return attendeeManager.validatePassword(username);
//        }
//        // checking if this user is an speaker
//        else if (speakerManager.isUser(username)){
//            return attendeeManager.validatePassword(username);
//        }

        // originally wanted to do something like this but this would be the responsibility of use cases
        // can't access entity methods
//        ArrayList<Attendee> attendees = attendeeManager.getAttendeeList();
//        for (Attendee attendee: attendees){
//            if (attendee.getUsername().equals(username)){
//                return attendee.getPassword().equals(password);
//            }
//        }
//        // ... same for organizers and speakers

        throw new UserNotFoundException();



    }


}
