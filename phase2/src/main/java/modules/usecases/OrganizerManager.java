package modules.usecases;

import java.util.ArrayList;

import modules.entities.*;
import modules.exceptions.*;

/**
 * A use case class that performs actions on Organizers and gives important information about all Organizers
 */
public class OrganizerManager extends UserManager {
    /**
     * List of all Organizers in the conference
     */
    ArrayList<Organizer> listOfOrganizers;

    /**
     * Constructor for this OrganizerManager
     * @param organizers a Arraylist of all organizers in this conference
     */
    public OrganizerManager(ArrayList<Organizer> organizers) {
        this.listOfOrganizers = new ArrayList<>();
        for (Organizer organizer : organizers) {
            this.listOfOrganizers.add(organizer);
        }
    }

    /**
     * Returns if there is a Organizer with a specific username
     *
     * @param username the username entered by user
     * @return true if there exists a organizer account with this username, false otherwise
     */
    @Override
    public boolean isUser(String username) {
        for (Organizer organizer : listOfOrganizers) {
            if (organizer.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns whether or not there is a specific organizer account with the given username and password
     *
     * @param username the username of the account whose password we want to check
     * @param password the password entered that we want to compare to password on file
     * @return true if entered password matches the password on file, false otherwise
     */
    @Override
    public boolean validatePassword(String username, String password) {
        for (Organizer organizer : listOfOrganizers) {
            if (organizer.getUsername().equals(username)) {
                return organizer.getPassword().equals(password);
            }
        }
        return false;
    }

    /**
     * Returns the total number of Organizers in this conference
     * @return the number Organizers entities stored in this OrganizerManager
     */
    public int getNumberOfOrganizers() {
        return listOfOrganizers.size();
    }

    /**
     * Returns a shallow copy of all Organizers in this OrganizerManager
     * @return a shallow copy of all Organizers in this OrganizerManager
     */
    public ArrayList<Organizer> getListOfOrganizers() {
        ArrayList<Organizer> copy = new ArrayList<>();
        if (!listOfOrganizers.isEmpty()){
            copy.addAll(listOfOrganizers);
        }
        return copy;
    }

    /**
     * Returns the specific Organizer's userID with username
     *
     * @param username the username we want to check
     * @return the userID of the specific Organizer entity that has the given username
     * @throws UserNotFoundException if there is no organizer with UserID in this conference
     */
    @Override
    public String getUserID(String username) {
        for (Organizer organizer : listOfOrganizers) {
            if (organizer.getUsername().equals(username)) {
                return organizer.getID();
            }
        }
        throw new UserNotFoundException();
    }

    /**
     * adds newly created organizer account into the system
     *
     * @param newOrganizer the organizer account added into the system
     */
    public void addOrganizer(Organizer newOrganizer) {
        listOfOrganizers.add(newOrganizer);
    }

    /**
     * Creates an Organizer account and adds it to the list of all Organizers
     * if there is not already an Organizer with the same username or userID
     *
     * @param userName the Organizer's username
     * @param password the Organizer's password
     * @param userId   the Organizer's userId
     * @throws NonUniqueIdException if there is already a user with that ID in listOfOrganizers
     */
    public void createOrganizerAccount(String userName, String password, String userId) {
        // Checking for a unique userID
        if(this.isUniqueID(this.listOfOrganizers,userId)) {
            listOfOrganizers.add(new Organizer(userName, password, userId));
        }
    }

    /**
     * Private helper method that finds the Organizer object in the system with the given id
     *
     * @param organizerId the Organizer's id
     * @return the Organizer object that stores the corresponding id
     */
    private Organizer findOrganizer(String organizerId) {
        for (Organizer currOrganizer : listOfOrganizers) {
            if (organizerId.equals(currOrganizer.getID())) {
                return currOrganizer;
            }
        }
        throw new UserNotFoundException();
    }


    /**
     * Updates the organizers list of organized events
     *
     * @param organizerId the organizer's id
     * @param eventId     the id of the Event being added to the organizer's list of organized events
     */
    public void addToOrganizedEvents(String organizerId, String eventId) {
        Organizer organizer = findOrganizer(organizerId);
        organizer.addManagedEvent(eventId);
    }

    /**
     * Removes the given event from the list of organized events for all organizers in the system
     * @param eventId the id of the event being removed
     */
    public void removeFromOrganizedEvents(String eventId){
        for (Organizer organizer: listOfOrganizers){
            organizer.removeManagedEvent(eventId);
        }
    }

}