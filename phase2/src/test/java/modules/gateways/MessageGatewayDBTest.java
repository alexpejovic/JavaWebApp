package modules.gateways;
import modules.entities.*;
import modules.gateways.EventGatewayDB;
import modules.gateways.MessageGatewayDB;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.Assert.*;
import java.time.LocalDateTime;

public class MessageGatewayDBTest {
    //Run the tests in order on an empty database

    @Test
    public void testWriteData(){
        Message newMessage = new Message("Hey, how are you?", "a1111", "s5555", "m8888", LocalDateTime.now());
        ArrayList<Message> messageList = new ArrayList();
        messageList.add(newMessage);
        MessageGatewayDB MGDB = new MessageGatewayDB();
        MGDB.writeData(messageList);
    }

    @Test
    public void testWriteData2(){
        Message newMessage = new Message("GoodBye!", "a1111", "s5555", "m9999", LocalDateTime.now());
        ArrayList<Message> messageList = new ArrayList();
        messageList.add(newMessage);
        MessageGatewayDB MGDB = new MessageGatewayDB();
        MGDB.writeData(messageList);
    }

    @Test
    public void testReadData(){
        MessageGatewayDB MGDB = new MessageGatewayDB();
        ArrayList<Message> messageList = MGDB.readData();
        assertEquals(messageList.get(0).getID(), "m8888");
        assertEquals(messageList.get(0).getSenderID(), "a1111");
        assertEquals(messageList.get(0).getReceiverID(), "s5555");
        assertEquals(messageList.size(), 2);
    }

    @Test
    public void testWriteDataDuplicate(){
        Message newMessage = new Message("THIS_IS_A_DUPLICATE", "a1111", "s5555", "m9999", LocalDateTime.now());
        ArrayList<Message> messageList = new ArrayList();
        messageList.add(newMessage);
        MessageGatewayDB MGDB = new MessageGatewayDB();
        MGDB.writeData(messageList);
    }

    @Test
    public void testReadDataCheckDuplicate(){
        MessageGatewayDB MGDB = new MessageGatewayDB();
        ArrayList<Message> messageList = MGDB.readData();
        assertEquals(messageList.get(0).getID(), "m8888");
        assertEquals(messageList.get(0).getSenderID(), "a1111");
        assertEquals(messageList.get(0).getReceiverID(), "s5555");
        assertEquals(messageList.get(1).getID(), "m9999");
        assertEquals(messageList.get(1).getContent(), "THIS_IS_A_DUPLICATE");
        assertEquals(messageList.size(), 2);
    }
    @Test
    public void testWriteDataArchived(){
        Message newMessage = new Message("GONNA GET ARCHIVED", "a1111", "s5555", "m8888", LocalDateTime.now());
        newMessage.markAsRead();
        newMessage.markAsArchived();
        ArrayList<Message> messageList = new ArrayList();
        messageList.add(newMessage);
        MessageGatewayDB MGDB = new MessageGatewayDB();
        MGDB.writeData(messageList);
    }

    @Test
    public void testWriteDataArchivedTest(){
        MessageGatewayDB MGDB = new MessageGatewayDB();
        ArrayList<Message> messageList = MGDB.readData();
        assertEquals(true, messageList.get(1).getHasBeenRead());
        assertEquals(true, messageList.get(1).getIsArchived());
    }
}
