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
        assertEquals("e1234", attendeeController.displayEvents().get(0));
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
        assertEquals(false, attendeeController.signUp("name"));
    }

    @Test
    public void testCancelEnrollment(){
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
        attendeeController.signUp("name");
        boolean result = attendeeController.cancelEnrollment("name");
        assertEquals(true, result);
        assertEquals(true, attendeeManager.getAttendee("a1234").hasNoEvents());
        assertEquals(false, attendeeController.cancelEnrollment("name"));
    }

    @Test
    public void testSendMessage(){
        EventManager eventManager = new EventManager(new ArrayList<>());
        AttendeeManager attendeeManager = new AttendeeManager(new ArrayList<>());
        ArrayList<String> friendsList = new ArrayList<>();
        attendeeManager.addAttendee("username", "password", "a1234", new ArrayList<>());
        attendeeManager.addAttendee("username2", "password2", "a2345", new ArrayList<>());
        attendeeManager.getAttendee("a1234").addToFriendList("a2345");
        MessageManager messageManager = new MessageManager(new ArrayList<>());
        AttendeeController attendeeController = new AttendeeController(attendeeManager, eventManager, "a1234",
                messageManager);
        boolean result = attendeeController.sendMessage("a2345", "heyyyy");
        assertEquals(true, result);
        boolean result2 = attendeeController.sendMessage("a4567", "uh oh");
        assertEquals(false, result2);
    }

    @Test
    public void testAddUserToFriendList(){
        EventManager eventManager = new EventManager(new ArrayList<>());
        AttendeeManager attendeeManager = new AttendeeManager(new ArrayList<>());
        ArrayList<String> friendsList = new ArrayList<>();
        attendeeManager.addAttendee("username", "password", "a1234", new ArrayList<>());
        attendeeManager.addAttendee("username2", "password2", "a2345", new ArrayList<>());
        MessageManager messageManager = new MessageManager(new ArrayList<>());
        AttendeeController attendeeController = new AttendeeController(attendeeManager, eventManager, "a1234",
                messageManager);
        boolean result = attendeeController.addUserToFriendList("a2345");
        assertEquals(true, result);
        boolean result2 = attendeeController.addUserToFriendList("a2345");
        assertEquals(false, result2);
    }

    @Test
    public void testSeeMessages(){
        EventManager eventManager = new EventManager(new ArrayList<>());
        AttendeeManager attendeeManager = new AttendeeManager(new ArrayList<>());
        ArrayList<String> friendsList = new ArrayList<>();
        attendeeManager.addAttendee("username", "password", "a1234", new ArrayList<>());
        attendeeManager.addAttendee("username2", "password2", "a2345", new ArrayList<>());
        attendeeManager.getAttendee("a1234").addToFriendList("a2345");
        MessageManager messageManager = new MessageManager(new ArrayList<>());
        AttendeeController attendeeController = new AttendeeController(attendeeManager, eventManager, "a1234",
                messageManager);
        boolean result = attendeeController.sendMessage("a2345", "heyyyy");
        assertEquals("heyyyy", attendeeController.seeMessage("a1234").get(0).getContent());
    }
}
