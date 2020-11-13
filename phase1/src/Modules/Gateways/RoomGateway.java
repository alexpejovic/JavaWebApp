package Modules.Gateways;

import Modules.Entities.Room;
import java.io.*;
import java.util.ArrayList;

/**
 * reads and writes files for Rooms
*/
public class RoomGateway {

    String filename = "res/rooms.ser";

    public RoomGateway() {
        this.filename = filename;
    }

    /**
     * @param filename the name of the file being read
     */
    public ArrayList<Room> readSerFile(String filename) {

        ArrayList<Room> rooms = null;
        try {
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream storedRooms = new ObjectInputStream(file);

            rooms = (ArrayList<Room>) storedRooms.readObject();

            storedRooms.close();
            file.close();

            System.out.println("Successfully read Rooms");

            return rooms;

        } catch (FileNotFoundException e) {
            System.out.println(filename + " is missing");
        } catch (IOException | ClassNotFoundException e) {
            return rooms;
        }

        return rooms;
    }

    /**
     * @param filename the name of the file being written to
     * @param writeRooms ArrayList of objects being written to filename
     * @throws IOException Exception thrown when Object cannot be found
     */
    public void writeSerFile(String filename, ArrayList<Room> writeRooms) throws IOException {
        try{
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream writer = new ObjectOutputStream(file);

            writer.writeObject(writeRooms);

            writer.close();
            file.close();

            System.out.println("successfully stored Rooms");

        } catch (FileNotFoundException e) {
            System.out.println(filename + " not found");
        }
    }
}
