package Modules.UI;

import Modules.Controllers.AttendeeController;
import Modules.Presenters.EventPresenter;

import java.util.ArrayList;
import java.util.Scanner;

public class CancelEnrollmentUI {

    private AttendeeController attendeeController;
    private EventPresenter eventPresenter;

    CancelEnrollmentUI(AttendeeController attendeeController, EventPresenter eventPresenter){
        this.attendeeController = attendeeController;
        this.eventPresenter = eventPresenter;
    }


    public boolean run(){

        Scanner input = new Scanner(System.in);
        String eventNum;
        ArrayList<String> eventStrings = eventPresenter.getEventList(attendeeController.getAttendingEvents());
        ArrayList<String> eventNames = eventPresenter.getEventNames(attendeeController.getAttendingEvents());
        int max = eventStrings.size();

        System.out.println("---Options to Cancel Enrollment");

        for(String event: eventStrings){
            System.out.println(event);
        }

        System.out.println("Enter the number of the event which you want to cancel for and click Enter");
        eventNum = input.nextLine();

        if(isValidInput(eventNum, max) && attendeeController.cancelEnrollment(eventNames.get(Integer.parseInt(eventNum) - 1))){
            System.out.println("Cancellation successful");
            return true;
        }
        else {
            System.out.println("Cancellation failed");
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
