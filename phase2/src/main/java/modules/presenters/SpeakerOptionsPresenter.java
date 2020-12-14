package modules.presenters;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Presenter class for all Speaker actions
 */
public class SpeakerOptionsPresenter implements IUserModelHandler, IMessageModelHandler{

    private Model model;

    public SpeakerOptionsPresenter(Model model){
        this.model = model;
        model.setUserType("speaker");
    }

    @Override
    public void setMessages(ArrayList<HashMap<String, String>> messages) {
        model.addMessages(messages);
    }

    @Override
    public void setAttendingEvents(ArrayList<HashMap<String, String>> attendingEvents) {
        model.addParticipatingEvents(attendingEvents);
    }

    @Override
    public void setNotAttendingEvents(ArrayList<HashMap<String, String>> notAttendingEvents) {
        model.addNotParticipatingEvents(notAttendingEvents);
    }

    /**
     * Displays message to user based on outcome of attempt to send a message
     * @param isSuccessful true if the message was successfully sent, false if the user was not on attendee's friends list
     */
    public void sendMessageSuccess(boolean isSuccessful){
        if (isSuccessful){
            model.setErrorStatus(true, "Message sent!");
        }
        else{
            model.setErrorStatus(false, "Sorry, that message did not go through.");
        }
    }

    public void sendMessageAllSuccess(boolean isSuccessful){
        if (isSuccessful)
            model.setErrorStatus(true, "All users attending all your talks have received your message!");
        else
            model.setErrorStatus(false, "No messages went through. Check if you are hosting any events and " +
                    "check if your events have any attendees.");
    }


}
