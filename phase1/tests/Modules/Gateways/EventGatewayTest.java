package Modules.Gateways;

import Modules.Entities.*;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.Assert.*;
import java.time.LocalDateTime;

public class EventGatewayTest {

    //NOTE: For these tests to run properly the final variable <filename>
    //in EventGateway must be changed to "eventsTest.ser"

    @Test
    public void testReadAndWriteSerFile() {

        ArrayList<Event> events = new ArrayList<>();
        LocalDateTime e1Start = LocalDateTime.of(2020, 10, 15, 13, 25);
        LocalDateTime e1End = LocalDateTime.of(2020, 10, 15, 14, 25);
        Event e1 = new Event("1", e1Start, e1End, "1");
        events.add(e1);

        File temp;
        EventGateway gw = new EventGateway();
        try
        {
            temp = File.createTempFile("eventsTest", ".ser");

            //Write to temp file
            gw.writeSerFile(events);

            //Read temp file and compare
            ArrayList<Event> actualEvents = gw.readSerFile();

            assertEquals(e1.getRoomNumber(), actualEvents.get(0).getRoomNumber());
            assertEquals(e1.getStartTime(), actualEvents.get(0).getStartTime());
            assertEquals(e1.getEndTime(), actualEvents.get(0).getEndTime());
            assertEquals(e1.getID(), actualEvents.get(0).getID());
            assertNotNull(actualEvents.get(0));

            temp.delete();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}