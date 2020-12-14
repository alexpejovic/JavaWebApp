package modules.presenters;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Presenter class for the Attendee actions
 */
public class AttendeeOptionsPresenter implements IUserModelHandler, IMessageModelHandler {
    private Model model;

    /**
     * Constructor for AttendeeOptionsPresenter
     * @param model Model object that formats data to json
     */
    public AttendeeOptionsPresenter(Model model){
        this.model = model;
        model.setUserType("attendee");
    }

    /**
     * Displays message to user based on outcome of attempt to sign up to an event
     * @param isSuccessful true if the user was signed up to event, false otherwise
     */
    public void signUpToEventMessage(boolean isSuccessful){
        if (isSuccessful){
            model.setErrorStatus(true, "Successfully signed up for event");
        }
        else{
            model.setErrorStatus(false, "Sorry you cannot signup to that event");
        }
    }

    /**
     * Displays message to user based on outcome of attempt to cancel attendance for event
     * @param isCancellationSuccessful true if the user's attendance for event was canceled, false otherwise
     */
    public void cancelAttendanceToEventMessage(boolean isCancellationSuccessful){
        if (isCancellationSuccessful){
            model.setErrorStatus(true, "Successfully canceled attendance to event");
        }
        else{
            model.setErrorStatus(false, "Sorry, you were not signed up for that event");
        }
    }

    /**
     * Displays message to user based on outcome of attempt to add another user to friends list
     * @param isSuccessful true if the user added another user to their friends list, false otherwise
     */
    public void addToFriendList(boolean isSuccessful){
        if (isSuccessful){
            model.setErrorStatus(true, "Successfully added to friends list");
        }
        else{
            model.setErrorStatus(false, "Sorry, that user is already in your friends list");
        }
    }

    /**
     * Displays message to user based on outcome of attempt to send a message
     * @param isSuccessful true if the message was successfully sent, false if the user was not on attendee's friends list
     */
    public void sendMessage(boolean isSuccessful){
        if (isSuccessful){
            model.setErrorStatus(true, "Message sent!");
        }
        else{
            model.setErrorStatus(false, "Sorry, that user is not in your friends list");
        }
    }

    /**
     * Displays message to user based on outcome of attempt to reply a message
     * @param isSuccessful true if the message was successfully sent, false if there was no messages between users
     */
    public void replyMessage(boolean isSuccessful){
        if (isSuccessful){
            model.setErrorStatus(true, "Message sent!");
        }
        else{
            model.setErrorStatus(false, "Sorry, no messages exist between you two");
        }
    }


    /**
     * Displays a message that a specified event was not in the system
     */
    public void eventNotFound(){
        model.setErrorStatus(false, "That event does not exist");
    }

    /**
     * Displays a message that a specified user was not in the system
     */
    public void userNotFound(){
        model.setErrorStatus(false, "That user does not exist");
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
