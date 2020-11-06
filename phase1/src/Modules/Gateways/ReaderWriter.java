package Modules.Gateways;

import java.util.ArrayList;
import java.io.*;

/**
 * reads and writes files for our program
 */
public class ReaderWriter {

    /**
     * @param filename the name of the file being read
     */
    public void readSerFile(String filename) {

        ArrayList<Object> objects = null;
        try {
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream object = new ObjectInputStream(file);

            objects = (ArrayList<Object>) object.readObject();

            object.close();
            file.close();

            System.out.println("Successfully read Events");

        } catch (FileNotFoundException e) {
            System.out.println(filename + " is missing");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param filename the name of the file being written to
     * @param writeObjects ArrayList of objects being written to filename
     * @throws IOException Exception thrown when Object cannot be found
     */
    public void writeSerFile(String filename, ArrayList<Object> writeObjects) throws IOException {
        try{
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream writer = new ObjectOutputStream(file);

            writer.writeObject(writeObjects);

            writer.close();
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println(filename + " not found");
        }
    }
}
