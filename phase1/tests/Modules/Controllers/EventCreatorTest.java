package Modules.Controllers;

import Modules.Entities.Organizer;
import Modules.Exceptions.NonUniqueUsernameException;
import Modules.UseCases.*;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EventCreatorTest {
    EventManager eventManager = new EventManager(new ArrayList<>());
    AttendeeManager attendeeManager = new AttendeeManager(new ArrayList<>());
    MessageManager messageManager = new MessageManager(new ArrayList<>());
    OrganizerManager organizerManager = new OrganizerManager(new ArrayList<>());
    RoomManager roomManager = new RoomManager(new ArrayList<>());
    SpeakerManager speakerManager = new SpeakerManager(new ArrayList<>());
    EventCreator eventCreator = new EventCreator(eventManager);
    AccountCreator accountCreator = new AccountCreator(organizerManager, attendeeManager, speakerManager);
    OrganizerController organizerController = new OrganizerController(organizerManager, eventManager,
            roomManager, speakerManager, messageManager, attendeeManager, eventCreator, accountCreator, "o123");
    Organizer organizer = new Organizer("Michael", "1234", "o123");

    @Test
    public void testCreateEventAccount() {
        EventManager eventManager = new EventManager(new ArrayList<>());
        EventCreator eventCreator = new EventCreator(eventManager);

        LocalDateTime startTime = LocalDateTime.of(2020, 11, 7, 11, 0);
        LocalDateTime endTime = LocalDateTime.of(2020, 11, 7, 12, 30);

        organizerController.addNewRoom("21", 5);

        assertTrue(eventCreator.createEvent(startTime, endTime, "21", "New Event"));
        assertEquals(1, eventManager.getNumberOfEvents());
        assertEquals("e0", eventManager.getEventList().get(0).getID());

        assertEquals("e0", eventCreator.listOfEvents().get(0));
        assertEquals(1, eventCreator.listOfEvents().size());
    }
}
