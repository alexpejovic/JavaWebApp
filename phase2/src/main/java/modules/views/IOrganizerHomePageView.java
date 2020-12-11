package modules.views;

import java.util.ArrayList;

/**
 * Interface for the home page organizer functions
 * Attendee functions that organizers also access would be in IAttendeeHomePageView
 */
public interface IOrganizerHomePageView {

    /**
     * Displays message to user
     * @param message the message to display
     */
    void displayMessage(String message);

    /**
     * Displays list of messages between two users
     * @param messages ArrayList of json strings representing message info for all messages between two users
     */
    void displayMessages(ArrayList<String> messages);

}
