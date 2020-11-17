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
    public void testIsUniqueIDUsername(){
        Attendee attendee0 = new Attendee("at1", "pass", "a0");
        ArrayList<Attendee> attendees = new ArrayList<>();
        attendees.add(attendee0);

        AttendeeManager attendeeManager = new AttendeeManager(attendees);

        assertTrue(attendeeManager.isUniqueIDUsername(attendees, "at2", "a1"));
    }

    @Test (expected = NonUniqueIdException.class)
    public void testIsUniqueIDUsernameNonUniqueID(){
        Attendee attendee0 = new Attendee("at1", "pass", "a0");
        ArrayList<Attendee> attendees = new ArrayList<>();
        attendees.add(attendee0);

        AttendeeManager attendeeManager = new AttendeeManager(attendees);

        // same id as attendee0
        attendeeManager.isUniqueIDUsername(attendees, "at2", "a0");
    }

    @Test (expected = NonUniqueUsernameException.class)
    public void testIsUniqueIDUsernameNonUniqueUsername(){
        Attendee attendee0 = new Attendee("at1", "pass", "a0");
        ArrayList<Attendee> attendees = new ArrayList<>();
        attendees.add(attendee0);

        AttendeeManager attendeeManager = new AttendeeManager(attendees);

        // same username as attendee0
        attendeeManager.isUniqueIDUsername(attendees, "at1", "a1");
    }

}