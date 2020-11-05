package Modules.Entities;

import java.util.ArrayList;
import java.time.LocalDateTime;

public class Event implements Identifiable{

    /** ArrayList that stores the usernames of attendees who are signed up to attend this event**/
    private ArrayList<String> attendeeList;

    /** The maximum number of attendees who can attend the event**/
    private int capacity;

    /** The location of the event indicated by the unique room number of the
     * room where the event will take place**/
    private String roomNumber;

    /** The start time for the Event **/
    private LocalDateTime startTime;

    /** The name of the Event **/
    private String name;

    private String eventId;

    /**
     * Sets the room capacity and room number for the room where the Event will take place
     * @param roomNumber the room number the Event will be held
     * @param time the time at which the Event will begin
     */
    public Event(String roomNumber, LocalDateTime time){
        this.capacity = 2;
        this.roomNumber = roomNumber;
        this.startTime = time;
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
    public String getRoomNumber(){
        return roomNumber;
    }

    /**
     * @return The the start time for the Event
     */
    public LocalDateTime getStartTime(){ return startTime;}

    @Override
    public String getID() { return eventId;}

    @Override
    public void setID(String ID) { eventId = ID;}


    /** Gets the name of the Event
     *
     * @return The name of the Event
     */
    public String getName() {
        return name;
    }


    /** Sets the name of the Event
     *
     * @param name The name of the Event
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Gets the remaining available seats at this event
     *
     * @return The number of seats available at this Event
     */
    public int getAvailableSeats() {
        return capacity - attendeeList.size();
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
