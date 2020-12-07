package modules.controllers;

import modules.exceptions.MessageNotFoundException;
import modules.presenters.AttendeeOptionsPresenter;
import modules.usecases.MessageManager;


public class MessageController {

    private String userID;
    private MessageManager messageManager;
    private AttendeeOptionsPresenter attendeeOptionsPresenter;

    /**
     * Marks the message specified by messageID as unread, if it exists
     * @param messageID the unique ID of the message to be marked as unread
     */
    public void markMessageAsUnread(String messageID){
        try {
            messageManager.markMessageAsUnread(messageID);
        }
        catch (MessageNotFoundException e){
            attendeeOptionsPresenter.messageDoesNotExist();
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
            attendeeOptionsPresenter.messageDoesNotExist();
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
            attendeeOptionsPresenter.messageDoesNotExist();
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
            attendeeOptionsPresenter.messageDoesNotExist();
        }

    }
}
