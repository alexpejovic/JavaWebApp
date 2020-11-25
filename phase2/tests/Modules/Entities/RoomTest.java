package Modules.Entities;

import Modules.Exceptions.EventNotFoundException;
import org.junit.Test;


import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class RoomTest {

    @Test
    // Testing getters for capacity and ID
    public void testGetters(){
        LocalDateTime eventDate = LocalDateTime.of(2020, 11, 3, 11, 11);
        Room rm = new Room("r0",2);

        // getCapacity()
        assertEquals(2, rm.getCapacity());
        // getID() - room number of room
        assertEquals("r0", rm.getRoomNumber());


    }

    @Test(expected = EventNotFoundException.class)
    // Testing methods related to events
    public void testEvents(){
        LocalDateTime eventDate = LocalDateTime.of(2020, 11, 3, 11, 11);
        Room rm = new Room("r0",2);

        // getEvent()
        // event should be initialized with empty event list
        assertEquals(new ArrayList<String>(), rm.getEvents());

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

    @Test
    // test equals()
    public void testEquals(){
        Room rm1 = new Room("r0",2);
        Room rm2 = new Room("r0", 2);
        Room rm3 = new Room("r1",5);
        Room rm4 = new Room("r1", 2);

        assertTrue(rm1.equals(rm1));
        assertTrue(rm1.equals(rm2));
        assertFalse(rm1.equals(rm3)); // different capacity
        assertFalse(rm1.equals(rm4)); // different id

        rm1.addEvent("e1");
        assertFalse(rm1.equals(rm2)); // different events

        rm2.addEvent("e1");
        assertTrue(rm1.equals(rm2));

        rm1.addEvent("e2");
        rm1.addEvent("e3");
        rm2.addEvent("e3");
        rm2.addEvent("e2");
        assertTrue(rm1.equals(rm2)); // same events but different order
    }


}