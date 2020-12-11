package modules.controllers;
import modules.entities.*;
import modules.exceptions.EventNotFoundException;
import modules.exceptions.UserNotFoundException;
import modules.presenters.OrganizerOptionsPresenter;
import modules.usecases.*;

import java.time.LocalDateTime;
import java.util.ArrayList;


public class OrganizerController {
    private final OrganizerManager organizerManager;
    private final EventManager eventManager;
    private final RoomManager roomManager;
    private final SpeakerManager speakerManager;
    private final String organizerId;
    private final MessageManager messageManager;
    private final AttendeeManager attendeeManager;
    private final EventCreator eventCreator;
    private final AccountCreator accountCreator;
    private final UpdateInfo updateInfo;
    private final OrganizerOptionsPresenter organizerOptionsPresenter;
    private final StringFormatter stringFormatter;

    public OrganizerController(OrganizerManager organizerManager, EventManager eventManager,
                               RoomManager roomManager, SpeakerManager speakerManager,
                               MessageManager messageManager, AttendeeManager attendeeManager, EventCreator eventCreator,
                               AccountCreator accountCreator, String organizerId, UpdateInfo updateInfo,
                               OrganizerOptionsPresenter organizerOptionsPresenter, StringFormatter stringFormatter){

        this.organizerManager = organizerManager;
        this.eventManager = eventManager;
        this.roomManager = roomManager;
        this.speakerManager = speakerManager;
        this.messageManager = messageManager;
        this.attendeeManager = attendeeManager;
        this.eventCreator = eventCreator;
        this.accountCreator = accountCreator;
        this.organizerId = organizerId;
        this.updateInfo = updateInfo;
        this.organizerOptionsPresenter = organizerOptionsPresenter;
        this.stringFormatter = stringFormatter;
    }


    /**
     * Creates new Room and enters it into the system
     * @param roomNumber the room's number
     * @param capacity the maximum number of people allowed in this room, including Speakers
     */
    public void addNewRoom(String roomNumber, int capacity){
        roomManager.createRoom(roomNumber, capacity);
        updateInfo.updateRoom(roomManager.getRoom(roomNumber));
    }

    /**
     * Schedules a new event to take place at a specific time and in a specific room
     * @param roomNumber the room number of the room where the event will take place
     * @param startTime the time when the event will start
     * @param endTime the time when the event ends
     * @param capacity the maximum number of attendees allowed in the event
     */
    public void scheduleEvent(String roomNumber, LocalDateTime startTime, LocalDateTime endTime, String eventName, int capacity,
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
                    updateInfo.updateEvent(eventManager.getEvent(eventId)); // updating event info to database
                    organizerOptionsPresenter.scheduleEventSuccess(true);
                }
                catch(EventNotFoundException | ClassNotFoundException e){
                    organizerOptionsPresenter.scheduleEventSuccess(false);
                }
            }
        }
        organizerOptionsPresenter.scheduleEventSuccess(false);
    }

    /**
     * Checks whether the Room specified by roomNumber has a capacity
     * greater than or equal to the given capacity
     * @param roomNumber the roomNumber of the room being checked
     * @param capacity the capacity being checked
     * @return true if and only if the specified Room has at least a capacity of capacity
     */
    private boolean isRoomCapacityEnough(String roomNumber, int capacity){
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
        organizerOptionsPresenter.createSpeakerAccount();
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
     * precondition: the event with eventName is an existing event
     */
    public void scheduleSpeaker(String username, String roomNumber, String eventName) throws ClassNotFoundException {
        //check if speaker is available
        if (!roomExists(roomNumber)) {organizerOptionsPresenter.scheduleSpeaker(false);}
        String eventId = eventManager.getEventID(eventName);
        if (!roomManager.getEventsInRoom(roomNumber).contains(eventId)){
            organizerOptionsPresenter.scheduleSpeaker(false);
        }
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
            eventManager.addSpeakerToEvent(speakerId,eventId);
            // update speaker info in database
            updateInfo.updateUser(speakerManager.getSpeaker(speakerId));
            // add event to speaker's properties
            speakerManager.addEventToSpeaker(eventId,speakerId);
            // update event info in database
            updateInfo.updateEvent(eventManager.getEvent(eventId));
            organizerOptionsPresenter.scheduleSpeaker(true);
        }
        //return if speaker was not scheduled
        organizerOptionsPresenter.scheduleSpeaker(false);
    }

    /**
     * Removes the specified speaker from the specified event
     * @param username the username of the Speaker being scheduled for the Event
     * @param eventName the name of the event in question
     * precondition: the event with eventName is an existing event
     */
    public void removeSpeakerFromEvent(String username, String eventName) throws ClassNotFoundException {
        String eventId = eventManager.getEventID(eventName);
        String speakerId = speakerManager.getUserID(username);
        //checking that the speaker is speaking at the given event
        if (eventManager.isSpeakerSpeakingAtEvent(eventId,speakerId)){
            //remove speaker from event
            eventManager.removeSpeakerFromEvent(eventId,speakerId);
            // update speaker info in database
            updateInfo.updateUser(speakerManager.getSpeaker(speakerId));
            //remove event from speaker
            speakerManager.removeEventFromSpeaker(eventId, speakerId);
            // update event info in database
            updateInfo.updateEvent(eventManager.getEvent(eventId));
            organizerOptionsPresenter.removeSpeakerFromEvent(true);
        }
        //speaker was not speaking at event
        organizerOptionsPresenter.removeSpeakerFromEvent(false);
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
            sendMessage(attendeeID, message);
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
        String messageID = messageManager.sendMessage(organizerId, receiverID, message);
        updateInfo.updateMessage(messageManager.getMessage(messageID));   // updating message info in database
        organizerOptionsPresenter.sendMessage(true);
    }

    /**
     * Shows all messages exchanged with other user (i.e the entire conversation)
     * @param senderID the userId of the user whom the organizer shared the conversation
     */
    public void viewMessage(String senderID){
        ArrayList<String> messages = messageManager.getConversation(organizerId, senderID);
        messages = stringFormatter.messageToJSONString(messages);
        organizerOptionsPresenter.viewMessages(messages);
    }

    /**
     * Checks if the event with given username exists in the system
     * @param eventName the name of the event being searched
     * @return true if the event is in the system, false otherwise
     */
    public boolean eventExists(String eventName){
        try{
            eventManager.getEventID(eventName);
            return true;
        } catch (EventNotFoundException e) {
            return false;
        }
    }

    /**
     * Cancels/removes event from the program, the event being cancelled exists in the system
     * @param eventName the name of the event being cancelled
     */
    public void cancelEvent(String eventName){
        // removes event from system
        eventManager.removeEvent(eventName);
        String eventId = eventManager.getEventID(eventName);
        ArrayList<String> roomList = roomManager.roomsContainingEvent(eventId);
        // removes event from all rooms contained in
        for (String roomNumber: roomList){
            roomManager.removeEventFromRoom(roomNumber, eventId);
            // update room info in database
            updateInfo.updateRoom(roomManager.getRoom(roomNumber));
        }
        // removes event from the attendance lists of all attendees in the conference
        attendeeManager.removeEventFromAllAttendees(eventId);
        // removes event from the organized lists of all organizers in the conference
        organizerManager.removeFromOrganizedEvents(eventId);
        // removes event from the hosting lists of all speakers in the conference
        speakerManager.removeEventFromAllSpeakers(eventId);
        cancelEnrollment(eventName);
        // update user info in database
        ArrayList<User> users = new ArrayList<>();
        users.addAll(attendeeManager.getAttendeeList());
        users.addAll(organizerManager.getListOfOrganizers());
        users.addAll(speakerManager.getSpeakers());
        updateInfo.updateUser(users);
        organizerOptionsPresenter.cancelEvent(true);
    }

    /**
     * creates a new organizer account and inputs it into the system
     * @param username the username of the organizer
     * @param password the password of the organizer
     */
    public void createOrganizerAccount(String username, String password){
        accountCreator.createOrganizerAccount(username, password);
    }

    /**
     * creates new attendee account (non-VIP) and inputs it into the system
     * @param username the username of attendee user
     * @param password the password of attendee user
     */
    public void createAttendeeAccount(String username, String password){
        accountCreator.createAttendeeAccount(username, password, new ArrayList<>(), false);
    }

    /**
     * creates new vip attendee account and inputs it into the system
     * @param username the username of the account user
     * @param password the password of the account user
     */
    public void createVIPAttendeeAccount(String username, String password){
        accountCreator.createAttendeeAccount(username, password, new ArrayList<>(), true);
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
            // update user info in database
            ArrayList<User> users = new ArrayList<>();
            users.addAll(attendeeManager.getAttendeeList());
            users.addAll(organizerManager.getListOfOrganizers());
            users.addAll(speakerManager.getSpeakers());
            updateInfo.updateUser(users);
            organizerOptionsPresenter.cancelEvent(true);
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
