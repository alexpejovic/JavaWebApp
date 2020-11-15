package Modules.UseCases;

import java.util.ArrayList;

import Modules.Entities.*;
import Modules.Exceptions.EventNotFoundException;
import Modules.Exceptions.RoomNotFoundException;
import Modules.Exceptions.UserNotFoundException;

import java.time.LocalDateTime;

public class OrganizerManager extends UserManager {
    /**
     * List of all Organizers in the conference
     */
    ArrayList<Organizer> listOfOrganizers;

    private EventManager eventManager;
    private RoomManager roomManager;
    private AttendeeManager attendeeManager;
    private SpeakerManager speakerManager;

    public OrganizerManager(EventManager eventManager, RoomManager roomManager,
                            AttendeeManager attendeeManager, SpeakerManager speakerManager,
                            ArrayList<Organizer> organizers) {
        this.listOfOrganizers = new ArrayList<>();
        for (Organizer organizer : organizers) {
            this.listOfOrganizers.add(organizer);
        }
        this.roomManager = roomManager;
        this.eventManager = eventManager;
        this.attendeeManager = attendeeManager;
        this.speakerManager = speakerManager;

    }

    /**
     * Returns if there is a attendee with a specific username
     *
     * @param username the username entered by user
     * @return true if there exists a organizer account with this username, false otherwise
     */
    @Override
    public boolean isUser(String username) {
        for (Organizer organizer : listOfOrganizers) {
            if (organizer.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns whether or not there is a specific organizer account with the given username and password
     *
     * @param username the username of the account whose password we want to check
     * @param password the password entered that we want to compare to password on file
     * @return true if entered password matches the password on file, false otherwise
     */
    @Override
    public boolean validatePassword(String username, String password) {
        for (Organizer organizer : listOfOrganizers) {
            if (organizer.getUsername().equals(username)) {
                return organizer.getPassword().equals(password);
            }
        }
        return false;
    }

    public ArrayList<Organizer> getListOfOrganizers() {
        return listOfOrganizers;
    }

    /**
     * Returns the specific Organizer's userID with username
     *
     * @param username the username we want to check
     * @return the userID of the specific Organizer entity that has the given username
     */
    @Override
    public String getUserID(String username) {
        for (Organizer organizer : listOfOrganizers) {
            if (organizer.getUsername().equals(username)) {
                return organizer.getID();
            }
        }
        throw new UserNotFoundException();
    }

    /**
     * adds newly created organizer account into the system
     *
     * @param newOrganizer the organizer account added into the system
     */
    public void addOrganizer(Organizer newOrganizer) {
        listOfOrganizers.add(newOrganizer);
    }

    /**
     * Creates an Organizer account and adds it to the list of all Organizers
     *
     * @param userName the Organizer's username
     * @param password the Organizer's password
     * @param userId   the Organizer's userId
     */
    public void createOrganizerAccount(String userName, String password, String userId) {
        listOfOrganizers.add(new Organizer(userName, password, userId));
    }

    /**
     * @param roomId    The id of the room where the Event is taking place
     * @param startTime The time at which the Event will start
     * @param endTime   The time at which the Event will end
     * @return the Event object of the event that is taking place in a specific Room at a specific time
     */
    private Event eventAtTime(String roomId, LocalDateTime startTime, LocalDateTime endTime) {
        Room room = findRoom(roomId);
        for (String currEvent : room.getEvents()) {
            Event event = findEvent(currEvent);
            if (event.getStartTime().equals(startTime) && event.getEndTime().equals(endTime)) {
                return event;
            }
        }
        throw new EventNotFoundException();
    }

    /**
     * Private method that return the room object of the room storing the given room number
     *
     * @param roomNumber the room number of the room object being searched for
     * @return The room object with the corresponding room number
     */
    private Room findRoom(String roomNumber) {
        for (Room currRoom : roomManager.getListOfRooms()) {
            if (roomNumber.equals(currRoom.getRoomNumber())) {
                return currRoom;
            }
        }
        throw new RoomNotFoundException();
    }

    /**
     * Private helper method that finds the speaker object with specific id
     *
     * @param speakerId the id of the speaker being searched for
     * @return the speaker object containing the corresponding speaker id
     */
    private Speaker findSpeaker(String speakerId) {
        for (Speaker currSpeaker : speakerManager.getListOfSpeakers()) {
            if (speakerId.equals(currSpeaker.getID())) {
                return currSpeaker;
            }
        }
        throw new UserNotFoundException();
    }

    public ArrayList<String> forMessagingAllAttendees() {
        ArrayList<String> allAttendeeIDs = new ArrayList<>();
        for (Attendee attendee : attendeeManager.getListOfAttendees()) {
            allAttendeeIDs.add(attendee.getID());
        }
        return allAttendeeIDs;
    }

    public ArrayList<String> forMessagingAllSpeakers() {
        ArrayList<String> allSpeakerIDs = new ArrayList<>();
        for (Speaker speaker : speakerManager.getListOfSpeakers()) {
            allSpeakerIDs.add(speaker.getID());
        }
        return allSpeakerIDs;
    }

    /**
     * Private helper method that finds the Organizer object in the system with the given id
     *
     * @param organizerId the Organizer's id
     * @return the Organizer object that stores the corresponding id
     */
    private Organizer findOrganizer(String organizerId) {
        for (Organizer currOrganizer : listOfOrganizers) {
            if (organizerId.equals(currOrganizer.getID())) {
                return currOrganizer;
            }
        }
        throw new UserNotFoundException();
    }

    /**
     * Private helper method that finds the event object in the system with the given id
     *
     * @param eventId the Organizer's id
     * @return the Event object that stores the corresponding id
     */
    private Event findEvent(String eventId) {
        for (Event currEvent : eventManager.getEventList()) {
            if (currEvent.getID().equals(eventId)) {
                return currEvent;
            }
        }
        throw new EventNotFoundException();
    }


    /**
     * Checks if speaker is scheduled for event that is taking place in the room during a specific time
     *
     * @param speakerId the speaker id of the Speaker speaking at the Event
     * @param startTime the time the event starts
     * @param endTime   the time the event finishes
     * @return true if speaker is available at the specific time and i.e the speaker
     * isn't scheduled for any Events at the given time, false otherwise
     */
    public boolean isSpeakerAvailable(String speakerId, LocalDateTime startTime, LocalDateTime endTime) {
        Speaker speaker = findSpeaker(speakerId);
        for (String currEventID : speaker.getHostEvents()) {
            Event currEvent = findEvent(currEventID);
            LocalDateTime currStartTime = currEvent.getStartTime();
            LocalDateTime currEndTime = currEvent.getEndTime();
            if (startTime.equals(currStartTime) && endTime.equals(currEndTime)) {
                return false;
            }
        }
        return true;
    }


    /**
     * Checks if room and speaker are available to schedule Speaker in the room at a given time
     * Assigns a new event to the room at a specific time and schedules Speaker to speak at an existing Event
     *
     * @param speakerId the Speaker's id
     * @param roomId    the Room's number where the Speaker will hold the event
     * @param eventId   the Event's id that the speaker will speak at
     */
    public void scheduleSpeaker(String speakerId, String roomId, String eventId) {
        Room room = findRoom(roomId);
        Event event = findEvent(eventId);
        event.scheduleSpeaker(speakerId);

    }

    /**
     * Updates the organizers list of organized events
     *
     * @param organizerId the organizer's id
     * @param eventId     the id of the Event being added to the organizer's list of organized events
     */
    public void addToOrganizedEvents(String organizerId, String eventId) {
        Organizer organizer = findOrganizer(organizerId);
        organizer.addManagedEvent(eventId);
    }

    public Event getEventInRoom(LocalDateTime startTime, LocalDateTime endTime, String roomNumber) {
        Room room = findRoom(roomNumber);
        for (String eventId : room.getEvents()) {
            Event event = findEvent(eventId);
            if (event.getStartTime() == startTime && event.getEndTime() == endTime) {
                return event;
            }
        }
        return null;
    }
}