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
}
