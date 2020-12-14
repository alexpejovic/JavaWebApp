package modules.gateways;

import modules.entities.Event;
import java.util.ArrayList;
import java.io.*;

/**
 * reads and writes files for Events
 */
public class EventGatewaySer implements EventStrategy{

    private String filename = "res/events.ser";

    //To run the unit test, this filename must be used
    //private String filename = "eventsTest.ser";


    public ArrayList<Event> readData() {

        ArrayList<Event> events = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream storedEvents = new ObjectInputStream(file);

            events = (ArrayList<Event>) storedEvents.readObject();

            storedEvents.close();
            file.close();

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
     */
    public void writeData(ArrayList<Event> writeEvents){
        try{
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream writer = new ObjectOutputStream(file);

            writer.writeObject(writeEvents);

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
}
