package Modules.Entities;

import java.util.ArrayList;

public class Attendee extends User {
    // ids of events Attendee is attending
    private ArrayList<String> eventsList;
    private boolean noEvents;

    public Attendee(String username, String password){
        super(username, password);
        this.eventsList = new ArrayList<>();
        noEvents = true;
    }

    public void addEvent(String id){
        eventsList.add(id);
        noEvents = false;
    }
    public void removeEvent(String id){
        eventsList.remove(id);
        if (eventsList.size() == 0){
            noEvents = true;
        }
    }

    public ArrayList<String> getEventsList() {
        ArrayList<String> copy = new ArrayList<>(eventsList.size());
        for(int i = 0; i < eventsList.size(); i++){
            copy.set(i, eventsList.get(i));
        }
        return copy;
    }
}
