package modules.views;

import java.util.ArrayList;

public interface ISpeakerHomePageView {

    /**
     * Displays list of messages between two users
     * @param messages ArrayList of json strings representing message info for all messages between two users
     */
    void displayMessages(ArrayList<String> messages);

    /**
     * Displays list of events that the speaker is assigned to speak at
     * @param events ArrayList of json strings representing event info for all events that user is speaking at
     */
    void displaySpeakingEvents(ArrayList<String> events);

    /**
     * Displays message to user
     * @param message the message to display
     */
    void displayMessage(String message);
}
