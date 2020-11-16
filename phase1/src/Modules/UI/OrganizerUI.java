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
        int userInput = 0;
        do{
            userInput = initialOpening(userInput);
            if (userInput == 3) {
                runOrganizing();
            } else if (userInput == 4) {
                runMessaging();
            }
        }while(userInput != 1 && userInput != 2);
        input.close();

        return userInput == 1;

    }

    /**
     * Gives Organizer it's initial prompt for usage
     * @param userInput the input number the Organizer inputs corresponding to their desired operation
     * @return the next input number they select for further usage
     */
    private int initialOpening(int userInput){
        System.out.println("Enter, \n" +
                "1, To Logout\n" + "2, To Exit the Program" +
                "3, To get to work and do some organizing!" +
                "4, To chat and message other users\n");
        userInput = this.validSelection();
        return userInput;
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
        int numChosen = this.validSelection();
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
        int numChosen = this.validSelection();
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
    private int validSelection() {
        int selection = 0;

        boolean isValidSelection = false;
        while (!isValidSelection) {
            try {
                selection = Integer.parseInt(input.nextLine());

                if (selection < 1 || selection > 4) {
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
        Scanner message = new Scanner(System.in);
        System.out.println("Input the message you wish to send to all Attendees");

        String messageContent = message.nextLine();


        organizerController.messageAllAttendees(messageContent);
        System.out.println("Message was sent to all Attendees");
        message.close();
    }

    /**
     * Private helper that enables Organizer to message all Speakers currently using the program
     */
    private void messageAllSpeakers(){
        Scanner message = new Scanner(System.in);
        System.out.println("Input the message you wish to send to all Speakers");

        String messageContent = message.nextLine();

        organizerController.messageAllSpeakers(messageContent);
        System.out.println("Message was sent to all Speakers");
        message.close();
    }

    /**
     * Private helper that enable Organizer to send a message to their desired recipient
     */
    private void sendMessage(){
        Scanner message = new Scanner(System.in);
        System.out.println("Input to whom you wish to send the message");
        String userName = message.nextLine();

        System.out.println("Input the message you wish to send to all Speakers");
        String messageContent = message.nextLine();

        organizerController.sendMessage(userName,messageContent);
        System.out.println("Message was sent to" + userName);
        message.close();
    }

    /**
     * Private helper that enables Organizer to view a message that they have received from a specific sender
     */
    private void seeMessage(){
        Scanner wantToSeeMessage = new Scanner(System.in);
        System.out.println("Input which user you want to see conversation with");
        String userName = wantToSeeMessage.nextLine();
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
        Scanner room = new Scanner(System.in);
        System.out.println("The roomNumber of the room you wish to create");
        String roomNumber = room.nextLine();
        System.out.println("The maximum capacity of the room");
        String capacity = room.nextLine();
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
        Scanner event = new Scanner(System.in);
        System.out.println("Input the room number of the room where you wish to schedule your Event to take place");
        String roomNumber = event.nextLine();
        System.out.println("Input the time you wish your event to begin\n"+
                "in the form of yyyy-MM-dd: HH:mm");
        ArrayList<LocalDateTime> dates = dateTimeFormatter(event, roomNumber);
        while (!organizerController.scheduleEvent(roomNumber,dates.get(0), dates.get(1))){
            System.out.println("I'm sorry, but either the room you have chosen is not available at this time.\n " +
                    "Please select a different room or a different time");
        }
        System.out.println("The Event was successfully added to the program and scheduled to the " +
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
                "in the form of year-month-day: hour:minute:second");
        LocalDateTime endTime = LocalDateTime.parse(event.nextLine(), formatter);

        ArrayList<LocalDateTime> dates = new ArrayList<>();
        dates.add(startTime);
        dates.add(endTime);
        return dates;
    }

    /**
     * Private helper that enables Organizer to create a Speaker account and add it to the system
     */
    private void createSpeakerAccount(){
        Scanner speaker = new Scanner(System.in);
        System.out.println("Input the username of the Speaker you wish to create");
        String speakerUserName = speaker.nextLine();
        System.out.println("Input the password of the Speaker you wish to create");
        String speakerPassword = speaker.nextLine();
        accountCreator.createSpeakerAccount(speakerUserName, speakerPassword, new ArrayList<>());
        System.out.println("The Speaker account has successfully been created");
    }

    /**
     * Private helper that enables Organizer to schedule a Speaker for an event
     */
    private void scheduleSpeaker(){
        Scanner speaker = new Scanner(System.in);
        System.out.println("Input the username of the Speaker you wish to schedule to present");
        String speakerUserName = speaker.nextLine();
        System.out.println("Input the room number where " + speakerUserName + " will present");
        String roomNumber = speaker.nextLine();

        ArrayList<LocalDateTime> dates = dateTimeFormatter(speaker, roomNumber);

        while(!organizerController.scheduleSpeaker(speakerUserName, roomNumber, dates.get(0), dates.get(1))){
            System.out.println("I'm sorry but you are not able to organize this speaker at this time.\n" +
                    "Please select another time to schedule the speaker or a different room");
            System.out.println("Input the room number where " + speakerUserName + " will present");
            roomNumber = speaker.nextLine();
            dates = dateTimeFormatter(speaker, roomNumber);
        }
        System.out.println(speakerUserName +" has been scheduled");
    }


}
