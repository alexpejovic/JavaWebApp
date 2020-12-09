package modules.testviews;

import modules.views.IOrganizerHomePageView;

import java.util.ArrayList;

public class TestOrganizerHomepageView implements IOrganizerHomePageView {

    public String returnValue;
    public ArrayList<String> returnList;

    @Override
    public void displayMessage(String message) { this.returnValue = message; }

    @Override
    public void displayMessages(ArrayList<String> messages) {
        this.returnList = messages;
    }
}
