package Modules.Gateways;

import Modules.Entities.User;
import java.util.ArrayList;
import java.io.*;

/**
 * reads and writes files for Users
 */
public class UserGateway {

    private final String filename = "res/users.ser";

    public ArrayList<User> readSerFile() {

        ArrayList<User> users = null;
        try {
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream storedUsers = new ObjectInputStream(file);

            users = (ArrayList<User>) storedUsers.readObject();

            storedUsers.close();
            file.close();

            System.out.println("successfully read Users");

            return users;

        } catch (FileNotFoundException e) {
            System.out.println(filename + " is missing");
        } catch (IOException | ClassNotFoundException e) {
            return users;
        }

        return users;
    }

    /**
     * @param writeUsers ArrayList of Users being written to filename
     * @throws IOException Exception thrown when Object cannot be found
     */
    public void writeSerFile(ArrayList<User> writeUsers) throws IOException {
        try{
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream writer = new ObjectOutputStream(file);

            writer.writeObject(writeUsers);

            writer.close();
            file.close();

            System.out.println("successfully stored Users");

        } catch (FileNotFoundException e) {
            System.out.println(filename + " not found");
        }
    }
}
