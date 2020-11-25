package Modules.Gateways;

import Modules.Entities.Room;
import java.io.*;
import java.util.ArrayList;

/**
 * reads and writes files for Rooms
*/
public class RoomGateway {

    private String filename = "res/rooms.ser";

    //To run the unit test, this filename must be used
    //private String filename = "roomsTest.ser";

    public ArrayList<Room> readSerFile() {

        ArrayList<Room> rooms = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream storedRooms = new ObjectInputStream(file);

            rooms = (ArrayList<Room>) storedRooms.readObject();

            storedRooms.close();
            file.close();

            return rooms;

        } catch (FileNotFoundException e) {
            System.out.println(filename + " is missing");
        } catch (IOException | ClassNotFoundException e) {
            return rooms;
        }

        return rooms;
    }

    /**
     * @param writeRooms ArrayList of objects being written to filename
     * @throws IOException Exception thrown when Object cannot be found
     */
    public void writeSerFile(ArrayList<Room> writeRooms) throws IOException {

        try{
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream writer = new ObjectOutputStream(file);

            writer.writeObject(writeRooms);

            writer.close();
            file.close();

        } catch (FileNotFoundException e) {
            System.out.println(filename + " not found");
        }
    }

    public void setFilename(String newFilename) {
        filename = newFilename;
    }
}
