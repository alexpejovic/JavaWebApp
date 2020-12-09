package modules.controllers;

import modules.entities.*;
import modules.gateways.EventGateway;
import modules.gateways.MessageGateway;
import modules.gateways.RoomGateway;
import modules.gateways.UserGateway;
import modules.usecases.*;
import modules.views.IOrganizerHomePageView;

import java.util.ArrayList;

public class ConfBuild {

    AttendeeManager attendeeManager;
    OrganizerManager organizerManager;
    SpeakerManager speakerManager;

    MessageGateway messageGateway = new MessageGateway();
    EventGateway eventGateway = new EventGateway();
    UserGateway userGateway = new UserGateway();
    RoomGateway roomGateway = new RoomGateway();

    public ConfBuild() {
        attendeeManager = new AttendeeManager(readAttendees());
        organizerManager = new OrganizerManager(readOrganizers());
        speakerManager = new SpeakerManager(readSpeakers());
    }


    public LoginController getLoginController() {
        LoginController loginController = new LoginController(attendeeManager, organizerManager, speakerManager);
        return loginController;
    }

/*    public OrganizerController getOrgController(String userID) {
        EventManager eventManager = new EventManager(readEvents());
        RoomManager roomManager = new RoomManager(readRooms());
        MessageManager messageManager = new MessageManager(readMessages());
        UpdateInfo updateInfo = new UpdateInfo(messageGateway, eventGateway, userGateway, roomGateway);
        EventCreator eventCreator = new EventCreator(eventManager, updateInfo);
        AccountCreator accountCreator = new AccountCreator(organizerManager, attendeeManager, speakerManager, updateInfo);
        OrganizerController organizerController = new OrganizerController(organizerManager, eventManager, roomManager, speakerManager, messageManager, attendeeManager, eventCreator, accountCreator, userID, updateInfo);
        return organizerController;
    }*/


    /**
     * @return an arraylist of Event entities
     */
    private ArrayList<Event> readEvents() {
//        EventGatewayDB eventGateway = new EventGatewayDB();
        EventGateway eventGateway = new EventGateway();
        return eventGateway.readData();
    }
    /**
     * @return an arraylist of Message entities
     */
    private ArrayList<Message> readMessages() {
//        MessageGatewayDB messageGateway = new MessageGatewayDB();
        MessageGateway messageGateway = new MessageGateway();
        return messageGateway.readData();
    }

    /**
     * @return an arraylist of Room entities
     */
    private ArrayList<Room> readRooms() {
//        RoomGatewayDB roomGateway = new RoomGatewayDB();
        RoomGateway roomGateway = new RoomGateway();
        return roomGateway.readData();
    }

    /**
     * @return an arraylist of Attendee entities
     */
    private ArrayList<Attendee> readAttendees() {
//        UserGatewayDB userGateway = new UserGatewayDB();
        UserGateway userGateway = new UserGateway();
        ArrayList<User> users = userGateway.readData();
        return getAttendeesFromUsers(users);
    }

    /**
     * @return an arraylist of Organizer entities
     */
    private ArrayList<Organizer> readOrganizers() {
//        UserGatewayDB userGateway = new UserGatewayDB();
        UserGateway userGateway = new UserGateway();
        ArrayList<User> users = userGateway.readData();
        return getOrganizersFromUsers(users);
    }

    /**
     * @return an arraylist of Speaker entities
     */
    private ArrayList<Speaker> readSpeakers() {
//        UserGatewayDB userGateway = new UserGatewayDB();
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
