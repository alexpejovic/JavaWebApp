package Modules.Entities;

import java.time.LocalDateTime;

public class Message implements Identifiable, Comparable<Message> {
    // Message content
    private final String content;

    // Sender and receiver IDs
    private final String senderID;
    private final String receiverID;

    // Date and time of message (when it was first sent)
    private final LocalDateTime dateTime;

    // Message ID
    private String messageID;

    /**
     * Initialize message object from data.
     * @param content The content of the message
     * @param senderID The ID string of the sender
     * @param receiverID The ID string of the receiver
     * @param messageID The ID of the message object
     * @param dateTime The date and time that the message was sent
     */
    public Message(String content, String senderID, String receiverID, String messageID, LocalDateTime dateTime) {
        this.content = content;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.messageID = messageID;
        this.dateTime = dateTime;
    }

    /**
     * Initialize new message, add current date, and create new unique message ID.
     * @param content The content of the message
     * @param senderID The ID string of the sender
     * @param receiverID The ID string of the receiver
     */
    public Message(String content, String senderID, String receiverID) {
        this.content = content;
        this.senderID = senderID;
        this.receiverID = receiverID;
        dateTime = LocalDateTime.now();
        messageID = String.format("%s,%s,%s", senderID, receiverID, dateTime);
    }

    /**
     * @return the unique ID of the message
     */
    @Override
    public String getID() {
        return messageID;
    }

    /**
     * @param ID unique message identification token
     */
    @Override
    public void setID(String ID) {
        messageID = ID;
    }

    /**
     * @return content of the message
     */
    public String getContent() {
        return content;
    }

    /**
     * @return unique ID of the sender
     */
    public String getSenderID() {
        return senderID;
    }

    /**
     * @return unique ID of the receiver
     */
    public String getReceiverID() {
        return receiverID;
    }

    /**
     * @return the date and time when the message was sent
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public int compareTo(Message message) {
        return this.dateTime.compareTo(message.getDateTime());
    }
}
