package Modules.UI;

import Modules.Controllers.AccountCreator;
import Modules.Controllers.EventCreator;
import Modules.Controllers.OrganizerController;
import Modules.Entities.Message;
import Modules.Exceptions.NonUniqueIdException;
import Modules.Presenters.EventPresenter;
import Modules.Presenters.MessagePresenter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/** UI for an Organizer who is currently using the program.
 * UI can be executed using run() method.
 */
public class OrganizerUI {
    private OrganizerController organizerController;
    private MessagePresenter messagePresenter;
    private EventPresenter eventPresenter;
    private EventCreator eventCreator;
    private AccountCreator accountCreator;
    private Scanner input = new Scanner(System.in);

    /**
     * Constructor for OrganizerUI
     * @param organizerController Controller corresponding to the user
     * @param messagePresenter Presenter needed to format incoming messages
     * @param eventPresenter Presented needed to format Event list
     * @param eventCreator Controller needed for Organizer to create/schedule an Event
     * @param accountCreator Controller needed for Organizer to create a Speaker account
     */
    public OrganizerUI(OrganizerController organizerController, MessagePresenter messagePresenter,
                       EventPresenter eventPresenter, EventCreator eventCreator, AccountCreator accountCreator){
        this.organizerController = organizerController;
        this.messagePresenter = messagePresenter;
        this.eventPresenter = eventPresenter;
        this.eventCreator = eventCreator;
        this.accountCreator = accountCreator;
    }

    /** Gives Organizer options for program usage
     * @return true if the Organizer user wants to sign out, return false if the user wants to exit the program
     */
    public boolean run() {
        int userInput;
        do{
            userInput = initialOpening();
            if (userInput == 3) {
                runOrganizing();
            } else if (userInput == 4) {
                runMessaging();
            } else if (userInput == 5){
                seeListOfEvents();
            } else if (userInput ==6){
                manageEventsAttending();
            }

        }while(userInput != 1 && userInput != 2);
        input.close();

        return userInput == 1;
    }

    /**
     * Gives Organizer it's initial prompt for usage
     * @return the next input number they select for further usage
     */
    private int initialOpening(){
        System.out.println("Enter, \n" +
                "1, To Logout\n" + "2, To Exit the Program\n" +
                "3, To get to work and do some organizing!\n" +
                "4, To chat and message other users\n" +
                "5, See total list of Events\n" +
                "6, Manage the Events you are attending\n");
        return this.validSelection(6);
    }

    /**
     * Gives Organizer prompt to select option for organizing events, Speaker Accounts and/or Rooms
     */
    private void runOrganizing(){
        System.out.println("Enter, \n" +
                "1, to schedule an Event\n" +
                "2, to create a Speaker Account\n" +
                "3, to schedule a Speaker to an Event\n" +
                "4, add a room into the system\n");
        int numChosen = this.validSelection(4);
        runOrganizingSpecifics(numChosen);
    }

    /**
     * Gives Organizer prompt for selecting a messaging option
     */
    private void runMessaging(){
        System.out.println("Enter, \n" +
            "1, send message to a fellow user\n" +
            "2, send message to all Attendees\n" +
            "3, send message to all Speakers\n" +
            "4, view messages with another user\n");
        int numChosen = this.validSelection(4);
        runMessagingSpecifics(numChosen);
    }

    /**
     * Private helper that completes the specific messaging operation based on the user input for messaging options
     * @param userInput the messaging option the Organizer has selected
     */
    private void runMessagingSpecifics(int userInput){
        if (userInput == 1){ sendMessage(); }
        else if (userInput== 2){messageAllAttendees();}
        else if (userInput== 3){messageAllSpeakers();}
        else {seeMessage();}
    }

    /**
     * Private helper that completes the specific organizational operation based on the user input for organizing options
     * @param userInput the organizing option the Organizer has selected
     */
    private void runOrganizingSpecifics(int userInput){
        if (userInput==1){ scheduleEvent(); }
        else if (userInput==2){createSpeakerAccount();}
        else if (userInput==3){scheduleSpeaker();}
        else {addNewRoom();}
    }

    /**
     * Private helper that makes sure that user input is valid selection and returns the user input as an integer type
     * for method convenience
     */
    private int validSelection(int num) {
        int selection = 0;

        boolean isValidSelection = false;
        while (!isValidSelection) {
            try {
                selection = Integer.parseInt(input.nextLine());

                if (selection >= 1 && selection <= num) {
                    // selection is within Range of options
                    System.out.println();
                    isValidSelection = true;
                } else {
                    System.out.println("Please select an option that is listed");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please input the number corresponding to the following options");
            }
        }

        return selection;
    }

    /**
     * Private helper that enables Organizer to message all Attendees currently using the program
     */
    private void messageAllAttendees(){

        System.out.println("Input the message you wish to send to all Attendees");

        String messageContent = input.nextLine();


        organizerController.messageAllAttendees(messageContent);
        System.out.println("Message was sent to all Attendees");
    }

    /**
     * Private helper that enables Organizer to message all Speakers currently using the program
     */
    private void messageAllSpeakers(){

        System.out.println("Input the message you wish to send to all Speakers");

        String messageContent = input.nextLine();

        organizerController.messageAllSpeakers(messageContent);
        System.out.println("Message was sent to all Speakers");
    }

    /**
     * Private helper that enable Organizer to send a message to their desired recipient
     */
    private void sendMessage(){
        System.out.println("Input to whom you wish to send the message");
        String userName = input.nextLine();

        System.out.println("Input the message you wish to send");
        String messageContent = input.nextLine();

        organizerController.sendMessage(userName,messageContent);
        System.out.println("Message was sent to " + userName);
    }

    /**
     * Private helper that enables Organizer to view a message that they have received from a specific sender
     */
    private void seeMessage(){
        System.out.println("Input which user you want to see conversation with");
        String userName = input.nextLine();
        ArrayList<Message> conversation = organizerController.viewMessage(userName);
        ArrayList<String> fullConversation = messagePresenter.getMessageList(conversation);

        for (String message: fullConversation){
            System.out.println(message);
        }
    }

    /**
     * Private helper that enables organizer to add a new room into the system
     */
    private void addNewRoom(){
        System.out.println("The roomNumber of the room you wish to create");
        String roomNumber = input.nextLine();
        System.out.println("The maximum capacity of the room");
        String capacity = input.nextLine();
        try{
            organizerController.addNewRoom(roomNumber, Integer.parseInt(capacity));
            System.out.println("A new room with room number" + capacity + "was successfully created");
        }
        catch(NonUniqueIdException e){
            System.out.println("Unfortunately this room number is taken by an existing room\n" +
                    "Please select a different room number and try again");
            addNewRoom();
        }
    }

    /**
     * Private helper that enables Organizer schedule a new event into the system
     */
    private void scheduleEvent(){
        //either your time, speaker or room is unavailable please try again
        System.out.println("Input the room number of the room where you wish to schedule your Event to take place");
        String roomNumber = input.nextLine();
        while (!organizerController.roomExists(roomNumber)){
            System.out.println("Oops! It looks like the room you have selected doesn't exist.\n" +
                    "Please select an existing room.");
            roomNumber = input.nextLine();
        }
        System.out.println("Input the time you wish your event to begin\n"+
                "in the form of yyyy-MM-dd HH:mm");
        ArrayList<LocalDateTime> dates = dateTimeFormatter(input, roomNumber);
        System.out.println("What is the name of the Event?");
        String name = input.nextLine();
        while (!organizerController.scheduleEvent(roomNumber,dates.get(0), dates.get(1), name)){
            System.out.println("I'm sorry, but either the room you have chosen is not available at this time.\n " +
                    "Please select a different time");
            System.out.println("Input the time you wish your event to begin\n"+
                    "in the form of yyyy-MM-dd HH:mm");
            dates = dateTimeFormatter(input, roomNumber);
        }
        System.out.println("The Event " + name + " was successfully added to the program and scheduled to the " +
                "room with room number " + roomNumber);

    }

    /**
     * Private helper that converts user input of dates in the form of Strings to LocalDateTime types,
     * for an event taking place in a specific room
     * @param event the scanner that takes in user input and preforms system output for user
     * @param roomNumber the room number where the event will take place
     * @return an ArrayList containing two dates corresponding to the start time and end time of the event the
     * user has selected
     */
    private ArrayList<LocalDateTime> dateTimeFormatter(Scanner event, String roomNumber) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startTime = LocalDateTime.parse(event.nextLine(), formatter);

        System.out.println("Input the time you wish your event to end\n"+
                "in the form of YYYY-MM-dd HH:mm");
        LocalDateTime endTime = LocalDateTime.parse(event.nextLine(), formatter);

        while(endTime.compareTo(startTime) <= 0){
            System.out.println("Pick a time at least one hour after the start time you have selected");
            endTime = LocalDateTime.parse(event.nextLine(), formatter);
        }

        ArrayList<LocalDateTime> dates = new ArrayList<>();
        dates.add(startTime);
        dates.add(endTime);
        return dates;
    }

    /**
     * Private helper that enables Organizer to create a Speaker account and add it to the system
     */
    private void createSpeakerAccount(){

        System.out.println("Input the username of the Speaker you wish to create");
        String speakerUserName = input.nextLine();
        System.out.println("Input the password of the Speaker you wish to create");
        String speakerPassword = input.nextLine();
        accountCreator.createSpeakerAccount(speakerUserName, speakerPassword, new ArrayList<>());
        System.out.println("The Speaker account has successfully been created");
    }

    /**
     * Private helper that enables Organizer to see the list of all events in the system and their description
     */
    private void seeListOfEvents() {
        for (String eventId : eventPresenter.getEventList(eventCreator.listOfEvents())) {
            System.out.println(eventId);
        }
    }

    /**
     * Private helper that allows organizer to decide if they want to attend or cancel an event for attendance
     */
    private void manageEventsAttending(){
        System.out.println("Would you like to: \n" +
                "1, Attend a new Event?\n" +
                "2, Cancel your spot in an event you are scheduled for?\n");
        String choice = input.nextLine();
        while (!choice.equals("1") && !choice.equals("2")){
            System.out.println("Please select a valid option");
            choice = input.nextLine();
        }
        if (choice.equals("1")){attendEvent();}
        cancelAttendanceForEvent();
    }

    /**
     * Private helper that allows Organizers to attend an event
     */
    private void attendEvent(){
        System.out.println("Please indicate which Event you would like to attend");
        String eventName = input.nextLine();
        while(!organizerController.attendEvent(eventName)){
            System.out.println("Oh no! Unfortunately you are unable to attend this event!\n" +
                    "Don't take it personally. Simply pick a different Event");
            eventName = input.nextLine();
        }
        //organizerController.attendEvent(eventName);
        System.out.println("You are scheduled to attend the following Event: " + eventName);
    }

    /**
     * Private helper that enable Organizer to cancel their attendance for an event
     */
    private void cancelAttendanceForEvent(){
        System.out.println("Please indicate the Event you would like to attend");
        String eventName = input.nextLine();
        while(!organizerController.cancelEnrollment(eventName).equals("Your Cancellation was successful")){
            System.out.println(organizerController.cancelEnrollment(eventName));
            eventName = input.nextLine();
        }
        System.out.println(organizerController.cancelEnrollment(eventName));

    }

    /**
     * Private helper that enables Organizer to schedule a Speaker for an event
     */
    private void scheduleSpeaker(){
        System.out.println("Input the username of the Speaker you wish to schedule to present");
        String speakerUserName = input.nextLine();
        while (!organizerController.isSpeakerInProgram(speakerUserName)){
            System.out.println("The speaker with username "+ speakerUserName+ " doesn't exist, please select and existing speaker");
            speakerUserName = input.nextLine();
        }
        System.out.println("Input the room number where " + speakerUserName + " will present");
        String roomNumber = input.nextLine();
        while(!organizerController.roomExists(roomNumber)){
            System.out.println("Oops the room you have selected doesn't exist. Please select and existing room");
            roomNumber = input.nextLine();
        }

        System.out.println("Input the time you wish your event to begin\n"+
                "in the form of yyyy-MM-dd HH:mm");
        ArrayList<LocalDateTime> dates = dateTimeFormatter(input, roomNumber);
        System.out.println("Input the name of the Event you wish for the speaker to speak at");
        String eventName = input.nextLine();
        while (!organizerController.isCorrectEvent(eventName, roomNumber)){
            System.out.println("Oops it seems there is no event under the name " +eventName + " in the room you have " +
                            "selected. \n" + "Please select an existing event and its corresponding room number");
            eventName = input.nextLine();
        }

        while(!organizerController.scheduleSpeaker(speakerUserName, roomNumber, eventName)){
            System.out.println("I'm sorry but you are not able to schedule this speaker for this event.\n" +
                    "Please select a different room");
            System.out.println("Input the room number where " + speakerUserName + " will present");
            roomNumber = input.nextLine();
        }
        System.out.println(speakerUserName +" has been scheduled");
    }


}
