package modules.controllers;

import modules.exceptions.NonUniqueIdException;
import modules.exceptions.NonUniqueUsernameException;
import modules.usecases.AttendeeManager;
import modules.usecases.OrganizerManager;
import modules.usecases.SpeakerManager;

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
     * Creates a new Speaker with unique user ID
     * @param username the username of the Speaker
     * @param password the password of the Speaker
     * @param events a list of event ids that the Speaker is speaking at
     * @return true iff a new Speaker account is created
     */
    public boolean createSpeakerAccount(String username, String password, ArrayList<String> events){
        this.isUniqueUsername(username); // throws exception if username is not unique

        boolean accountCreated = true;
        try{
            // speaker's id starts with s then has the speaker # starting from 0
            String userId = "s"+ speakerManager.NumSpeakers();
            speakerManager.addSpeaker(username,password,userId,events);
        } catch (NonUniqueIdException e){
            accountCreated = false;
        }
        return accountCreated;
    }

    /**
     * Creates a new Attendee with unique user ID
     * @param username the username of the Attendee
     * @param password the password of the Attendee
     * @param events a list of event ids that the Attendee is attending at
     */
    public boolean createAttendeeAccount(String username, String password, ArrayList<String> events){
        this.isUniqueUsername(username); // throws exception if username is not unique

        boolean accountCreated = true;
        try{
            // attendee's id starts with a then has the attendee # starting from 0
            String userId = "a"+ attendeeManager.getAttendeeList().size();
            attendeeManager.addAttendee(username,password,userId,events);
        } catch (NonUniqueIdException e){
            accountCreated = false;
        }
        return accountCreated;

    }

    /**
     * Creates a new Organizer with unique user ID
     * @param username the username of the Organizer
     * @param password the password of the Organizer
     */
    public boolean createOrganizerAccount(String username, String password){
        this.isUniqueUsername(username); // throws exception if username is not unique

        boolean accountCreated = true;
        try{
            // organizer's id starts with a then has the organizer # starting from 0
            String userId = "o"+ organizerManager.getNumberOfOrganizers();
            organizerManager.createOrganizerAccount(username,password,userId);
        } catch (NonUniqueIdException e){
            accountCreated = false;
        }
        return accountCreated;
    }

    /**
     * Private helper to determine if the given username is Unique
     * @throws modules.exceptions.NonUniqueUsernameException if there is another user with username
     */
    private void isUniqueUsername(String username){
        if(!attendeeManager.isUniqueUsername(attendeeManager.getAttendeeList(), username)){
            throw new NonUniqueUsernameException();
        }
        else if (!organizerManager.isUniqueUsername(organizerManager.getListOfOrganizers(), username)){
            throw new NonUniqueUsernameException();
        }
        else if (!speakerManager.isUniqueUsername(speakerManager.getSpeakers(),username)){
            throw new NonUniqueUsernameException();
        }
    }



}
