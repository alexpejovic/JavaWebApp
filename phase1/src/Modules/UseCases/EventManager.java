package Modules.UseCases;

import Modules.Entities.Event;
import Modules.Exceptions.EventNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;

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
    public LocalDateTime timeOfEvent(String eventID) {
        return getEvent(eventID).getStartTime();
    }

    /**
     * Creates an event (if possible) and adds it to ArrayList events
     *
     * @param roomNumber the room number of the room where the event is set to take place
     * @param startTime the time that the event starts at
     * @return Returns true if the event was successfully created and added to events, false otherwise
     */
    public boolean createEvent(String roomNumber, LocalDateTime startTime) {
        return this.eventList.add(new Event(roomNumber, startTime));
    }

    /**
     *
     * @return a shallow copy of the existing events in this conference
     */
    public ArrayList<Event> getEventList() {
        ArrayList<Event> copy = new ArrayList<>(eventList.size());
        for(int i = 0; i < eventList.size(); i++){
            copy.set(i, eventList.get(i));
        }
        return copy;
    }

}
