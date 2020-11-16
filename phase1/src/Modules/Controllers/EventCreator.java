package Modules.Controllers;

import Modules.Exceptions.NonUniqueIdException;
import Modules.UseCases.EventManager;

import java.time.LocalDateTime;

/**
 * A controller that creates new Events
 */
public class EventCreator {
    private EventManager eventManager;

    public EventCreator(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    public boolean createEvent(LocalDateTime startTime, LocalDateTime endTime, String roomNumber) {
        boolean eventCreated = true;
        try {
            String eventId = "e" + eventManager.getListOfEvents().size();
            eventManager.createEvent(roomNumber, startTime, endTime, eventId);
        } catch (NonUniqueIdException e) {
            eventCreated = false;
        }
        return eventCreated;

    }
}
