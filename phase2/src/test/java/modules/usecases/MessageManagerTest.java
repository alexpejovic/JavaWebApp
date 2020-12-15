package modules.usecases;

import modules.entities.Attendee;
import modules.entities.Message;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class MessageManagerTest {
    LocalDateTime message1Date = LocalDateTime.of(2020, 10, 15, 13, 25);
    LocalDateTime message2Date = LocalDateTime.of(2020, 10, 15, 13, 26);
    LocalDateTime message3Date = LocalDateTime.of(2020, 10, 15, 13, 35);
    String message1ID = String.format("%s,%s,%s", "user1", "user2", message1Date);
    String message2ID = String.format("%s,%s,%s", "user2", "user3", message2Date);
    String message3ID = String.format("%s,%s,%s", "user3", "user1", message3Date);
    Message message1 = new Message("yo event 2 is lit!", "user1", "user2", message1ID, message1Date);
    Message message2 = new Message("where is the bathroom?", "user2", "user3", message2ID, message2Date);
    Message message3 = new Message("how you doin?", "user3", "user1", message3ID, message3Date);

    // Test MessageManager Constructor
    @Test
    public void testMessageManager() {
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);
        messages.add(message3);
        MessageManager manager = new MessageManager(messages);
    }

    // Test getUserMessages
    @Test
    public void testGetUserMessages() {
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);
        messages.add(message3);
        MessageManager manager = new MessageManager(messages);
        ArrayList<String> userMessages = manager.getUserMessages("user1");
        assertEquals(message1.getID(), userMessages.get(0));
        assertEquals(message3.getID(), userMessages.get(1));
    }

    // Test sendMessage
    @Test
    public void testSendMessage() {
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);
        messages.add(message3);
        MessageManager manager = new MessageManager(messages);

        ArrayList<String> user1Messages = manager.getUserMessages("user1");
        ArrayList<String> user2Messages = manager.getUserMessages("user2");

        assertEquals(2, user1Messages.size());
        assertEquals(2, user2Messages.size());
        manager.sendMessage("user1", "user2", "never gonna give you up");

        user1Messages = manager.getUserMessages("user1");
        user2Messages = manager.getUserMessages("user2");
        assertEquals(3, user1Messages.size());
        assertEquals(3, user2Messages.size());

        System.out.println(user1Messages);
        System.out.println(user2Messages);

        assertEquals(user1Messages.get(2), user2Messages.get(2));
    }

    // Test getConversation
    @Test
    public void testGetConversation() {
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);
        messages.add(message3);
        MessageManager manager = new MessageManager(messages);
        manager.sendMessage("user1", "user2", "never gonna give you up");
        manager.sendMessage("user2", "user1", "never gonna let you down");

        ArrayList<HashMap<String, String>> conversation = manager.getConversation("user1", "user2");

        assertEquals("user1", manager.getSenderIDOfMessage(conversation.get(0).get("senderID")));
        assertEquals("user1", manager.getSenderIDOfMessage(conversation.get(1).get("senderID")));
        assertEquals("user2", manager.getSenderIDOfMessage(conversation.get(2).get("senderID")));

        assertEquals("yo event 2 is lit!",  manager.getContentOfMessage(conversation.get(0).get("content")));
        assertEquals("never gonna give you up", manager.getContentOfMessage(conversation.get(1).get("content")));
        assertEquals("never gonna let you down", manager.getContentOfMessage(conversation.get(2).get("content")));
    }

    @Test
    public void testSendMessageFirstMessage(){
        ArrayList<Message> newList = new ArrayList<>();
        MessageManager mm = new MessageManager(newList);
        Attendee a1 = new Attendee("Attendee1", "pwd", "101");
        Attendee a2 = new Attendee("Attendee2", "pwd", "202");
        a1.addToFriendList("202");
        a2.addToFriendList("101");
        mm.sendMessage(a1.getID(), a2.getID(), "Hello Attendee 2!");
        assertTrue(mm.getUserMessages(a1.getID()).size() == 1);
        assertTrue(mm.getUserMessages(a2.getID()).size() == 1);
    }

    @Test
    public void testGetAllMessages() {
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message3);
        messages.add(message1);
        messages.add(message2);

        MessageManager mm = new MessageManager(messages);
        ArrayList<Message> allMessages = mm.getAllMessages();

        assertEquals(message1, allMessages.get(0));
        assertEquals(message2, allMessages.get(1));
        assertEquals(message3, allMessages.get(2));

        mm.sendMessage("user1", "user3", "this is a new message");
        mm.sendMessage("user3", "user1", "bro what are you talking about?");

        allMessages = mm.getAllMessages();
        int len = allMessages.size();

        assertEquals(message1, allMessages.get(0));
        assertEquals("bro what are you talking about?", allMessages.get(len - 1).getContent());
        assertEquals("user3", allMessages.get(len - 2).getReceiverID());
    }


}