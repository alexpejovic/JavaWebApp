package modules.entities;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class OrganizerTest {

    @Test
    public void testOrganizer() {
        Organizer o = new Organizer("username", "password", "userID");
    }

    @Test
    public void testGetters() {
        Organizer o = new Organizer("o", "oiscool", "1");
        ArrayList<String> expected = new ArrayList<>();
        assertEquals(expected, o.getManagedEvents());

        o.addManagedEvent("Event1");
        expected.add("Event1");
        assertEquals(expected, o.getManagedEvents());

        o.addManagedEvent("Event2");
        o.addManagedEvent("Event3");
        expected.add("Event2");
        expected.add("Event3");
        assertEquals(expected, o.getManagedEvents());
    }

    @Test
    public void testAddManagedEvent() {
        Organizer o = new Organizer("o", "oiscool", "1");
        ArrayList<String> expected = new ArrayList<>();

        o.addManagedEvent("Event1");
        expected.add("Event1");
        assertEquals(expected, o.getManagedEvents());

        o.addManagedEvent("Event2");
        o.addManagedEvent("Event3");
        expected.add("Event2");
        expected.add("Event3");
        assertEquals(expected, o.getManagedEvents());
    }

    @Test
    public void testRemoveManagedEvent() {
        Organizer o = new Organizer("o", "oiscool", "1");
        ArrayList<String> expected = new ArrayList<>();
        o.addManagedEvent("Event1");
        o.addManagedEvent("Event2");
        o.addManagedEvent("Event3");
        expected.add("Event1");
        expected.add("Event3");
        o.removeManagedEvent("Event2");
        assertEquals(expected, o.getManagedEvents());

        o.removeManagedEvent("Event3");
        ArrayList<String> expected2 = new ArrayList<>();
        expected2.add("Event1");
        assertEquals(expected2, o.getManagedEvents());
    }

    @Test
    public void testManagedEventsIsEmpty() {
        Organizer o = new Organizer("o", "oiscool", "1");
        assertTrue(o.managedEventsIsEmpty());

        o.addManagedEvent("Event1");
        assertFalse(o.managedEventsIsEmpty());
    }
}