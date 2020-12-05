package modules.testviews;


import modules.views.IAttendeeHomePageView;

import java.util.ArrayList;

/**
 * A class just for testing purposes that implements IAttendeeHomepage Interface
 * to test the AttendeeController and AttendeeOptionsPresenter
 * with the strings that should be passes to UI in variable
 * returnValue if it is a string
 * returnValueList if it is an ArrayList of Strings
 */
public class TestAttendeeHomepageView implements IAttendeeHomePageView {
    public String returnValue;
    public ArrayList<String> returnValueList;

    public void displayMessage(String message){
        this.returnValue = message;
    }

    public void displayAllEvents(ArrayList<String> events){
        this.returnValueList = events;
    }

    public void displayAttendingEvents(ArrayList<String> events){
        this.returnValueList = events;
    }
    public void displayMessages(ArrayList<String> messages){
        this.returnValueList = messages;
    }


}
