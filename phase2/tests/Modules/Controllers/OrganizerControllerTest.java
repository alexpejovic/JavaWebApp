package Modules.Controllers;

import Modules.Entities.Attendee;
import Modules.Entities.Organizer;
import Modules.Entities.Speaker;
import Modules.Entities.User;
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

    @Test
    public void testIsCorrectEvent(){
        Organizer organizer = new Organizer("Michael Scott", "Dundermifflin", "o123");
        organizerManager.addOrganizer(organizer);
        organizerController.addNewRoom("21", 5);
        organizerController.addNewRoom("19", 5);
        organizerController.addNewRoom("20", 5);
        LocalDateTime startTime = LocalDateTime.of(2020, 11, 7, 5, 30);
        LocalDateTime endTime = LocalDateTime.of(2020, 11, 7,7,0);
        organizerController.scheduleEvent("20", startTime, endTime, "Fitness");
        assertTrue(organizerController.isCorrectEvent("Fitness", "20"));
        assertFalse(organizerController.isCorrectEvent("Eating", "20"));
    }

    @Test
    public void testScheduleSpeaker(){
        Organizer organizer = new Organizer("Michael Scott", "Dundermifflin", "o123");
        organizerManager.addOrganizer(organizer);
        organizerController.addNewRoom("21", 5);
        organizerController.addNewRoom("19", 5);
        organizerController.addNewRoom("20", 5);
        LocalDateTime startTime = LocalDateTime.of(2020, 11, 7, 10, 30);
        LocalDateTime endTime = LocalDateTime.of(2020, 11, 7,11,30);
        assertTrue(organizerController.scheduleEvent("20", startTime, endTime, "Fitness"));
        assertTrue(organizerController.scheduleEvent("21", startTime, endTime, "Cooking"));
        Speaker speaker = new Speaker("Jessica",  "1234", "s123");
        Speaker speaker2 = new Speaker("Jim",  "1234", "s124");
        assertFalse(organizerController.scheduleSpeaker("Jessica", "25", "Fitness"));
        assertFalse(organizerController.roomExists("25"));
        speakerManager.addSpeaker(speaker);
        speakerManager.addSpeaker(speaker2);
        assertTrue(organizerController.scheduleSpeaker("Jessica", "20", "Fitness"));
        assertFalse(organizerController.scheduleSpeaker("Jessica", "21", "Cooking"));
        assertTrue(organizerController.scheduleEvent("21", startTime.plusHours(2), endTime.plusHours(2), "Drawing"));
        assertTrue(organizerController.scheduleSpeaker("Jessica", "21", "Drawing"));
        assertFalse(organizerController.scheduleSpeaker("Jim", "21", "Drawing"));
    }

    @Test
    public void testInputOrganizer(){
        Organizer organizer1 = new Organizer("Michael Scott", "1234", "o123");
        Organizer organizer2 = new Organizer("Jim Halpert", "beets", "o124");
        Organizer organizer3 = new Organizer("Pam Beasley", "artSchool", "o125");

        assertEquals(0, organizerManager.getNumberOfOrganizers());
        ArrayList<User> organizerList = new ArrayList<>();
        organizerList.add(organizer1);
        organizerList.add(organizer2);
        organizerList.add(organizer3);
        organizerController.inputOrganizer(organizerList);

        assertEquals(3, organizerManager.getNumberOfOrganizers());
        assertTrue(organizerManager.isUser("Michael Scott"));
        assertTrue(organizerManager.validatePassword("Michael Scott", "1234"));

    }

    @Test
    public void testMessageAllAttendeesAndSpeaker(){
        Attendee a1 = new Attendee("Jim Halpert", "1234", "a1");
        Attendee a2 = new Attendee("Pam Beesley", "1234", "a2");
        Attendee a3 = new Attendee("Dwight Schrute", "1234", "a3");
        Attendee a4 = new Attendee("Stanley", "1234", "a4");

        attendeeManager.addAttendee(a1);
        attendeeManager.addAttendee(a2);
        attendeeManager.addAttendee(a3);
        attendeeManager.addAttendee(a4);


        organizerController.messageAllAttendees("Meeting in the Conference Room");
        for (String attendeeId:attendeeManager.getUserIDOfAllAttendees()) {
            assertEquals("Meeting in the Conference Room",
                    messageManager.getContentOfMessage( messageManager.getUserMessages(attendeeId).get(0)));
            assertEquals("o123",
                    messageManager.getSenderIDOfMessage(messageManager.getUserMessages(attendeeId).get(0)));
        }

        Speaker s1 = new Speaker("Jim Halpert", "1234", "s1");
        Speaker s2 = new Speaker("Pam Beesley", "1234", "s2");
        Speaker s3 = new Speaker("Dwight Schrute", "1234", "s3");

        speakerManager.addSpeaker(s1);
        speakerManager.addSpeaker(s2);
        speakerManager.addSpeaker(s3);

        organizerController.messageAllSpeakers("Birthday Party");
        for (String speakerId:speakerManager.getUserIDOfAllSpeakers()){
            assertEquals("Birthday Party",
                    messageManager.getContentOfMessage( messageManager.getUserMessages(speakerId).get(0)));
            assertEquals("o123",
                    messageManager.getSenderIDOfMessage(messageManager.getUserMessages(speakerId).get(0)));
        }
    }

    @Test
    public void testSendMessageAndViewMessage(){
        Speaker s1 = new Speaker("Jim Halpert", "1234", "s1");
        Attendee a1 = new Attendee("Jim Halpert", "1234", "a1");
        organizerController.sendMessage("s1", "yoooooo");
        organizerController.sendMessage("a1", "yooooooo");

        attendeeManager.addAttendee(a1);
        speakerManager.addSpeaker(s1);

        assertEquals("yoooooo",messageManager.getContentOfMessage( messageManager.getUserMessages("s1").get(0)));
        assertEquals("o123", messageManager.getSenderIDOfMessage(messageManager.getUserMessages("a1").get(0)));
        assertEquals("o123", messageManager.getSenderIDOfMessage(messageManager.getUserMessages("s1").get(0)));
        assertEquals("yooooooo", messageManager.getContentOfMessage(messageManager.getUserMessages("a1").get(0)));

        organizerController.messageAllSpeakers("Birthday Party");
        organizerController.messageAllAttendees("Meeting in the Conference Room");

        assertEquals("Birthday Party", messageManager.getContentOfMessage(messageManager.getUserMessages("s1").get(1)));
        assertEquals("o123",  messageManager.getSenderIDOfMessage(messageManager.getUserMessages("a1").get(1)));
        assertEquals("o123", messageManager.getSenderIDOfMessage(messageManager.getUserMessages("s1").get(1)));
        assertEquals("Meeting in the Conference Room", messageManager.getContentOfMessage(messageManager.getUserMessages("a1").get(1)));




    }




}

