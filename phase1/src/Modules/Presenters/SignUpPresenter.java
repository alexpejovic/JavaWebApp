package Modules.Presenters;

import Modules.Entities.Event;
import java.util.ArrayList;

/** A class used to format all information regarding a Sign-Up Menu for an Attendee. This includes formatting a list
 * of Events into a list of Strings which adequately represent an event.
 *
 */
public class SignUpPresenter {

    /** Reformats a list of events into a list of Strings which each describe the event
     *
     * @param events The list of Events to be reformatted
     * @return A list of Events in String form: 'EventIndex'. 'EventName' Remaining Seats: 'RemainingSeats' Start Time: 'StartTime'
     */
    public ArrayList<String> getEventList(ArrayList<Event> events){

        ArrayList<String> eventList = new ArrayList<>();
        int i = 1;

        for(Event event: events){
            String eventString = i + ". " + event.getName() + " Remaining Seats: " + event.getAvailableSeats() +
                    "Start Time: " + event.getStartTime().toString();
            eventList.add(eventString);
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
}
