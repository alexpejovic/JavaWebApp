package Modules.Gateways;

import Modules.Entities.Event;
import java.util.ArrayList;
import java.io.*;

/**
 * reads and writes files for Events
 */
public class EventGateway {

    private final String filename = "res/events.ser";

    public ArrayList<Event> readSerFile() {

        ArrayList<Event> events = null;
        try {
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream storedEvents = new ObjectInputStream(file);

            events = (ArrayList<Event>) storedEvents.readObject();

            storedEvents.close();
            file.close();

            System.out.println("Successfully read Events");

            return events;

        } catch (FileNotFoundException e) {
            System.out.println(filename + " is missing");
        } catch (IOException | ClassNotFoundException e) {
            return events;
        }

        return events;
    }

    /**
     * @param writeEvents ArrayList of objects being written to filename
     * @throws IOException Exception thrown when Object cannot be found
     */
    public void writeSerFile(ArrayList<Event> writeEvents) throws IOException {
        try{
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream writer = new ObjectOutputStream(file);

            writer.writeObject(writeEvents);

            writer.close();
            file.close();

            System.out.println("successfully stored Events");

        } catch (FileNotFoundException e) {
            System.out.println(filename + " not found");
        }
    }
}
