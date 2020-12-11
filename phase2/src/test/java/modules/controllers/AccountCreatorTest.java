package modules.controllers;

import modules.exceptions.NonUniqueUsernameException;
import modules.gateways.EventGateway;
import modules.gateways.MessageGateway;
import modules.gateways.RoomGateway;
import modules.gateways.UserGateway;
import modules.usecases.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

// tests would affect database info if connected to database
// tests pass but since we are not connecting there is an SQLException message that is printed
public class AccountCreatorTest {
    UpdateInfo updateInfo = new UpdateInfo(new MessageGateway(),new EventGateway(), new UserGateway(), new RoomGateway());

    @Test (expected = NonUniqueUsernameException.class)
    public void testCreateSpeakerAccount(){
        AttendeeManager attendeeManager = new AttendeeManager(new ArrayList<>());
        SpeakerManager speakerManager = new SpeakerManager(new ArrayList<>());

        OrganizerManager organizerManager = new OrganizerManager(new ArrayList<>());

        AccountCreator accountCreator = new AccountCreator(organizerManager,attendeeManager,speakerManager,updateInfo);

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
        
        AccountCreator accountCreator = new AccountCreator(organizerManager,attendeeManager,speakerManager,updateInfo);

        assertTrue(accountCreator.createAttendeeAccount("at","pass", new ArrayList<>(), false));
        assertTrue(attendeeManager.isUser("at"));
        assertTrue(attendeeManager.validatePassword("at","pass"));

        // exception should be thrown if username is not unique
        accountCreator.createAttendeeAccount("at", "pass", new ArrayList<>(), false);
    }

    @Test (expected = NonUniqueUsernameException.class)
    public void testCreateOrganizerAccount(){
        AttendeeManager attendeeManager = new AttendeeManager(new ArrayList<>());
        SpeakerManager speakerManager = new SpeakerManager(new ArrayList<>());

        OrganizerManager organizerManager = new OrganizerManager(new ArrayList<>());

        AccountCreator accountCreator = new AccountCreator(organizerManager,attendeeManager,speakerManager,updateInfo);

        assertTrue(accountCreator.createOrganizerAccount("org","pass"));
        assertTrue(organizerManager.isUser("org"));
        assertTrue(organizerManager.validatePassword("org","pass"));

        // exception should be thrown if username is not unique
        accountCreator.createOrganizerAccount("org", "pass");
    }

}
