package modules.gateways;
import modules.entities.*;
import modules.gateways.EventGatewayDB;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.Assert.*;
import java.time.LocalDateTime;

public class RoomGatewayDBTest {
    //Run tests in order on an empty database

    @Test
    public void testWriteData(){
        RoomGatewayDB RGDB = new RoomGatewayDB();
        Room room = new Room("r1111", 23);
        ArrayList<Room> roomList = new ArrayList<>();
        roomList.add(room);
        RGDB.writeData(roomList);
    }

    @Test
    public void testWriteDataWithEvent(){
        RoomGatewayDB RGDB = new RoomGatewayDB();
        Room room = new Room("r1234", 23);
        ArrayList<Room> roomList = new ArrayList<>();
        roomList.add(room);
        RGDB.writeData(roomList);
        EventGatewayDB EGDB = new EventGatewayDB();
        Event event1 = new Event("r1234", LocalDateTime.now(), "e5555");
        ArrayList<Event> eventList = new ArrayList<>();
        eventList.add(event1);
        EGDB.writeData(eventList);
    }

    @Test
    public void testReadData(){
        RoomGatewayDB RGDB = new RoomGatewayDB();
        ArrayList<Room> roomList = RGDB.readData();
        assertEquals(roomList.size(), 2);
        assertEquals(roomList.get(0).getCapacity(), 23);
    }
}
