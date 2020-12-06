package modules.ui;

import modules.controllers.SpeakerController;
import modules.presenters.EventPresenter;
import modules.presenters.MessagePresenter;

import java.util.ArrayList;
import java.util.Scanner;


/**
 * the UI that speaker users interact with
 */
public class SpeakerUI {
    /** the speakerController for this Conference which stores the userid of the speaker that is currently logged in*/
    private SpeakerController speakerController;

    private EventPresenter eventPresenter;
    private MessagePresenter messagePresenter;



    /**
     * Initializes SpeakerUI
     * @param speakerController the speakerController for this Conference
     * @param eventPresenter a presenter that reformats a list of Events into Strings
     */
    public SpeakerUI(SpeakerController speakerController, EventPresenter eventPresenter, MessagePresenter messagePresenter){
        this.speakerController = speakerController;
        this.eventPresenter = eventPresenter;
        this.messagePresenter = messagePresenter;
    }

    /**
     * The main method for user selecting options
     * @return true if the user wants to logout, false if the user wants to exit
     */
    public boolean run(){

/*
        // int to store user selection
        int selection; // initially 0

        do{

            //menu of options for the user
            System.out.println("Enter: \n" +
                    "1. to logout \n" +
                    "2. to exit Program \n" +
                    "3. to see a list of my events \n" +
                    "4. to message a single attendee attending my events \n" +
                    "5. to message all attendees attending my events \n"+
                    "6. to see all my messages\n");

             selection = this.validSelection();


             if (selection == 3){
                 // See list of talks
                 this.seeListOfTalks();
             } else if (selection == 4){
                 // Message single attendee
                 this.messageAttendee();
             } else if (selection == 5){
                 // Message all attendees
                 this.messageAllAttendees();
             } else if (selection == 6){
                // see messages
                this.seeMessages();
             }

        }while (!(selection==1) && !(selection==2)); // loop stops if user wants to logout or exit


        if (selection == 1){
            // Logout
            return true;
        }
        else{
            // selection == 2
            // exit program
            return false;
        }
        */
        return true;
    }

    /**
     * private helper to allow speaker user to see the list of all talks they are giving
     */
    private void seeListOfTalks(){
        /*
        // getting list of event ids of Events for speaker
        ArrayList<String> events = speakerController.showEvents();

        // parsing events to strings
        ArrayList<String> eventStrings = eventPresenter.getEventList(events);

        // printing events to screen
        if (eventStrings.isEmpty()){
            System.out.println("You have currently have no events you are speaking at");
        }
        else{
            System.out.println("List of events that you are speaking at: ");
            for(String eventString: eventStrings){
                System.out.println(eventString);
            }
        }

        System.out.println(); // spacing
         */
    }

    /**
     * private helper that allows speakers to message single attendees that are attending their events
     */
    /*
    private void messageAttendee(){
        Scanner input = new Scanner(System.in);
        System.out.println("Please input the recipient's user ID");

        String IdString = input.nextLine();

        System.out.println("Please input a message");

        String messageString = input.nextLine();

        //sending message to attendees with userId = IdString
        if (speakerController.message(IdString, messageString)){
            System.out.println("Successfully sent message to " + IdString);
        }
        else{
            System.out.println(IdString + " is not attending any of your events. No message sent.");
        }
        System.out.println(); // spacing
    }
    */


    /**
     * private helper that allows speakers to message all attendees that are attending their events
     */
    /*
    private void messageAllAttendees(){
        Scanner input = new Scanner(System.in);
        System.out.println("Please input a message");

        String messageString = input.nextLine();

        //sending message to attendees
        if (speakerController.messageAll(messageString)){
            System.out.println("Successfully sent message to all attendees attending your events.");
        }
        else{
            System.out.println("There are no attendees attending your events. No messages sent.");
        }
        System.out.println(); // spacing
    }

     */

    /**
     * private helper to display all messages that this speaker has sent/ recieved
     */
    /*
    private void seeMessages(){
        ArrayList<String> msgs = messagePresenter.getMessageList( speakerController.getAllMessages());
        if (msgs.isEmpty()){
            System.out.println("You have no messages.");
        }
        else{
            System.out.println("Here are your messages:");
            for (String msg: msgs){
                System.out.println(msg);
            }
        }
        System.out.println(); // spacing
    }

     */


    /**
     * private helper that makes sure that user input is valid selection and returns the
     */
    /*
    private int validSelection(){
        Scanner input = new Scanner(System.in);
        int selection = 0;

        boolean isValidSelection = false;
        while(!isValidSelection){
            try{
                selection = Integer.valueOf(input.nextLine());

                if (!(selection < 1 || selection > 6)){
                    // selection is within Range of options
                    System.out.println();
                    isValidSelection = true;
                }
                else{
                    System.out.println("Please select a valid option.");
                }

            }catch(NumberFormatException e){
                System.out.println("Please input a integer value.");
            }
        }

        return selection;
    }

     */




}
