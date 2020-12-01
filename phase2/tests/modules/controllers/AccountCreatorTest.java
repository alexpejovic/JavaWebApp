package modules.controllers;

import modules.exceptions.NonUniqueUsernameException;
import modules.usecases.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class AccountCreatorTest {

    @Test (expected = NonUniqueUsernameException.class)
    public void testCreateSpeakerAccount(){
        AttendeeManager attendeeManager = new AttendeeManager(new ArrayList<>());
        SpeakerManager speakerManager = new SpeakerManager(new ArrayList<>());

        OrganizerManager organizerManager = new OrganizerManager(new ArrayList<>());

        AccountCreator accountCreator = new AccountCreator(organizerManager,attendeeManager,speakerManager);

        assertTrue(accountCreator.createSpeakerAccount("sp","pass", new ArrayList<>()));
        assertTrue(speakerManager.isUser("sp"));
        assertTrue(speakerManager.validatePassword("sp","pass"));

        // exception should be thrown if username is not unique
        accountCreator.createSpeakerAccount("sp", "pass", new ArrayList<>());
    }


    @Test (expected = NonUniqueUsernameException.class)
    public void testCreateAttendeeAccount(){
        AttendeeManager attendeeManager = new AttendeeManager(new ArrayList<>());
        SpeakerManager speakerManager = new SpeakerManager(new ArrayList<>());

        OrganizerManager organizerManager = new OrganizerManager(new ArrayList<>());
        
        AccountCreator accountCreator = new AccountCreator(organizerManager,attendeeManager,speakerManager);

        assertTrue(accountCreator.createAttendeeAccount("at","pass", new ArrayList<>()));
        assertTrue(attendeeManager.isUser("at"));
        assertTrue(attendeeManager.validatePassword("at","pass"));

        // exception should be thrown if username is not unique
        accountCreator.createAttendeeAccount("at", "pass", new ArrayList<>());
    }

    @Test (expected = NonUniqueUsernameException.class)
    public void testCreateOrganizerAccount(){
        AttendeeManager attendeeManager = new AttendeeManager(new ArrayList<>());
        SpeakerManager speakerManager = new SpeakerManager(new ArrayList<>());

        OrganizerManager organizerManager = new OrganizerManager(new ArrayList<>());

        AccountCreator accountCreator = new AccountCreator(organizerManager,attendeeManager,speakerManager);

        assertTrue(accountCreator.createOrganizerAccount("org","pass"));
        assertTrue(organizerManager.isUser("org"));
        assertTrue(organizerManager.validatePassword("org","pass"));

        // exception should be thrown if username is not unique
        accountCreator.createOrganizerAccount("org", "pass");
    }

}
