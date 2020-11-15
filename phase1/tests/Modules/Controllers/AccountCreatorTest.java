package Modules.Controllers;

import Modules.UseCases.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class AccountCreatorTest {

    @Test
    public void testCreateSpeakerAccount(){
        AttendeeManager attendeeManager = new AttendeeManager(new ArrayList<>());
        SpeakerManager speakerManager = new SpeakerManager(new ArrayList<>());

        OrganizerManager organizerManager = new OrganizerManager(new EventManager(new ArrayList<>()),
                new RoomManager(new ArrayList<>()),
                new ArrayList<>());

        AccountCreator accountCreator = new AccountCreator(organizerManager,attendeeManager,speakerManager);

        assertTrue(accountCreator.createSpeakerAccount("sp","pass", new ArrayList<>()));
        assertTrue(speakerManager.isUser("sp"));
        assertTrue(speakerManager.validatePassword("sp","pass"));
    }

    @Test
    public void testCreateAttendeeAccount(){
        AttendeeManager attendeeManager = new AttendeeManager(new ArrayList<>());
        SpeakerManager speakerManager = new SpeakerManager(new ArrayList<>());

        OrganizerManager organizerManager = new OrganizerManager(new EventManager(new ArrayList<>()),
                new RoomManager(new ArrayList<>()),
                new ArrayList<>());

        AccountCreator accountCreator = new AccountCreator(organizerManager,attendeeManager,speakerManager);

        assertTrue(accountCreator.createAttendeeAccount("at","pass", new ArrayList<>()));
        assertTrue(attendeeManager.isUser("at"));
        assertTrue(attendeeManager.validatePassword("at","pass"));
    }

    @Test
    public void testCreateOrganizerAccount(){
        AttendeeManager attendeeManager = new AttendeeManager(new ArrayList<>());
        SpeakerManager speakerManager = new SpeakerManager(new ArrayList<>());

        OrganizerManager organizerManager = new OrganizerManager(new EventManager(new ArrayList<>()),
                new RoomManager(new ArrayList<>()),
                new ArrayList<>());

        AccountCreator accountCreator = new AccountCreator(organizerManager,attendeeManager,speakerManager);

        assertTrue(accountCreator.createOrganizerAccount("org","pass"));
        assertTrue(organizerManager.isUser("org"));
        assertTrue(organizerManager.validatePassword("org","pass"));
    }

}
