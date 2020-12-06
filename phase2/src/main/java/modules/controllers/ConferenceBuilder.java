package modules.controllers;

import modules.entities.*;
import modules.gateways.EventGateway;
import modules.gateways.MessageGateway;
import modules.gateways.RoomGateway;
import modules.gateways.UserGateway;
import modules.presenters.*;
import modules.usecases.*;
import modules.views.IAttendeeHomePageView;
import modules.views.ILoginView;
import modules.views.ISignupView;

import java.util.ArrayList;

/**
 * Initializes the Use case, Controller, and Presenter classes in this conference
 * except for any user controllers which are initialized in ConferenceBuilder after login
 */
public class ConferenceBuilder {
    private EventGateway eventGateway = new EventGateway();
    private MessageGateway messageGateway = new MessageGateway();
    private UserGateway userGateway = new UserGateway();
    private RoomGateway roomGateway = new RoomGateway();

    // UI interfaces
    private ILoginView iLoginView;
    private ISignupView iSignupView;
    private IAttendeeHomePageView iAttendeeHomePageView;

    public ConferenceBuilder(ILoginView iLoginView, ISignupView iSignupView, IAttendeeHomePageView iAttendeeHomePageView){
        this.iLoginView = iLoginView;
        this.iSignupView = iSignupView;
        this.iAttendeeHomePageView = iAttendeeHomePageView;
    }

    public void buildConference(StartConference startConference){
        // Init event, message, and room managers
        EventManager eventManager = new EventManager(this.readEvents());
        startConference.setEventManager(eventManager);
        RoomManager roomManager = new RoomManager(this.readRooms());
        startConference.setRoomManager(roomManager);
        MessageManager messageManager = new MessageManager(this.readMessages());
        startConference.setMessageManager(messageManager);

        // Init user managers
        AttendeeManager attendeeManager = new AttendeeManager(readAttendees());
        startConference.setAttendeeManager(attendeeManager);
        OrganizerManager organizerManager = new OrganizerManager(readOrganizers());
        startConference.setOrganizerManager(organizerManager);
        SpeakerManager speakerManager = new SpeakerManager(readSpeakers());
        startConference.setSpeakerManager(speakerManager);

        // Init controllers
        startConference.setAccountCreator(new AccountCreator(organizerManager, attendeeManager, speakerManager));
        startConference.setLoginController(new LoginController(attendeeManager, organizerManager, speakerManager));
        startConference.setStringFormatter(new StringFormatter(eventManager, messageManager));
        startConference.setScheduleCreator(new ScheduleCreator(eventManager));

        // Init presenters
        startConference.setMessagePresenter(new MessagePresenter(messageManager));
        startConference.setEventPresenter(new EventPresenter(eventManager));
        startConference.setLoginPresenter(new LoginPresenter(iLoginView));
        startConference.setSignupPresenter(new SignupPresenter(iSignupView));
        startConference.setAttendeeOptionsPresenter(new AttendeeOptionsPresenter(iAttendeeHomePageView));

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
