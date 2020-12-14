package modules.controllers;

import modules.entities.*;
import modules.gateways.EventGateway;
import modules.gateways.MessageGateway;
import modules.gateways.RoomGateway;
import modules.gateways.UserGateway;
import modules.presenters.AttendeeOptionsPresenter;
import modules.presenters.Model;
import modules.presenters.OrganizerOptionsPresenter;
import modules.presenters.SpeakerOptionsPresenter;
import modules.usecases.*;

import java.util.ArrayList;

public class ConfBuild {

    private Model model;

    private AttendeeManager attendeeManager;
    private OrganizerManager organizerManager;
    private SpeakerManager speakerManager;
    private EventManager eventManager;
    private MessageManager messageManager;

    private MessageGateway messageGateway = new MessageGateway();
    private EventGateway eventGateway = new EventGateway();
    private UserGateway userGateway = new UserGateway();
    private RoomGateway roomGateway = new RoomGateway();

    private UpdateInfo updateInfo;

    public ConfBuild(Model model) {
        this.model = model;
        attendeeManager = new AttendeeManager(readAttendees());
        organizerManager = new OrganizerManager(readOrganizers());
        speakerManager = new SpeakerManager(readSpeakers());
        eventManager = new EventManager(readEvents());
        messageManager = new MessageManager(readMessages());
        updateInfo = new UpdateInfo(messageGateway, eventGateway, userGateway, roomGateway);
    }


    public LoginController getLoginController() {
        LoginController loginController = new LoginController(attendeeManager, organizerManager, speakerManager);
        return loginController;
    }

    public OrganizerController getOrgController(String userID) {
        RoomManager roomManager = new RoomManager(readRooms());
        EventCreator eventCreator = new EventCreator(eventManager, updateInfo);
        AccountCreator accountCreator = new AccountCreator(organizerManager, attendeeManager, speakerManager, updateInfo);
        OrganizerOptionsPresenter organizerOptionsPresenter = new OrganizerOptionsPresenter(model);
        return new OrganizerController(
                organizerManager, eventManager, roomManager, speakerManager,
                messageManager, attendeeManager, eventCreator, accountCreator,
                userID, updateInfo, organizerOptionsPresenter);
    }

    public AttendeeController getAttController(String userID) {
        AttendeeOptionsPresenter attendeeOptionsPresenter = new AttendeeOptionsPresenter(model);
        return new AttendeeController(
                attendeeManager, organizerManager, speakerManager, eventManager, userID, messageManager, attendeeOptionsPresenter, updateInfo);
    }

    public SpeakerController getSpkController(String userID) {
        SpeakerOptionsPresenter speakerOptionsPresenter = new SpeakerOptionsPresenter(model);
        return new SpeakerController(
                userID, eventManager, speakerManager, attendeeManager,
                messageManager, speakerOptionsPresenter, updateInfo);
    }


    /**
     * @return an arraylist of Event entities
     */
    private ArrayList<Event> readEvents() {
        EventGateway eventGateway = new EventGateway();
        return eventGateway.readData();
    }
    /**
     * @return an arraylist of Message entities
     */
    private ArrayList<Message> readMessages() {
        MessageGateway messageGateway = new MessageGateway();
        return messageGateway.readData();
    }

    /**
     * @return an arraylist of Room entities
     */
    private ArrayList<Room> readRooms() {
        RoomGateway roomGateway = new RoomGateway();
        return roomGateway.readData();
    }

    /**
     * @return an arraylist of Attendee entities
     */
    private ArrayList<Attendee> readAttendees() {
        UserGateway userGateway = new UserGateway();
        ArrayList<User> users = userGateway.readData();
        return getAttendeesFromUsers(users);
    }

    /**
     * @return an arraylist of Organizer entities
     */
    private ArrayList<Organizer> readOrganizers() {
        UserGateway userGateway = new UserGateway();
        ArrayList<User> users = userGateway.readData();
        return getOrganizersFromUsers(users);
    }

    /**
     * @return an arraylist of Speaker entities
     */
    private ArrayList<Speaker> readSpeakers() {
        UserGateway userGateway = new UserGateway();
        ArrayList<User> users = userGateway.readData();
        return getSpeakersFromUsers(users);
    }

    /**
     * private helper to separate organizer users
     */
    private ArrayList<Organizer> getOrganizersFromUsers(ArrayList<User> users) {
        ArrayList<Organizer> organizers = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Organizer) {
                organizers.add((Organizer)user);
            }
        }
        return organizers;
    }

    /**
     * private helper to separate attendee users
     */
    private ArrayList<Attendee> getAttendeesFromUsers(ArrayList<User> users) {
        ArrayList<Attendee> attendees = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Attendee) {
                attendees.add((Attendee)user);
            }
        }
        return attendees;
    }

    /**
     * private helper to separate speaker users
     */
    private ArrayList<Speaker> getSpeakersFromUsers(ArrayList<User> users) {
        ArrayList<Speaker> speakers = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Speaker) {
                speakers.add((Speaker)user);
            }
        }
        return speakers;
    }

}
