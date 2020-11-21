package Modules.Controllers;

import Modules.Entities.*;
import Modules.Gateways.EventGateway;
import Modules.Gateways.MessageGateway;
import Modules.Gateways.RoomGateway;
import Modules.Gateways.UserGateway;
import Modules.Presenters.EventPresenter;
import Modules.Presenters.MessagePresenter;
import Modules.UI.*;
import Modules.UseCases.*;

import java.util.ArrayList;
import java.util.Scanner;


public class Conference {

    LoginController login;
    AttendeeManager attendeeManager;
    OrganizerManager organizerManager;
    SpeakerManager speakerManager;
    EventManager eventManager;
    RoomManager roomManager;
    MessageManager messageManager;
    AccountCreator accountCreator;

    public Conference() {
        // Init event, message, and room managers
        eventManager = new EventManager(readEvents());
        roomManager = new RoomManager(readRooms());
        messageManager = new MessageManager(readMessages());

        // Init user managers
        this.attendeeManager = new AttendeeManager(readAttendees());
        this.organizerManager = new OrganizerManager(readOrganizers());
        this.speakerManager = new SpeakerManager(readSpeakers());

        // Init controllers
        accountCreator = new AccountCreator(organizerManager, attendeeManager, speakerManager);
        login = new LoginController(attendeeManager, organizerManager, speakerManager);

    }

    /**
     * @return an arraylist of Event entities
     */
    private ArrayList<Event> readEvents() {
        EventGateway eventGateway = new EventGateway();
        return eventGateway.readSerFile();
    }
    /**
     * @return an arraylist of Message entities
     */
    private ArrayList<Message> readMessages() {
        MessageGateway messageGateway = new MessageGateway();
        return messageGateway.readSerFile();
    }

    /**
     * @return an arraylist of Room entities
     */
    private ArrayList<Room> readRooms() {
        RoomGateway roomGateway = new RoomGateway();
        return roomGateway.readSerFile();
    }

    /**
     * @return an arraylist of Attendee entities
     */
    private ArrayList<Attendee> readAttendees() {
        UserGateway userGateway = new UserGateway();
        ArrayList<User> users = userGateway.readSerFile();
            return getAttendeesFromUsers(users);
    }

    /**
     * @return an arraylist of Organizer entities
     */
    private ArrayList<Organizer> readOrganizers() {
        UserGateway userGateway = new UserGateway();
        ArrayList<User> users = userGateway.readSerFile();
            return getOrganizersFromUsers(users);
    }

    /**
     * @return an arraylist of Speaker entities
     */
    private ArrayList<Speaker> readSpeakers() {
        UserGateway userGateway = new UserGateway();
        ArrayList<User> users = userGateway.readSerFile();
            return getSpeakersFromUsers(users);
    }

    public void run() {
        if (createNewAccount()) {
            CreateAccountUI createAccountUI = new CreateAccountUI(accountCreator);
            createAccountUI.run();
        }
        LogInUI loginUI = new LogInUI(login);
        boolean userLogin = loginUI.run();

        if (userLogin) {
            boolean sessionOutcome = initUserSession();
        }
    }

    public boolean initUserSession() {
        String userID = login.getLoggedUser();
        EventPresenter eventPresenter = new EventPresenter(eventManager);
        MessagePresenter messagePresenter = new MessagePresenter();
        boolean logout = false;

        if (userID.startsWith("a")) {
            AttendeeController attendeeController = new AttendeeController(attendeeManager, eventManager, userID, messageManager);
            AttendeeUI attendeeUI = new AttendeeUI(attendeeController, eventPresenter, messagePresenter);
            attendeeUI.run();
        }
        else if (userID.startsWith("o")) {
            EventCreator eventCreator = new EventCreator(eventManager);
            AccountCreator accountCreator = new AccountCreator(organizerManager, attendeeManager, speakerManager);
            OrganizerController organizerController = new OrganizerController(organizerManager, eventManager, roomManager, speakerManager, messageManager, attendeeManager, eventCreator, accountCreator, userID);
            OrganizerUI organizerUI = new OrganizerUI(organizerController, messagePresenter, eventPresenter, eventCreator, accountCreator);
            logout = organizerUI.run();
        }
        else if (userID.startsWith("s")) {
            SpeakerController speakerController = new SpeakerController(userID, eventManager, speakerManager, attendeeManager, messageManager);
            SpeakerUI speakerUI = new SpeakerUI(speakerController, eventPresenter,messagePresenter);
            logout = speakerUI.run();
        }
        return logout;
    }

    private ArrayList<Organizer> getOrganizersFromUsers(ArrayList<User> users) {
        ArrayList<Organizer> organizers = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Organizer) {
                organizers.add((Organizer)user);
            }
        }
        return organizers;
    }

    private ArrayList<Attendee> getAttendeesFromUsers(ArrayList<User> users) {
        ArrayList<Attendee> attendees = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Attendee) {
                attendees.add((Attendee)user);
            }
        }
        return attendees;
    }

    private ArrayList<Speaker> getSpeakersFromUsers(ArrayList<User> users) {
        ArrayList<Speaker> speakers = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Speaker) {
                speakers.add((Speaker)user);
            }
        }
        return speakers;
    }

    private boolean createNewAccount() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("----Welcome----\n");
        System.out.println("0 - Login");
        System.out.println("1 - Create account");
        System.out.print("\nEnter selection (0/1): ");

        int choice = scanner.nextInt();
        return choice == 1;
    }

}
