package modules.gateways;

import modules.entities.Room;

import java.util.ArrayList;

public interface RoomStrategy {
    public ArrayList<Room> readData();
    public void writeData(ArrayList<Room> writeRooms);
    public void setFilename(String newFilename);
}
