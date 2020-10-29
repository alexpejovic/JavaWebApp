package Modules.Entities;

import java.util.ArrayList;

public class Attendee extends User {
    // ids of events Attendee is attending
    private ArrayList<String> eventsList;

    public Attendee(String username, String password){
        super(username, password);
        this.eventsList = new ArrayList<>();
    }

    public void addEvent(String id){
        eventsList.add(id);
    }
    public void removeEvent(String id){
        eventsList.remove(id);
    }

    public ArrayList<String> getEventsList() {
        ArrayList<String> copy = new ArrayList<>(eventsList.size());
        for(int i = 0; i < eventsList.size(); i++){
            copy.set(i, eventsList.get(i));
        }
        return copy;
    }
    public boolean hasNoEvents(){
        if (eventsList.size() == 0){
            return true;
        }
        return false;
    }
    public boolean alreadyAttendingEvent(String id){
        if (eventsList.contains(id) == true){
            return true;
        }
        return false;
    }
}
