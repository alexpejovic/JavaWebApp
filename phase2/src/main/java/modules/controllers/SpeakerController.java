package modules.controllers;

import modules.entities.Attendee;
import modules.presenters.SpeakerOptionsPresenter;
import modules.usecases.AttendeeManager;
import modules.usecases.EventManager;
import modules.usecases.MessageManager;
import modules.usecases.SpeakerManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * SpeakerController class allows speakers to message attendees and also get a list of the events they are hosting
 */
public class SpeakerController implements Messageable {
    private final String speakerId;
    private final SpeakerManager speakerManager;
    private final EventManager eventManager;
    private final AttendeeManager attendeeManager;
    private final MessageManager messageManager;
    private final SpeakerOptionsPresenter speakerOptionsPresenter;
    private final UpdateInfo updateInfo;


    /**
     * A constructor for the speakerController class
     * @param speakerId the Id of a specific speaker
     * @param eventManager the event manager for the conference
     * @param speakerManager the speaker manager for the conference
     * @param attendeeManager the attendee manager for the conference
     * @param messageManager the message manager for the conference
     */
    public SpeakerController(String speakerId, EventManager eventManager, SpeakerManager speakerManager,
                             AttendeeManager attendeeManager, MessageManager messageManager,
                             SpeakerOptionsPresenter speakerOptionsPresenter, UpdateInfo updateInfo){
        this.speakerId = speakerId;
        this.eventManager = eventManager;
        this.speakerManager = speakerManager;
        this.attendeeManager = attendeeManager;
        this.messageManager = messageManager;
        this.speakerOptionsPresenter = speakerOptionsPresenter;
        this.updateInfo = updateInfo;
    }

    /**
     * Sends a message to all Attendees coming to an event this speaker is hosting
     * @param message the message to be sent
     */
    public void messageAll(String message, String eventID){
        if(getAttendees().isEmpty()){
            speakerOptionsPresenter.sendMessageAllSuccess(false);
        } else{
            for(String attendee: eventManager.getAttendeesOfEvent(eventID)){
                String messageID = messageManager.sendMessage(speakerId, attendee, message);
                updateInfo.updateMessage(messageManager.getMessage(messageID));   // updating message info in database
            }
            speakerOptionsPresenter.sendMessageAllSuccess(true);
        }
    }

    /**
     * Sends a message to an individual Attendee
     * @param recipientId the Id of the attendee the message is being sent to
     * @param message the message that is being sent
     */
    public void sendMessage(String recipientId, String message){
        boolean sent = false;
        for(Attendee attendee: this.getAttendees()){
            if(attendee.getID().equals(recipientId)){
                String messageID = messageManager.sendMessage(speakerId, recipientId, message);
                updateInfo.updateMessage(messageManager.getMessage(messageID));   // updating message info in database
                speakerOptionsPresenter.sendMessageSuccess(true);
                sent = true;
            }
        }
        if (!sent){
            speakerOptionsPresenter.sendMessageSuccess(false);
        }

    }

    /**
     * Return a list of Attendees that are attending at least 1 event this speaker is hosting
     * @return a list of Attendees that are attending at least 1 event this speaker is hosting
     */
    private ArrayList<Attendee> getAttendees(){
        ArrayList<Attendee> allAttendees = attendeeManager.getAttendeeList();
        ArrayList<String> myEventIds = speakerManager.getSpeaker(speakerId).getHostEvents();
        ArrayList<Attendee> myAttendees = new ArrayList<>();
        for(Attendee attendee: allAttendees){
            for(String eventId: myEventIds){
                if(attendee.alreadyAttendingEvent(eventId)){
                    myAttendees.add(attendee);
                    break;
                }
            }
        }
        return myAttendees;
    }


    public void updateModel() {
        updateModelMessages();
        updateModelEvents();
    }

    private void updateModelMessages() {
        ArrayList<HashMap<String, String>> messages = messageManager.getMessages(speakerId);
        speakerOptionsPresenter.setMessages(messages);
    }

    private void updateModelEvents() {
        ArrayList<HashMap<String, String>> speakingEvents = eventManager.getSpeakingEvents(speakerId, true);
        ArrayList<HashMap<String, String>> notSpeakingEvents = eventManager.getSpeakingEvents(speakerId, false);

        speakerOptionsPresenter.setAttendingEvents(speakingEvents);
        speakerOptionsPresenter.setNotAttendingEvents(notSpeakingEvents);
    }


}
