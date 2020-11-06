package Modules.Entities;

import Modules.Exceptions.EventNotFoundException;

import java.util.ArrayList;

/** A class representing a room in a conference
 **/
public class Room implements Identifiable{
    /** list of event ids for the Events that this room is hosting
    rooms can only host one event at a time **/
    private ArrayList<String> events;

    /** room number of this Room
    room number are unique to each Room object **/
    private String roomNumber;

     /** the maximum number of people allowed in this room, including Speakers **/
    private int capacity;

    /**
     * Initializes a new Room object with no events
     * @param roomNumber the number of the room
     * @param capacity the maximum number of people allowed in the room
     */
    public Room(String roomNumber, int capacity){
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        events = new ArrayList<>();
    }

    /**
     * Returns the unique room number of this Room
     * @return room number of the room
     */
    @Override
    public String getID() {
        return roomNumber;
    }

    /**
     * @param ID unique id room number for this Room
     */
    @Override
    public void setID(String ID) {
        roomNumber = ID;
    }

    /**
     * Adds a event id to the list of events this room is hosting
     * @param eventId the id of the event we want to add
     */
    public void addEvent(String eventId){
        events.add(eventId);
    }

    /**
     * Removes a event id to the list of events this room is hosting
     * If the event id passed in is not scheduled in the room,
     * a error message is printed
     * @param eventId the id of the event we want to add
     */
    public void removeEvent(String eventId){
        if(events.contains(eventId)){
            events.remove(eventId);
        }
        else{
            // there is no event with that id in this room
            throw new EventNotFoundException();
        }
    }

    /**
     * Returns a list of event ids for all events that occur in this room
     * @return the list of event ids
     */
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

    /**
     * Returns the maximum people allowed in this room, including Speakers
     * @return maximum number of people allowed in this room
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * @return the room number of this Room
     */
    public String getRoomNumber() {return this.roomNumber;}

}
