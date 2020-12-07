package modules.usecases;

import modules.entities.Room;
import modules.exceptions.NonUniqueIdException;
import modules.exceptions.RoomNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Use Case for basic management of room actions specified in {@link modules.entities.Room}
 */
public class RoomManager {
    /** A list of all Room entities in this conference */
    private ArrayList<Room> rooms;


    /**
     * Initializes a new RoomManager
     * @param rooms the Rooms to be put in this RoomManager
     */
    public RoomManager(ArrayList<Room> rooms){
        this.rooms = rooms;
    }

    /**
     * Creates a new Room object and adds it to this RoomManager's list of Rooms
     * only if the new Room's roomNumber is not the same as any other Room
     * in this RoomManager's list of Rooms
     * @param roomNumber the unique room number of this room
     * @param capacity maximum number of people allowed in this room
     * @throws NonUniqueIdException when there is already a room with the same room number in this RoomManager
     */
    public void createRoom(String roomNumber, int capacity){
        for(Room room: rooms){
            if (room.getRoomNumber().equals(roomNumber)){
                throw new NonUniqueIdException();
            }
        }
        rooms.add(new Room(roomNumber,capacity));
    }

    /**
     * Creates a new Room object and adds it to this RoomManager's list of Rooms
     * only if the new Room's roomNumber is not the same as any other Room
     * in this RoomManager's list of Rooms
     * the new room number will be "r" +"i" where i represents that this is the ith Room in this RoomManager
     * @param capacity maximum number of people allowed in this room
     */
    public void createRoom(int capacity){
        String roomNumber = "r" + rooms.size();
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

    public ArrayList<String> roomsContainingEvent(String eventId){
        ArrayList<String> roomNumberList = new ArrayList<>();
        for (Room room: rooms){
            if (room.isEventInRoom(eventId)){
                roomNumberList.add(room.getRoomNumber());
            }
        }
        return roomNumberList;
    }

    /**
     * Private helper that returns the Room in this RoomManager with roomNumber
     * or raises a RoomNotFoundException if there is no Room in this RoomManager
     * @param roomNumber the unique room number of the Room we want
     * @return the Room in this RoomManager with roomNumber
     * @throws RoomNotFoundException if there is no room with given RoomNumber in this Room
     */
    private Room getRoom(String roomNumber){
        for(Room room: rooms){
            if (room.getRoomNumber().equals(roomNumber)){
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
    public int getCapacityOfRoom(String roomNumber){
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
     * Remove eventId to the list of events for a Room in rooms
     * @param roomNumber the room number of the Room we want to remove the event to
     * @param eventId the id of the Event that we want to remove from the specified Room
     * @throws modules.exceptions.EventNotFoundException if there is no event in this room with eventID
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
            if(eventManager.isEventInTimePeriod(eventId,startTime,endTime)){
                return false;
            }
        }
        return true;
    }

}
