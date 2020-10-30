package Modules.Entities;

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
     */
    public Organizer(String username, String pwd) {
        super(username, pwd);
        this.managedEvents = new ArrayList<>();
    }

    //getters
    /**
     * @return a soft copy of the Events this Organizer is managing
     */
    public ArrayList<String> getManagedEvents() {
        ArrayList<String> copy = new ArrayList<>();

        for (int i = 0; i < this.managedEvents.size(); i++) {
            copy.set(i, this.managedEvents.get(i));
        }

        return copy;
    }

    //setters
    /**
     * @param eventID the ID of the new Event this Organizer will manage
     */
    public void addManagedEvent(String eventID) {
        this.managedEvents.add(eventID);
    }

    /**
     * @param eventID the ID of the Event this Organizer is no longer manage
     */
    public void removeManagedEvent(String eventID) {
        this.managedEvents.remove(eventID);
    }

    /**
     * @return true if and only if this Organizer is not managing any Events
     */
    public boolean managedEventsIsEmpty() {
        int size = this.managedEvents.size();
        return size == 0;
    }
}
