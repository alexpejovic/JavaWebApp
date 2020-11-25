package Modules.Controllers;

import Modules.Entities.*;
import Modules.Exceptions.EventNotFoundException;
import Modules.Exceptions.UserNotFoundException;
import Modules.Gateways.UserGateway;
import Modules.UseCases.AttendeeManager;
import Modules.UseCases.EventManager;
import Modules.UseCases.MessageManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AttendeeController {
    private AttendeeManager attendeeManager;
    private EventManager eventManager;
    private MessageManager messageManager;
    private String attendeeID;
    public AttendeeController(AttendeeManager attendeeManager, EventManager eventManager,String attendeeID,
                              MessageManager messageManager){
        this.attendeeManager = attendeeManager;
        this.eventManager = eventManager;
        this.attendeeID = attendeeID;
        this.messageManager = messageManager;
    }


    /**
     * Enters Organizer users into the system
     * @param users list of all user ids in the system
     */
    public void inputAttendee(ArrayList<User> users){
        for(User user : users){
            String id = user.getID();
            if (id.startsWith("a")){
                attendeeManager.addAttendee((Attendee) user);
            }
        }
    }

    /**
     *
     * @return the list ids for of existing events
     */
    public ArrayList<String> displayEvents(){
        return eventManager.getAllEventIDs();
    }


    /** Getter for list of ids of the events which attendee is attending
     *
     * @return the list of ids of the events which this Attendee is attending
     */
    public ArrayList<String> getAttendingEvents() {

         return attendeeManager.getAttendee(attendeeID).getEventsList();
//         ArrayList<String> eventStrings = attendeeManager.getAttendee(attendeeID).getEventsList();
//         ArrayList<Event> events = new ArrayList<>();
//
//         for(String event: eventStrings){
//             events.add(eventManager.getEvent(event));
//         }
//
//         return events;
    }

    /**
     *
     * @param eventName the name of the event that attendee wishes to sign up for
     * @return true if the sign up was successful, false if attendee was not available at that time or if the event was
     * full
     */
    public boolean signUp(String eventName){
        String eventID = eventManager.getEventID(eventName);
        ArrayList<Event> events = eventManager.getEventList();
        boolean signUpSuccessful = false;
        for (Event event: events){
            if (event.getID().equals(eventID)){
                if (attendeeManager.timeAvailable(attendeeManager.getAttendee(attendeeID), event.getStartTime(), event.getEndTime(), eventManager) &&
                        eventManager.canAttend(event.getID())){
                    attendeeManager.addEventToAttendee(attendeeManager.getAttendee(attendeeID), event, eventManager);
                    eventManager.addAttendee(event.getID(), attendeeID);
                    signUpSuccessful = true;
                }
            }
        }
        return signUpSuccessful;
    }

    /** Cancels attendee's enrollment for one event
     *
     * @param eventName Name of event which is wished to be cancelled
     * @return True if cancellation successful, false otherwise
     */
    public boolean cancelEnrollment(String eventName){
        String eventID = eventManager.getEventID(eventName);
        //check if user is signed up for event
        Attendee attendee = attendeeManager.getAttendee(attendeeID);
        if (attendee.getEventsList().contains(eventID)){
            eventManager.removeAttendee(eventID, attendeeID);
            try {
                attendee.removeEvent(eventID);
            }catch (EventNotFoundException e){
                e.printStackTrace();
            }
            return true;

        }
        return false;
    }

    /**
     *
     * @param receiverID the ID of the user that this message is to be sent to
     * @param message the content of the message to be sent
     * @return true if the message was successfully sent, false if the user was not on attendee's friends list
     */
    public boolean sendMessage(String receiverID, String message) throws UserNotFoundException{
        if (attendeeManager.getAttendee(attendeeID).getFriendList().contains(receiverID)){
            messageManager.sendMessage(attendeeID, receiverID, message);
            return true;
        }
        return false;
    }

    /**
     *
     * @param userID the ID of the user to be added to attendee's friends list
     * @return true if the given user was added to attendee's friend list, false if the user was already friend
     */
    public boolean addUserToFriendList(String userID){
        if(!attendeeManager.getAttendee(attendeeID).getFriendList().contains(userID)){
            attendeeManager.getAttendee(attendeeID).addToFriendList(userID);
            return true;
        }
        return false;
    }

    /**
     * Returns the message received by user and the full conversation between the receiver and sender
     * @param senderId the id of the user who sends the message
     * @return array list of messages that correspond to the sorted conversation between sender and receiver
     */
    public ArrayList<Message> seeMessage(String senderId){
        return messageManager.getConversation(attendeeID, senderId);
    }

}


