package modules.views;

import java.util.ArrayList;

/**
 * Interface for the home page attendee functions
 */
public interface IAttendeeHomePageView {

    /**
     * Displays message to user
     * @param message the message to display
     */
    public void displayMessage(String message);

    /**
     * Displays list of all events to user
     * @param events ArrayList of json strings representing event info for all events in this conference
     */
    public void displayAllEvents(ArrayList<String> events);

    /**
     * Displays list of events that user is attending to user
     * @param events ArrayList of json strings representing event info for all events that user is attending
     */
    public void displayAttendingEvents(ArrayList<String> events);

    /**
     * Displays list of messages between two users
     * @param messages ArrayList of json strings representing message info for all messages between two users
     */
    public void displayMessages(ArrayList<String> messages);
}
