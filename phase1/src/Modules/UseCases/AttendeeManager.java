package Modules.UseCases;

import Modules.Entities.Attendee;
import Modules.Entities.Event;
import Modules.Entities.User;
import Modules.Exceptions.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class AttendeeManager extends UserManager{
    /** a list of all existing attendees */
    ArrayList<Attendee> attendeeList;

    /**
     *
     * @param attendeeList a list of all attendees read from file
     */
    public AttendeeManager(ArrayList<Attendee> attendeeList){
        this.attendeeList = new ArrayList<>();
        for (Attendee attendee: attendeeList){
            this.attendeeList.add(attendee);
        }

    }

    /**
     * creates a new attendee using the given parameters and adds them to the list of existing attendees
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
     * @param attendee the attendee whose availabilty we want to check
     * @param startTime the beginning of the time period we want to check
     * @param endTime the end of the tim eperiod we want to check
     * @param eventManager the current EventManager
     * @return true if attendee is available between startTime and endTime, false otherwise
     */
    public boolean timeAvailable(Attendee attendee, LocalDateTime startTime, LocalDateTime endTime,
                                 EventManager eventManager){
        ArrayList<String> attendeeEventList = attendee.getEventsList();
        ArrayList<Event> allEventsList = eventManager.getEventList();
        for (String id : attendeeEventList) {
            for (Event event : allEventsList) {
                if (id.equals(event.getID())) {
                    if (event.getStartTime().equals(startTime) || event.getEndTime().equals(endTime)) {
                        return false;
                    }else if (event.getStartTime().isAfter(startTime) && event.getStartTime().isBefore(endTime)){
                        return false;
                    }else if (event.getEndTime().isAfter(startTime) && event.getEndTime().isBefore(endTime)){
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
        if (timeAvailable(attendee, event.getStartTime(), event.getEndTime(), eventManager)){
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
            copy.add(attendeeList.get(i));
        }
        return copy;
    }

    /**
     * Returns if there is a attendee with a specific username
     * @param username the username entered by user
     * @return true if there exists a attendee account with this username, false otherwise
     */
    @Override
    public boolean isUser(String username){
        for (Attendee attendee: attendeeList){
            if (attendee.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns whether or not there is a specific attendee account with the given username and password
     * @param username the username of the account whose password we want to check
     * @param password the password entered that we want to compare to password on file
     * @return true if entered password matches the password on file, false otherwise
     */
    @Override
    public boolean validatePassword(String username, String password){
        for (Attendee attendee: attendeeList){
            if (attendee.getUsername().equals(username)){
                return attendee.getPassword().equals(password);
            }
        }
        return false;
    }

    /**
     * Returns the specific Attendee's userID with username
     * @param username the username we want to check
     * @return the userID of the specific Attendee entity that has the given username
     */
    @Override
    public String getUserID(String username){
        for(Attendee attendee: attendeeList){
            if (attendee.getUsername().equals(username)){
                return attendee.getID();
            }
        }
        throw new UserNotFoundException();
    }

    /**
     * Returns the Attendee with this user ID
     * @param userID the userID that we are looking for
     * @return the Attendee with this userID
     */
    public Attendee getAttendee(String userID){
        for (Attendee attendee: attendeeList){
            if (attendee.getID().equals(userID)){
                return attendee;
            }
        }
        throw new UserNotFoundException();
    }

}
