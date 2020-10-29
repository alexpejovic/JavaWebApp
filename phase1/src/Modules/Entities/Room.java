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

    public void removeEvent(String eventId){
        try{
            events.remove(eventId);
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("There is no event with that id occurring in this Room");
        }
    }

    public ArrayList getEvents(){
        ArrayList<String> copy = new ArrayList<>();

        for (String eventId: events){
            copy.add(eventId);
        }

        return copy;
    }

    /**
     * Checks if a event is occurring in this room.
     * @param eventId the id of the event that we want to check
     * @return true iff the event is in the list of events occurring in this room
     */
    public boolean isEventInRoom(String eventId){
        for (String eventsId: events){
            if(eventId.equals(eventsId)){
                return true;
            }
        }
        return false;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getRoomNumber() {
        return roomNumber;
    }
}
