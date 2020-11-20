package Modules.Controllers;

import Modules.Entities.Attendee;
import Modules.Entities.Organizer;
import Modules.Entities.Speaker;
import Modules.Exceptions.NonUniqueIdException;
import Modules.Exceptions.UserNotFoundException;
import Modules.UseCases.*;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class OrganizerControllerTest{
    // Creating the Controller Object
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

    @Test (expected = NonUniqueIdException.class)
    public void testAddNewRoom(){
        organizerController.addNewRoom("21", 5);
        organizerController.addNewRoom("19", 5);
        organizerController.addNewRoom("20", 5);
        assertEquals(3, roomManager.getRooms().size());
        organizerController.addNewRoom("20", 5);
        assertEquals(3, roomManager.getRooms().size());
    }

    @Test
    public void testScheduleEvent(){
        LocalDateTime startTime = LocalDateTime.of(2020, 11, 7, 5, 30);
        LocalDateTime endTime = LocalDateTime.of(2020, 11, 7,7,0);
        organizerController.addNewRoom("21", 5);
        organizerManager.addOrganizer(new Organizer("Michael Scott", "Dundermifflin", "o123"));
        assertTrue(organizerController.scheduleEvent("21", startTime, endTime, "Fitness"));
        assertFalse(organizerController.scheduleEvent("21", startTime, endTime, "Health"));
        assertEquals(1, eventManager.getNumberOfEvents());
        LocalDateTime newStartTime = LocalDateTime.of(2020, 11, 7, 4, 0);
        LocalDateTime newEndTime = LocalDateTime.of(2020, 11, 7, 8, 30);
        assertTrue(organizerController.scheduleEvent("21", newStartTime, startTime, "Cooking"));
        assertEquals(2, eventManager.getNumberOfEvents());
        assertFalse(organizerController.scheduleEvent("21", newStartTime, endTime, "Singing"));

        assertEquals(2, eventManager.getNumberOfEvents());
        assertEquals("Fitness", eventManager.getEvent(eventManager.getEventList().get(0).getID()).getName());
        assertEquals(startTime, eventManager.startTimeOfEvent(eventManager.getAllEventIDs().get(0)));
        assertEquals(endTime, eventManager.endTimeOfEvent(eventManager.getEventList().get(0).getID()));

        assertEquals("Cooking", eventManager.getEvent(eventManager.getEventList().get(1).getID()).getName());
        assertEquals(newStartTime, eventManager.startTimeOfEvent(eventManager.getAllEventIDs().get(1)));
        assertEquals(startTime, eventManager.endTimeOfEvent(eventManager.getEventList().get(1).getID()));

        assertNotEquals("Singing", eventManager.getEvent(eventManager.getEventList().get(1).getID()).getName());

        assertTrue(eventManager.isEventInTimePeriod(eventManager.getEventList().get(0).getID(), startTime, endTime));
        assertTrue(eventManager.isEventInTimePeriod(eventManager.getEventList().get(1).getID(), newStartTime, startTime));
        assertEquals("21", eventManager.getRoomNumberOfEvent(eventManager.getEventList().get(0).getID()));
        assertEquals("21", eventManager.getRoomNumberOfEvent(eventManager.getEventList().get(1).getID()));

    }

    @Test
    public void testCreateSpeakerAccount(){
        Organizer organizer = new Organizer("Michael Scott", "Dundermifflin", "o123");
        organizerManager.addOrganizer(organizer);
        assertEquals(0, speakerManager.getListOfSpeakers().size());
        organizerController.createSpeakerAccount("John Doe", "1234");
        assertEquals(1, speakerManager.getListOfSpeakers().size());
        organizerController.createSpeakerAccount("Jessica Doe", "1234");
        organizerController.createSpeakerAccount("George Clooney", "1234");
        assertEquals(3, speakerManager.NumSpeakers());
        assertEquals("John Doe", speakerManager.getListOfSpeakers().get(0).getUsername());
        assertEquals("Jessica Doe", speakerManager.getListOfSpeakers().get(1).getUsername());
        assertEquals("George Clooney", speakerManager.getListOfSpeakers().get(2).getUsername());

        assertTrue(speakerManager.isUser("George Clooney"));
        assertTrue(speakerManager.isUser("Jessica Doe"));
        assertTrue(speakerManager.isUser("John Doe"));
        assertFalse(speakerManager.isUser("Al Pacino"));
    }
    


}

