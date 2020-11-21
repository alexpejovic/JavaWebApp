package Modules.UI;

import Modules.Controllers.AttendeeController;
import Modules.Exceptions.UserNotFoundException;
import Modules.Presenters.EventPresenter;
import Modules.Presenters.MessagePresenter;

import java.util.ArrayList;
import java.util.Scanner;

public class AttendeeUI {

    private EventPresenter eventPresenter;
    private MessagePresenter messagePresenter;
    private AttendeeController attendeeController;

    public AttendeeUI(AttendeeController attendeeController, EventPresenter eventPresenter, MessagePresenter messagePresenter){
        this.attendeeController = attendeeController;
        this.eventPresenter = eventPresenter;
        this.messagePresenter = messagePresenter;
    }

    /** Displays UI for Attendees, giving the user options to sign up for events, view a list of events currently
     * signed-up for, cancel event enrollment, view/send messages, and quit. Method stops executing only when user
     * wishes to quit menu.
     *
     */
    public boolean run(){

        Scanner input = new Scanner(System.in);

        System.out.println("---Main Menu---\n");

        while(true){
            System.out.println(); // for spacing
            System.out.println("Enter 1 if you'd like to sign up to an event\n" +
                    "Enter 2 if you'd like to view the list of events you are currently signed up for\n" +
                    "Enter 3 if you'd like to cancel your enrollment into attending an event\n" +
                    "Enter 4 if you'd like to view/send messages to another user\n" +
                    "Enter 5 if you'd like to add a user to your friend list\n"+
                    "Enter l to logout\n" +
                    "Enter q to exit");

            switch (input.nextLine()){
                case "1":
                    runSignUp();
                    break;
                case "2":
                    displayEventList();
                    break;
                case "3":
                    runEnrollmentCancellation();
                    break;
                case "4":
                    runMessageSystem();
                    break;
                case "5":
                    addFriend();
                    break;
                case "l":
                    return true;
                case "q":
                    return false;
                default:
                    System.out.println("Improper input, try again");
            }
        }

    }

    /** Prompts the user to enter the ID of another user to whom which they would like to communicate. The user is then
     * presented a message history with the user, and is prompted to send that user a message.
     *
     */
    private void runMessageSystem(){

        Scanner input = new Scanner(System.in);

        System.out.println("---Send and View Messages---\n");
        System.out.println("Type the ID of the user of whom which you'd like to send a message, or view messages from");
        String userId = input.nextLine();

        if(userId.matches("[sa][0-9]+")){

            System.out.println("---Messages with User with userID " + userId + "---\n");
            for(String message: messagePresenter.getMessageList(attendeeController.seeMessage(userId))){
                System.out.println(message);
            }
            System.out.println("\nIf you wish to send a message to this user, type it and click enter, if you don't, type nothing and click enter\n");
            String message = input.nextLine();

            if(!message.equals("")){
                try {
                    if(attendeeController.sendMessage(userId, message)){
                        System.out.println("Message sent.");
                    }
                    else{
                        System.out.println("Sorry, that person is not on your friend list. Message not sent");
                    }
                }
                catch (UserNotFoundException e){
                    System.out.println("There is no user with that ID");
                }
            }

        }
        else{
            System.out.println("Not a valid userID for an Attendee or Speaker");
        }

    }

    private void displayEventList(){
        for(String event: eventPresenter.getEventList(attendeeController.getAttendingEvents()))
            System.out.println(event);
    }

    /** Displays to the user a list of events which they are currently attending. The user is given one chance to
     * un-enroll in one of these events.
     *
     */
    private void runEnrollmentCancellation(){

        Scanner input = new Scanner(System.in);
        String eventNum;
        ArrayList<String> eventStrings = eventPresenter.getEventList(attendeeController.getAttendingEvents());
        ArrayList<String> eventNames = eventPresenter.getEventNames(attendeeController.getAttendingEvents());
        int max = eventStrings.size();

        System.out.println("---Options to Cancel Enrollment---\n");

        for(String event: eventStrings){
            System.out.println(event);
        }

        System.out.println("Enter the number of the event which you want to cancel for and click Enter");
        eventNum = input.nextLine();

        if(isValidInput(eventNum, max) && attendeeController.cancelEnrollment(eventNames.get(Integer.parseInt(eventNum) - 1))){
            System.out.println("Cancellation successful");
        }
        else {
            System.out.println("Cancellation failed");
        }
    }

    /** Gives a user a list of events in the console, and gives them one opportunity to sign-up for an event.
     *
     */
    private void runSignUp() {

        Scanner input = new Scanner(System.in);
        String eventNum;
        ArrayList<String> eventStrings = eventPresenter.getEventList(attendeeController.displayEvents());
        ArrayList<String> eventNames = eventPresenter.getEventNames(attendeeController.displayEvents());
        int max = eventStrings.size();

        System.out.println("---Sign Up for an Event---\n");

        for(String event: eventStrings) {
            System.out.println(event);
        }

        System.out.println("Enter the number of the event which you want to sign up for and click Enter");
        eventNum = input.nextLine();

        if(isValidInput(eventNum, max) && attendeeController.signUp(eventNames.get(Integer.parseInt(eventNum) - 1))){
            System.out.println("Sign-Up successful");
        }
        else {
            System.out.println("Sign-Up failed");
        }
    }

    private boolean isValidInput(String input, int max) {
        int a;

        try {
            a = Integer.parseInt(input);
            return a >= 1 && a <= max;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    private void addFriend(){
        Scanner input = new Scanner(System.in);
        System.out.println("Type the ID of the attendee of whom which you'd like to add as a friend");
        try{
            if(attendeeController.addUserToFriendList(input.nextLine())){
                System.out.println("Successfully added to friend list");
            }
            else{
                System.out.println("That user is already in your friend list!");
            }
        }catch (UserNotFoundException e){
            System.out.println("There is no attendee with that ID");
        }
    }



}
