package Modules.Presenters;

import Modules.Entities.Event;

import java.time.LocalDateTime;
import java.util.ArrayList;

/** A class used to format all information regarding a Sign-Up Menu for an Attendee. This includes formatting a list
 * of Events into a list of Strings which adequately represent an event.
 *
 */
public class EventPresenter {

    /** Reformats a list of events into a list of Strings which each describe the event
     *
     * @param events The list of Events to be reformatted
     * @return A list of Events in String form: 'EventIndex'. 'EventName' Remaining Seats: 'RemainingSeats' Start Time: 'StartTime'
     */
    public ArrayList<String> getEventList(ArrayList<Event> events){

        ArrayList<String> eventList = new ArrayList<>();
        int i = 1;

        for(Event event: events){
            String eventName = event.getName();
            if (eventName== null){
                eventName = "unnamed event";
            }

            String eventString = i + ".   " + eventName + "   Remaining Seats: " + event.getAvailableSeats()
                    + "   Room: " + event.getRoomNumber()
                    + "   Start Time: " + event.getStartTime().toString()
                    + "   End Time: " + event.getEndTime().toString();

            eventList.add(eventString);
            i ++;
        }

        return eventList;
    }


    /** Reformats a list of events into a list of event names
     *
     * @param events List of events
     * @return List of names of events
     */
    public ArrayList<String> getEventNames(ArrayList<Event> events){

        ArrayList<String> eventList = new ArrayList<>();

        for(Event event: events){
            eventList.add(event.getName());
        }

        return eventList;
    }

    /** Reformats list of events into a list of event names with respective dates
     *
     * @param events List of events
     * @return List of names of events along with dates
     */
    public ArrayList<String> getEventDates(ArrayList<Event> events){

        ArrayList<String> eventList = new ArrayList<>();

        for(Event event: events){
            eventList.add(event.getName() + " Start Time: " + event.getStartTime().toString() + "" +
                    " End Time: " + event.getEndTime().toString());
        }

        return eventList;
    }
}
