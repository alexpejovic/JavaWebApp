package Modules.Entities;

import java.util.ArrayList;

public class Organizer extends User {
    //Store list of ids for managed Events
    private ArrayList<String> managedEvts;

    public Organizer(String username, String pwd, ArrayList<String> evtIDs, ArrayList<String> managedEvtIDs) {
        User(username, pwd, evtIDs);
        this.managedEvts = managedEvtIDs;
    }

    //managedEvts getter
    public ArrayList<String> getManagedEvts() {
        return this.managedEvts;
    }

    //manangedEvts setter
    public void setManagedEvts(ArrayList<String> newManagedEvts) {
        this.managedEvts = newManagedEvts;
    }
}
