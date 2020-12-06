package modules.gateways;
import com.sun.org.apache.xpath.internal.operations.Or;
import modules.entities.*;
import modules.gateways.EventGatewayDB;
import modules.gateways.UserGatewayDB;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.Assert.*;
import java.time.LocalDateTime;

public class UserGatewayDBTest {
    //Run all tests in order on an empty database

    @Test
    public void testWriteDataAttendee(){
        UserGatewayDB UGDB = new UserGatewayDB();
        Attendee attendee1 = new Attendee("Tim", "Squire", "a1234");
        ArrayList<User> userList = new ArrayList<>();
        userList.add(attendee1);
        UGDB.writeData(userList);
    }

    @Test
    public void testWriteDataSpeaker(){
        UserGatewayDB UGDB = new UserGatewayDB();
        Speaker speaker1 = new Speaker("Tim", "Squire", "s1234");
        ArrayList<User> userList = new ArrayList<>();
        userList.add(speaker1);
        UGDB.writeData(userList);
    }

    @Test
    public void testWriteDataOrganizer(){
        UserGatewayDB UGDB = new UserGatewayDB();
        Organizer organizer1 = new Organizer("Tim", "Squire", "o1234");
        ArrayList<User> userList = new ArrayList<>();
        userList.add(organizer1);
        UGDB.writeData(userList);
    }

    @Test
    public void testReadData(){
        UserGatewayDB UGDB = new UserGatewayDB();
        ArrayList<User> userList = UGDB.readData();
        assertEquals(userList.size(), 3);
    }

    @Test
    public void testAttendeeGoingToAnEvent(){
        UserGatewayDB UGDB = new UserGatewayDB();
        Attendee attendee1 = new Attendee("Tim", "Squire", "a12345");
        Attendee attendee2 = new Attendee("Bob", "Thompson", "a6789");
        attendee1.addToFriendList(attendee2.getID());
        ArrayList<User> userList = new ArrayList<>();
        userList.add(attendee1);
        userList.add(attendee2);
        Event event1 = new Event("r101", LocalDateTime.now(), "event12345");
        ArrayList<Event> eventList = new ArrayList<>();
        eventList.add(event1);
        EventGatewayDB EGDB = new EventGatewayDB();
        attendee1.addEvent(event1.getID());
        event1.addAttendee(attendee1.getID());
        EGDB.writeData(eventList);
        UGDB.writeData(userList);
        ArrayList<User> userslist = UGDB.readData();
        assertEquals(userList.size(), 2);
        assertEquals(userList.get(0).getFriendList().size(), 1);
        assertEquals(userList.get(0).getUsername(), "Tim");
        assertTrue(userList.get(0) instanceof Attendee);
    }

    @Test
    public void testOrganizerManaging2EventsWith1Friend(){
        UserGatewayDB UGDB = new UserGatewayDB();
        EventGatewayDB EGDB = new EventGatewayDB();
        Organizer organizer1 = new Organizer("Tim", "Squire", "o12345");
        Attendee attendee1 = new Attendee("Jon", "Squire", "a00000");
        organizer1.addToFriendList(attendee1.getID());
        Event event1 = new Event("r123", LocalDateTime.now(), "e99999");
        Event event2 = new Event("r567", LocalDateTime.now(), "e77777");
        ArrayList<User> userList = new ArrayList<>();
        ArrayList<Event> eventList = new ArrayList<>();
        userList.add(organizer1);
        userList.add(attendee1);
        eventList.add(event1);
        eventList.add(event2);
        organizer1.addManagedEvent(event1.getID());
        organizer1.addManagedEvent(event2.getID());
        attendee1.addEvent(event1.getID());
        event1.addAttendee(attendee1.getID());
        EGDB.writeData(eventList);
        UGDB.writeData(userList);
        ArrayList<User> result = UGDB.readData();
        assertEquals(result.size(), 7);
        assertEquals(result.get(5).getFriendList().size(), 1);
        assertTrue(result.get(5) instanceof Organizer);
        assertTrue(result.get(6) instanceof Attendee);
        assertEquals(((Organizer) result.get(5)).getManagedEvents().size(), 2);
        assertEquals(result.get(6).getFriendList().size(), 0);
    }
}
