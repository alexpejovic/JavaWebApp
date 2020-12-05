package modules.gateways;

import modules.entities.*;
import modules.gateways.EventGatewayDB;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.Assert.*;
import java.time.LocalDateTime;

public class EventGatewayDBTest {

    @Test
    public void testReadFile(){
        EventGatewayDB eventDB = new EventGatewayDB();
        ArrayList<Event> eventList = eventDB.readData();
        assertEquals(2, eventList.size());
        assertEquals("room3", eventList.get(0).getRoomNumber());
        assertEquals("event23", eventList.get(0).getID());
    }

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
}