package modules.gateways;

import modules.entities.*;
import modules.gateways.EventGatewayDB;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Month;
import java.util.ArrayList;
import static org.junit.Assert.*;
import java.time.LocalDateTime;

public class EventGatewayDBTest {
    //Run the tests in order on an empty database

    @Test
    public void testWriteFile(){
        EventGatewayDB eventDB = new EventGatewayDB();
        Event newEvent = new Event("room3", LocalDateTime.now(), LocalDateTime.now(), "event23", 23);
        ArrayList<Event> eventList = new ArrayList<>();
        eventList.add(newEvent);
        eventDB.writeData(eventList);
    }
    @Test
    public void testWriteFile2(){
        EventGatewayDB eventDB = new EventGatewayDB();
        Event newEvent = new Event("room2", LocalDateTime.now(), LocalDateTime.now(), "event2", 23);
        ArrayList<Event> eventList = new ArrayList<>();
        eventList.add(newEvent);
        eventDB.writeData(eventList);
    }
    @Test
    public void testWriteFileWithAttendees(){
        Attendee a = new Attendee("Lebron", "James", "a101");
        EventGatewayDB eventDB = new EventGatewayDB();
        Event newEvent = new Event("room2", LocalDateTime.now(), LocalDateTime.now(), "event1", 23);
        newEvent.addAttendee(a.getID());
        ArrayList<Event> eventList = new ArrayList<>();
        eventList.add(newEvent);
        eventDB.writeData(eventList);
    }
    @Test
    public void testWriteFileWithAttendeesAndSpeaker(){
        Attendee a = new Attendee("Lebron", "James", "a101");
        Speaker s = new Speaker("Steph", "Curry", "s101");
        EventGatewayDB eventDB = new EventGatewayDB();
        Event newEvent = new Event("room2", LocalDateTime.now(), LocalDateTime.now(), "event4", 23);
        newEvent.addAttendee(a.getID());
        newEvent.scheduleSpeaker(s.getID());
        ArrayList<Event> eventList = new ArrayList<>();
        eventList.add(newEvent);
        eventDB.writeData(eventList);
    }
    @Test
    public void testReadFile(){
        EventGatewayDB eventDB = new EventGatewayDB();
        ArrayList<Event> eventList = eventDB.readData();
        assertEquals("room3", eventList.get(0).getRoomNumber());
        assertEquals("event23", eventList.get(0).getID());
        assertEquals(1, eventList.get(3).getAttendees().size());
        assertEquals(1, eventList.get(3).getSpeakers().size());
        assertEquals(eventList.size(), 4);

    }

    @Test
    public void testWriteFileDuplicate(){
        EventGatewayDB eventDB = new EventGatewayDB();
        Event newEvent = new Event("THIS_IS_DUPLICATE", LocalDateTime.now(), LocalDateTime.now(), "event23", 23);
        ArrayList<Event> eventList = new ArrayList<>();
        eventList.add(newEvent);
        eventDB.writeData(eventList);
    }

    @Test
    public void testReadFileConfirmDuplicate(){
        EventGatewayDB eventDB = new EventGatewayDB();
        ArrayList<Event> eventList = eventDB.readData();
        assertEquals("room2", eventList.get(0).getRoomNumber());
        assertEquals("event2", eventList.get(0).getID());
        assertEquals(1, eventList.get(2).getAttendees().size());
        assertEquals(1, eventList.get(2).getSpeakers().size());
        assertEquals("THIS_IS_DUPLICATE", eventList.get(3).getRoomNumber());
        assertEquals("event23", eventList.get(3).getID());
        assertEquals(eventList.size(), 4);
        assertEquals(eventList.get(1).getAttendees().size(), 1);

    }

    @Test
    public void testWriteDuplicatesInRelations(){
        EventGatewayDB eventDB = new EventGatewayDB();
        Attendee a = new Attendee("Lebron", "James", "a101");
        Event newEvent = new Event("room2", LocalDateTime.now(), LocalDateTime.now(), "event1", 23);
        newEvent.addAttendee(a.getID());
        ArrayList<Event> eventList = new ArrayList<>();
        eventList.add(newEvent);
        eventDB.writeData(eventList);
    }

    @Test
    public void testWriteUnwantedAttendee(){
        EventGatewayDB eventDB = new EventGatewayDB();
        Event newEvent = new Event("room2", LocalDateTime.now(), LocalDateTime.now(), "event1", 23);
        newEvent.setAsVIP();
        ArrayList<Event> eventList = new ArrayList<>();
        eventList.add(newEvent);
        eventDB.writeData(eventList);
    }

    @Test
    public void testUnwantedRemoved(){
        EventGatewayDB eventDB = new EventGatewayDB();
        ArrayList<Event> eventList = eventDB.readData();
        assertEquals(eventList.get(3).getAttendees().size(), 0);
        assertTrue(eventList.get(3).getVIPStatus());
    }
    @Test
    public void testDeleteData(){
        EventGatewayDB EGDB = new EventGatewayDB();
        EGDB.deleteData("event1");
    }
}