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
        EventManager manager = new EventManager();
    }

    @Test
    public void testCreateEvent() {
        EventManager manager = new EventManager();
        LocalDateTime eventDate1 = LocalDateTime.of(2020, 11, 3, 11, 11);
        LocalDateTime eventDate2 = LocalDateTime.of(2020, 12, 3, 10, 11);
        LocalDateTime eventDate3 = LocalDateTime.of(2020, 11, 4, 11, 0);

        assertTrue(manager.createEvent("1", eventDate1, eventDate1.plusHours(1), "event1"));
        assertTrue(manager.createEvent("2", eventDate1, eventDate1.plusHours(1), "event2"));
        assertTrue(manager.createEvent("1", eventDate2, eventDate2.plusHours(1), "event3"));
        assertTrue(manager.createEvent("3", eventDate3, eventDate3.plusHours(1), "event4"));
    }

    @Test
    public void testGetters() {

    }

    @Test
    public void testHelpers() {
        EventManager manager = new EventManager();
        LocalDateTime eventDate1 = LocalDateTime.of(2020, 11, 3, 11, 11);
        LocalDateTime eventDate2 = LocalDateTime.of(2020, 12, 3, 10, 11);
        LocalDateTime eventDate3 = LocalDateTime.of(2020, 11, 4, 11, 0);

        manager.createEvent("1", eventDate1, eventDate1.plusHours(1), "event1");
        manager.createEvent("2", eventDate2, eventDate2.plusHours(1), "event2");
        manager.createEvent("3", eventDate3, eventDate3.plusHours(1), "event3");
    }

    @Test
    public void testIsScheduleFree() {
        EventManager manager = new EventManager();
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

}