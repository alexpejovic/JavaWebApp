package modules.entities;

import modules.exceptions.UserNotFoundException;

import java.io.Serializable;
import java.util.ArrayList;
import java.time.LocalDateTime;

/**
 * A class representing an event in a conference
 */
public class Event implements Comparable<Event>, Serializable {

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
    private ArrayList<String> speakerList = new ArrayList<>();
   /** Indicates whether this event only permits VIP attendees, defaults to false**/
    private boolean isVIP;


    /**
     * Sets the room capacity and room number for the room where the Event will take place with only StartTime
     * Assumes this event will last 1 hour
     * Assumes this event will have a capacity of 2
     * @param roomNumber the room number the Event will be held
     * @param time the time at which the Event will begin
     */
    public Event(String roomNumber, LocalDateTime time, String eventId){
        this.capacity = 2;
        this.roomNumber = roomNumber;
        this.startTime = time;
        this.endTime = time.plusHours(1);
        this.eventId = eventId;
        attendeeList = new ArrayList<>();
        this.isVIP = false;
    }

    /**
     * Constructor for this Event
     * @param roomNumber the room number where this Event will be held
     * @param startTime the time at which this Event will begin
     * @param endTime the time at which this Event will end
     * @param eventId the unique id of this Event
     * @param capacity the maximum number of attendees allowed in this event
     */
    public Event(String roomNumber, LocalDateTime startTime, LocalDateTime endTime, String eventId, int capacity){
        this.capacity = capacity;
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
    public void scheduleSpeaker(String speaker){this.speakerList.add(speaker);}

    /** Checks if there is a speaker presenting at the Event
     * @return true if the event has a speaker scheduled, false if not
     */
    public boolean hasSpeaker(){
        return !this.speakerList.isEmpty();
    }

    /**
     * Checks if there is a speaker with given speakerID speaking at this Event
     * @param speakerID the id being checked
     * @return true if the event has the speaker with speakerID scheduled, false if not
     */
    public boolean hasSpeaker(String speakerID){return this.speakerList.contains(speakerID);}

    /**
     * Returns a shallow copy of the speaker ids of speakers speaking at this event
     * @return a shallow copy of the speaker ids of speakers speaking at this event
     */
    public ArrayList<String> getSpeakers() {
        ArrayList<String> speakers = new ArrayList<>();
        speakers.addAll(speakerList);
        return speakers;
    }

    /**
     * Returns a shallow copy of the attendee ids of attendees of this event
     * @return a shallow copy of the attendee ids of attendees of this event
     */
    public ArrayList<String> getAttendees() {
        ArrayList<String> attendees  = new ArrayList<>();
        attendees.addAll(attendeeList);
        return attendees;
    }

    /**
     * Removes specific speaker from the Event's list of attendees
     * @param userID the unique id of the speaker being removed from the Event's speaker list
     * @throws UserNotFoundException if there is no speaker user with userID speaking at this event
     */
    public void removeSpeaker(String userID){
        if (speakerList.contains(userID)){
            speakerList.remove(userID);
        }
        else{
            throw new UserNotFoundException();
        }
    }

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
     * Returns the unique id of this event
     * @return the id of this event
     */
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

    /**
     * Marks this event as a VIP only event
     */
    public void setAsVIP(){
        this.isVIP = true;
    }

    /**
     * Returns whether this event permits VIP attendees only, or permits all attendees
     * @return true if and only if this is a VIP-only event, false otherwise
     */
    public boolean getVIPStatus(){
        return this.isVIP;
    }

    /**
     * Compares another event with this event
     * @param event The event being compared with
     * @return an integer greater than 0 if event started later than this event
     *         0 if the events started at the same time
     *         an integer less than 0 if event started before this event
     */
    @Override
    public int compareTo(Event event) {
        return this.startTime.compareTo(event.getStartTime());
    }
}
