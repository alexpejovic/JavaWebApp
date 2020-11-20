package Modules.Gateways;

import Modules.Entities.Message;
import java.util.ArrayList;
import java.io.*;

/**
 * reads and writes files for Messages
 */
public class MessageGateway {

    private final String filename = "res/messages.ser";

    //To run the unit test, this filename must be used
    //private final String filename = "messagesTest.ser";

    public ArrayList<Message> readSerFile() {

        ArrayList<Message> messages = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream storedMessages = new ObjectInputStream(file);

            messages = (ArrayList<Message>) storedMessages.readObject();

            storedMessages.close();
            file.close();

            return messages;

        } catch (FileNotFoundException e) {
            System.out.println(filename + " is missing");
        } catch (IOException | ClassNotFoundException e) {
            return messages;
        }

        return messages;
    }

    /**
     * @param writeMessages ArrayList of objects being written to filename
     * @throws IOException Exception thrown when Object cannot be found
     */
    public void writeSerFile(ArrayList<Message> writeMessages) throws IOException {
        try{
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream writer = new ObjectOutputStream(file);

            writer.writeObject(writeMessages);

            writer.close();
            file.close();

        } catch (FileNotFoundException e) {
            System.out.println(filename + " not found");
        }
    }
}
