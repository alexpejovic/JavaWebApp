package Modules.UseCases;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import Modules.Entities.Message;


/**
 * A use case that performs actions on Message entities and gives important information about all Messages
 */
public class MessageManager {
    // Hashmap to track messages each user has sent or received
    private HashMap<String, ArrayList<Message>> messages = new HashMap<>();

    /**
     * Initialize a MessageManager object given an ArrayList of Message objects
     * @param messages ArrayList of Message objects
     */
    public MessageManager(ArrayList<Message> messages) {
        for (Message message : messages) {
            // Get sender and receiver of each message
            String[] keys = {message.getSenderID(), message.getReceiverID()};

            // For each message, check if sender and receiver are already in the hashmap
            // If not, add a new key with an arraylist containing the current message
            // If yes, append the current message to the user's arraylist
            for (String key : keys) {
                if (!this.messages.containsKey(key)) {
                    ArrayList<Message> newMessagesArray = new ArrayList<>();
                    newMessagesArray.add(message);
                    this.messages.put(key, newMessagesArray);
                } else {
                    this.messages.get(key).add(message);
                }
            }
        }
    }

    /**
     * Return an ArrayList of messages sent or received by the user with the given ID
     * @param user The ID string of the user
     * @return A list of messages sent or received by the user
     */
    public ArrayList<Message> getUserMessages(String user) {
        ArrayList<Message> userMessages = (ArrayList<Message>) messages.get(user).clone();

        Collections.sort(userMessages);

        return userMessages;
    }

    /**
     * Create a new Message object and add it to messages hashmap for corresponding users
     * @param senderID The ID string of the sender
     * @param receiverID The ID string of the receiver
     * @param message The string of the message
     */
    public void sendMessage(String senderID, String receiverID, String message) {
        Message newMessage = new Message(message, senderID, receiverID);
        // Get userMessages for sender and add new message
        if(messages.containsKey(senderID)) {
            messages.get(senderID).add(newMessage);
        } else{
            ArrayList<Message> newMessagesArray = new ArrayList<>();
            newMessagesArray.add(newMessage);
            messages.put(senderID, newMessagesArray);
        }
        // Get userMessages for receiver and add new message
        if(messages.containsKey(receiverID)) {
            messages.get(receiverID).add(newMessage);
        }
        else{
            ArrayList<Message> newMessagesArray = new ArrayList<>();
            newMessagesArray.add(newMessage);
            messages.put(receiverID, newMessagesArray);
        }
    }

    /**
     * Return an ArrayList of messages between two users sorted by date
     * @param user1 The ID string of the first user
     * @param user2 The ID string of the second user
     * @return An ArrayList of messages between two users sorted by date
     */
    public ArrayList<Message> getConversation(String user1, String user2) {
        ArrayList<Message> conversation = new ArrayList<>();
        for (Message message : messages.get(user1)) {
            if (message.getReceiverID().equals(user2)) {
                conversation.add(message);
            }
        }
        for (Message message : messages.get(user1)) {
            if (message.getSenderID().equals(user2)) {
                conversation.add(message);
            }
        }

        Collections.sort(conversation);
        return conversation;
    }
}
