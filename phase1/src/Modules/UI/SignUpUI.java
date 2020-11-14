package Modules.UI;

import Modules.Controllers.AttendeeController;
import Modules.Entities.Event;
import Modules.Presenters.SignUpPresenter;

import java.util.ArrayList;
import java.util.Scanner;

/** UI for an attendee who wishes to sign-up to an event. UI can be excecuted using run() method.
 *
 */
public class SignUpUI {

    private SignUpPresenter signUpPresenter;
    private AttendeeController attendeeController;

    /** Constructor.
     *
     * @param signUpPresenter Presenter needed to format event list
     * @param attendeeController Controller corresponding to correct user
     */
    SignUpUI(SignUpPresenter signUpPresenter, AttendeeController attendeeController){
        this.attendeeController = attendeeController;
        this.signUpPresenter = signUpPresenter;
    }


    /** Gives a user a list of events in the console, and gives them one opportunity to sign-up for an event.
     *
     * @return True if sign-up is successful, false otherwise
     */
    public boolean run() {

        Scanner input = new Scanner(System.in);
        String eventNum;
        ArrayList<Event> events = attendeeController.displayEvents();
        ArrayList<String> eventStrings = signUpPresenter.getEventList(events);
        int max = events.size();

        System.out.println("---Sign Up for an Event---\n");

        for(String event: eventStrings) {
            System.out.println(event);
        }

        System.out.println("Enter the Number of the event which you want to sign up for and click Enter");
        eventNum = input.nextLine();

        if(isValidInput(eventNum, max) && attendeeController.signUp(events.get(Integer.parseInt(eventNum) - 1).getID())){
            System.out.println("Sign-Up successful");
            return true;
        }
        else {
            System.out.println("Sign-Up failed");
            return false;
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
}
