package modules.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

import static org.junit.Assert.*;

public class MessageTest {
    // Example messages
    LocalDateTime message1Date = LocalDateTime.of(2020, 10, 15, 13, 25);
    LocalDateTime message2Date = LocalDateTime.of(2020, 10, 15, 13, 26);
    LocalDateTime message3Date = LocalDateTime.of(2020, 10, 15, 13, 35);
    LocalDateTime message4Date = LocalDateTime.of(2020, 10, 15, 13, 35);
    String message1ID = String.format("%s,%s,%s", "user1", "user2", message1Date);
    String message2ID = String.format("%s,%s,%s", "user1", "user3", message2Date);
    String message3ID = String.format("%s,%s,%s", "user3", "user2", message3Date);
    String message4ID = String.format("%s,%s,%s", "user2", "user1", message4Date);
    Message message1 = new Message("yo event 2 is lit!", "user1", "user2", message1ID, message1Date);
    Message message2 = new Message("where is the bathroom?", "user1", "user3", message2ID, message2Date);
    Message message3 = new Message("how you doin?", "user3", "user2", message3ID, message3Date);
    Message message4 = new Message("event 3 is better tbh", "user2", "user1", message4ID, message4Date);

    // Test Message constructor
    @Test
    public void testMessage() {
        LocalDateTime messageDate = LocalDateTime.of(2020, 10, 15, 13, 25);
        String messageID = String.format("%s,%s,%s", "user1", "user2", messageDate);
        Message m = new Message("Hello World!", "user1", "user2", messageID, messageDate);
        Message m2 = new Message("So long and thanks for all the fish", "user1", "user2");
    }

    // Test Message getters
    @Test
    public void testGetters() {
        Message message = new Message("So long and thanks for all the fish", "user3", "user1");
        assertEquals("yo event 2 is lit!", message1.getContent());
        assertEquals("where is the bathroom?", message2.getContent());
        assertEquals("user3", message3.getSenderID());
        assertEquals("user1", message4.getReceiverID());
        assertEquals("user2", message1.getReceiverID());
        assertEquals("user3", message3.getSenderID());
        assertEquals(message4Date, message4.getDateTime());
        assertNotNull(message.getDateTime());
    }

    // Test Message comparison
    @Test
    public void testComparison() {
        assertEquals(-1, message1.compareTo(message2));
        assertEquals(1, message3.compareTo(message1));
        assertEquals(-1, message1.compareTo(message3));
        assertEquals(0, message3.compareTo(message4));
        assertEquals(0, message4.compareTo(message3));
    }


    // Test sorting
    @Test
    public void testSorting() {
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message3);
        messages.add(message1);
        messages.add(message4);
        messages.add(message2);
        Collections.sort(messages);
        assertEquals(message1, messages.get(0));
        assertEquals(message2, messages.get(1));
        assertEquals(message3, messages.get(2));
        assertEquals(message4, messages.get(3));
    }
}