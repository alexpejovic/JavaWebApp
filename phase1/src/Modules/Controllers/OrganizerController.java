package Modules.Controllers;
import Modules.Entities.*;
import Modules.Exceptions.UserNotFoundException;
import Modules.UseCases.*;

import java.time.LocalDateTime;
import java.util.ArrayList;


public class OrganizerController {
    private OrganizerManager organizerManager;
    private EventManager eventManager;
    private RoomManager roomManager;
    private SpeakerManager speakerManager;

    public OrganizerController(OrganizerManager organizerManager, EventManager eventManager,
                               RoomManager roomManager, SpeakerManager speakerManager){

        this.organizerManager = organizerManager;
        this.eventManager = eventManager;
        this.roomManager = roomManager;
        this.speakerManager = speakerManager;
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
     * @param organizerId the organizer scheduling the event
     * @param roomNumber the room number of the room where the event will take place
     * @param startTime the time when the event will start
     * @param endTime the time when the event ends
     * @param eventId the id of the event
     * @return true if the event was scheduled, false if the event was not scheduled
     */
    public boolean scheduleEvent(String organizerId, String roomNumber, LocalDateTime startTime, LocalDateTime endTime, String eventId){
        //check room is available at this time, doesn't have other event
        boolean isRoomAvailable = roomManager.isRoomAvailable(roomNumber, startTime, endTime, eventManager);
        //check event not already in another room
        boolean canBook = eventManager.canBook(roomNumber, startTime, endTime);
        if (isRoomAvailable && canBook) {
            organizerManager.addToOrganizedEvents(organizerId, eventId);
            return eventManager.createEvent(roomNumber, startTime, endTime, eventId);
        }
        return false;
    }

    /**
     * Creates a new speaker account and passes/adds it into the program
     * @param userName the username of the Speaker account
     * @param password the password of the Speaker account
     * @param userId the userID of the speaker account
     */
    public void createSpeakerAccount(String userName, String password, String userId){
        ArrayList<String> listOfEvents = new ArrayList<>();
        speakerManager.addSpeaker(userName, password, userId,listOfEvents);
    }

    /**
     * Schedules speaker to speak at a new event, at a specific time in a specific room
     * @param speakerId the id of the speaker who is presenting
     * @param roomNumber the number of room where the speaker will host the evnt
     * @param startTime the time the event will start
     * @param endTime the time the event will end
     * @return true if the speaker is able to spek at the event and has been scheduled to the room
     */
    public boolean scheduleSpeaker(String speakerId, String roomNumber, LocalDateTime startTime, LocalDateTime endTime){
        //check if speaker is available
        boolean isSpeakerAvailable = organizerManager.isSpeakerAvailable(speakerId, startTime, endTime);
        //check if room is available
        boolean isRoomAvailable = roomManager.isRoomAvailable(roomNumber, startTime, endTime, eventManager);
        //check that event doesn't have speakers organized
        boolean isEventAvailable = eventManager.hasSpeaker(speakerId);
        //schedule the speaker
        if (isSpeakerAvailable && isRoomAvailable && isEventAvailable){
            organizerManager.scheduleSpeaker(speakerId, roomNumber, startTime, endTime);
            return true;
        }
        //return if speaker was not scheduled
        return false;

    }

    /**
     * Schedules speaker to speak at an existing event if speaker, event and room are available
     * @param speakerId the id of the Speaker being scheduled for the Event
     * @param roomNumber the number of the room where the event will be help and where the speaker will present
     * @param eventId the id of the event taking place in the room
     * @return true if the speaker is able to present at the event
     */
    public boolean scheduleSpeaker(String speakerId, String roomNumber, String eventId){
        //check if speaker is available
        LocalDateTime startTime = eventManager.startTimeOfEvent(eventId);
        LocalDateTime endTime = eventManager.endTimeOfEvent(eventId);
        boolean isSpeakerAvailable = organizerManager.isSpeakerAvailable(speakerId, startTime, endTime);
        //check if room is available
        boolean isRoomAvailable = roomManager.isRoomAvailable(roomNumber, startTime, endTime, eventManager);
        //check that event doesn't have speakers organized
        boolean isEventAvailable = eventManager.hasSpeaker(speakerId);
        //schedule the speaker
        if (isSpeakerAvailable && isRoomAvailable && isEventAvailable){
            organizerManager.scheduleSpeaker(speakerId, roomNumber, eventId);
            return true;
        }
        //return if speaker was not scheduled
        return false;

    }

    /**
     * Enters Organizer users into the system
     * @param users list of all user ids in the system
     */
    public void inputOrganizer(ArrayList<User> users){
        for(User user : users){
            String id = user.getID();
            if (id.startsWith("o")){
                organizerManager.addOrganizer((Organizer) user);
            }
        }
    }

}
