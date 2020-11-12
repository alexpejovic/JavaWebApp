package Modules.Controllers;

import Modules.Entities.Attendee;
import Modules.Entities.Event;
import Modules.Entities.User;
import Modules.Gateways.UserGateway;
import Modules.UseCases.AttendeeManager;
import Modules.UseCases.EventManager;
import Modules.UseCases.MessageManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AttendeeController {
    private AttendeeManager attendeeManager;
    private EventManager eventManager;
    private MessageManager messageManager;
    private Attendee attendee;
    public AttendeeController(AttendeeManager attendeeManager, EventManager eventManager,Attendee attendee,
                              MessageManager messageManager){
        this.attendeeManager = attendeeManager;
        this.eventManager = eventManager;
        this.attendee = attendee;
        this.messageManager = messageManager;
    }

    /**
     *
     * @param users the existing users read-in from the stored file
     */
    public void addAttendees(ArrayList<User> users){
        for(User user : users){
            String id = user.getID();
                    if (id.startsWith("a")){
                        attendeeManager.addAttendee((Attendee) user);
                    }
        }
    }

    /**
     *
     * @return the list of existing events
     */
    public ArrayList<Event> displayEvents(){
        return eventManager.getEventList();
    }

    /**
     *
     * @param eventID the ID of the event that attendee wishes to sign up for
     * @return true if the sign up was successful, false if attendee was not available at that time or if the event was
     * full
     */
    public boolean signUp(String eventID){
        ArrayList<Event> events = eventManager.getEventList();
        boolean signUpSuccessful = false;
        for (Event event: events){
            if (event.getID().equals(eventID)){
                if (attendeeManager.timeAvailable(attendee, event.getStartTime(), event.getEndTime(), eventManager) &&
                        eventManager.canAttend(event.getID())){
                    attendeeManager.addEventToAttendee(attendee, event, eventManager);
                    eventManager.addAttendee(event.getID(), attendee.getID());
                    signUpSuccessful = true;
                }
            }
        }
        return signUpSuccessful;
    }

    /**
     *
     * @param receiverID the ID of the user that this message is to be sent to
     * @param message the content of the message to be sent
     * @return true if the message was successfully sent, false if the user was not on attendee's friends list
     */
    public boolean sendMessage(String receiverID, String message){
        if (attendee.getFriendList().contains(receiverID)){
            messageManager.sendMessage(attendee.getID(), receiverID, message);
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
        if(!attendee.getFriendList().contains(userID)){
            attendee.addToFriendList(userID);
            return true;
        }
        return false;
    }


}
