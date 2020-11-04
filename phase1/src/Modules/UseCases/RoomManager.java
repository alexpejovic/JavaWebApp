package Modules.UseCases;

import Modules.Entities.Room;
import Modules.Exceptions.EventNotFoundException;
import Modules.Exceptions.NonUniqueIdException;
import Modules.Exceptions.RoomNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Use Case for basic management of room actions specified in {@link Modules.Entities.Room}
 */
public class RoomManager {
    /** A list of all Room entities in this conference */
    private ArrayList<Room> rooms;

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
    public void createRoom(String roomNumber, int capacity){
        for(Room room: rooms){
            if (room.getID().equals(roomNumber)){
                throw new NonUniqueIdException();
            }
        }
        rooms.add(new Room(roomNumber,capacity));
    }

    /**
     * Returns shallow copy of list of all Rooms in this RoomManager
     * @return copy of all Rooms entities in this RoomManager
     */
    public ArrayList<Room> getRooms(){
        ArrayList<Room> copy = new ArrayList<>();
        for (Room room : rooms){
            copy.add(room);
        }
        return copy;
    }

    /**
     * Private helper that returns the Room in this RoomManager with roomNumber
     * or raises a RoomNotFoundException if there is no Room in this RoomManager
     * @param roomNumber the unique room number of the Room we want
     * @return the Room in this RoomManager with roomNumber
     */
    private Room getRoom(String roomNumber){
        for(Room room: rooms){
            if (room.getID().equals(roomNumber)){
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
    public boolean isEventInRoom(String roomNumber, String eventId){
        return this.getRoom(roomNumber).isEventInRoom(eventId);
    }

    /**
     * Returns the capacity of a Room in rooms
     * @param roomNumber the room number of the Room we want to get the capacity of
     * @return the capacity of the room 
     */
    public int capacityOfRoom(String roomNumber){
        return this.getRoom(roomNumber).getCapacity();
    }

    /**
     * Add eventid to the list of events for a Room in rooms
     * @param roomNumber the room number of the Room we want to add the event to
     * @param eventId the id of the Event that we want to schedule in the specified Room
     */
    public void addEventToRoom(String roomNumber, String eventId){
        this.getRoom(roomNumber).addEvent(eventId);
    }

    /**
     * Remove eventid to the list of events for a Room in rooms
     * @param roomNumber the room number of the Room we want to remove the event to
     * @param eventId the id of the Event that we want to remove from the specified Room
     */
    public void removeEventFromRoom(String roomNumber, String eventId){
        this.getRoom(roomNumber).removeEvent(eventId);
    }

    /**
     * return a list of all event ids for events occurring for a Room in rooms
     * @param roomNumber the room number of the Room we want the list from
     */
    public ArrayList<String> getEventsInRoom(String roomNumber){
        return this.getRoom(roomNumber).getEvents();
    }

    /**
     * Returns whether or not there is an event booked in this room at a certain time period
     * @param roomNumber the room number of the room we want to check
     * @param startTime the start (inclusive) of the time period we want to check
     * @param endTime the end (exclusive) of the time period we want to check
     * @param eventManager the eventManager for this conference
     * @return true iff there is no events in this room anytime during the time period
     */
    public boolean isRoomAvailable(String roomNumber, LocalDateTime startTime,LocalDateTime endTime,
                                   EventManager eventManager){
        ArrayList<String> eventList = this.getRoom(roomNumber).getEvents();
        for (String eventId: eventList){
            LocalDateTime eventStartTime = eventManager.timeOfEvent(eventId);
            // For phase 1, events only last 1 hour so eventEndTime 1 hour past eventStartTime
            // TODO: change this once Event has endTime
            LocalDateTime eventEndTime = eventStartTime.plusHours(1);
            if(eventStartTime.isEqual(startTime) ||
                    (eventStartTime.isAfter(startTime) && eventStartTime.isBefore(endTime)) ||
                    (eventEndTime.isAfter(startTime) && eventEndTime.isBefore(endTime)) ) {
                return false;
            }
        }
        return true;
    }

}
