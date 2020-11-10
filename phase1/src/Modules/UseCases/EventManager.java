package Modules.UseCases;

import Modules.Entities.Event;
import Modules.Exceptions.EventNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * This class represents the use case class EventManager
 * This use case manages the entity Event
 */
public class EventManager {

    /**
     * An ArrayList of all events
     */
    private ArrayList<Event> eventList;

    public EventManager() {
        this.eventList = new ArrayList<>();
    }

    /**
     * Checks if the schedule is free from time to time + 1 hour. This considers all events, regardless of room number
     *
     * @param time Time of day
     * @return Returns true if there are no events already occurring at time to time + 1 hour, false otherwise
     */
    public boolean isScheduleFree(LocalDateTime time) {

        for (Event event : this.eventList) {
            // Iterates through each event and checks if schedule is free from time to time + 1 hour
            // (assumes 1 hour length)
            // TODO: No longer assume 1 hour length
            if (!(time.until(event.getStartTime(), java.time.temporal.ChronoUnit.HOURS) > 1 ||
                    time.until(event.getStartTime(), java.time.temporal.ChronoUnit.HOURS) < -1)) {
                return false;
            }
        }

        return true;

    }

    /**
     * Private helper function that returns the index of the event identified by an eventID. Raises an
     * EventNotFoundException if there is no event without the specified event ID
     * @param eventID the unique ID of the event
     * @return The index of the event if eventID is valid
     */
    private int indexOfEvent(String eventID) {
        for (int i = 0; i < this.eventList.size(); i++) {
            if (this.eventList.get(i).getID().equals(eventID)) {
                return i;
            }
        }

        throw new EventNotFoundException();
    }

    /**
     * Private helper function that returns the event identified by an event ID. Raises an EventNotFoundException if
     * there is no event with the specified event ID
     * @param eventID the unique ID of the event
     * @return The event of the ID
     */
    private Event getEvent(String eventID) {
        for (Event event: this.eventList) {
            if (event.getID().equals(eventID)) {
                return event;
            }
        }
        throw new EventNotFoundException();
    }

    /**
     * Returns the start time of an event
     * @param eventID the unique ID of the event
     * @return LocalDateTime representing the start time of the event
     */
    public LocalDateTime startTimeOfEvent(String eventID) {
        return getEvent(eventID).getStartTime();
    }

    /**
     * Returns the end time of an event
     * @param eventID the unique ID of the event
     * @return LocalDateTime representing the end time of the event
     */
    public LocalDateTime endTimeOfEvent(String eventID) {
        return getEvent(eventID).getEndTime();
    }

    /**
     * Creates an event (if possible) and adds it to ArrayList events
     *
     * @param roomNumber the room number of the room where the event is set to take place
     * @param startTime the time that the event starts at
     * @param endTime the time that the event end at
     * @param eventID the unique ID of the event
     * @return Returns true if the event was successfully created and added to events, false otherwise
     */
    public boolean createEvent(String roomNumber, LocalDateTime startTime, LocalDateTime endTime, String eventID) {
        return this.eventList.add(new Event(roomNumber, startTime, endTime, eventID));
    }

    /**
     *
     * @return a shallow copy of the existing events in this conference
     */
    public ArrayList<Event> getEventList() {
        ArrayList<Event> copy = new ArrayList<>();
        for(Event event: this.eventList){
            copy.add(event);
        }
        return copy;
    }

    /**
     * Checks if an event can allow another attendee to attend
     * @param eventID the unique ID of the event
     * @return returns true if there is room for another attendee in the room that the event takes place in, false
     * otherwise
     */
    public boolean canAttend(String eventID) {
        return getEvent(eventID).getAvailableSeats() > 0;
    }

    /**
     * Adds an attendee userID to the list of attendees attending an event
     * @param eventID the unique ID of the event
     * @param userID the unique userID of the user
     */
    public void addAttendee(String eventID, String userID) {
        getEvent(eventID).addAttendee(userID);
    }

    /**
     * Removes an attendee's userID from the list of attendees attending an event
     * @param eventID the unique ID of the event
     * @param userID the unique userID of the user
     */
    public void removeAttendee(String eventID, String userID) {
        getEvent(eventID).removeAttendee(userID);
    }

    //TODO: Set, remove speaker methods

}
