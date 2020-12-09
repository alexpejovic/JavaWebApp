package modules.controllers;

import modules.entities.Organizer;
import modules.gateways.EventGateway;
import modules.gateways.MessageGateway;
import modules.gateways.RoomGateway;
import modules.gateways.UserGateway;
import modules.usecases.*;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

// tests would affect database info if connected to database
// tests pass but since we are not connecting there is an SQLException message that is printed
public class EventCreatorTest {
    EventManager eventManager = new EventManager(new ArrayList<>());
    AttendeeManager attendeeManager = new AttendeeManager(new ArrayList<>());
    MessageManager messageManager = new MessageManager(new ArrayList<>());
    OrganizerManager organizerManager = new OrganizerManager(new ArrayList<>());
    RoomManager roomManager = new RoomManager(new ArrayList<>());
    SpeakerManager speakerManager = new SpeakerManager(new ArrayList<>());
    UpdateInfo updateInfo = new UpdateInfo(new MessageGateway(),new EventGateway(),
                                            new UserGateway(), new RoomGateway());
    EventCreator eventCreator = new EventCreator(eventManager,updateInfo);
    AccountCreator accountCreator = new AccountCreator(organizerManager, attendeeManager, speakerManager,updateInfo);
    OrganizerController organizerController = new OrganizerController(organizerManager, eventManager,
                                                    roomManager, speakerManager, messageManager, attendeeManager,
                                                    eventCreator, accountCreator, "o123", updateInfo);
    Organizer organizer = new Organizer("Michael", "1234", "o123");

    @Test
    public void testCreateEventAccount() {
        EventManager eventManager = new EventManager(new ArrayList<>());
        EventCreator eventCreator = new EventCreator(eventManager,updateInfo);

        LocalDateTime startTime = LocalDateTime.of(2020, 11, 7, 11, 0);
        LocalDateTime endTime = LocalDateTime.of(2020, 11, 7, 12, 30);

        organizerController.addNewRoom("21", 5);

        assertTrue(eventCreator.createEvent(startTime, endTime, "21", "New Event",2, false));
        assertEquals(1, eventManager.getNumberOfEvents());
        assertEquals("e0", eventManager.getEventList().get(0).getID());

        assertEquals("e0", eventCreator.listOfEvents().get(0));
        assertEquals(1, eventCreator.listOfEvents().size());
    }
}
