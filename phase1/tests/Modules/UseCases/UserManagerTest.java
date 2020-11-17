package Modules.UseCases;

import Modules.Entities.Attendee;
import Modules.Exceptions.NonUniqueIdException;
import Modules.Exceptions.NonUniqueUsernameException;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class UserManagerTest {

    // tests of the inherited methods for UserManager using child AttendeeManager

    @Test
    public void testCanMessage(){
        Attendee attendee0 = new Attendee("at1", "pass", "a0");
        Attendee attendee1 = new Attendee("at2", "pass", "a1");

        attendee0.addToFriendList("a1");

        ArrayList<Attendee> attendees = new ArrayList<>();
        attendees.add(attendee0);
        attendees.add(attendee1);

        AttendeeManager attendeeManager = new AttendeeManager(attendees);

        assertTrue(attendeeManager.canMessage(attendee0,attendee1));
        assertFalse(attendeeManager.canMessage(attendee1,attendee0));
    }

    @Test
    public void testIsUniqueID(){
        Attendee attendee0 = new Attendee("at1", "pass", "a0");
        ArrayList<Attendee> attendees = new ArrayList<>();
        attendees.add(attendee0);

        AttendeeManager attendeeManager = new AttendeeManager(attendees);

        assertTrue(attendeeManager.isUniqueID(attendees, "a1"));

    }

    @Test (expected = NonUniqueIdException.class)
    public void testIsUniqueIDNonUniqueID(){
        Attendee attendee0 = new Attendee("at1", "pass", "a0");
        ArrayList<Attendee> attendees = new ArrayList<>();
        attendees.add(attendee0);

        AttendeeManager attendeeManager = new AttendeeManager(attendees);

        // same id as attendee0
        attendeeManager.isUniqueID(attendees, "a0");
    }

    @Test
    public void testIsUniqueUsername(){
        Attendee attendee0 = new Attendee("at1", "pass", "a0");
        ArrayList<Attendee> attendees = new ArrayList<>();
        attendees.add(attendee0);

        AttendeeManager attendeeManager = new AttendeeManager(attendees);

        // same username as attendee0
        attendeeManager.isUniqueUsername(attendees, "at1");
        // unique username
        assertTrue(attendeeManager.isUniqueUsername(attendees, "at2"));
    }

}