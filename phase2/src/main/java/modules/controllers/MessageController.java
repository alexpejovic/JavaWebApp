package modules.controllers;

import modules.exceptions.MessageNotFoundException;
import modules.presenters.AttendeeOptionsPresenter;
import modules.presenters.MessagePresenter;
import modules.usecases.MessageManager;

import java.util.ArrayList;


public class MessageController {

    private String userID;
    private MessageManager messageManager;
    private MessagePresenter messagePresenter;
    private StringFormatter stringFormatter;

    public MessageController(String userID, MessageManager messageManager, MessagePresenter messagePresenter,
                             StringFormatter stringFormatter){
        this.userID = userID;
        this.messageManager = messageManager;
        this.messagePresenter = messagePresenter;
        this.stringFormatter = stringFormatter;
    }
    /**
     * Marks the message specified by messageID as unread, if it exists
     * @param messageID the unique ID of the message to be marked as unread
     */
    public void markMessageAsUnread(String messageID){
        try {
            messageManager.markMessageAsUnread(messageID);
        }
        catch (MessageNotFoundException e){
            messagePresenter.messageDoesNotExist();
        }

    }

    /**
     * Marks the message specified by messageID as archived, if it exists
     * @param messageID the unique ID of the message to be marked as archived
     */
    public void markMessageAsArchived(String messageID){
        try {
            messageManager.markMessageAsArchived(messageID);
        }
        catch (MessageNotFoundException e){
            messagePresenter.messageDoesNotExist();
        }

    }

    /**
     * Marks the message specified by messageID as unarchived, if it exists
     * @param messageID the unique ID of the message to be marked as unarchived
     */
    public void markMessageAsUnarchived(String messageID){
        try {
            messageManager.markMessageAsUnarchived(messageID);
        }
        catch (MessageNotFoundException e){
            messagePresenter.messageDoesNotExist();
        }

    }

    /**
     * Marks the message specified by messageID as read, if it exists
     * @param messageID the unique ID of the message to be marked as unread
     */
    public void markMessageAsDeleted(String messageID){
        try {
            messageManager.deleteMessage(messageID, userID);
        }
        catch (MessageNotFoundException e){
            messagePresenter.messageDoesNotExist();
        }

    }

    /**
     * Returns the messageIDs of archived messages between the receiver and sender
     * @param user2ID the id of the other user involved in the archived messages to be presented
     */
    public void seeArchivedMessages(String user2ID){
        ArrayList<String> archivedMessages = messageManager.getArchivedMessages(userID, user2ID);
        ArrayList<String> formattedMessages = stringFormatter.messageToJSONString(archivedMessages);
        messagePresenter.seeMessages(formattedMessages);
    }

    /**
     * Returns the messageIDs of messages received by user and the full conversation between the receiver and sender
     * @param senderId the id of the user who sends the message
     */
    public void seeMessages(String senderId){
        ArrayList<String> conversation = messageManager.getConversation(userID, senderId);
        for(String ID: conversation){
            if(messageManager.getReceiverIDOfMessage(ID).equals(userID)){
                messageManager.markMessageAsRead(ID);
            }
        }
        // message is presented to user if there is no messages found between the two
        if(conversation.isEmpty()){
            messagePresenter.noMessagesFound();
        }
        else{
            ArrayList<String> formattedMessages = stringFormatter.messageToJSONString(conversation);
            messagePresenter.seeMessages(formattedMessages);
        }

    }
}
