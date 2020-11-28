package modules.gateways;

import modules.entities.*;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.Assert.*;
import java.time.LocalDateTime;

public class MessageGatewayTest {

    //NOTE: For these tests to run properly the final variable <filename>
    //in MessageGateway must be changed to "messagesTest.ser"

    @Test
    public void testReadAndWriteSerFile() {

        ArrayList<Message> messages = new ArrayList<>();
        LocalDateTime m1Date = LocalDateTime.of(2020, 10, 15, 13, 25);
        Message m1 = new Message("hi", "1", "2", "1", m1Date);
        messages.add(m1);

        File temp;
        MessageGateway gw = new MessageGateway();
        try
        {
            temp = File.createTempFile("messagesTest", ".ser");

            //Write to temp file
            gw.writeSerFile(messages);

            //Read temp file and compare
            ArrayList<Message> actualMessages = gw.readSerFile();

            assertEquals(m1.getContent(), actualMessages.get(0).getContent());
            assertEquals(m1.getSenderID(), actualMessages.get(0).getSenderID());
            assertEquals(m1.getReceiverID(), actualMessages.get(0).getReceiverID());
            assertEquals(m1.getID(), actualMessages.get(0).getID());
            assertEquals(m1.getDateTime(), actualMessages.get(0).getDateTime());
            assertNotNull(actualMessages.get(0));

            temp.delete();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}