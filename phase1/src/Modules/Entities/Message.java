package Modules.Entities;

import java.time.LocalDateTime;

/**
 * A class representing a message that contains a sender, recipient, and date
 */
public class Message implements Comparable<Message> {
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
     * Returns the unique ID of this message
     * @return the unique ID of the message
     */
    public String getID() {
        return messageID;
    }

    /**
     * Returns the content of this message
     * @return content of the message
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns the ID of the sender of this message
     * @return unique ID of the sender
     */
    public String getSenderID() {
        return senderID;
    }

    /**
     * Returns the ID of the receiver of this message
     * @return unique ID of the receiver
     */
    public String getReceiverID() {
        return receiverID;
    }

    /**
     * Returns the date and time when this message was sent
     * @return the date and time when the message was sent
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Compares another message with this message
     * @param message The message being compared with
     * @return an integer greater than 0 if message was sent later than this message
     *         0 if the messages where sent at the same time
     *         an integer less than 0 if message was sent before this message
     */
    @Override
    public int compareTo(Message message) {
        return this.dateTime.compareTo(message.getDateTime());
    }
}
