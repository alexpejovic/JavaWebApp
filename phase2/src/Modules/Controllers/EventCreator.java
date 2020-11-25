package Modules.Controllers;

import Modules.Exceptions.NonUniqueIdException;
import Modules.UseCases.EventManager;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * A controller that creates new Events
 */
public class EventCreator {
    private EventManager eventManager;

    /**
     * Constructor for EventCreator
     * @param eventManager the corresponding Manager needed to enter events into the system
     */
    public EventCreator(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * Creates a new event with a unique ID and enters it into the system
     * Given that the room is available at the specified time
     * @param startTime the time the event starts
     * @param endTime the time the event ends
     * @param roomNumber the room number where the event will take place
     * @return true if the event was successfully created and entered into the system, false if it wasn't
     */
    public boolean createEvent(LocalDateTime startTime, LocalDateTime endTime, String roomNumber, String eventName, int capacity) {
        boolean eventCreated = true;
        try {
            String eventId = "e" + eventManager.getNumberOfEvents();
            eventManager.createEvent(roomNumber, startTime, endTime, eventId, capacity);
            eventManager.getEvent(eventId).setName(eventName);
        } catch (NonUniqueIdException e) {
            eventCreated = false;
        }
        return eventCreated;

    }

    /**
     * Retrieves a list of all the events in the system
     * @return list of all event ids of the events in the system
     */
    public ArrayList<String> listOfEvents(){
        return eventManager.getAllEventIDs();
    }
}
