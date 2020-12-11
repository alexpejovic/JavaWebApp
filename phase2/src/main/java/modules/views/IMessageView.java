package modules.views;

import java.util.ArrayList;

/**
 * Interface for the message functions that all users use
 */
public interface IMessageView {

    /**
     * Displays list of messages
     * @param messages ArrayList of json strings representing message info
     */
    public void displayMessages(ArrayList<String> messages);

    /**
     * Displays message to user
     * @param message the message to display
     */
    public void displayMessage(String message);
}
