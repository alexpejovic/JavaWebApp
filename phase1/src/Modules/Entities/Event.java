package Modules.Entities;

import Modules.Exceptions.UserNotFoundException;

import java.util.ArrayList;
import java.time.LocalDateTime;

/**
 * A class representing an event in a conference
 */
public class Event{

    /** ArrayList that stores the userIDs of attendees who are signed up to attend this event**/
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
    private LocalDateTime endTime;

    /** The name of the Event **/
    private String name;
    /** The Speaker who will present at the Event **/
    private String speaker = null;

    /**
     * Sets the room capacity and room number for the room where the Event will take place with only StartTime
     * Assumes this event will last 1 hour
     * @param roomNumber the room number the Event will be held
     * @param time the time at which the Event will begin
     */
    public Event(String roomNumber, LocalDateTime time, String eventId){
        this.capacity = 2;
        this.roomNumber = roomNumber;
        this.startTime = time;
        this.endTime = time.plusHours(1);
        attendeeList = new ArrayList<>();
    }

    /**
     * Constructor for this Event
     * @param roomNumber the room number where this Event will be held
     * @param startTime the time at which this Event will begin
     * @param endTime the time at which this Event will end
     * @param eventId the unique id of this Event
     */
    public Event(String roomNumber, LocalDateTime startTime, LocalDateTime endTime, String eventId){
        this.capacity = 2;
        this.roomNumber = roomNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventId = eventId;
        attendeeList = new ArrayList<>();
    }

    /**
     * Gets the capacity of the event
     * @return The maximum number of attendees allowed to attend this Event
     */
    public int getCapacity(){
        return capacity;
    }

    /**
     * Gets the room number of the room this event is in
     * @return The room number of the room where this Event will be held
     */
    public String getRoomNumber(){
        return roomNumber;
    }

    /**
     * Gets time that event starts
     * @return The the start time for the Event
     */
    public LocalDateTime getStartTime(){ return startTime;}

    /**
     * Set the start time for the Event
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

    public String getID() { return eventId;}

    /**
     * Removes specific attendee from the Event's list of attendees
     * @param userID the unique id of the attendee being removed from the Event's attendee list
     * @throws UserNotFoundException if there is no attendee user with userID attending this event
     */
    public void removeAttendee(String userID){
        if (attendeeList.contains(userID)){
            attendeeList.remove(userID);
        }
        else{
            throw new UserNotFoundException();
        }
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
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /** Gets the time the event will end
     * @return The end time for the Event
     */
    public LocalDateTime getEndTime(){return this.endTime;}
}
