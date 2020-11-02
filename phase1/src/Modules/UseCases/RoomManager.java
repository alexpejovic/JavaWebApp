package Modules.UseCases;

import Modules.Entities.Room;

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
    public void addRoom(int roomNumber, int capacity){
        boolean uniqueRoomNumber = true;
        for(Room room: rooms){
            if (room.getRoomNumber() == roomNumber){
                System.out.println("There is already a room with that room number.");
                uniqueRoomNumber = false;
            }
        }
        if (uniqueRoomNumber){
            rooms.add(new Room(roomNumber,capacity));
        }
    }

    /**
     * Checks if a room is hosting a specific event
     * @param roomNumber the room number of the Room we want to check
     * @param eventId the id of the Event we want to check
     * @return true iff there is a Room in this RoomManager that matches roomNumber and that Room
     */
    public boolean isHostingEvent(int roomNumber, String eventId){
        for(Room room: rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room.isEventInRoom(eventId);
            }
        }
        // there is no Room with this RoomManager with roomNumber
        System.out.println("There is no room with that room number in this Room Manager.");
        return false;
    }

    /**
     *Returns the capacity of the Room specified by that room number
     * @param roomNumber the room number of the Room we want to get the capacity of
     * @return the capacity of the room or -1 if there is no Room with that roomNumber in this RoomManager
     */
    public int capacityOfRoom(int roomNumber){
        for(Room room: rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room.getCapacity();
            }
        }
        // there is no Room with this RoomManager with roomNumber
        return -1;
    }


}
