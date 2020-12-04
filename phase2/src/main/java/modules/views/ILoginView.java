package modules.views;

import modules.controllers.AttendeeController;
import modules.controllers.OrganizerController;
import modules.controllers.SpeakerController;

/**
 * Interface for the login page
 */
public interface ILoginView {

    /**
     * Moves the page to the homepage for attendee
     * @param attendeeController the controller class that the page uses for attendee selections
     */
    public void moveToAttendeeHomePage(AttendeeController attendeeController);

    /**
     * Moves the page to the homepage for organizer
     * @param organizerController the controller class that the page uses for organizer selections
     * @param attendeeController the controller class that the page uses for attendee actions
     */
    public void moveToOrganizerHomePage(OrganizerController organizerController, AttendeeController attendeeController);

    /**
     * Moves the page to the homepage for speaker
     * @param speakerController the controller class that the page uses for speaker selections
     */
    public void moveToSpeakerHomePage(SpeakerController speakerController);

    /**
     * Displays given message to user on the page
     * @param message the message to display
     */
    public void displayLoginMessage(String message);


}
