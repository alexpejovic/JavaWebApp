package Modules.Entities;

import static org.junit.Assert.*;

import Modules.Exceptions.EventNotFoundException;
import org.junit.Test;

import java.util.ArrayList;

public class AttendeeTest {

    private RuntimeException EventNotFoundException;

    //test Attendee constructor
    @Test
    public void testAttendee(){
        Attendee a1 = new Attendee("fakeuser", "password", "a1234");
    }

    @Test
    public void testChangingEventsList() throws Exception {
        Attendee a1 = new Attendee("fakeuser", "password", "a1234");
        a1.addEvent("e1234");
        ArrayList<String> example1 = new ArrayList<String>();
        example1.add("e1234");
        assertEquals(example1, a1.getEventsList());
        a1.removeEvent("e1234");
        example1.remove(0);
        assertEquals(example1, a1.getEventsList());
    }

    @Test
    public void testGetEventsList(){
        Attendee a1 = new Attendee("fakeuser", "password", "a1234");
        a1.addEvent("e1234");
        a1.addEvent("e2345");
        ArrayList<String> example1 = new ArrayList<String>();
        example1.add("e1234");
        example1.add("e2345");
        assertEquals(example1, a1.getEventsList());
    }

    @Test
    public void testBooleanMethods(){
        Attendee a1 = new Attendee("fakeuser", "password", "a1234");
        assertEquals(true, a1.hasNoEvents());
        a1.addEvent("e1234");
        assertEquals(false, a1.hasNoEvents());
        assertEquals(true, a1.alreadyAttendingEvent("e1234"));
        assertEquals(false, a1.alreadyAttendingEvent("e2345"));
    }
}