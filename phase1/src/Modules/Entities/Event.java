package Modules.Entities;

import java.util.ArrayList;

public class Event {

    /** ArrayList that stores the usernames of attendees who are signed up to attend this event**/
    private ArrayList<String> attendeeList;

    /** The maximum number of attendees who can attend the event**/
    private int capacity;

    /** The location of the event indicated by the unique room number of the
     * room where the event will take place**/
    private int roomNumber;


    /**
     * Sets the room capacity and room number for the room where the Event will take place
     * @param roomNumber the room number the Event will be held
     */
    public Event(int roomNumber){
        this.capacity = 2;
        this.roomNumber = roomNumber;
        attendeeList = new ArrayList<>();
    }

    /**
     * @return The room capacity for the Event
     */
    public int getRoomCapacity(){
        return capacity;
    }

    /**
     * @return The room number of the room where the Event will be held
     */
    public int getRoomNumber(){
        return roomNumber;
    }

    /**
     * Removes specific attendee from the Event's list of attendees
     * @param userName the username of the attendee being removed from the Event's attendee list
     */
    public void removeAttendee(String userName){
        attendeeList.remove(userName);
    }

    /**
     * Adds specific attendee to the Event's list of attendees
     * @param userName the username of the attendee being added to the Event's attendee list
     */
    public void addAttendee(String userName){
        attendeeList.add(userName);
    }

}
