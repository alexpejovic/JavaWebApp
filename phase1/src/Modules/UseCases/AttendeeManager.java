package Modules.UseCases;

import Modules.Entities.Attendee;

import java.util.ArrayList;

public class AttendeeManager{
    /** a list of all existing attendees */
    ArrayList<Attendee> attendeeList;

    public AttendeeManager(){
        this.attendeeList = new ArrayList<>();
    }

    /**
     * creates a new attendee using the given parameters and adds them to the list of exisitng attendees
     * @param username the username for this attendee
     * @param password the password for this attendee
     * @param userID the userID for this attendee
     * @param events the list of events this attendee is attending
     */
    public void addAttendee(String username, String password, String userID, ArrayList<String> events) {
        Attendee newAttendee = new Attendee(username, password, userID);
        for (int i = 0; i < events.size(); i++){
            newAttendee.addEvent(events.get(i));
        }
        attendeeList.add(newAttendee);
    }
}
