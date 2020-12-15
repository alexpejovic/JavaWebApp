package modules.usecases;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import modules.entities.Message;
import modules.exceptions.MessageNotFoundException;

import javax.swing.*;


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
     * @return the unique ID of the Message sent
     */
    public String sendMessage(String senderID, String receiverID, String message) {
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
        return newMessage.getID();
    }

    /**
     * Return an messageIDs of ArrayList of messages between two users sorted by date
     * @param user1 The ID string of the first user
     * @param user2 The ID string of the second user
     * @return An ArrayList of messageIDs of messages between two users sorted by date
     */
    public ArrayList<HashMap<String, String>> getConversation(String user1, String user2) {
        ArrayList<Message> conversation = new ArrayList<>();
        for (Message message : messages.get(user1)) {
            if (message.getReceiverID().equals(user2) && !message.getIsArchived()) {
                conversation.add(message);
            }
        }
        for (Message message : messages.get(user1)) {
            if (message.getSenderID().equals(user2) && !message.getIsArchived()) {
                conversation.add(message);
            }
        }

        Collections.sort(conversation);
        ArrayList<HashMap<String, String>> convo = new ArrayList<>();
        for (Message message : conversation) {
            HashMap<String, String> msg = new HashMap<>();
            msg.put("messageID", message.getID());
            msg.put("senderID", message.getSenderID());
            msg.put("receiverID", message.getReceiverID());
            msg.put("content", message.getContent());
            msg.put("time", message.getDateTime().toString());
            if (message.getHasBeenRead())
                msg.put("hasBeenRead", "true");
            else
                msg.put("hasBeenRead", "false");

            if (message.getIsArchived())
                msg.put("isArchived", "true");
            else
                msg.put("isArchived", "false");

            convo.add(msg);
        }
        return convo;
//        ArrayList<String> conversationIDs = new ArrayList<>();
//        for (Message message: conversation){
//            conversationIDs.add(message.getID());
//        }
//        return conversationIDs;
    }

    public ArrayList<HashMap<String, String>> getMessages(String userID) {
        Collection<ArrayList<Message>> allUserMessages = messages.values();
        ArrayList<HashMap<String, String>> userMessages = new ArrayList<>();

        for (ArrayList<Message> list : allUserMessages) {
            for (Message message : list) {
                if (message.getReceiverID().equals(userID)) {
                    HashMap<String, String> messageMap = objToMap(message);
                    if (!userMessages.contains(messageMap)) {
                        userMessages.add(messageMap);
                    }
                }
            }
        }

        return userMessages;
    }

    private HashMap<String, String> objToMap(Message message) {
        HashMap<String, String> map = new HashMap<>();
        map.put("messageID", message.getID());
        map.put("senderID", message.getSenderID());
        map.put("receiverID", message.getReceiverID());
        map.put("content", message.getContent());
        map.put("time", message.getDateTime().toString());
        if (message.getHasBeenRead())
            map.put("hasBeenRead", "true");
        else
            map.put("hasBeenRead", "false");

        if (message.getIsArchived())
            map.put("isArchived", "true");
        else
            map.put("isArchived", "false");
        return map;
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
     * Returns Message in this MessageManager matching given messageID
     * @param messageID the unique ID of the message in question
     * @return the Message in this MessageManager matching given messageID
     * @throws MessageNotFoundException if there is no message with messageID
     */
    public Message getMessage(String messageID){
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

    /**
     * Mark the message specified by messageID as read
     * @param messageID the unique ID of the message that is to be marked as read
     */
    public void markMessageAsRead(String messageID){
        Message message = this.getMessage(messageID);
        message.markAsRead();
    }

    /**
     * Mark the message specified by messageID as unread
     * @param messageID the unique ID of the message to be marked as unread
     */
    public void markMessageAsUnread(String messageID){
        Message message = this.getMessage((messageID));
        message.markAsUnread();
    }

    /**
     * Returns a boolean representing if the specified message has been read
     * @param messageID the unique ID of the message to be marked as unread
     * @return true if the message has been read, false otherwise
     */
    public boolean getHasMessageBeenRead(String messageID){
        Message message = this.getMessage((messageID));
        return message.getHasBeenRead();
    }

    /**
     * Checks if there are any messages between two users
     * @param user1 the userId of one of the users
     * @param user2 the userId of one of the users
     * @return true if there exits a message between the two users, false otherwise
     */
    public boolean hasUsersMessagedBefore(String user1, String user2){
        if(!messages.containsKey(user1)){
            // to prevent NullPointerExceptions
            return false;
        }
        ArrayList<Message> user1Messages = messages.get(user1);
        for (Message message: user1Messages){
            if (message.getReceiverID().equals(user2) || message.getSenderID().equals(user2)){
                // if there exits a message between the two users
                return true;
            }
        }
        // no message exists between two users
        return false;
    }

    /**
     * Mark the message specified by messageID as archived
     * @param messageID the unique ID of the message to be marked as archived
     */
    public void markMessageAsArchived(String messageID){
        Message message = this.getMessage(messageID);
        message.markAsArchived();
    }

    /**
     * Mark the message specified by messageID as unarchived
     * @param messageID the unique ID of the message to be marked as unarchived
     */
    public void markMessageAsUnarchived(String messageID){
        Message message = this.getMessage(messageID);
        message.markAsUnarchived();
    }

    /**
     * Returns a boolean representing if the specified message has been archived
     * @param messageID the unique ID of the message whose archived status is to be returned
     * @return true if this message has been archived, false otherwise
     */
    public boolean getHasMessageBeenArchived(String messageID){
        Message message = this.getMessage(messageID);
        return message.getIsArchived();
    }

    /**
     * Returns an ArrayList of unique IDs of messages between two users that have been archived, sorted by date
     * @param user1 the unique ID of the first user involved in this conversation
     * @param user2 the unique ID of the second user involved in this conversation
     * @return an ArrayList of unique IDs of messages that have been archived
     */
    public ArrayList<HashMap<String, String>> getArchivedMessages(String user1, String user2) {
        ArrayList<Message> archivedMessages = new ArrayList<>();
        for (Message message : messages.get(user1)) {
            if (message.getReceiverID().equals(user2) && message.getIsArchived()) {
                archivedMessages.add(message);
            }
        }
        for (Message message : messages.get(user1)) {
            if (message.getSenderID().equals(user2) && message.getIsArchived()) {
                archivedMessages.add(message);
            }
        }

        Collections.sort(archivedMessages);
        ArrayList<HashMap<String, String>> archivedMsg = new ArrayList<>();
        for (Message message: archivedMessages){
            archivedMsg.add(objToMap(message));
        }
        return archivedMsg;
    }

    /**
     * Deletes the message with the specified unique ID from the list of existing messages
     * @param messageID the unique ID of the message to be deleted
     * @param userID the unique ID of the user who wishes to delete the message
     */
    public void deleteMessage(String messageID, String userID){
        String senderID = getSenderIDOfMessage(messageID);
        String receiverID = getReceiverIDOfMessage(messageID);

        for (Message message: messages.get(senderID)){
            if(message.getID().equals(messageID)){
                messages.remove(userID, message);
            }
        }
        for (Message message: messages.get(receiverID)){
            if(message.getID().equals(messageID)){
                messages.remove(userID, message);
            }
        }
    }
}
