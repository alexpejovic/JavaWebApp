package Modules.Entities;

import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void testUser() {

        User o = new Organizer("organizer1", "oOo", "O33");
        User a = new Attendee("attendee1", "aAa", "A23");
        User s = new Speaker("speaker1", "sSs", "S10");

    }

    @Test
    public void testGetters() {
        User o = new Organizer("organizer1", "oOo", "O33");
        User a = new Attendee("attendee1", "aAa", "A23");
        User s = new Speaker("speaker1", "sSs", "S10");

        assertEquals("O33", o.getID());
        assertEquals("A23", a.getID());
        assertEquals("S10", s.getID());

        assertEquals("organizer1", o.getUsername());
        assertEquals("attendee1", a.getUsername());
        assertEquals("speaker1", s.getUsername());

        assertEquals("oOo", o.getPassword());
        assertEquals("aAa", a.getPassword());
        assertEquals("sSs", s.getPassword());

    }

    @Test
    public void testFriendList() {
        User o = new Organizer("organizer1", "oOo", "O33");
        User a = new Attendee("attendee1", "aAa", "A23");
        User s = new Speaker("speaker1", "sSs", "S10");

        o.addToFriendList(a.getID());
        o.addToFriendList(s.getID());
        a.addToFriendList(o.getID());

        ArrayList<String> expectedO = new ArrayList<>();
        ArrayList<String> expectedA = new ArrayList<>();
        ArrayList<String> expectedS = new ArrayList<>();

        expectedO.add("A23");
        expectedO.add("S10");
        expectedA.add("O33");

        assertEquals(expectedO, o.getFriendList());
        assertEquals(expectedA, a.getFriendList());
        assertEquals(expectedS, s.getFriendList());

        o.removeFromFriendList(a.getID());
        a.removeFromFriendList(o.getID());

        expectedO.remove("A23");
        expectedA.remove("O33");

        assertEquals(expectedO, o.getFriendList());
        assertEquals(expectedA, a.getFriendList());
        assertEquals(expectedS, s.getFriendList());
    }

}