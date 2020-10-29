package Modules.Entities;

import java.time.LocalDateTime;

public class Message {
    // Message content
    private final String content;

    // Sender and receiver
    private final String sender;
    private final String receiver;

    // Date and time of message (when it was first sent)
    private final LocalDateTime dateTime;

    public Message(String content, String sender, String receiver, LocalDateTime dateTime) {
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.dateTime = dateTime;
    }

    public Message(String content, String sender, String receiver) {
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        dateTime = LocalDateTime.now();
    }

    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
