package Modules.UseCases;

import Modules.Entities.Event;
import Modules.Entities.Organizer;
import Modules.Exceptions.EventNotFoundException;
import Modules.Exceptions.NonUniqueIdException;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * This class represents the use case class EventManager
 * This use case manages the entity Event
 */
public class EventManager {

    /**
     * An ArrayList of all events
     */
    private ArrayList<Event> eventList;

    public EventManager(ArrayList<Event> eventList) {
        this.eventList = new ArrayList<>();
        this.eventList.addAll(eventList);
    }

    /**
     * Checks if the schedule is free from time to time + 1 hour. This considers all events, regardless of room number
     *
     * @param time Time of day
     * @return Returns true if there are no events already occurring at time to time + 1 hour, false otherwise
     */
    public boolean isScheduleFree(LocalDateTime time) {

        for (Event event : this.eventList) {
            // Iterates through each event and checks if schedule is free from time to time + 1 hour
            // (assumes 1 hour length)
            if (!(time.until(event.getStartTime(), java.time.temporal.ChronoUnit.HOURS) > 1 ||
                    time.until(event.getStartTime(), java.time.temporal.ChronoUnit.HOURS) < -1)) {
                return false;
            }
        }

        return true;

    }

    public ArrayList<Event> getListOfEvents(){return eventList;}

    /**
     * Checks if an event can be booked in a specific room, at a specified start and end time
     * Precondition: startTime is before endTime
     * @param roomNumber the room number of the room to check
     * @param startTime the time that the event would start at
     * @param endTime the time that the event would end at
     * @return returns true if it is possible to book an event at the given location and time, false otherwise
     */
    public boolean canBook(String roomNumber, LocalDateTime startTime, LocalDateTime endTime) {
        for (Event event: getEventsInRoom(roomNumber)) {
            if (!(endTime.isBefore(event.getStartTime()) || startTime.isAfter(event.getEndTime()))) {
                // Note that false is returned if endTime is equal to an event's startTime
                return false;
            }
        }
        return true;
    }

    /**
     * Private helper function that returns the index of the event identified by an eventID. Raises an
     * EventNotFoundException if there is no event without the specified event ID
     * @param eventID the unique ID of the event
     * @return The index of the event if eventID is valid
     */
    private int indexOfEvent(String eventID) {
        for (int i = 0; i < this.eventList.size(); i++) {
            if (this.eventList.get(i).getID().equals(eventID)) {
                return i;
            }
        }

        throw new EventNotFoundException();
    }

    /**
     * Function that returns the event identified by an event ID. Raises an EventNotFoundException if
     * there is no event with the specified event ID
     * @param eventID the unique ID of the event
     * @return The event of the ID
     */
    public Event getEvent(String eventID) {
        for (Event event: this.eventList) {
            if (event.getID().equals(eventID)) {
                return event;
            }
        }
        throw new EventNotFoundException();
    }

    public String getEventID(String eventName){
        for(Event event: eventList){
            if (eventName.equals(event.getName())){ return event.getID();}
        }
        throw new EventNotFoundException();
    }

    /**
     * Returns the start time of an event
     * @param eventID the unique ID of the event
     * @return LocalDateTime representing the start time of the event
     */
    public LocalDateTime startTimeOfEvent(String eventID) {
        return getEvent(eventID).getStartTime();
    }

    /**
     * Returns the end time of an event
     * @param eventID the unique ID of the event
     * @return LocalDateTime representing the end time of the event
     */
    public LocalDateTime endTimeOfEvent(String eventID) {
        return getEvent(eventID).getEndTime();
    }

    /**
     * Creates an event (if possible) and adds it to ArrayList events
     *
     * @param roomNumber the room number of the room where the event is set to take place
     * @param startTime the time that the event starts at
     * @param endTime the time that the event end at
     * @param eventID the unique ID of the event
     * @return Returns true if the event was successfully created and added to events, false otherwise
     */
    public boolean createEvent(String roomNumber, LocalDateTime startTime, LocalDateTime endTime, String eventID) {
        for (Event event: this.eventList) {
            if (event.getID().equals(eventID)) {
                throw new NonUniqueIdException();
            }
        }
        return this.eventList.add(new Event(roomNumber, startTime, endTime, eventID));
    }

    /**
     *
     * @return a shallow copy of the existing events in this conference
     */
    public ArrayList<Event> getEventList() {
        ArrayList<Event> copy = new ArrayList<>();
        copy.addAll(this.eventList);
        return copy;
    }

    /**
     * Checks if an event can allow another attendee to attend
     * @param eventID the unique ID of the event
     * @return returns true if there is room for another attendee in the room that the event takes place in, false
     * otherwise
     */
    public boolean canAttend(String eventID) {
        return getEvent(eventID).getAvailableSeats() > 0;
    }

    /**
     * Adds an attendee userID to the list of attendees attending an event
     * @param eventID the unique ID of the event
     * @param userID the unique userID of the user
     */
    public void addAttendee(String eventID, String userID) {
        getEvent(eventID).addAttendee(userID);
    }

    /**
     * Removes an attendee's userID from the list of attendees attending an event
     * @param eventID the unique ID of the event
     * @param userID the unique userID of the user
     */
    public void removeAttendee(String eventID, String userID) {
        getEvent(eventID).removeAttendee(userID);
    }

    /**
     * Returns a list of events that occur in a room
     * @param roomNumber the room number of the room
     * @return returns an arrayList of events that take place in the specified room
     */
    private ArrayList<Event> getEventsInRoom(String roomNumber) {
        ArrayList<Event> eventsInRoom = new ArrayList<>();
        for (Event event: this.eventList) {
            if (event.getRoomNumber().equals(roomNumber)) {
                eventsInRoom.add(event);
            }
        }
        return eventsInRoom;
    }

    /**
     * Returns if an event has a speaker
     * @param eventID the unique ID of the event
     * @return returns true if the event has a speaker booked, false otherwise
     */
    public boolean hasSpeaker(String eventID) {
        return getEvent(eventID).hasSpeaker();
    }

    /**
     * Sets the speaker of the event. This method removes the previous speaker if there was one
     * @param speakerID the unique user ID of the speaker
     * @param eventID the unique ID of the event
     */
    public void setSpeaker(String speakerID, String eventID) {
        getEvent(eventID).scheduleSpeaker(speakerID);
    }

    /**
     * Returns the name of the event
     * @param eventID the unique ID of the event
     * @return returns a String representing the name of the event
     */
    public String getName(String eventID) {
        return getEvent(eventID).getName();
    }

    /**
     * Sets the new name of the event
     * @param eventID the unique ID of the event
     * @param name the new name of the event
     */
    public void renameEvent(String eventID, String name) {
        getEvent(eventID).setName(name);
    }

}
