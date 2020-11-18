package Modules.Controllers;

import Modules.Exceptions.NonUniqueIdException;
import Modules.UseCases.EventManager;

import java.time.LocalDateTime;

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
    public boolean createEvent(LocalDateTime startTime, LocalDateTime endTime, String roomNumber) {
        boolean eventCreated = true;
        try {
            String eventId = "e" + eventManager.getNumberOfEvents();
            eventManager.createEvent(roomNumber, startTime, endTime, eventId);
        } catch (NonUniqueIdException e) {
            eventCreated = false;
        }
        return eventCreated;

    }
}
