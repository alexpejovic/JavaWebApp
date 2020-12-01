package modules.gateways;

import modules.entities.*;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class RoomGatewayTest {

    //NOTE: For these tests to run properly the final variable <filename>
    //in RoomGateway must be changed to "roomsTest.ser"

    @Test
    public void testReadAndWriteSerFile() {

        ArrayList<Room> rooms = new ArrayList<>();
        Room r1 = new Room("12", 12);
        Room r2 = new Room("7", 50);
        r2.addEvent("1");
        rooms.add(r1);
        rooms.add(r2);

        File temp;
        RoomGateway gw = new RoomGateway();
        try
        {
            temp = File.createTempFile("roomsTest", ".ser");

            //Write to temp file
            gw.writeSerFile(rooms);

            //Read temp file and compare
            ArrayList<Room> actualRooms = gw.readSerFile();

            assertEquals(r1.getRoomNumber(), actualRooms.get(0).getRoomNumber());
            assertEquals(r1.getEvents(), actualRooms.get(0).getEvents());
            assertNotNull(actualRooms.get(0));

            assertEquals(r2.getCapacity(), actualRooms.get(1).getCapacity());
            assertEquals(r2.getEvents(), actualRooms.get(1).getEvents());

            temp.delete();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}