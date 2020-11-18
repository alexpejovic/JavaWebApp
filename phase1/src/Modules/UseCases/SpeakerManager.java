package Modules.UseCases;

import Modules.Entities.Event;
import Modules.Entities.Speaker;
import Modules.Entities.User;
import Modules.Exceptions.NonUniqueIdException;
import Modules.Exceptions.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * This class performs actions on Speakers and gives important information about all Speakers
 */
public class SpeakerManager extends UserManager{
    ArrayList<Speaker> speakerList;

    /**
     * A constructor for the SpeakerManager class
     * speakerList a list of existing speakers read from stored file
     */
    public SpeakerManager(ArrayList<Speaker> speakerList){
        this.speakerList = new ArrayList<>();
        for(Speaker speaker: speakerList){
            this.speakerList.add(speaker);
        }
    }

    /**
     * Returns the list of registered speakers in this conference
     * @return the list of registered speakers in this conference
     */
    public ArrayList<Speaker> getListOfSpeakers(){
        return speakerList;
    }

    /**
     * Adds a new speaker entity to the speakerList
     * only if there is not already a speaker with given userID
     * @param username the username being assigned to the new Speaker
     * @param password the password being assigned to the new Speaker
     * @param userId the Id being assigned to the new Speaker
     * @param events the list of events this new Speaker will be hosting
     * @throws NonUniqueIdException if there is already a speaker with that ID in users
     */
    public void addSpeaker(String username, String password, String userId, ArrayList<String> events){
        // Checking for a unique userID
        if (this.isUniqueID(this.speakerList,userId)){
            Speaker newSpeaker = new Speaker(username, password, userId);
            int i = 0;
            while(i < events.size()){
                newSpeaker.addEvent(events.get(i));
                i++;
            }
            speakerList.add(newSpeaker);
        }
    }

    /**
     * Add an existing speaker to the conference
     * @param speaker the speaker to be added
     */
    public void addSpeaker(Speaker speaker){
        speakerList.add(speaker);
    }

    /**
     * Checks whether there is any Speaker hosting a particular event
     * @param eventId the particular event in question
     * @return whether there is any Speaker hosting a particular event
     */
    public boolean isHosted(String eventId){
        boolean hosted = false;
        int c = 0;
        while(!hosted && c < speakerList.size()){
            if(speakerList.get(c).isHosting(eventId)){
                hosted = true;
            }
            c++;
        }
        return hosted;
    }

    /**
     * Returns the number of Speakers in this conference
     * @return the number of Speakers in this conference
     */
    public int NumSpeakers(){
        return speakerList.size();
    }

    /**
     * Returns a list of all Speakers in this conference
     * @return a list of all Speakers in this conference
     */
    public ArrayList<Speaker> getSpeakers(){
        ArrayList<Speaker> newSpeakerList = new ArrayList<>(speakerList.size());
        for(int c = 0; c < speakerList.size(); c++){
            newSpeakerList.add(speakerList.get(c));
        }
        return newSpeakerList;
    }

    /**
     * Returns whether or not there is a specific speaker account with the given username and password
     * @param username the username of the account whose password we want to check
     * @param password the password entered that we want to compare to password on file
     * @return true if entered password matches the password on file, false otherwise
     */
    @Override
    public boolean validatePassword(String username, String password){
        for (Speaker speaker: speakerList){
            if (speaker.getUsername().equals(username)){
                return speaker.getPassword().equals(password);
            }
        }
        return false;
    }

    /**
     * Returns whether a particular speaker is a registered speaker
     * @param username the username entered by user
     * @return whether a particular speaker is a registered speaker
     */
    @Override
    public boolean isUser(String username){
         int ind = 0;
         while(ind < speakerList.size()){
             if(speakerList.get(ind).getUsername().equals(username)){
                 return true;
             }
             ind++;
         }
         return false;
    }

    /**
     * Returns the specific Speaker's userID with username
     * @param username the username we want to check
     * @return the userID of the specific Speaker entity that has the given username
     * @throws UserNotFoundException if there is no speaker with UserID in this conference
     */
    @Override
    public String getUserID(String username){
        for (Speaker speaker: speakerList){
            if (speaker.getUsername().equals(username)){
                return speaker.getID();
            }
        }
        throw new UserNotFoundException();
    }

    /**
     * Return the speaker entity matching a given speakerId
     * @param speakerId the speaker Id given
     * @return the speaker entity matching speakerId
     */
    public Speaker getSpeaker(String speakerId){
        for(Speaker speaker: speakerList){
            if(speaker.getID().equals(speakerId)) {
                return speaker;
            }
        }
        throw new UserNotFoundException();
    }

    /**
     * Returns a list of the userIDs of all speakers in this conference
     * @return a list of the userIDs of all speakers in this conference
     */
    public ArrayList<String> getUserIDOfAllSpeakers() {
        ArrayList<String> allSpeakerIDs = new ArrayList<>();
        for (Speaker speaker : this.speakerList) {
            allSpeakerIDs.add(speaker.getID());
        }
        return allSpeakerIDs;
    }

    /**
     * Checks if speaker is scheduled for event that is taking place in the room during a specific time
     *
     * @param speakerId the speaker id of the Speaker speaking at the Event
     * @param startTime the time the event starts
     * @param endTime   the time the event finishes
     * @return true if speaker is available at the specific time and i.e the speaker
     * isn't scheduled for any Events at the given time, false otherwise
     */
    public boolean isSpeakerAvailable(String speakerId, LocalDateTime startTime, LocalDateTime endTime,
                                      EventManager eventManager) {
        Speaker speaker = getSpeaker(speakerId);
        for (String currEventID : speaker.getHostEvents()) {
            if(eventManager.isEventInTimePeriod(currEventID,startTime,endTime)){
                // one of the speaker's events is within the given timer period
                return false;
            }
        }
        return true;
    }

    /**
     * Added specified eventID to the list of events for the given speaker with speakerID
     * @param eventID the eventID being added
     * @param speakerID the unique userid of the speaker
     */
    public void addEventToSpeaker(String eventID, String speakerID){
        this.getSpeaker(speakerID).addEvent(eventID);
    }

}
