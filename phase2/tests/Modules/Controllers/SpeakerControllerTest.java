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
    public void testMessageValidAttendee(){
        Speaker s = new Speaker("Lebron", "James", "s23");
        Event e = new Event("101", LocalDateTime.now(), LocalDateTime.now(), "1234");
        Attendee a = new Attendee("Steph", "Curry", "a30");
        Message m = new Message("Blew a 3-1 lead", s.getID(), a.getID(), "m123", LocalDateTime.now());
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
        assertTrue(mm.getUserMessages(s.getID()).size() == 1);
    }

    @Test
    public void testMessageInvalidAttendee(){
        Speaker s = new Speaker("Lebron", "James", "s23");
        Event e = new Event("101", LocalDateTime.now(), LocalDateTime.now(), "1234");
        Attendee a = new Attendee("Steph", "Curry", "a30");
        Message m = new Message("Blew a 3-1 lead", s.getID(), a.getID(), "m123", LocalDateTime.now());
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
        assertFalse(sc.message("a30", "Blew a 3-1 lead"));
    }

    @Test
    public void testMessageMultipleAttendee(){
        Speaker s = new Speaker("Lebron", "James", "s23");
        Event e = new Event("101", LocalDateTime.now(), LocalDateTime.now(), "1234");
        Attendee a1 = new Attendee("Steph", "Curry", "a30");
        Attendee a2 = new Attendee("James", "Harden", "a13");
        Attendee a3 = new Attendee("Kevin", "Durant", "a5");
        ArrayList<Speaker> speakerArray = new ArrayList<>();
        speakerArray.add(s);
        SpeakerManager sm = new SpeakerManager(speakerArray);
        ArrayList<Attendee> attendeeArray = new ArrayList<>();
        attendeeArray.add(a1);
        attendeeArray.add(a2);
        attendeeArray.add(a3);
        AttendeeManager am = new AttendeeManager(attendeeArray);
        ArrayList<Event> eventArray = new ArrayList<>();
        eventArray.add(e);
        EventManager em = new EventManager(eventArray);
        ArrayList<Message> messageArray = new ArrayList<>();
        MessageManager mm = new MessageManager(messageArray);
        SpeakerController sc = new SpeakerController(s.getID(), em, sm, am, mm);
        s.addEvent("1234");
        a1.addEvent("1234");
        a2.addEvent("1234");
        a3.addEvent("1234");
        assertTrue(sc.messageAll("Hello Everyone!"));
        assertTrue(mm.getUserMessages(s.getID()).size() == 3);
        assertTrue(mm.getUserMessages(a1.getID()).size() == 1);
        assertTrue(mm.getUserMessages(a2.getID()).size() == 1);
        assertTrue(mm.getUserMessages(a3.getID()).size() == 1);

    }

    @Test
    public void testMessageMultipleAttendeeOneValid(){
        Speaker s = new Speaker("Lebron", "James", "s23");
        Event e = new Event("101", LocalDateTime.now(), LocalDateTime.now(), "1234");
        Attendee a1 = new Attendee("Steph", "Curry", "a30");
        Attendee a2 = new Attendee("James", "Harden", "a13");
        Attendee a3 = new Attendee("Kevin", "Durant", "a5");
        ArrayList<Speaker> speakerArray = new ArrayList<>();
        speakerArray.add(s);
        SpeakerManager sm = new SpeakerManager(speakerArray);
        ArrayList<Attendee> attendeeArray = new ArrayList<>();
        attendeeArray.add(a1);
        attendeeArray.add(a2);
        attendeeArray.add(a3);
        AttendeeManager am = new AttendeeManager(attendeeArray);
        ArrayList<Event> eventArray = new ArrayList<>();
        eventArray.add(e);
        EventManager em = new EventManager(eventArray);
        ArrayList<Message> messageArray = new ArrayList<>();
        MessageManager mm = new MessageManager(messageArray);
        SpeakerController sc = new SpeakerController(s.getID(), em, sm, am, mm);
        s.addEvent("1234");
        a1.addEvent("1234");
        assertTrue(sc.messageAll("Hello Everyone!"));
        assertTrue(mm.getUserMessages(s.getID()).size() == 1);
        assertTrue(mm.getUserMessages(a1.getID()).size() == 1);

    }

    @Test
    public void testShowEventsSingle(){
        Speaker s = new Speaker("Lebron", "James", "s23");
        Event e = new Event("101", LocalDateTime.now(), LocalDateTime.now(), "1234");
        Attendee a = new Attendee("Steph", "Curry", "a30");
        Message m = new Message("Blew a 3-1 lead", s.getID(), a.getID(), "m123", LocalDateTime.now());
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
        assertTrue(sc.showEvents().size() == 1);
    }

    @Test
    public void testShowEventsMultiple(){
        Speaker s = new Speaker("Lebron", "James", "s23");
        Event e1 = new Event("101", LocalDateTime.now(), LocalDateTime.now(), "1234");
        Event e2 = new Event("102", LocalDateTime.now(), LocalDateTime.now(), "5678");
        Event e3 = new Event("103", LocalDateTime.now(), LocalDateTime.now(), "910");
        Attendee a = new Attendee("Steph", "Curry", "a30");
        Message m = new Message("Blew a 3-1 lead", s.getID(), a.getID(), "m123", LocalDateTime.now());
        ArrayList<Speaker> speakerArray = new ArrayList<>();
        speakerArray.add(s);
        SpeakerManager sm = new SpeakerManager(speakerArray);
        ArrayList<Attendee> attendeeArray = new ArrayList<>();
        attendeeArray.add(a);
        AttendeeManager am = new AttendeeManager(attendeeArray);
        ArrayList<Event> eventArray = new ArrayList<>();
        eventArray.add(e1);
        eventArray.add(e2);
        eventArray.add(e3);
        EventManager em = new EventManager(eventArray);
        ArrayList<Message> messageArray = new ArrayList<>();
        MessageManager mm = new MessageManager(messageArray);
        SpeakerController sc = new SpeakerController(s.getID(), em, sm, am, mm);
        s.addEvent("1234");
        s.addEvent("5678");
        s.addEvent("910");
        a.addEvent("1234");
        assertTrue(sc.showEvents().size() == 3);
    }
}
