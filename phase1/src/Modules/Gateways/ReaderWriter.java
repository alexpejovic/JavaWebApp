package Modules.Gateways;

import java.util.ArrayList;
import java.io.*;

public class ReaderWriter {

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
            System.out.println("events.ser is missing");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

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
