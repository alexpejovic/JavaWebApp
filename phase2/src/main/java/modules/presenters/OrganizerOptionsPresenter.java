package modules.presenters;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Presenter class for the Organizer actions
 */
public class OrganizerOptionsPresenter implements IUserModelHandler, IMessageModelHandler {
    private Model model;

    /**
     * Constructor for OrganizerOptionsPresenter
     *
     * @param model Model object that formats data to json
     */
    public OrganizerOptionsPresenter(Model model) {
        this.model = model;
        model.setUserType("organizer");
    }

    /** Presents to the user a message regarding the scheduling of an event
     *
     * @param isSuccessful True if scheduling was successful, false otherwise
     */
    public void scheduleEventSuccess(boolean isSuccessful) {
        if (isSuccessful){
            model.setErrorStatus(true, "Event has been successfully scheduled");
        }
        else{
            model.setErrorStatus(false, "Something went wrong scheduling the event. Check if the room" +
                    " is available and can meet all your requirements");
        }
    }

    /** Presents to the user a message regarding the capacity of a room
     *
     * @param hasCapacity True if room has capacity, false otherwise
     */
    public void isRoomCapacityEnough(boolean hasCapacity) {
        if (hasCapacity){
            model.setErrorStatus(true, "The room has the required capacity");
        }
        else{
            model.setErrorStatus(false, "The room does not have the required capacity");
        }
    }

    /**
     * Presents to the user a message regarding the creation of a speaker account
     */
    public void createSpeakerAccount(){
        model.setErrorStatus(true, "Speaker account was created");
    }

    /** Presents to the user a message regarding the success of a speaker scheduling
     *
     * @param isSuccessful True if scheduling was successful, false otherwise
     */
    public void scheduleSpeaker(boolean isSuccessful){
        if(isSuccessful){
            model.setErrorStatus(true, "Speaker was successfully scheduled for the event");
        }
        else {
            model.setErrorStatus(false, "Speaker was not scheduled. Check the requirements for the room and speaker");
        }
    }

    /** Presents to the user a message regarding the removal of a speaker account
     *
     * @param isSuccessful True if speaker was successfully removed, false otherwise
     */
    public void removeSpeakerFromEvent(boolean isSuccessful){
        if(isSuccessful){
            model.setErrorStatus(true, "Speaker was successfully removed from the event");
        }
        else{
            model.setErrorStatus(false, "Speaker was not successfully removed. Check if the speaker was " +
                    "speaking originally");
        }
    }

    /** Presents to the user a message regarding the success of a message being sent
     *
     * @param isSuccessful True if message sending was successful, false otherwise
     */
    public void sendMessage(boolean isSuccessful){
        if(isSuccessful){
            model.setErrorStatus(true, "Message sent");
        }
        else{
            model.setErrorStatus(false, "Message sending unsuccessful");
        }
    }


    /** Presents to the user a message regarding the success of an event cancellation
     *
     * @param isSuccessful True if event cancellation was successful, false otherwise
     */
    public void cancelEvent(boolean isSuccessful){
        if(isSuccessful)
            model.setErrorStatus(true, "Event cancelled");
        else
            model.setErrorStatus(false, "Event cancellation was unsuccessful");
    }

    public void cancelEnrollment(boolean isSuccessful) {
        if (isSuccessful)
            model.setErrorStatus(true, "Removed attendance from event");
        else
            model.setErrorStatus(false, "Unable to remove attendance from event");
    }

    public void setFriends(ArrayList<HashMap<String, String>> friends) {
        model.addFriends(friends);
    }

    @Override
    public void setMessages(ArrayList<HashMap<String, String>> messages) {
        model.addMessages(messages);
    }

    @Override
    public void setAttendingEvents(ArrayList<HashMap<String, String>> attending) {
        model.addParticipatingEvents(attending);
    }

    @Override
    public void setNotAttendingEvents(ArrayList<HashMap<String, String>> notAttending) {
        model.addNotParticipatingEvents(notAttending);
    }
}


