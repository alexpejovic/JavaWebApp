package modules.gateways;

import modules.entities.Room;

import java.util.ArrayList;

public class RoomGatewayDB implements RoomStrategy {
    @Override
    public ArrayList<Room> readData() {
        return null;
    }

    @Override
    public void writeData(ArrayList<Room> writeRooms) {

    }

    @Override
    public void setFilename(String newFilename) {

    }
}
