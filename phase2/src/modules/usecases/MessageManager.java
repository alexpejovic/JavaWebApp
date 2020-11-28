package modules.usecases;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import modules.entities.Message;
import modules.exceptions.MessageNotFoundException;


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
     * Return an ArrayList of IDs of messages sent or received by the user with the given ID
     * @param user The ID string of the user
     * @return A list of messageIDs of messages sent or received by the user
     */
    public ArrayList<String> getUserMessages(String user) {
        ArrayList<Message> userMessages = new ArrayList<>();
        ArrayList<String> userMessageIDs = new ArrayList<>();

        // to prevent NullPointerException
        if (messages.get(user)== null){
            return userMessageIDs;
        }

        for(Message message: messages.get(user)){
            userMessages.add(message);
        }

        Collections.sort(userMessages);

        for (Message message: userMessages){
            userMessageIDs.add(message.getID());
        }

        return userMessageIDs;
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
     * Return an messageIDs of ArrayList of messages between two users sorted by date
     * @param user1 The ID string of the first user
     * @param user2 The ID string of the second user
     * @return An ArrayList of messageIDs of messages between two users sorted by date
     */
    public ArrayList<String> getConversation(String user1, String user2) {
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
        ArrayList<String> conversationIDs = new ArrayList<>();
        for (Message message: conversation){
            conversationIDs.add(message.getID());
        }
        return conversationIDs;
    }

    /**
     * Return a list of all unique messages, sorted by date, that are stored in the <messages> hashmap
     * @return an arraylist of Message entities
     */
    public ArrayList<Message> getAllMessages() {
        Collection<ArrayList<Message>> allUserMessages = messages.values();
        ArrayList<Message> allMessages = new ArrayList<>();

        for (ArrayList<Message> list : allUserMessages) {
            for (Message message : list) {
                if (!allMessages.contains(message)) {
                    allMessages.add(message);
                }
            }
        }

        Collections.sort(allMessages);
        return allMessages;
    }

    /**
     * private helper to return Message in this MessageManager matching given messageID
     * Precondition: Message with messageID exists
     * other wise throws MessageNotFound exception
     */
    private Message getMessage(String messageID){
        for (ArrayList<Message> msgs:  messages.values()){
            for(Message message: msgs){
                if (message.getID().equals(messageID))
                    return message;
            }
        }
        throw new MessageNotFoundException();
    }

    /**
     * Returns the senderID of the Message matching given message ID
     * @param messageID the unique ID of the Message in question
     * @return  the senderID of the Message matching given message ID
     * @throws MessageNotFoundException if there is no message with messsageID
     */
    public String getSenderIDOfMessage(String messageID){
        Message message = this.getMessage(messageID);
        return message.getSenderID();
    }

    /**
     * Returns the receiverID of the Message matching given message ID
     * @param messageID the unique ID of the Message in question
     * @return  the receiverID of the Message matching given message ID
     * @throws MessageNotFoundException if there is no message with messsageID
     */
    public String getReceiverIDOfMessage(String messageID){
        Message message = this.getMessage(messageID);
        return message.getReceiverID();
    }

    /**
     * Returns the content of the Message matching given message ID
     * @param messageID the unique ID of the Message in question
     * @return  the content of the Message matching given message ID
     * @throws MessageNotFoundException if there is no message with messsageID
     */
    public String getContentOfMessage(String messageID){
        Message message = this.getMessage(messageID);
        return message.getContent();
    }

    /**
     * Returns the time of the Message matching given message ID
     * @param messageID the unique id of the Message in question
     * @return  the date time of the Message matching given message ID
     * @throws MessageNotFoundException if there is no message with messsageID
     */
    public LocalDateTime getTimeOfMessage(String messageID){
        Message message = this.getMessage(messageID);
        return message.getDateTime();
    }
}
