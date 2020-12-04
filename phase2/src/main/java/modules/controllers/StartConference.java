package modules.controllers;


import modules.presenters.EventPresenter;
import modules.presenters.LoginSignupPresenter;
import modules.presenters.MessagePresenter;
import modules.usecases.*;
import modules.views.ILoginView;
import modules.views.ISignupView;

import java.util.ArrayList;

public class StartConference {

    // use cases
    private AttendeeManager attendeeManager;
    private OrganizerManager organizerManager;
    private SpeakerManager speakerManager;
    private EventManager eventManager;
    private RoomManager roomManager;
    private MessageManager messageManager;
    // controllers
    private LoginController loginController;
    private AccountCreator accountCreator;
    private EventCreator eventCreator;
    // presenters
    private LoginSignupPresenter loginSignupPresenter;
    private MessagePresenter messagePresenter ;
    private EventPresenter eventPresenter;

    // UI interfaces
    private ILoginView iLoginSignupView;
    private ISignupView iSignupView;

    public StartConference(){
        ConferenceBuilder.buildConference(); // this should set initialize all the variables above
    }

    /**
     * Logs user in and moves user to their homepage
     * otherwise displays invalid login message to user
     * @param username inputted username to check
     * @param password inputted password to check
     */
    public void login(String username, String password){
        boolean isValidLogin = loginController.logIn(username, password);
        if (isValidLogin){
            this.initUserSession();
        }
        else{ // invalid username password combo
            loginSignupPresenter.invalidLogin();
        }
    }

    /**
     * Creates a new (non-VIP) attendee account if the username is unique,
     * otherwise displays invalid signup message to user
     * @param username inputted username
     * @param password inputted password
     */
    public void attendeeSignup(String username, String password){
        boolean isValidSignup = accountCreator.createAttendeeAccount(username,password, new ArrayList<>(), false);
        this.checkValidSignup(isValidSignup);
    }

    /**
     * Creates a new organizer account if the username is unique,
     * otherwise displays invalid signup message to user
     * @param username inputted username
     * @param password inputted password
     */
    public void organizerSignup(String username, String password){
        boolean isValidSignup = accountCreator.createOrganizerAccount(username, password);
        this.checkValidSignup(isValidSignup);
    }

    /**
     * Creates a new speaker account if the username is unique,
     * otherwise displays invalid signup message to user
     * @param username inputted username
     * @param password inputted password
     */
    public void speakerSignup(String username, String password){
        boolean isValidSignup = accountCreator.createSpeakerAccount(username,password, new ArrayList<>());
        this.checkValidSignup(isValidSignup);
    }

    /**
     * private helper to check for valid signup
     * @param isValidSignup true if the signup was successful, false otherwise
     */
    private void checkValidSignup(boolean isValidSignup){
        if (isValidSignup){
            loginSignupPresenter.signupSuccessful();
        }
        else{ // username is not unique
            loginSignupPresenter.invalidSignup();
        }
    }

    /**
     * private helper to create a new user controller based on a userid
     */
    private void initUserSession() {
        // userID of the person logged in currently
        String userID = loginController.getLoggedUser();

        if (userID.startsWith("a")) {
            AttendeeController attendeeController = new AttendeeController(attendeeManager, eventManager, userID, messageManager);
            loginSignupPresenter.attendeeLogin(attendeeController);
        }
        else if (userID.startsWith("o")) {
            AttendeeController attendeeController = new AttendeeController(attendeeManager, eventManager, userID, messageManager);
            OrganizerController organizerController = new OrganizerController(organizerManager, eventManager, roomManager, speakerManager, messageManager, attendeeManager, eventCreator, accountCreator, userID);
            loginSignupPresenter.organizerLogin(organizerController, attendeeController);
        }
        else if (userID.startsWith("s")) {
            SpeakerController speakerController = new SpeakerController(userID, eventManager, speakerManager, attendeeManager, messageManager);
            loginSignupPresenter.speakerLogin(speakerController);
        }
    }

    // Setters for all the variables


}
