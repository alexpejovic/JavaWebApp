package modules.gateways;

import modules.entities.Message;
import java.util.ArrayList;
import java.io.*;

/**
 * reads and writes files for Messages
 */
public class MessageGatewaySer implements MessageStrategy {

    private String filename = "res/messages.ser";

    //To run the unit test, this filename must be used
    //private String filename = "messagesTest.ser";

    public ArrayList<Message> readData() {

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
     */
    public void writeData(ArrayList<Message> writeMessages) {
        try{
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream writer = new ObjectOutputStream(file);

            writer.writeObject(writeMessages);

            writer.close();
            file.close();

        } catch (FileNotFoundException e) {
            System.out.println(filename + " not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setFilename(String newFilename) {
        filename = newFilename;
    }

    @Override
    public void deleteData(String messageID) {

    }
}
