package Modules.Controllers;

import Modules.Entities.Attendee;
import Modules.Entities.Event;
import Modules.Entities.Message;
import Modules.Entities.Speaker;
import Modules.UseCases.AttendeeManager;
import Modules.UseCases.EventManager;
import Modules.UseCases.MessageManager;
import Modules.UseCases.SpeakerManager;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SpeakerControllerTest {
    @Test
    public void testMessage(){
        Speaker s = new Speaker("Lebron", "James", "s23");
        Event e = new Event("101", LocalDateTime.now(), LocalDateTime.now(), "1234");
        Attendee a = new Attendee("Steph", "Curry", "a30");
        ArrayList<Speaker> speakerArray = new ArrayList<>();
        speakerArray.add(s);
        SpeakerManager sm = new SpeakerManager(speakerArray);
        ArrayList<Attendee> attendeeArray = new ArrayList<>();
        attendeeArray.add(a);
        AttendeeManager am = new AttendeeManager(attendeeArray);
        ArrayList<Event> eventArray = new ArrayList<>();
        eventArray.add(e);
        EventManager em = new EventManager(eventArray);
        ArrayList<Message> messageArray = new ArrayList<>();
        MessageManager mm = new MessageManager(messageArray);
        SpeakerController sc = new SpeakerController(s.getID(), em, sm, am, mm);
        s.addEvent("1234");
        a.addEvent("1234");
        sc.message("a30", "Blew a 3-1 lead");
        assertTrue(mm.getUserMessages(s.getUsername()).size() == 1);
    }
}
