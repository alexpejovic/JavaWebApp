package Modules.UseCases;

import Modules.Entities.Event;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class EventManager {

    /**
     * An ArrayList of all events
     */
    private ArrayList<Event> eventList;

    public EventManager() {
        this.eventList = new ArrayList<>();
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

    /**
     * Creates an event (if possible) and adds it to ArrayList events
     *
     * @param roomNumber the room number of the room where the event is set to take place
     * @param startTime the time that the event starts at
     * @return Returns true if the event was successfully created and added to events, false otherwise
     */
    public boolean createEvent(String roomNumber, LocalDateTime startTime) {
        return this.eventList.add(new Event(roomNumber, startTime));
    }

    /**
     *
     * @return a shallow copy of the existing events in this conference
     */
    public ArrayList<Event> getEventList() {
        ArrayList<Event> copy = new ArrayList<>(eventList.size());
        for(int i = 0; i < eventList.size(); i++){
            copy.set(i, eventList.get(i));
        }
        return copy;
    }

}
