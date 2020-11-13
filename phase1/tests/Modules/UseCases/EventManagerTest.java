package Modules.UseCases;

import Modules.Entities.Room;
import Modules.Entities.Event;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class EventManagerTest {

    @Test
    public void testEventManager() {
        ArrayList<Event> events = new ArrayList<Event>();
        EventManager manager = new EventManager(events);
    }

    @Test
    public void testCreateEvent() {
        ArrayList<Event> events = new ArrayList<Event>();
        EventManager manager = new EventManager(events);
        LocalDateTime eventDate1 = LocalDateTime.of(2020, 11, 3, 11, 11);
        LocalDateTime eventDate2 = LocalDateTime.of(2020, 12, 3, 10, 11);
        LocalDateTime eventDate3 = LocalDateTime.of(2020, 11, 4, 11, 0);

        assertTrue(manager.createEvent("1", eventDate1, eventDate1.plusHours(1), "event1"));
        assertTrue(manager.createEvent("2", eventDate1, eventDate1.plusHours(1), "event2"));
        assertTrue(manager.createEvent("1", eventDate2, eventDate2.plusHours(1), "event3"));
        assertTrue(manager.createEvent("3", eventDate3, eventDate3.plusHours(1), "event4"));

        manager.renameEvent("event2", "How 2 Do Good at Something");
        assertEquals("How 2 Do Good at Something", manager.getName("event2"));
        assertNotEquals("How (not) 2 Do Good at Something", manager.getName("event2"));
    }

    @Test
    public void testCanBook() {
        ArrayList<Event> events = new ArrayList<Event>();
        EventManager manager = new EventManager(events);
        LocalDateTime eventDate1 = LocalDateTime.of(2020, 11, 3, 11, 11);
        LocalDateTime eventDate2 = LocalDateTime.of(2020, 12, 3, 10, 11);
        LocalDateTime eventDate3 = LocalDateTime.of(2020, 11, 4, 11, 0);
        LocalDateTime eventDate4 = LocalDateTime.of(2020, 10, 3, 11, 11);
        LocalDateTime eventDate5 = LocalDateTime.of(2021, 11, 3, 11, 11);

        manager.createEvent("1", eventDate1, eventDate1.plusHours(1), "event1");
        manager.createEvent("2", eventDate2, eventDate2.plusHours(1), "event2");
        manager.createEvent("1", eventDate3, eventDate3.plusHours(1), "event3");

        assertFalse(manager.canBook("1", eventDate1, eventDate1.plusMinutes(10)));
        assertFalse(manager.canBook("1", eventDate1, eventDate1.plusHours(10)));
        assertTrue(manager.canBook("2", eventDate1, eventDate1.plusMinutes(10)));
        assertFalse(manager.canBook("1", eventDate4, eventDate1.plusMinutes(120)));
        assertTrue(manager.canBook("1", eventDate5, eventDate1.plusMinutes(120)));
        assertFalse(manager.canBook("2", eventDate2.minusHours(1), eventDate2.plusMinutes(1)));
        assertTrue(manager.canBook("2", eventDate2.minusHours(1), eventDate2.minusMinutes(1)));
    }

    @Test
    public void testHelpers() {
        ArrayList<Event> events = new ArrayList<Event>();
        EventManager manager = new EventManager(events);
        LocalDateTime eventDate1 = LocalDateTime.of(2020, 11, 3, 11, 11);
        LocalDateTime eventDate2 = LocalDateTime.of(2020, 12, 3, 10, 11);
        LocalDateTime eventDate3 = LocalDateTime.of(2020, 11, 4, 11, 0);

        manager.createEvent("1", eventDate1, eventDate1.plusHours(1), "event1");
        manager.createEvent("2", eventDate2, eventDate2.plusHours(1), "event2");
        manager.createEvent("3", eventDate3, eventDate3.plusHours(1), "event3");
    }

    @Test
    public void testIsScheduleFree() {
        ArrayList<Event> events = new ArrayList<Event>();
        EventManager manager = new EventManager(events);
        LocalDateTime eventDate1 = LocalDateTime.of(2020, 11, 3, 11, 11);
        LocalDateTime eventDate2 = LocalDateTime.of(2020, 12, 3, 10, 11);
        LocalDateTime eventDate3 = LocalDateTime.of(2020, 11, 4, 11, 0);

        manager.createEvent("1", eventDate1, eventDate1.plusHours(1), "event1");
        manager.createEvent("2", eventDate2, eventDate2.plusHours(1), "event2");
        manager.createEvent("3", eventDate3, eventDate3.plusHours(1), "event3");

        LocalDateTime eventDate4 = LocalDateTime.of(2020, 11, 3, 11, 12);
        LocalDateTime eventDate5 = LocalDateTime.of(2021, 12, 3, 10, 11);
        LocalDateTime eventDate6 = LocalDateTime.of(2020, 11, 4, 13, 0);

        assertTrue(manager.isScheduleFree(eventDate5));
        assertFalse(manager.isScheduleFree(eventDate1));
        assertFalse(manager.isScheduleFree(eventDate4));
        assertTrue(manager.isScheduleFree(eventDate6));
    }

    @Test
    public void testBooleans() {
        ArrayList<Event> events = new ArrayList<Event>();
        EventManager manager = new EventManager(events);
        LocalDateTime eventDate1 = LocalDateTime.of(2020, 11, 3, 11, 11);
        LocalDateTime eventDate2 = LocalDateTime.of(2020, 12, 3, 10, 11);
        LocalDateTime eventDate3 = LocalDateTime.of(2020, 11, 4, 11, 0);

        manager.createEvent("1", eventDate1, eventDate1.plusHours(1), "event1");
        manager.createEvent("2", eventDate2, eventDate2.plusHours(1), "event2");
        manager.createEvent("3", eventDate3, eventDate3.plusHours(1), "event3");

        assertTrue(manager.canAttend("event1"));
        assertTrue(manager.canAttend("event2"));
        assertTrue(manager.canAttend("event3"));

        manager.addAttendee("event1", "A01");
        manager.addAttendee("event1", "A02");

        assertFalse(manager.canAttend("event1"));
        assertTrue(manager.canAttend("event2"));

        manager.removeAttendee("event1", "A01");
        assertTrue(manager.canAttend("event1"));
    }

}