package modules.gateways;

import modules.entities.User;
import java.util.ArrayList;
import java.io.*;

/**
 * reads and writes files for Users
 */
public class UserGateway {

    private String filename = "res/users.ser";

    //To run the unit test, this filename must be used
    //private String filename = "usersTest.ser";

    public ArrayList<User> readSerFile() {

        ArrayList<User> users = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream storedUsers = new ObjectInputStream(file);

            users = (ArrayList<User>) storedUsers.readObject();

            storedUsers.close();
            file.close();

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

        } catch (FileNotFoundException e) {
            System.out.println(filename + " not found");
        }
    }

    public void setFilename(String newFilename) {
        filename = newFilename;
    }
}
