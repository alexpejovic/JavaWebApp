package modules.gateways;

import modules.entities.Attendee;
import modules.entities.Organizer;
import modules.entities.Speaker;
import modules.entities.User;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class UserGatewaySerTest {

    //NOTE: For these tests to run properly the final variable <filename>
    //in UserGateway must be changed to "usersTest.ser"

    @Test
    public void testReadAndWriteSerFile() {
        User u1 = new Attendee("u1", "u1pass", "1");
        User u2 = new Speaker("u2", "u2pass", "2");
        User u3 = new Organizer("u3", "u3pass", "3");
        ArrayList<User> users = new ArrayList<>();
        users.add(u1);
        users.add(u2);
        users.add(u3);

        File temp;
        UserGateway gw = new UserGateway();
        try
        {
            temp = File.createTempFile("usersTest", ".ser");

            //Write to temp file
            gw.writeSerFile(users);

            //Read temp file and compare
            ArrayList<User> actualUsers = gw.readSerFile();
            assertEquals("u1", actualUsers.get(0).getUsername());
            assertTrue(actualUsers.get(0) instanceof Attendee);
            assertFalse(actualUsers.get(0) instanceof Speaker);

            assertEquals(u2.getPassword(), actualUsers.get(1).getPassword());
            assertTrue(actualUsers.get(1) instanceof Speaker);

            assertEquals(u3.getID(), actualUsers.get(2).getID());
            assertTrue(actualUsers.get(2) instanceof Organizer);

            temp.delete();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
