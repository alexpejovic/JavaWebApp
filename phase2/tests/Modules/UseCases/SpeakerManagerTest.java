package Modules.UseCases;

import static org.junit.Assert.*;
import Modules.Entities.Attendee;
import Modules.Entities.Event;
import Modules.Entities.Message;
import Modules.Entities.Speaker;
import Modules.Exceptions.UserNotFoundException;
import Modules.UseCases.AttendeeManager;
import Modules.UseCases.EventManager;
import Modules.UseCases.MessageManager;
import Modules.UseCases.SpeakerManager;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SpeakerManagerTest {
    @Test
    public void testGetListOfSpeakers(){
        Speaker s = new Speaker("Tim", "Squire", "s101");
        Event e = new Event("101", LocalDateTime.now(), LocalDateTime.now(), "1234",2);
        ArrayList<Speaker> list = new ArrayList<>();
        SpeakerManager sm = new SpeakerManager(list);
        assertTrue(sm.getListOfSpeakers().size() == 0);
        sm.addSpeaker(s);
        assertTrue(sm.getListOfSpeakers().size() == 1);
        assertFalse(sm.isHosted("1234"));
        s.addEvent("1234");
        assertTrue(sm.isHosted("1234"));
    }

    @Test
    public void testNumSpeakers(){
        Speaker s = new Speaker("Tim", "Squire", "s101");
        Event e = new Event("101", LocalDateTime.now(), LocalDateTime.now(), "1234",2);
        ArrayList<Speaker> list = new ArrayList<>();
        SpeakerManager sm = new SpeakerManager(list);
        assertEquals(sm.NumSpeakers(), 0);
        assertTrue(sm.getListOfSpeakers().size() == 0);
        sm.addSpeaker(s);
        assertTrue(sm.getListOfSpeakers().size() == 1);
        assertFalse(sm.isHosted("1234"));
        s.addEvent("1234");
        assertTrue(sm.isHosted("1234"));
        assertEquals(sm.NumSpeakers(), 1);
    }

    @Test
    public void testValidatePassword(){
        Speaker s = new Speaker("Tim", "Squire", "s101");
        Event e = new Event("101", LocalDateTime.now(), LocalDateTime.now(), "1234",2);
        ArrayList<Speaker> list = new ArrayList<>();
        SpeakerManager sm = new SpeakerManager(list);
        assertTrue(sm.getListOfSpeakers().size() == 0);
        sm.addSpeaker(s);
        assertFalse(sm.validatePassword("Tim", "goobagooba"));
        assertTrue(sm.validatePassword("Tim", "Squire"));
    }

    @Test
    public void testGetUserId(){
        Speaker s = new Speaker("Tim", "Squire", "s101");
        Event e = new Event("101", LocalDateTime.now(), LocalDateTime.now(), "1234",2);
        ArrayList<Speaker> list = new ArrayList<>();
        SpeakerManager sm = new SpeakerManager(list);
        assertTrue(sm.getListOfSpeakers().size() == 0);
        sm.addSpeaker(s);
        assertEquals(sm.getUserID("Tim"), "s101");
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetUserIdFail(){
        Speaker s = new Speaker("Tim", "Squire", "s101");
        Event e = new Event("101", LocalDateTime.now(), LocalDateTime.now(), "1234",2);
        ArrayList<Speaker> list = new ArrayList<>();
        SpeakerManager sm = new SpeakerManager(list);
        assertTrue(sm.getListOfSpeakers().size() == 0);
        sm.addSpeaker(s);
        sm.getUserID("Joe");
    }

    @Test
    public void testGetSpeaker(){
        Speaker s = new Speaker("Tim", "Squire", "s101");
        Event e = new Event("101", LocalDateTime.now(), LocalDateTime.now(), "1234",2);
        ArrayList<Speaker> list = new ArrayList<>();
        SpeakerManager sm = new SpeakerManager(list);
        assertTrue(sm.getListOfSpeakers().size() == 0);
        sm.addSpeaker(s);
        assertEquals(sm.getSpeaker("s101"), s);
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetSpeakerFail(){
        Speaker s = new Speaker("Tim", "Squire", "s101");
        Event e = new Event("101", LocalDateTime.now(), LocalDateTime.now(), "1234",2);
        ArrayList<Speaker> list = new ArrayList<>();
        SpeakerManager sm = new SpeakerManager(list);
        assertTrue(sm.getListOfSpeakers().size() == 0);
        sm.addSpeaker(s);
        sm.getSpeaker("s222");
    }
}