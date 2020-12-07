package modules.gateways;

import modules.entities.User;

import java.util.ArrayList;

public interface UserStrategy {
    public ArrayList<User> readData();
    public void writeData(ArrayList<User> writeUsers);
    public void setFilename(String newFilename);
}
