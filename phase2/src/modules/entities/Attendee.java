package modules.entities;

import modules.exceptions.EventNotFoundException;

import java.util.ArrayList;

/**
 * This class represents a Attendee user.
 * Attendees can signup to attend events and message other users.
 */
public class Attendee extends User {
    // ids of events Attendee is attending
    /** a list of events that this attendee is attending */
    private ArrayList<String> eventsList;
    /** an exception thrown if looking for an event ID that is not in this attendee's list */
    private RuntimeException EventNotFoundException;

    /**
     * Constructor for Attendee
     * @param username the username for this attendee
     * @param password the password for this attendee
     * @param userID the unique userID for this attendee
     */
    public Attendee(String username, String password, String userID){
        super(username, password, userID);
        this.eventsList = new ArrayList<>();
    }

    /**
     * Adds the event with ID id to this attendee's list of events
     * @param id the unique ID of the event to be added
     */
    public void addEvent(String id){
        eventsList.add(id);
    }

    /**
     * Removes the event with ID id from this attendee's events list, if it is in the list
     * @param id the unique ID of the event to be removed
     * @throws EventNotFoundException if there is no event with matching ID that this user is attending
     */
    public void removeEvent(String id) throws EventNotFoundException {
        if (!this.alreadyAttendingEvent(id)){
            throw EventNotFoundException;
        }
        eventsList.remove(id);
    }

    /**
     * Returns a shallow copy of this attendee's events list
     * @return a shallow copy of this attendee's events list
     */
    public ArrayList<String> getEventsList() {
        ArrayList<String> copy = new ArrayList<>(eventsList.size());
        copy.addAll(eventsList);
        return copy;
    }

    /**
     * Checks whether this attendee is attending any events or not
     * @return true if this attendee's events list is empty, otherwise return false
     */
    public boolean hasNoEvents(){
        return eventsList.size() == 0;
    }

    /**
     * Checks if this attendee is attending a event with eventID id
     * @param id the ID of an event
     * @return true if this attendee is already attending the event with ID id, false otherwise
     */
    public boolean alreadyAttendingEvent(String id){
        return eventsList.contains(id);
    }
}
