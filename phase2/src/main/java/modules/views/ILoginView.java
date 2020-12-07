package modules.views;

import modules.controllers.AttendeeController;
import modules.controllers.MessageController;
import modules.controllers.OrganizerController;
import modules.controllers.SpeakerController;

/**
 * Interface for the login page
 */
public interface ILoginView {

    /**
     * Moves the page to the homepage for attendee
     * @param attendeeController the controller class that the page uses for attendee selections
     * @param messageController the controller class that the page uses for message selections that apply to every user
     */
    public void moveToAttendeeHomePage(AttendeeController attendeeController,MessageController messageController);

    /**
     * Moves the page to the homepage for organizer
     * @param organizerController the controller class that the page uses for organizer selections
     * @param attendeeController the controller class that the page uses for attendee actions
     * @param messageController the controller class that the page uses for message selections that apply to every user
     */
    public void moveToOrganizerHomePage(OrganizerController organizerController, AttendeeController attendeeController,
                                        MessageController messageController);

    /**
     * Moves the page to the homepage for speaker
     * @param speakerController the controller class that the page uses for speaker selections
     * @param messageController the controller class that the page uses for message selections that apply to every user
     */
    public void moveToSpeakerHomePage(SpeakerController speakerController, MessageController messageController);

    /**
     * Displays given message to user on the page
     * @param message the message to display
     */
    public void displayLoginMessage(String message);


}
