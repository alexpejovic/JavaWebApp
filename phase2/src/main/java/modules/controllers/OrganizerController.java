package modules.controllers;
import modules.entities.*;
import modules.exceptions.EventNotFoundException;
import modules.exceptions.UserNotFoundException;
import modules.usecases.*;

import java.time.LocalDateTime;
import java.util.ArrayList;


public class OrganizerController {
    private OrganizerManager organizerManager;
    private EventManager eventManager;
    private RoomManager roomManager;
    private SpeakerManager speakerManager;
    private String organizerId;
    private MessageManager messageManager;
    private AttendeeManager attendeeManager;
    private EventCreator eventCreator;
    private AccountCreator accountCreator;

    public OrganizerController(OrganizerManager organizerManager, EventManager eventManager,
                               RoomManager roomManager, SpeakerManager speakerManager,
                               MessageManager messageManager, AttendeeManager attendeeManager, EventCreator eventCreator,
                               AccountCreator accountCreator, String organizerId){

        this.organizerManager = organizerManager;
        this.eventManager = eventManager;
        this.roomManager = roomManager;
        this.speakerManager = speakerManager;
        this.messageManager = messageManager;
        this.attendeeManager = attendeeManager;
        this.eventCreator = eventCreator;
        this.accountCreator = accountCreator;
        this.organizerId = organizerId;
    }


    /**
     * Creates new Room and enters it into the system
     * @param roomNumber the room's number
     * @param capacity the maximum number of people allowed in this room, including Speakers
     */
    public void addNewRoom(String roomNumber, int capacity){
        roomManager.createRoom(roomNumber, capacity);
    }

    /**
     * Schedules a new event to take place at a specific time and in a specific room
     * @param roomNumber the room number of the room where the event will take place
     * @param startTime the time when the event will start
     * @param endTime the time when the event ends
     * @param capacity the maximum number of attendees allowed in the event
     * @return true if the event was scheduled, false if the event was not scheduled
     */
    public boolean scheduleEvent(String roomNumber, LocalDateTime startTime, LocalDateTime endTime, String eventName, int capacity,
                                 boolean isVIP){
        //check room is available at this time, doesn't have other event
        boolean isRoomAvailable = roomManager.isRoomAvailable(roomNumber, startTime, endTime, eventManager);
        //check event not already in another room
        boolean canBook = eventManager.canBook(roomNumber, startTime, endTime);
        //check that room capacity can handle the capacity of the event
        boolean canRoomHold = this.isRoomCapacityEnough(roomNumber,capacity);
        if (isRoomAvailable && canBook && canRoomHold) {
            //create the Event
            boolean created = eventCreator.createEvent(startTime, endTime, roomNumber, eventName,capacity, isVIP);
            if (created){
                try {
                    String eventId = eventManager.eventAtTime(roomNumber, startTime, endTime).getID();
                    eventManager.renameEvent(eventId, eventName);
                    roomManager.addEventToRoom(roomNumber, eventId);
                    organizerManager.addToOrganizedEvents(organizerId, eventId);
                    return true;
                }
                catch(EventNotFoundException e){
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Checks whether the Room specified by roomNumber has a capacity
     * greater than or equal to the given capacity
     * @param roomNumber the roomNumber of the room being checked
     * @param capacity the capacity being checked
     * @return true if and only if the specified Room has at least a capacity of capacity
     */
    public boolean isRoomCapacityEnough(String roomNumber, int capacity){
        return roomManager.getCapacityOfRoom(roomNumber) >= capacity;
    }


    /**
     * Creates a new speaker account and passes/adds it into the program
     * @param userName the username of the Speaker account
     * @param password the password of the Speaker account
     */
    public void createSpeakerAccount(String userName, String password){
        ArrayList<String> listOfEvents = eventManager.getAllEventIDs();
        accountCreator.createSpeakerAccount(userName, password, listOfEvents);
    }

    /**
     * Checks if the event with the event name is in the room with room number
     * @param eventName the name of the event in question
     * @param roomNumber the room number of the room
     * @return true if the room contains the event with eventName, false if it is not
     */
    public boolean isCorrectEvent(String eventName, String roomNumber){
        try {
            String eventId = eventManager.getEventID(eventName);
            return roomManager.isEventInRoom(roomNumber, eventId);
        }
        catch (EventNotFoundException e) {
            return false;
        }
    }

    /**
     * Checks if the speaker with the given username is in the system
     * @param username the username of the speaker
     * @return if the speaker exists in the system
     */
    public boolean isSpeakerInProgram(String username){
        return speakerManager.isUser(username);
    }

    /**
     * Schedules speaker to speak at an existing event if speaker, event and room are available
     * and if the speaker is not already speaking at the specified event
     * @param username the id of the Speaker being scheduled for the Event
     * @param roomNumber the number of the room where the event will be held and where the speaker will present
     * @param eventName the name of the event taking place in the room
     * @return true if the speaker is able to present at the event
     * precondition: the event with eventName is an existing event
     */
    public boolean scheduleSpeaker(String username, String roomNumber, String eventName){
        //check if speaker is available
        if (!roomExists(roomNumber)) {return false;}
        String eventId = eventManager.getEventID(eventName);
        if (!roomManager.getEventsInRoom(roomNumber).contains(eventId)){return false;}
        LocalDateTime startTime = eventManager.startTimeOfEvent(eventId);
        LocalDateTime endTime = eventManager.endTimeOfEvent(eventId);
        String speakerId = speakerManager.getUserID(username);
        boolean isSpeakerAvailable = speakerManager.isSpeakerAvailable(speakerId, startTime, endTime, eventManager);
        //check if room is available
        boolean isEventAvailable = !eventManager.hasSpeaker(eventId);
        //check if speaker is already speaking at event
        boolean isSpeakerSpeakingAtEvent = eventManager.isSpeakerSpeakingAtEvent(eventId,speakerId);
        //schedule the speaker
        if (isSpeakerAvailable && isEventAvailable && !isSpeakerSpeakingAtEvent){
            // add speaker to event's properties
            eventManager.setSpeaker(speakerId,eventId);
            // add event to speaker's properties
            speakerManager.addEventToSpeaker(eventId,speakerId);
            return true;
        }
        //return if speaker was not scheduled
        return false;
    }

    /**
     * Removes the specified speaker from the specified event
     * @param username the username of the Speaker being scheduled for the Event
     * @param eventName the name of the event in question
     * @return true if the speaker is removed from the event, false if the speaker was not speaking at the event
     * precondition: the event with eventName is an existing event
     */
    public boolean removeSpeakerFromEvent(String username, String eventName){
        String eventId = eventManager.getEventID(eventName);
        String speakerId = speakerManager.getUserID(username);
        //checking that the speaker is speaking at the given event
        if (eventManager.isSpeakerSpeakingAtEvent(eventId,speakerId)){
            //remove speaker from event
            eventManager.removeSpeakerFromEvent(eventId,speakerId);
            //remove event from speaker
            speakerManager.removeEventFromSpeaker(eventId, speakerId);
            return true;
        }
        //speaker was not speaking at event
        return false;
    }


    /**
     * Checks the system to see if the room exists
     * @param roomNumber the number of the room being searched for
     * @return true if the room is in the system, false if the room doesn't exists
     */
    public boolean roomExists(String roomNumber){
        boolean roomExists = false;
        for (Room room: roomManager.getRooms()){
            if (room.getRoomNumber().equals(roomNumber)) {
                roomExists = true;
                break;
            }
        }
        return roomExists;
    }

    /**
     * Enters Organizer users into the system
     * @param users list of all user ids in the system, each organizer in the list is a unique user (i.e has a unique id)
     */
    public void inputOrganizer(ArrayList<User> users){
        for(User user : users){
            String id = user.getID();
            if (id.startsWith("o")){
                organizerManager.addOrganizer((Organizer) user);
            }
        }
    }

    /**
     * Sends a singular message to all attendees in the program
     * @param message the content of the message being sent
     */
    public void messageAllAttendees(String message){
        for (String attendeeID: attendeeManager.getUserIDOfAllAttendees()){
            messageManager.sendMessage(organizerId, attendeeID, message);
        }
    }

    /**
     * Sends a singular message to all speakers in the program
     * @param message the content of the message being sent
     */
    public void messageAllSpeakers(String message){
        for (String speakerID: speakerManager.getUserIDOfAllSpeakers()){
            sendMessage(speakerID, message);
        }
    }

    /**
     * Send message to another user
     * @param receiverID the userId of the user the organizer is sending the message to
     * @param message the content of the message being sent
     */
    public void sendMessage(String receiverID, String message){
        messageManager.sendMessage(organizerId, receiverID, message);
    }

    /**
     * Shows all messages exchanged with other user (i.e the entire conversation)
     * @param senderID the userId of the user whom the organizer shared the conversation
     * @return an ArrayList where each element is a messageID of message exchanged in the conversation
     */
    public ArrayList<String> viewMessage(String senderID){
        return messageManager.getConversation(organizerId, senderID);
    }

    /**
     * Schedules organizer to attend event
     * @param eventName the name of the event the Organizer will attend, if that event exists or has
     *                  available seating
     * @return true if the organizer has been scheduled, false if not
     */
    public boolean attendEvent(String eventName){
        try {
            String eventId = eventManager.getEventID(eventName);
            if (eventManager.canAttend(eventId)){
                eventManager.addAttendee(eventId, organizerId);
                return true;
            }
            return false;
        }
        catch(EventNotFoundException e){
            return false;
        }
    }

    /**
     * Organizer cancels their enrollment to attend an event
     * @param eventName the name of the event the organizer will no longer attend,
     *                  if event exists and the organizer is already attending the event
     * @return a string that indicates the status of the organizer, if they were able to cancel their enrollment
     * this will be displayed on the screen for user to see
     */
    public String cancelEnrollment(String eventName){
        try{
            String eventId = eventManager.getEventID(eventName);
            eventManager.removeAttendee(eventId, organizerId);
            return "Your Cancellation was successful";
        }
        catch(UserNotFoundException e){
            return "You were never signed up for this event. Please select another event.";
        }
        catch(EventNotFoundException e){
            return "This event doesn't exist. Please select a new existing event.";
        }
    }

}
