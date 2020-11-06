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

    /** The Id of the Event **/
    private String eventId;

    /** The end time for the Event **/
    private String endTime;

    /** The name of the Event **/
    private String name;
    /** The Speaker who will present at the Event **/
    private String speaker = null;

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

    /**
     * Set the end time for the Event
     * @param time is the start Time for the Event
     */
    public void setStartTime(LocalDateTime time){this.startTime = time;}

    /** Set a speaker to present at this Event
     * @param speaker the speaker who is assigned to present at this Event
     */
    public void scheduleSpeaker(String speaker){this.speaker = speaker;}

    /** Checks if there is a speaker presenting at the Event
     * @return true if the event has a speaker scheduled, false if not
     */
    public boolean hasSpeaker(){return this.speaker != null;}

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

    @Override
    public String getID() { return eventId;}

    @Override
    public void setID(String ID) { eventId = ID;}

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

    /**
     * Set the end time for the Event
     * @param endTime is the time the Event ends
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /** Gets the time the event will end
     * @return The end time for the Event
     */
    public String getEndTime(){return this.endTime;}
}
