package Modules.Entities;

import java.util.ArrayList;

//TODO: change eventIDs from <String> to <UUID>
public class Organizer extends User {
    //Store list of ids for managed Events
    private ArrayList<String> managedEvents;

    public Organizer(String username, String pwd) {
        super(username, pwd);
        this.managedEvents = new ArrayList<>();
    }



    //managedEvents getter
    public ArrayList<String> getManagedEvents() {
        ArrayList<String> copy = new ArrayList<>();

        for (int i = 0; i < this.managedEvents.size(); i++) {
            copy.set(i, this.managedEvents.get(i));
        }

        return copy;
    }

    //setters
    public void addManagedEvent(String eventID) {
        this.managedEvents.add(eventID);
    }

    public void removeManagedEvent(String eventID) {
        this.managedEvents.remove(eventID);
    }

    public boolean managedEventsIsEmpty() {
        int size = this.managedEvents.size();
        return size == 0;
    }
}
