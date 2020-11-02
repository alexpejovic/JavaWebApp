package Modules.UseCases;

import Modules.Entities.Speaker;
import java.util.ArrayList;

/**
 * This class performs actions on Speakers and gives important information about all Speakers
 */
public class SpeakerManager {
    private ArrayList<Speaker> speakerList;

    /**
     * A constructor for the SpeakerManager class
     * Instantiates the speakerList as an empty ArrayList
     */
    public SpeakerManager(){
        this.speakerList = new ArrayList<>();
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
}
