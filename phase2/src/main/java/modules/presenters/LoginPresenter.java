package modules.presenters;

import modules.controllers.AttendeeController;
import modules.controllers.MessageController;
import modules.controllers.OrganizerController;
import modules.controllers.SpeakerController;
import modules.views.ILoginView;

/**
 * Presenter class for the Login actions
 */
public class LoginPresenter {
    private ILoginView iLoginView;

    /**
     * Constructor for LoginPresenter
     * @param iLoginView the interface for the login page
     */
    public LoginPresenter(ILoginView iLoginView){
        this.iLoginView = iLoginView;
    }

    /**
     * Sends a message to login page that login was unsuccessful
     */
    public void invalidLogin(){
        iLoginView.displayLoginMessage("Invalid username password combination");
    }

    /**
     * Moves to the homepage for the attendees
     * @param attendeeController the controller that the attendee Homepage uses for attendee actions
     * @param messageController the controller that the Homepage uses for message actions for all users
     */
    public void attendeeLogin(AttendeeController attendeeController, MessageController messageController){
        iLoginView.moveToAttendeeHomePage(attendeeController,messageController);
    }

    /**
     * Moves to the homepage for the organizers
     * @param organizerController the controller that the organizer Homepage uses for organizer actions
     * @param attendeeController the controller that the organizer Homepage uses for attendee actions
     * @param messageController the controller that the Homepage uses for message actions for all users
     */
    public void organizerLogin(OrganizerController organizerController, AttendeeController attendeeController,
                               MessageController messageController){
        iLoginView.moveToOrganizerHomePage(organizerController, attendeeController,messageController);
    }

    /**
     * Moves to the homepage for the speakers
     * @param speakerController the controller that the speaker Homepage uses for speaker actions
     * @param messageController the controller that the Homepage uses for message actions for all users
     */
    public void speakerLogin(SpeakerController speakerController, MessageController messageController){
        iLoginView.moveToSpeakerHomePage(speakerController,messageController);
    }



}
