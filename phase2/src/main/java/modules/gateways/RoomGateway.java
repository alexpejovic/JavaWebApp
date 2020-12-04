package modules.gateways;

import modules.entities.Room;

import java.util.ArrayList;

public class RoomGateway {

    private RoomStrategy strategy;

    public RoomGateway() {
        this.strategy = new RoomGatewayDB();
    }

    public ArrayList<Room> readData() {
        return this.strategy.readData();
    }

    public void writeData(ArrayList<Room> writeRooms) {
        this.strategy.writeData(writeRooms);
    }

    public void setStrategy(RoomStrategy newStrat) {
        this.strategy = newStrat;
    }

    public void setFilename(String newFilename) {
        this.strategy.setFilename(newFilename);
    }
}

