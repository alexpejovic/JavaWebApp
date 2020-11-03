package Modules.Entities;

import Modules.Exceptions.EventNotFoundException;
import org.junit.Test;
import org.junit.rules.ExpectedException;


import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class RoomTest {


    // Testing getters for capacity and ID
    @Test
    public void testGetters(){
        LocalDateTime eventDate = LocalDateTime.of(2020, 11, 3, 11, 11);
        Room rm = new Room("1",2);

        // getCapacity()
        assertEquals(2, rm.getCapacity());
        // getID() - room number of room
        assertEquals("1", rm.getID());


    }

    @Test(expected = EventNotFoundException.class)
    // Testing methods related to events
    public void testEvents(){
        LocalDateTime eventDate = LocalDateTime.of(2020, 11, 3, 11, 11);
        Room rm = new Room("1",2);

        // getEvent()
        // event should be initialized with empty event list
        assertEquals(new ArrayList<String>(), rm.getEvents());

        Event event1 = new Event("1", eventDate);
        event1.setID("e1");
        Event event2 = new Event("2", eventDate);
        event2.setID("e2");

        // addEvent()
        rm.addEvent("e1");
        ArrayList<String> expected = new ArrayList<>();
        expected.add("e1");
        assertEquals(expected, rm.getEvents());

        // isEventInRoom()
        assertTrue(rm.isEventInRoom("e1"));
        assertFalse(rm.isEventInRoom("e2"));

        // removeEvent()
        rm.removeEvent("e1");
        assertEquals(new ArrayList<String>(), rm.getEvents());

        // should raise EventNotFoundException if we try to remove a eventId that is not in room
        rm.removeEvent("e2");
    }


}