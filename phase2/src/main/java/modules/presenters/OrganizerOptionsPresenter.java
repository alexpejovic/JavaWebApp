package modules.presenters;

import modules.views.IOrganizerHomePageView;

import java.util.ArrayList;


/**
 * Presenter class for the Organizer actions
 */
public class OrganizerOptionsPresenter {
    private IOrganizerHomePageView iOrganizerHomePageView;

    /**
     * Constructor for OrganizerOptionsPresenter
     *
     * @param iOrganizerHomePageView interface for the organizer home page
     */
    public OrganizerOptionsPresenter(IOrganizerHomePageView iOrganizerHomePageView) {
        this.iOrganizerHomePageView = iOrganizerHomePageView;
    }

    /** Presents to the user a message regarding the scheduling of an event
     *
     * @param isSuccessful True if scheduling was successful, false otherwise
     */
    public void scheduleEventSuccess(boolean isSuccessful) {
        if (isSuccessful){
            iOrganizerHomePageView.displayMessage("Event has been successfully scheduled");
        }
        else{
            iOrganizerHomePageView.displayMessage("Something went wrong scheduling the event. Check if the room" +
                    " is available and can meet all your requirements");
        }
    }

    /** Presents to the user a message regarding the capacity of a room
     *
     * @param hasCapacity True if room has capacity, false otherwise
     */
    public void isRoomCapacityEnough(boolean hasCapacity) {
        if (hasCapacity){
            iOrganizerHomePageView.displayMessage("The room has the required capacity");
        }
        else{
            iOrganizerHomePageView.displayMessage("The room does not have the required capacity");
        }
    }

    /**
     * Presents to the user a message regarding the creation of a speaker account
     */
    public void createSpeakerAccount(){
        iOrganizerHomePageView.displayMessage("Speaker account was created");
    }

    /** Presents to the user a message regarding the success of a speaker scheduling
     *
     * @param isSuccessful True if scheduling was successful, false otherwise
     */
    public void scheduleSpeaker(boolean isSuccessful){
        if(isSuccessful){
            iOrganizerHomePageView.displayMessage("Speaker was successfully scheduled for the event");
        }
        else {
            iOrganizerHomePageView.displayMessage("Speaker was not scheduled. Check the requirements for the room " +
                    "and speaker");
        }
    }

    /** Presents to the user a message regarding the removal of a speaker account
     *
     * @param isSuccessful True if speaker was successfully removed, false otherwise
     */
    public void removeSpeakerFromEvent(boolean isSuccessful){
        if(isSuccessful){
            iOrganizerHomePageView.displayMessage("Speaker was successfully removed from the event");
        }
        else{
            iOrganizerHomePageView.displayMessage("Speaker was not successfully removed. Check if the speaker was " +
                    "speaking originally");
        }
    }

    /** Presents to the user list of messages
     *
     * @param formattedEvents List of JSON Strings in the form of events
     */
    public void viewMessages(ArrayList<String> formattedEvents){
        iOrganizerHomePageView.displayMessages(formattedEvents);
    }

    /** Presents to the user a message regarding the success of a message being sent
     *
     * @param isSuccessful True if message sending was successful, false otherwise
     */
    public void sendMessage(boolean isSuccessful){
        if(isSuccessful){
            iOrganizerHomePageView.displayMessage("Message sent");
        }
        else{
            iOrganizerHomePageView.displayMessage("Message sending unsuccessful");
        }
    }


    /** Presents to the user a message regarding the success of an event cancellation
     *
     * @param isSuccessful True if event cancellation was successful, false otherwise
     */
    public void cancelEvent(boolean isSuccessful){
        if(isSuccessful)
        iOrganizerHomePageView.displayMessage("Event cancelled");
        else
            iOrganizerHomePageView.displayMessage("Event cancellation was unsuccessful");
    }



}


