package Modules.Entities;

import java.util.ArrayList;

/**
 * This class represents a Speaker.
 * Speakers can host events.
 * Speakers can also message all attendees of the event(s) they hosting
 */
public class Speaker extends User {
    //id of events the speaker is hosting
    private ArrayList<String> hostEvents;

    /**
     * This is a constructor for the Speaker class
     * @param username the username of the Speaker
     * @param password the password of the Speaker
     * @param userId the Id of the Speaker
     */
    public Speaker(String username,String password, String userId){
        super(username, password, userId);
        this.hostEvents = new ArrayList<>();
    }

    /**
     * Adds an event to the Speaker's list of Events they are hosting
     * @param eventId the Id of the event the Speaker is adding to the list of events they are hosting
     */
    public void addEvent(String eventId){
        hostEvents.add(eventId);
    }

    /**
     * Removes a particular event from the list of events this Speaker is hosting
     * @param eventId the Id of the event this Speaker is removing from the list of events they are hosting
     */
    public void removeEvent(String eventId){
        hostEvents.remove(eventId);
    }

    /**
     * Returns the list of events this Speaker is hosting
     * @return the list of events this Speaker is hosting
     */
    public ArrayList<String> getHostEvents(){
        ArrayList<String> newHostEvents = new ArrayList<>(hostEvents.size());
        for(int c = 0; c < hostEvents.size(); c++){
            newHostEvents.set(c, hostEvents.get(c));
        }
        return newHostEvents;
    }

    /**
     * Returns whether the Speaker is already hosting a particular event or not
     * @param eventId the event the Speaker is checking to see if they are hosting
     * @return whether the Speaker is already hosting a particular event or not
     */
    public boolean isHosting(String eventId){
        return hostEvents.contains(eventId);
    }

    /**
     * Returns the number of events that this Speaker is hosting
     * @return the number of events that this Speaker is hosting
     */
    public int numEvents(){
        return hostEvents.size();
    }
}
