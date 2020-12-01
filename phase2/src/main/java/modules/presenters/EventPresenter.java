package modules.presenters;

import modules.usecases.EventManager;

import java.util.ArrayList;

/** A class used to format all information regarding a Sign-Up Menu for an Attendee. This includes formatting a list
 * of Events into a list of Strings which adequately represent an event.
 *
 */
public class EventPresenter {
    private EventManager eventManager;

    public EventPresenter(EventManager eventManager){
        this.eventManager = eventManager;
    }

    /** Reformats a list of events into a list of Strings which each describe the event
     *
     * @param eventIDs The list of eventIDs of the Events to be reformatted
     * @return A list of Events in String form:
     *          'EventIndex'. 'EventName' Remaining Seats: 'RemainingSeats' Start Time: 'StartTime' End Time: 'EndTime'
     */
    public ArrayList<String> getEventList(ArrayList<String> eventIDs){

        ArrayList<String> eventList = new ArrayList<>();
        int i = 1;

        for(String eventID: eventIDs){
            String eventName = eventManager.getName(eventID);
            if (eventName== null){
                eventName = "unnamed event";
            }

            String eventString = i + ".   " + eventName +
                    "   Remaining Seats: " + eventManager.getRemainingSeats(eventID)
                    + "   Room: " + eventManager.getRoomNumberOfEvent(eventID)
                    + "   Start Time: " + eventManager.startTimeOfEvent(eventID)
                    + "   End Time: " + eventManager.endTimeOfEvent(eventID);

            eventList.add(eventString);
            i ++;
        }

        return eventList;
    }


    /** Reformats a list of events into a list of event names
     *
     * @param eventIDs List of eventIDs
     * @return List of names of events
     */
    public ArrayList<String> getEventNames(ArrayList<String> eventIDs){

        ArrayList<String> eventList = new ArrayList<>();

        for(String eventID: eventIDs){
            eventList.add(eventManager.getName(eventID));
        }

        return eventList;
    }

    /** Reformats list of events into a list of event names with respective dates
     *
     * @param eventIDs List of events
     * @return List of names of events along with dates
     */
    public ArrayList<String> getEventDates(ArrayList<String> eventIDs){

        ArrayList<String> eventList = new ArrayList<>();

        for(String eventID: eventIDs){
            eventList.add(eventManager.getName(eventID) + " Start Time: "
                    + eventManager.startTimeOfEvent(eventID).toString() + "" +
                    " End Time: " + eventManager.endTimeOfEvent(eventID));
        }

        return eventList;
    }
}
