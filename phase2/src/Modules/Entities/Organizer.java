package Modules.Entities;

import Modules.Exceptions.EventNotFoundException;

import java.util.ArrayList;

/**
 * Represents an Organizer who can use this program
 * Organizers can attend and manage events
 */
public class Organizer extends Attendee {
    /**
     * An ArrayList of Event IDs that this Organizer manages
     */
    private ArrayList<String> managedEvents;

    /**
     * @param username the username for this Organizer
     * @param pwd the password for this Organizer
     * @param userID the unique ID for this Organizer
     */
    public Organizer(String username, String pwd, String userID) {
        super(username, pwd, userID);
        this.managedEvents = new ArrayList<>();
    }

    //getters
    /**
     * Returns a shallow copy of the Events this Organizer is managing
     * @return a shallow copy of the Events this Organizer is managing
     */
    public ArrayList<String> getManagedEvents() {
        return new ArrayList<>(this.managedEvents);
    }

    //setters
    /**
     * Adds a new eventID to the list of events tha this Organizer is managing
     * @param eventID the ID of the new Event this Organizer will manage
     */
    public void addManagedEvent(String eventID) {
        this.managedEvents.add(eventID);
    }

    /**
     * Removes a eventID from the list of events that this Organizer is managing
     * @param eventID the ID of the Event this Organizer is no longer manage
     * @throws EventNotFoundException if there is no event with eventID that this Organizer is managing
     */
    public void removeManagedEvent(String eventID) {
        if (this.managedEvents.contains(eventID)){
            this.managedEvents.remove(eventID);
        }
        else{
            throw new EventNotFoundException();
        }
    }

    /**
     * Checks whether or not this organizer has any events that they are managing
     * @return true if and only if this Organizer is not managing any Events
     */
    public boolean managedEventsIsEmpty() {
        int size = this.managedEvents.size();
        return size == 0;
    }
}
