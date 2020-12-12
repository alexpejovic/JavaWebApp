package modules.controllers;

import modules.exceptions.EventNotFoundException;
import modules.exceptions.UserNotFoundException;
import modules.presenters.AttendeeOptionsPresenter;
import modules.usecases.AttendeeManager;
import modules.usecases.EventManager;
import modules.usecases.MessageManager;

import java.util.ArrayList;
import java.util.HashMap;

public class AttendeeController {
    private String attendeeID;
    private AttendeeManager attendeeManager;
    private EventManager eventManager;
    private MessageManager messageManager;
    private AttendeeOptionsPresenter attendeeOptionsPresenter;
    private StringFormatter stringFormatter;
    private UpdateInfo updateInfo;

    public AttendeeController(AttendeeManager attendeeManager, EventManager eventManager,String attendeeID,
                              MessageManager messageManager, AttendeeOptionsPresenter attendeeOptionsPresenter,
                              StringFormatter stringFormatter, UpdateInfo updateInfo){
        this.attendeeManager = attendeeManager;
        this.eventManager = eventManager;
        this.attendeeID = attendeeID;
        this.messageManager = messageManager;
        this.attendeeOptionsPresenter = attendeeOptionsPresenter;
        this.stringFormatter = stringFormatter;
        this.updateInfo = updateInfo;
    }

    /**
     * Signs attendee up to one event only if the attendee is not attending another event at the same time
     * if the specified event is VIP only, only VIP attendees are allowed to signup
     * @param eventID the ID of the event that attendee wishes to sign up for
     */
    public void signUp(String eventID){
        boolean signUpSuccessful = false;
        try{
            if (attendeeManager.timeAvailable(attendeeID, eventManager.startTimeOfEvent(eventID),
                                                eventManager.endTimeOfEvent(eventID), eventManager) &&
                    eventManager.canAttend(eventID) &&
                    attendeeManager.canAttendEvent(attendeeID, eventID, eventManager)){
                attendeeManager.addEventToAttendee(attendeeID, eventID);
                eventManager.addAttendee(eventID, attendeeID);
                updateInfo.updateEvent(eventManager.getEvent(eventID)); // updating event info to database
                updateInfo.updateUser(attendeeManager.getAttendee(attendeeID)); // updating attendee info
                signUpSuccessful = true;
            }
        }
        catch (EventNotFoundException | ClassNotFoundException e){
            attendeeOptionsPresenter.eventNotFound();
        }
        finally {
            attendeeOptionsPresenter.signUpToEventMessage(signUpSuccessful);
        }
    }

    /**
     * Cancels attendee's enrollment for one event
     * @param eventID ID of event which is wished to be cancelled
     */
    public void cancelEnrollment(String eventID){
        //check if user is signed up for event
        if (attendeeManager.getEventsList(attendeeID).contains(eventID)){
            // user is signed up to event
            try {
                attendeeManager.removeEvent(eventID, attendeeID);
                eventManager.removeAttendee(eventID, attendeeID);
                updateInfo.updateEvent(eventManager.getEvent(eventID)); // updating event info to database
                updateInfo.updateUser(attendeeManager.getAttendee(attendeeID)); // updating attendee info
                attendeeOptionsPresenter.cancelAttendanceToEventMessage(true);
            }catch (UserNotFoundException | ClassNotFoundException e){
                // event did not have attendee in attending list
                // still send success since removed event from attendee's list of attending events
                attendeeOptionsPresenter.cancelAttendanceToEventMessage(true);
            }
        }
        else{
            // user is not signed up to event
            attendeeOptionsPresenter.cancelAttendanceToEventMessage(false);
        }
    }

    /**
     * Sends a message to user specified by receiverID if that user is in this user's friendList
     * @param receiverID the ID of the user that this message is to be sent to
     * @param message the content of the message to be sent
     */
    public void sendMessage(String receiverID, String message) {
        try{
            if (attendeeManager.getFriendList(attendeeID).contains(receiverID)){
                String messageID = messageManager.sendMessage(attendeeID, receiverID, message);
                updateInfo.updateMessage(messageManager.getMessage(messageID));   // updating message info in database
                attendeeOptionsPresenter.sendMessage(true);
            }
            else{ // receiver is not in this attendee's friendList
                attendeeOptionsPresenter.sendMessage(false);
            }
        }
        catch (UserNotFoundException e){
            attendeeOptionsPresenter.userNotFound();
        }
    }

    /**
     * Sends a message to user specified by receiverID if there exists messages between this
     * attendee and the specified user
     * @param receiverID the ID of the user that this message is to be sent to
     * @param message the content of the message to be sent
     */
    public void replyMessage(String receiverID, String message){
        try{
            if (messageManager.hasUsersMessagedBefore(attendeeID, receiverID)){
                String messageID = messageManager.sendMessage(attendeeID, receiverID, message);
                updateInfo.updateMessage(messageManager.getMessage(messageID));  // updating message info to database
                attendeeOptionsPresenter.replyMessage(true);
            }
            attendeeOptionsPresenter.replyMessage(false);
        }
        catch (UserNotFoundException e){
            attendeeOptionsPresenter.userNotFound();
        }
    }

    /**
     * Adds a specified user to this attendee's friendList if the specified user was not already in the list
     * @param userID the ID of the user to be added to attendee's friends list
     */
    public void addUserToFriendList(String userID){
        if(!attendeeManager.getFriendList(attendeeID).contains(userID)){
            attendeeManager.addToFriendList(attendeeID, userID);
            updateInfo.updateUser(attendeeManager.getAttendee(attendeeID)); // updating attendee info
            attendeeOptionsPresenter.addToFriendList(true);
        }
        else{
            // the specified userID is already in this attendee's friendList
            attendeeOptionsPresenter.addToFriendList(false);
        }
    }

    public void updateModel() {
        updateModelMessages();
        updateModelEvents();
    }

    private void updateModelMessages() {
        ArrayList<HashMap<String, String>> messages = messageManager.getMessages(attendeeID);
        attendeeOptionsPresenter.setMessages(messages);
    }

    private void updateModelEvents() {
        ArrayList<HashMap<String, String>> attendingEvents = eventManager.getAttendingEvents(attendeeID, true);
        ArrayList<HashMap<String, String>> notAttendingEvents = eventManager.getAttendingEvents(attendeeID, false);

        attendeeOptionsPresenter.setAttendingEvents(attendingEvents);
        attendeeOptionsPresenter.setNotAttendingEvents(notAttendingEvents);
    }

}


