package Modules.Controllers;

import Modules.UseCases.AttendeeManager;
import Modules.UseCases.OrganizerManager;
import Modules.UseCases.SpeakerManager;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Controller that creates new Users
 */
public class AccountCreator {
    private AttendeeManager attendeeManager;
    private OrganizerManager organizerManager;
    private SpeakerManager speakerManager;

    /**
     * Constructor for AccountCreator
     * @param attendeeManager the AttendeeManager use case for this conference
     * @param organizerManager the OrganizerManager use case for this conference
     * @param speakerManager the SpeakerManager use case for this conference
     */
    public AccountCreator(OrganizerManager organizerManager,
                           AttendeeManager attendeeManager,
                           SpeakerManager speakerManager){
        this.attendeeManager = attendeeManager;
        this.organizerManager = organizerManager;
        this.speakerManager = speakerManager;
    }

    /**
     * Creates a new Speaker
     * @param username the username of the Speaker
     * @param password the password of the Speaker
     * @param events a list of event ids that the Speaker is speaking at
     */
    public void createSpeakerAccount(String username, String password, ArrayList<String> events){
        // speaker's id starts with s then has the speaker # starting from 0
        String userId = "s"+ speakerManager.getSpeakers().size();
        speakerManager.addSpeaker(username,password,userId,events);
    }

    /**
     * Creates a new Attendee
     * @param username the username of the Attendee
     * @param password the password of the Attendee
     * @param events a list of event ids that the Attendee is attending at
     */
    public void createAttendeeAccount(String username, String password, ArrayList<String> events){
        // speaker's id starts with s then has the speaker # starting from 0
        String userId = "a"+ attendeeManager.getAttendeeList().size();
        attendeeManager.addAttendee(username,password,userId,events);
    }

    // seems like a code smell... idk maybe

}
