package modules.gateways;

import modules.entities.Event;

import java.io.IOException;
import java.util.ArrayList;

public class EventGatewayDB implements EventStrategy{
    @Override
    public ArrayList<Event> readData() {

        return null;
    }

    @Override
    public void writeData(ArrayList<Event> writeEvents) {

    }

    @Override
    public void setFilename(String newFilename) {

    }
}
