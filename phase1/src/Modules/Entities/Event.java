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
    private int roomNumber;

    /** The start time for the Event **/
    private LocalDateTime startTime;

    /** Speaker assigned to this Event**/
    private String speakerID = null;

    private String eventId;

    /**
     * Sets the room capacity and room number for the room where the Event will take place
     * @param roomNumber the room number the Event will be held
     * @param time the time at which the Event will begin
     */
    public Event(int roomNumber, LocalDateTime time){
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
    public int getRoomNumber(){
        return roomNumber;
    }

    /**
     * @return The the start time for the Event
     */
    public LocalDateTime getStartTime(){ return startTime;}

    /** set The the start time for the Event*/
    public void setStartTime(LocalDateTime time){ this.startTime = time;}

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
     * Schedules speaker to speak at this event
     * @param userID the userID of the speaker
     */
    public void scheduleSpeaker(String userID){this.speakerID = userID;}

    /**
     * @return if speaker is assigned to the Event
     */
    public boolean hasSpeaker(){
        return speakerID != null;
    }

}
