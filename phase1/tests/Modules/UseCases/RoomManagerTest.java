package Modules.UseCases;

import Modules.Entities.Event;
import Modules.Entities.Room;
import Modules.Exceptions.RoomNotFoundException;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class RoomManagerTest {

    @Test
    public void testCreateRoom(){
        RoomManager roomManager = new RoomManager();

        //should start with no events in room
        assertEquals(new ArrayList<Room>(), roomManager.getRooms() );

        // creating a room
        roomManager.createRoom("1",2);
        ArrayList<Room> expected = new ArrayList<>();
        expected.add(new Room("1", 2));
        assertTrue(testRoomArrayListEquals(expected,roomManager.getRooms()));

        // creating another room
        roomManager.createRoom("2", 5);
        expected.add(new Room("2", 5));
        assertTrue(testRoomArrayListEquals(expected,roomManager.getRooms()));

    }

    @Test
    // tests relating to accessing events occurring in a room
    public void testRoomEvents(){

        RoomManager roomManager = new RoomManager();

        // adding a room
        roomManager.createRoom("1",2);
        ArrayList<Room> expected = new ArrayList<>();
        expected.add(new Room("1", 2));
        assertTrue(testRoomArrayListEquals(expected,roomManager.getRooms()));

        // capacityOfRoom()
        assertEquals(2, roomManager.capacityOfRoom("1"));

        // isEventInRoom()
        assertFalse(roomManager.isEventInRoom("1", "e1"));

        // getEventsInRoom()
        // should have no events in room 1
        assertEquals(new ArrayList<String>(), roomManager.getEventsInRoom("1"));

        // addEventToRoom() - Adding event to room 1
        LocalDateTime date1 = LocalDateTime.of(2020, 11, 3, 11, 11);
        Event event1 = new Event("1", date1);
        event1.setID("e1");
        roomManager.addEventToRoom("1","e1");
        ArrayList<String> expected1 = new ArrayList<>();
        expected1.add("e1");
        assertEquals(expected1, roomManager.getEventsInRoom("1"));

        // removeEventFromRoom()
        roomManager.removeEventFromRoom("1","e1");
        expected1.remove("e1");
        assertEquals(expected1, roomManager.getEventsInRoom("1"));

    }

    @Test
    public void testIsRoomAvailable(){
        RoomManager roomManager = new RoomManager();
        EventManager eventManager = new EventManager();

        LocalDateTime time1 = LocalDateTime.of(2020,11, 5, 1 ,0);
        LocalDateTime time2 = LocalDateTime.of(2020,11, 5, 2 ,0);
        LocalDateTime time3 = LocalDateTime.of(2020,11, 5, 3 ,0);
        LocalDateTime time4 = LocalDateTime.of(2020,11, 5, 4 ,0);
        LocalDateTime time5 = LocalDateTime.of(2020,11, 5, 5 ,0);

        // adding a room 1 to eventManager
        roomManager.createRoom("1",2);
        ArrayList<Room> expected = new ArrayList<>();
        expected.add(new Room("1", 2));
        assertTrue(testRoomArrayListEquals(expected,roomManager.getRooms()));

        // adding events e1 and e2 to eventManager
        eventManager.createEvent("1",time2);
        // create event doesn't take a id?
        // constructor for event doesn't take id either?? - so possible for some instances of Event to not have id??
        //TODO: finish test


        // room 1 has no events
        assertTrue(roomManager.isRoomAvailable("1", time1, time3, eventManager));




    }


    // Testing that the correct exceptions are raised if we try to access a room that does not exist in a RoomManager

    @Test(expected = RoomNotFoundException.class)
    public void testCapacityOfRoomEx(){
        RoomManager roomManager = new RoomManager();
        roomManager.capacityOfRoom("1");
    }

    @Test(expected = RoomNotFoundException.class)
    public void testIsEventInRoomEx(){
        RoomManager roomManager = new RoomManager();
        roomManager.isEventInRoom("1", "e1");
    }

    @Test(expected = RoomNotFoundException.class)
    public void testAddEventToRoomEx(){
        RoomManager roomManager = new RoomManager();
        roomManager.addEventToRoom("1", "e1");
    }

    // helper method to determine if two ArrayLists of Rooms have the same rooms
    // using the .equals() method in Room not the one from Object
    private boolean testRoomArrayListEquals(ArrayList<Room> rooms1, ArrayList<Room> rooms2){
        for (Room room1: rooms1){
            boolean isFound = false;
            for (Room room2: rooms2){
                if (room1.equals(room2)){
                    isFound = true;
                }
            }
            if (!isFound){
                return false;
            }
        }
        for (Room room2: rooms2){
            boolean isFound = false;
            for (Room room1: rooms1){
                if (room1.equals(room2)){
                    isFound = true;
                }
            }
            if (!isFound){
                return false;
            }
        }
        return true;
    }


}