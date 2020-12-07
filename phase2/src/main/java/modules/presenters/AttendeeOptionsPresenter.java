package modules.presenters;

import modules.views.IAttendeeHomePageView;

import java.util.ArrayList;

/**
 * Presenter class for the Signup actions
 */
public class AttendeeOptionsPresenter {
    private IAttendeeHomePageView iAttendeeHomePageView;

    /**
     * Constructor for AttendeeOptionsPresenter
     * @param iAttendeeHomePageView interface for the attendee home page
     */
    public AttendeeOptionsPresenter(IAttendeeHomePageView iAttendeeHomePageView){
        this.iAttendeeHomePageView = iAttendeeHomePageView;
    }

    /**
     * Sends list of event info to homepage to display
     * @param formattedEvents a list of json strings representing all events in the conference
     */
    public void showAllEvents(ArrayList<String> formattedEvents){
        iAttendeeHomePageView.displayAllEvents(formattedEvents);
    }

    /**
     * Sends list of event info to homepage to display
     * @param formattedEvents a list of son strings representing the events that a specific user is attending
     */
    public void showAttendingEvents(ArrayList<String> formattedEvents){
        iAttendeeHomePageView.displayAttendingEvents(formattedEvents);
    }

    /**
     * Displays message to user based on outcome of attempt to sign up to an event
     * @param isSuccessful true if the user was signed up to event, false otherwise
     */
    public void signUpToEventMessage(boolean isSuccessful){
        if (isSuccessful){
            iAttendeeHomePageView.displayMessage("Successfully signed up for event");
        }
        else{
            iAttendeeHomePageView.displayMessage("Sorry you cannot signup to that event");
        }
    }

    /**
     * Displays message to user based on outcome of attempt to cancel attendance for event
     * @param isCancellationSuccessful true if the user's attendance for event was canceled, false otherwise
     */
    public void cancelAttendanceToEventMessage(boolean isCancellationSuccessful){
        if (isCancellationSuccessful){
            iAttendeeHomePageView.displayMessage("Successfully canceled attendance to event");
        }
        else{
            iAttendeeHomePageView.displayMessage("Sorry, you were not signed up for that event");
        }
    }

    /**
     * Displays message to user based on outcome of attempt to add another user to friends list
     * @param isSuccessful true if the user added another user to their friends list, false otherwise
     */
    public void addToFriendList(boolean isSuccessful){
        if (isSuccessful){
            iAttendeeHomePageView.displayMessage("Successfully added to friends list");
        }
        else{
            iAttendeeHomePageView.displayMessage("Sorry, that user is already in your friends list");
        }
    }

    /**
     * Displays message to user based on outcome of attempt to send a message
     * @param isSuccessful true if the message was successfully sent, false if the user was not on attendee's friends list
     */
    public void sendMessage(boolean isSuccessful){
        if (isSuccessful){
            iAttendeeHomePageView.displayMessage("Message sent!");
        }
        else{
            iAttendeeHomePageView.displayMessage("Sorry, that user is not in your friends list");
        }
    }

    /**
     * Displays message to user based on outcome of attempt to reply a message
     * @param isSuccessful true if the message was successfully sent, false if there was no messages between users
     */
    public void replyMessage(boolean isSuccessful){
        if (isSuccessful){
            iAttendeeHomePageView.displayMessage("Message sent!");
        }
        else{
            iAttendeeHomePageView.displayMessage("Sorry, no messages exist between you two");
        }
    }

    /**
     * Sends list of message info to homepage to display
     * @param formattedMessages a list of json strings representing all messages between two users
     */
    public void seeMessages(ArrayList<String> formattedMessages){
        iAttendeeHomePageView.displayMessages(formattedMessages);
    }


    /**
     * Displays a message that a specified event was not in the system
     */
    public void noMessagesFound(){
        iAttendeeHomePageView.displayMessage("Sorry, no messages exist between you two");
    }

    /**
     * Displays a message that a specified message was not in the system
     */
    public void messageDoesNotExist(){ iAttendeeHomePageView.displayMessage("Sorry, that message does not exist");}

    /**
     * Displays a message that a specified event was not in the system
     */
    public void eventNotFound(){
        iAttendeeHomePageView.displayMessage("That event does not exist");
    }

    /**
     * Displays a message that a specified user was not in the system
     */
    public void userNotFound(){
        iAttendeeHomePageView.displayMessage("That user does not exist");
    }



}
