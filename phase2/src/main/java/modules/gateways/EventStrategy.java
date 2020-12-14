package modules.gateways;

import modules.entities.Event;

import java.io.IOException;
import java.util.ArrayList;

public interface EventStrategy {
    public ArrayList<Event> readData();
    public void writeData(ArrayList<Event> writeEvents) throws ClassNotFoundException;
    public void setFilename(String newFilename);
}
