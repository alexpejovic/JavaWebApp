package Modules.Controllers;

import static org.junit.Assert.*;

import Modules.Entities.Attendee;
import Modules.UseCases.AttendeeManager;
import Modules.UseCases.EventManager;
import Modules.UseCases.MessageManager;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class AttendeeControllerTest {
    @Test
    public void testAttendeeController(){
        EventManager eventManager = new EventManager(new ArrayList<>());
        AttendeeManager attendeeManager = new AttendeeManager(new ArrayList<>());
        attendeeManager.addAttendee("username", "password", "a1234", new ArrayList<>());
        MessageManager messageManager = new MessageManager(new ArrayList<>());
        AttendeeController attendeeController = new AttendeeController(attendeeManager, eventManager, "a1234",
                messageManager);
    }

    @Test
    public void testDisplayEvents(){
        EventManager eventManager = new EventManager(new ArrayList<>());
        AttendeeManager attendeeManager = new AttendeeManager(new ArrayList<>());
        attendeeManager.addAttendee("username", "password", "a1234", new ArrayList<>());
        MessageManager messageManager = new MessageManager(new ArrayList<>());
        AttendeeController attendeeController = new AttendeeController(attendeeManager, eventManager, "a1234",
                messageManager);
        LocalDateTime time1 = LocalDateTime.of(2020, 1, 1, 1, 0);
        LocalDateTime time2 = LocalDateTime.of(2020, 1, 1, 2, 0);
        eventManager.createEvent("1", time1, time2, "e1234");
        assertEquals("e1234", attendeeController.displayEvents().get(0).getID());
    }

    @Test
    public void testSignUp(){
        EventManager eventManager = new EventManager(new ArrayList<>());
        AttendeeManager attendeeManager = new AttendeeManager(new ArrayList<>());
        attendeeManager.addAttendee("username", "password", "a1234", new ArrayList<>());
        MessageManager messageManager = new MessageManager(new ArrayList<>());
        AttendeeController attendeeController = new AttendeeController(attendeeManager, eventManager, "a1234",
                messageManager);
        LocalDateTime time1 = LocalDateTime.of(2020, 1, 1, 1, 0);
        LocalDateTime time2 = LocalDateTime.of(2020, 1, 1, 2, 0);
        eventManager.createEvent("1", time1, time2, "e1234");
        eventManager.renameEvent("e1234", "name");
        boolean result = attendeeController.signUp("name");
        assertEquals(true, result);
        assertEquals("e1234", attendeeManager.getAttendee("a1234").getEventsList().get(0));
    }
}
