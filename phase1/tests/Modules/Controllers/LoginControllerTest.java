package Modules.Controllers;

import Modules.Entities.Attendee;
import Modules.Entities.Speaker;
import Modules.Exceptions.UserNotFoundException;
import Modules.UseCases.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class LoginControllerTest {

    @Test
    public void testLoginController(){
        ArrayList attendees = new ArrayList();
        ArrayList speakers = new ArrayList();

        Attendee attendee0 = new Attendee("at1", "pass1","a0");
        attendees.add(attendee0);
        Attendee attendee1 = new Attendee("at2", "pass2","a1");
        attendees.add(attendee1);
        Speaker speaker = new Speaker("sp", "pass3","s0");
        speakers.add(speaker);

        AttendeeManager attendeeManager = new AttendeeManager(attendees);
        SpeakerManager speakerManager = new SpeakerManager(speakers);

        OrganizerManager organizerManager = new OrganizerManager(new EventManager(new ArrayList<>()),
                new RoomManager(new ArrayList<>()),
                new ArrayList<>());

        LoginController loginController = new LoginController(attendeeManager,organizerManager,speakerManager);

        //Testing .validatePassword()
        // correct username and password
        assertTrue(loginController.validateUsernamePassword("at1","pass1"));
        assertTrue(loginController.validateUsernamePassword("sp","pass3"));
        // wrong password
        assertFalse(loginController.validateUsernamePassword("at1","pass2"));
        // no user with username
        assertFalse(loginController.validateUsernamePassword("at3","pass1"));

        //Testing .Login() and .getLoggedUser()
        // wrong password
        assertFalse(loginController.logIn("at1","pass2"));
        // no user with username
        assertFalse(loginController.logIn("at3","pass1"));

        assertTrue(loginController.logIn("at1","pass1"));
        assertEquals(loginController.getLoggedUser(),"a0");

        assertTrue(loginController.logIn("sp","pass3"));
        assertEquals(loginController.getLoggedUser(),"s0");

    }



}
