package modules.controllers;


import modules.presenters.EventPresenter;
import modules.presenters.LoginPresenter;
import modules.presenters.MessagePresenter;
import modules.presenters.SignupPresenter;
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
    private LoginPresenter loginPresenter;
    private SignupPresenter signupPresenter;
    private MessagePresenter messagePresenter ;
    private EventPresenter eventPresenter;


    /**
     * Constructor for StartConference that initializes and stores all use-case,presenter,controller methods
     * @param iLoginView the class with login page functionalities
     * @param iSignupView the class with signup page functionalities
     */
    public StartConference(ILoginView iLoginView, ISignupView iSignupView){
        ConferenceBuilder conferenceBuilder = new ConferenceBuilder(iLoginView, iSignupView);
        conferenceBuilder.buildConference(this); // this should set initialize all the variables above
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
            loginPresenter.invalidLogin();
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
            signupPresenter.signupSuccessful();
        }
        else{ // username is not unique
            signupPresenter.invalidSignup();
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
            loginPresenter.attendeeLogin(attendeeController);
        }
        else if (userID.startsWith("o")) {
            AttendeeController attendeeController = new AttendeeController(attendeeManager, eventManager, userID, messageManager);
            OrganizerController organizerController = new OrganizerController(organizerManager, eventManager, roomManager, speakerManager, messageManager, attendeeManager, eventCreator, accountCreator, userID);
            loginPresenter.organizerLogin(organizerController, attendeeController);
        }
        else if (userID.startsWith("s")) {
            SpeakerController speakerController = new SpeakerController(userID, eventManager, speakerManager, attendeeManager, messageManager);
            loginPresenter.speakerLogin(speakerController);
        }
    }

    // Below are just setters for all the variables

    /**
     * Setter for attendeeManager
     * @param attendeeManager the attendeeManager for this conference
     */
    public void setAttendeeManager(AttendeeManager attendeeManager) {
        this.attendeeManager = attendeeManager;
    }

    /**
     * Setter for organizerManager
     * @param organizerManager the organizerManager for this conference
     */
    public void setOrganizerManager(OrganizerManager organizerManager) {
        this.organizerManager = organizerManager;
    }

    /**
     * Setter for speakerManager
     * @param speakerManager the speakerManager for this conference
     */
    public void setSpeakerManager(SpeakerManager speakerManager) {
        this.speakerManager = speakerManager;
    }

    /**
     * Setter for eventManager
     * @param eventManager the eventManager for this conference
     */
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * Setter for roomManager
     * @param roomManager the roomManager for this conference
     */
    public void setRoomManager(RoomManager roomManager) {
        this.roomManager = roomManager;
    }

    /**
     * Setter for messageManager
     * @param messageManager the messageManager for this conference
     */
    public void setMessageManager(MessageManager messageManager) {
        this.messageManager = messageManager;
    }

    /**
     * Setter for loginController
     * @param loginController the loginController for this conference
     */
    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    /**
     * Setter for accountCreator
     * @param accountCreator the accountCreator for this conference
     */
    public void setAccountCreator(AccountCreator accountCreator) {
        this.accountCreator = accountCreator;
    }

    /**
     * Setter for eventCreator
     * @param eventCreator the eventCreator for this conference
     */
    public void setEventCreator(EventCreator eventCreator) {
        this.eventCreator = eventCreator;
    }

    /**
     * Setter for loginPresenter
     * @param loginPresenter the loginPresenter for this conference
     */
    public void setLoginPresenter(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
    }

    /**
     * Setter for signupPresenter
     * @param signupPresenter the signupPresenter for this conference
     */
    public void setSignupPresenter(SignupPresenter signupPresenter) {
        this.signupPresenter = signupPresenter;
    }

    /**
     * Setter for messagePresenter
     * @param messagePresenter the messagePresenter for this conference
     */
    public void setMessagePresenter(MessagePresenter messagePresenter) {
        this.messagePresenter = messagePresenter;
    }

    /**
     * Setter for eventPresenter
     * @param eventPresenter the eventPresenter for this conference
     */
    public void setEventPresenter(EventPresenter eventPresenter) {
        this.eventPresenter = eventPresenter;
    }


}
