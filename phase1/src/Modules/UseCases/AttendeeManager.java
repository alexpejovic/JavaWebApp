package Modules.UseCases;

import Modules.Entities.Attendee;
import Modules.Entities.Event;

import java.time.LocalDateTime;
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

    /**
     * takes an already created attendee and adds it to the list of current existing attendees
     * @param attendee an existing attendee
     */
    public void addAttendee(Attendee attendee){
        attendeeList.add(attendee);
    }

    /**
     *
     * @param attendee the attendee whose time availability we want to check
     * @param time the time at which we want to know if the attendee is available
     * @param eventManager the EventManager instance for this program
     * @return true if the attendee does not have any events at this time, false otherwise
     */
    public boolean timeAvailable(Attendee attendee, LocalDateTime time, EventManager eventManager){
        ArrayList<String> attendeeEventList = attendee.getEventsList();
        ArrayList<Event> allEventsList = eventManager.getEventList();
        for (String id : attendeeEventList) {
            for (Event event : allEventsList) {
                if (id == event.getID()) {
                    if (event.getStartTime() == time) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     *
     * @param attendee the attendee whose events list we want to alter
     * @param event the event that we want to add to the attendee's events list
     */
    public void addEventToAttendee(Attendee attendee, Event event, EventManager eventManager){
        if (timeAvailable(attendee, event.getStartTime(), eventManager)){
            attendee.addEvent(event.getID());
        }
    }

    /**
     *
     * @return a shallow copy of the list of existing attendees
     */
    public ArrayList<Attendee> getAttendeeList() {
        ArrayList<Attendee> copy = new ArrayList<>(attendeeList.size());
        for(int i = 0; i < attendeeList.size(); i++){
            copy.set(i, attendeeList.get(i));
        }
        return copy;
    }

    /**
     *
     * @param username the username entered by user
     * @return true if there exists a user account with this username, false otherwise
     */
    public boolean isUser(String username){
        for (Attendee attendee: attendeeList){
            if (attendee.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    /**
     * check if the password entered by user matches password on file
     * @param username the username og the account whose password we want to check
     * @param password the password entered that we want to compare to password on file
     * @return true if entered password matches the password on file, false otherwise
     */
    public boolean validatePassword(String username, String password){
        for (Attendee attendee: attendeeList){
            if (attendee.getUsername().equals(username)){
                if (attendee.getPassword().equals(password)){
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        return false;
    }
}
