package modules.usecases;

import static org.junit.Assert.*;

import modules.entities.Attendee;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;


public class AttendeeManagerTest {

    @Test
    public void testAttendeeManager(){
        AttendeeManager attendeeManager = new AttendeeManager(new ArrayList<Attendee>());
    }

    @Test
    public void testAddAttendee(){
        AttendeeManager attendeeManager = new AttendeeManager(new ArrayList<>());
        attendeeManager.addAttendee("username", "password", "a1234", new ArrayList<>());
        assertEquals("username", attendeeManager.getAttendeeList().get(0).getUsername());
        Attendee attendee = new Attendee("username2", "password2", "a2345");
        attendeeManager.addAttendee(attendee);
        assertEquals("username2", attendeeManager.getAttendeeList().get(1).getUsername());
    }

    @Test
    public void testTimeAvailable(){
        AttendeeManager attendeeManager = new AttendeeManager(new ArrayList<>());
        Attendee attendee = new Attendee("username", "password", "a1234");
        attendeeManager.addAttendee(attendee);
        EventManager eventManager = new EventManager(new ArrayList<>());
        LocalDateTime time1 = LocalDateTime.of(2020, 1, 1, 1, 0);
        LocalDateTime time2 = LocalDateTime.of(2020, 1, 1, 2, 0);
        assertEquals(true, attendeeManager.timeAvailable(attendee.getID(), time1, time2, eventManager));
        eventManager.createEvent("1", time1, time2, "e1234",2);
        attendee.addEvent("e1234");
        assertEquals(false, attendeeManager.timeAvailable(attendee.getID(), time1, time2, eventManager));
    }

    @Test
    public void testAddEventToAttendee(){
        AttendeeManager attendeeManager = new AttendeeManager(new ArrayList<>());
        LocalDateTime time1 = LocalDateTime.of(2020, 1, 1, 1, 0);
        LocalDateTime time2 = LocalDateTime.of(2020, 1, 1, 2, 0);
        Attendee attendee = new Attendee("username", "password", "a1234");
        attendeeManager.addAttendee(attendee);
        EventManager eventManager = new EventManager(new ArrayList<>());
        eventManager.createEvent("1", time1, time2, "e1234",2);
        attendeeManager.addEventToAttendee(attendee.getID(), eventManager.getEventList().get(0).getID());
        assertEquals("e1234", attendee.getEventsList().get(0));
    }

    @Test
    public void testGetAttendeeList(){
        AttendeeManager attendeeManager = new AttendeeManager(new ArrayList<>());
        attendeeManager.addAttendee("username", "password", "a1234", new ArrayList<>());
        attendeeManager.addAttendee("username2", "password2", "a2345", new ArrayList<>());
        assertEquals("username", attendeeManager.getAttendeeList().get(0).getUsername());
        assertEquals("username2", attendeeManager.getAttendeeList().get(1).getUsername());
    }

    @Test
    public void testIsUser(){
        AttendeeManager attendeeManager = new AttendeeManager(new ArrayList<>());
        attendeeManager.addAttendee("username", "password", "a1234", new ArrayList<>());
        assertEquals(true, attendeeManager.isUser("username"));
        assertEquals(false, attendeeManager.isUser("username2"));
    }

    @Test
    public void testValidatePassword(){
        AttendeeManager attendeeManager = new AttendeeManager(new ArrayList<>());
        attendeeManager.addAttendee("username", "password", "a1234", new ArrayList<>());
        assertEquals(true, attendeeManager.validatePassword("username", "password"));
        assertEquals(false, attendeeManager.validatePassword("username", "wrongpassword"));
    }

    @Test
    public void testGetUserID(){
        AttendeeManager attendeeManager = new AttendeeManager(new ArrayList<>());
        attendeeManager.addAttendee("username", "password", "a1234", new ArrayList<>());
        assertEquals("a1234", attendeeManager.getUserID("username"));
    }

    @Test
    public void testGetAttendee(){
        AttendeeManager attendeeManager = new AttendeeManager(new ArrayList<>());
        attendeeManager.addAttendee("username", "password", "a1234", new ArrayList<>());
        assertEquals("username", attendeeManager.getAttendee("a1234").getUsername());
    }
}