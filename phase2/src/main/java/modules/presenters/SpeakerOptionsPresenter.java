package modules.presenters;

import modules.views.ISpeakerHomePageView;

import java.util.ArrayList;

/**
 * Presenter class for all Speaker actions
 */
public class SpeakerOptionsPresenter {

    private ISpeakerHomePageView iSpeakerHomePageView;

    public SpeakerOptionsPresenter(ISpeakerHomePageView iSpeakerHomePageView){
        this.iSpeakerHomePageView = iSpeakerHomePageView;
    }

    /**
     * Sends list of event info to homepage to display
     * @param formattedEvents a list of json strings representing the events that a specific user is speaking at
     */
    public void showSpeakingEvents(ArrayList<String> formattedEvents){
        iSpeakerHomePageView.displaySpeakingEvents(formattedEvents);
    }

    /**
     * Sends a list of messages to the UI to display
     * @param formattedMessages a list of json string representing the messages that the speaker sent and received
     */
    public void showAllMessages(ArrayList<String> formattedMessages){
        iSpeakerHomePageView.displayMessages(formattedMessages);
    }

    /**
     * Displays message to user based on outcome of attempt to send a message
     * @param isSuccessful true if the message was successfully sent, false if the user was not on attendee's friends list
     */
    public void sendMessageSuccess(boolean isSuccessful){
        if (isSuccessful){
            iSpeakerHomePageView.displayMessage("Message sent!");
        }
        else{
            iSpeakerHomePageView.displayMessage("Sorry, that message did not go through.");
        }
    }

    public void sendMessageAllSuccess(boolean isSuccessful){
        if (isSuccessful)
            iSpeakerHomePageView.displayMessage("All users attending all your talks have received your message!");
        else
            iSpeakerHomePageView.displayMessage("No messages went through. Check if you are hosting any events and " +
                    "check if your events have any attendees.");
    }


}
