package Modules.Entities;

import java.util.ArrayList;


public class Room {
    // list of event ids for the Events that this room is hosting
    // rooms can only host one event at a time
    private ArrayList<String> events;

    // room number of this Room
    // room number are unique to each Room object
    private int roomNumber;

    // the maximum number of people allowed in this room, including Speakers
    private int capacity ;

    public Room(int roomNumber, int capacity){
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        events = new ArrayList<String>();
    }

    public void addEvent(String eventId){
        events.add(eventId);
    }

    public int getCapacity() {
        return capacity;
    }

    public int getRoomNumber() {
        return roomNumber;
    }
}
