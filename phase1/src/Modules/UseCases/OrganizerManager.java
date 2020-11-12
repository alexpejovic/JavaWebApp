package Modules.UseCases;

import java.util.ArrayList;

import Modules.Entities.Event;
import Modules.Entities.Organizer;
import Modules.Entities.Speaker;
import Modules.Entities.Room;
import java.time.LocalDateTime;
import Modules.Entities.Attendee;

public class OrganizerManager{
    /** List of all Organizers in the conference*/
    ArrayList<Organizer> listOfOrganizers;

    /** List of all Speakers in the conference*/
    ArrayList<Speaker> listOfSpeakers;

    /** List of all Rooms in the conference*/
    ArrayList<Room> listOfRooms;

    /** List of all Attendees in the conference*/
    ArrayList<Attendee> listOfAttendees;

    /** List of all Events in the conference */
    ArrayList<Event> listOfEvents;

    public OrganizerManager(){
        this.listOfOrganizers = new ArrayList<>();
        this.listOfSpeakers = new ArrayList<>();
        this.listOfRooms = new ArrayList<>();
        this.listOfAttendees = new ArrayList<>();
        this.listOfEvents = new ArrayList<>();
    }

    /**
     * Creates a Speaker account and adds it to the list of all speakers
     * @param userName the Speaker's username
     * @param password the Speaker's password
     * @param userId the Speaker's userId
     * @return a new Speaker account               
     */
    public Speaker createSpeakerAccount(String userName, String password, String userId){
        Speaker newSpeaker = new Speaker(userName, password, userId);
        return newSpeaker;

    }

    /**
     * adds speaker into the system
     * @param newSpeaker the speaker being added into the system
     */
    public void addSpeakerAccount(Speaker newSpeaker){listOfSpeakers.add(newSpeaker);}

    /**
     * adds newly created organizer account into the system
     * @param newOrganizer the organizer account added into the system
     */
    public void addOrganizer(Organizer newOrganizer){listOfOrganizers.add(newOrganizer);}

    /**
     * Creates an Organizer account and adds it to the list of all Organizers
     * @param userName the Organizer's username
     * @param password the Organizer's password
     * @param userId the Organizer's userId
     * @return a new Organizer account based on the info given
     */
    public Organizer createOrganizerAccount(String userName, String password, String userId){
        Organizer newOrganizer = new Organizer(userName, password, userId);
        return newOrganizer;
    }

    /**
     * Creates a new Room and adds it to the system
     * @param roomNumber the Room's unique room number
     * @param capacity the the maximum number of users allowed in the room
     * @return a new room
     */
    public Room createNewRoom(String roomNumber, int capacity){
        Room newRoom = new Room(roomNumber, capacity);
        return newRoom;
    }

    /**
     * Adds new room into the system
     * @param newRoom the room being added into the system
     */
    public void addNewRoom(Room newRoom){listOfRooms.add(newRoom);}

    /**
     * @param room The room where the Event is taking place
     * @param time The time at which the Event is taking place
     * @return the Event object of the event that is taking place in a specific Room at a specific time
     */
    private Event eventAtTime(Room room, LocalDateTime time) {
        Event eventAtTime = null;
        for (int i = 0; i < room.getEvents().size(); i++) {
            Object eventID = room.getEvents().get(i);
            for (Event event : listOfEvents) {
                if (event.getID() == eventID) {
                    eventAtTime = event;
                }
            }
        }
        return eventAtTime;
    }

    /**
     * Checks if speaker is scheduled for event that is taking place in the room during a specific time
     * @param speaker the Speaker speaking at an event
     * @param time the time the Speaker is speaking at
     * @return true if speaker is available at the specific time and i.e the speaker
     * isn't scheduled for any Events at the given time, false otherwise
     */
    private boolean isSpeakerAvailable(Speaker speaker, LocalDateTime time){
        boolean availableSpeaker = true;
        for (int counter = 0; counter < speaker.getHostEvents().size(); counter++){
            String eventId = speaker.getHostEvents().get(counter);
            for (Event currEvent : listOfEvents) {
                if (currEvent.getID().equals(eventId) && currEvent.getStartTime() == time) {
                    availableSpeaker = false;
                    break;
                }
            }
        }
        return availableSpeaker;
    }

    /**
     * Checks if room and speaker are available available to schedule Speaker in the room at a given time
     * Schedules Speaker to speak at an existing Event, in a specific room, at a specific time
     * @param speaker the Speaker
     * @param room the Room where the Speaker will hold the event
     * @param time the time the Speaker is scheduled to be in the room
     */
    public void scheduleSpeaker(Organizer organizer, Speaker speaker, Room room, LocalDateTime time){
        boolean availableSpeaker = isSpeakerAvailable(speaker, time);
        boolean availableRoom = true;
        Event eventAtTime = eventAtTime(room,time);

        if (organizer.getManagedEvents().contains(eventAtTime.getID())) {
            //check if room is available at that time (has 0 speakers)
            if (eventAtTime.hasSpeaker()) {
                availableRoom = false;
            }
            if (availableRoom && availableSpeaker) {
                //assign speaker to room by assigning speaker to event in this room
                eventAtTime.scheduleSpeaker(speaker.getID());
                room.addEvent(eventAtTime.getID());
            }
        }
    }

    /**
     * Checks if room and speaker are available available to schedule Speaker in the room at a given time
     * Assigns a new event to the room at a specific time and schedules Speaker to speak at this Event
     * @param speaker the Speaker
     * @param room the Room where the Speaker will hold the event
     * @param event the Event that the speaker will speak at
     */
    public void scheduleSpeaker(Organizer organizer, Speaker speaker, Room room, Event event){
        boolean availableSpeaker = isSpeakerAvailable(speaker, event.getStartTime());
        boolean availableRoom = true;

        if (organizer.getManagedEvents().contains(event.getID())) {
            //check if room is available at that time (has 0 speakers)
            if (event.hasSpeaker()) {
                availableRoom = false;
            }
            if (availableRoom && availableSpeaker) {
                //assign speaker to room by assigning speaker to event in this room
                event.scheduleSpeaker(speaker.getID());
                room.addEvent(event.getID());

            }
        }
    }

    /**
     * Checks that event can be scheduled for specific time in a specific room
     * If it can then creates new Event, assigned it a location (room number) and start time
     * @param organizer The organizer who is organizing the Event
     * @param roomNumber The location where the Event will take place
     * @param time The start time for the Event
     */
    public void createNewEvent(Organizer organizer, String roomNumber, LocalDateTime time){
        //checks that room number doesn't have event at given time
        boolean canOrganize = true;
        for (Room room: listOfRooms){
            if (room.getRoomNumber().equals(roomNumber)){
                for(Object eventID: room.getEvents()){
                    Event eventObject = listOfEvents.get(listOfEvents.indexOf(eventID));
                    if (eventObject.getStartTime() == time){canOrganize = false;}
                }
            }
        }
        if (canOrganize) {
            Event newEvent = new Event(roomNumber, time);
            organizer.addManagedEvent(newEvent.getID());
        }
    }

    /**
     * Reschedules the time the Event will start at
     * @param event the Event being rescheduled for another time
     * @param time the new start time being set for the Event
     * @param organizer the organizer who is wants to reschedule the event
     */
    public void rescheduleEvent(Organizer organizer, Event event, LocalDateTime time){
        // checks that the Organizer can reschedule the event
        if (organizer.getManagedEvents().contains(event.getID())){ event.setStartTime(time);}
    }

    /**
     * Cancels and removes Event fully from the conference
     * @param event the Event being removed from the conference
     * @param organizer the organizer who is wants to cancel the event
     */
    public void cancelEvent(Organizer organizer, Event event) throws Exception {
        //checks that the organizer can cancel the event
        if (organizer.getManagedEvents().contains(event.getID())) {

            // remove Event from total list of events in the conference
            listOfEvents.remove(event);

            //remove event stored in every attendee scheduled to attend the event
            for (Attendee attendee : listOfAttendees) {
                ArrayList<String> eventList = attendee.getEventsList();
                if (eventList.contains(event.getID())) {
                    attendee.removeEvent(event.getID());
                }
            }
            //remove event from the room that had the event scheduled
            for (Room room : listOfRooms) {
                ArrayList eventList = room.getEvents();
                eventList.remove(event.getID());
            }
            // removed event from the speaker's list of events it is scheduled to speak at
            for (Speaker speaker : listOfSpeakers) {
                ArrayList<String> eventList = speaker.getHostEvents();
                eventList.remove(event.getID());
            }
            //remove event from list of events organized of the organizer who organized the event
            for (Organizer thisOrganizer : listOfOrganizers) {
                thisOrganizer.removeEvent(event.getID());
            }
        }
    }
}










