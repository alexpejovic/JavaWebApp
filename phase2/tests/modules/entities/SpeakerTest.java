package modules.entities;

import org.junit.Test;

import static org.junit.Assert.*;

public class SpeakerTest {

    //Constructor test
    @Test
    public void TestSpeaker(){
        Speaker s = new Speaker("LeBron", "James", "s123");
    }

    //Test adding a valid event
    @Test
    public void TestAddValid(){
        Speaker s = new Speaker("LeBron", "James", "s123");
        int length = s.numEvents();
        s.addEvent("1164");
        assertEquals(length + 1, s.numEvents());
        assertTrue(s.isHosting("1164"));

    }

    //Test the removal of an event
    @Test
    public void TestRemoveValid(){
        Speaker s = new Speaker("LeBron", "James", "s123");
        int length = s.numEvents();
        s.addEvent("1164");
        assertEquals(length + 1, s.numEvents());
        assertTrue(s.isHosting("1164"));
        s.removeEvent("1164");
        assertEquals(0, s.numEvents());
        assertFalse(s.isHosting("1164"));
    }

    @Test
    public void TestGetHostEvents(){
        Speaker s = new Speaker("LeBron", "James", "s123");
        int length = s.numEvents();
        s.addEvent("1164");
        assertEquals(length + 1, s.numEvents());
        assertTrue(s.isHosting("1164"));
        assertEquals(1, s.numEvents());
        assertTrue(s.getHostEvents().size() == 1);
    }

    @Test
    public void TestNumEvents(){
        Speaker s = new Speaker("LeBron", "James", "s123");
        int length = s.numEvents();
        s.addEvent("1164");
        int length2 = s.numEvents();
        assertEquals(length, length2 - 1);
        assertEquals(0, length);
        assertEquals(1, length2);
    }

    @Test
    public void TestAddEventAlreadyAdded(){
        Speaker s = new Speaker("LeBron", "James", "s123");
        s.addEvent("1164");
        int length1 = s.numEvents();
        s.addEvent("1164");
        int length2 = s.numEvents();
        assertEquals(length1, length2);

    }

    @Test
    public void testGetHostEvents(){
        Speaker s = new Speaker("LeBron", "James", "s123");
        int length = s.numEvents();
        assertTrue(s.getHostEvents().size() == 0);
        s.addEvent("1164");
        assertEquals(length + 1, s.numEvents());
        assertTrue(s.isHosting("1164"));
        assertEquals(1, s.numEvents());
        assertTrue(s.getHostEvents().size() == 1);
        assertEquals(s.getHostEvents().get(0), "1164");
    }
}