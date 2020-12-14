package modules.gateways;

import modules.entities.Message;

import java.util.ArrayList;

public interface MessageStrategy {
    public ArrayList<Message> readData();
    public void writeData(ArrayList<Message> writeMessage);
    public void setFilename(String newFilename);
    public void deleteData(String messageID);
}
