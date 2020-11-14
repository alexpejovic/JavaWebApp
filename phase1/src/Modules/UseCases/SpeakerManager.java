package Modules.UseCases;

import Modules.Entities.Attendee;
import Modules.Entities.Speaker;
import Modules.Entities.User;
import Modules.Exceptions.UserNotFoundException;

import java.util.ArrayList;

/**
 * This class performs actions on Speakers and gives important information about all Speakers
 */
public class SpeakerManager extends UserManager{
    private ArrayList<Speaker> speakerList;

    /**
     * A constructor for the SpeakerManager class
     * speakerList a list of existing speakers read from stored file
     */
    public SpeakerManager(ArrayList<Speaker> speakerList){
        for(Speaker speaker: speakerList){
            this.speakerList.add(speaker);
        }
    }

    /**
     * Adds a new speaker entity to the speakerList
     * @param username the username being assigned to the new Speaker
     * @param password the password being assigned to the new Speaker
     * @param userId the Id being assigned to the new Speaker
     * @param events the list of events this new Speaker will be hosting
     */
    public void addSpeaker(String username, String password, String userId, ArrayList<String> events){
        Speaker newSpeaker = new Speaker(username, password, userId);
        int i = 0;
        while(i < events.size()){
            newSpeaker.addEvent(events.get(i));
            i++;
        }
        speakerList.add(newSpeaker);
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
            newSpeakerList.set(c, speakerList.get(c));
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
             if(speakerList.get(ind).getID().equals(username)){
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



}
