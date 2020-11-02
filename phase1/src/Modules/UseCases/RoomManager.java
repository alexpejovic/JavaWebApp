package Modules.UseCases;

import Modules.Entities.Room;
import Modules.Exceptions.NonUniqueIdException;
import Modules.Exceptions.RoomNotFoundException;

import java.util.ArrayList;

/**
 * Use Case for basic management of room actions specified in {@link Modules.Entities.Room}
 */
public class RoomManager {
    /** A list of all Room entities in this conference */
    private ArrayList<Room> rooms;
    private Object RoomNotFoundException;

    /**
     * Initializes a new RoomManager with no Rooms
     */
    public RoomManager(){
        this.rooms = new ArrayList<Room>();
    }

    /**
     * Creates a new Room object and adds it to this RoomManager's list of Rooms
     * only if the new Room's roomNumber is not the same as any other Room
     * in this RoomManager's list of Rooms
     * @param roomNumber the unique room number of this room
     * @param capacity maximum number of people allowed in this room
     */
    public void addRoom(int roomNumber, int capacity){
        for(Room room: rooms){
            if (room.getRoomNumber() == roomNumber){
                throw new NonUniqueIdException();
            }
        }
        rooms.add(new Room(roomNumber,capacity));
    }

    /**
     * Private helper that returns the Room in this RoomManager with roomNumber
     * or raises a RoomNotFoundException if there is no Room in this RoomManager
     * @param roomNumber the unique room number of the Room we want
     * @return the Room in this RoomManager with roomNumber
     */
    private Room getRoom(int roomNumber){
        for(Room room: rooms){
            if (room.getRoomNumber() == roomNumber){
                return room;
            }
        }
        // there is no Room with this RoomManager with roomNumber
        throw new RoomNotFoundException();
    }


    /**
     * Checks if a Room in rooms is hosting a specific event
     * @param roomNumber the room number of the Room we want to check
     * @param eventId the id of the Event we want to check
     * @return true iff there is a Room in this RoomManager that matches roomNumber and that Room
     */
    public boolean isHostingEvent(int roomNumber, String eventId){
        return this.getRoom(roomNumber).isEventInRoom(eventId);
    }

    /**
     * Returns the capacity of a Room in rooms
     * @param roomNumber the room number of the Room we want to get the capacity of
     * @return the capacity of the room 
     */
    public int capacityOfRoom(int roomNumber){
        return this.getRoom(roomNumber).getCapacity();
    }

    /**
     * Add eventid to the list of events for a Room in rooms
     * @param roomNumber the room number of the Room we want to add the event to
     * @param eventId the id of the Event that we want to schedule in the specified Room
     */
    public void addEventToRoom(int roomNumber, String eventId){
        this.getRoom(roomNumber).addEvent(eventId);
    }

    /**
     * Remove eventid to the list of events for a Room in rooms
     * @param roomNumber the room number of the Room we want to remove the event to
     * @param eventId the id of the Event that we want to remove from the specified Room
     */
    public void removeEventFromRoom(int roomNumber, String eventId){
        this.getRoom(roomNumber).removeEvent(eventId);
    }

    /**
     * return a list of all event ids for events occurring for a Room in rooms
     * @param roomNumber the room number of the Room we want the list from
     */
    public ArrayList getEventsInRoom(int roomNumber){
        return this.getRoom(roomNumber).getEvents();
    }


}
