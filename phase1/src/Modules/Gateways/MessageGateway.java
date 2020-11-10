package Modules.Gateways;

import Modules.Entities.Message;
import java.util.ArrayList;
import java.io.*;

/**
 * reads and writes files for Messages
 */
public class MessageGateway {

    /**
     * @param filename the name of the file being read
     */
    public ArrayList<Message> readSerFile(String filename) {

        ArrayList<Message> messages = null;
        try {
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream storedMessages = new ObjectInputStream(file);

            messages = (ArrayList<Message>) storedMessages.readObject();

            storedMessages.close();
            file.close();

            System.out.println("Successfully read Messages");

            return messages;

        } catch (FileNotFoundException e) {
            System.out.println(filename + " is missing");
        } catch (IOException | ClassNotFoundException e) {
            return messages;
        }

        return messages;
    }

    /**
     * @param filename the name of the file being written to
     * @param writeMessages ArrayList of objects being written to filename
     * @throws IOException Exception thrown when Object cannot be found
     */
    public void writeSerFile(String filename, ArrayList<Message> writeMessages) throws IOException {
        try{
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream writer = new ObjectOutputStream(file);

            writer.writeObject(writeMessages);

            writer.close();
            file.close();

            System.out.println("successfully stored Messages");

        } catch (FileNotFoundException e) {
            System.out.println(filename + " not found");
        }
    }
}
