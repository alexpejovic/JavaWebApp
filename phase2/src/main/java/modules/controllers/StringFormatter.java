package modules.controllers;

import modules.usecases.EventManager;
import modules.usecases.MessageManager;

import java.util.ArrayList;

/**
 * A controller class that uses use case methods to format entity infromation into json strings to pass to the UI
 */
public class StringFormatter {

    private EventManager eventManager;
    private MessageManager messageManager;

    /**
     * Constructor for StringFormatter
     * @param eventManager the eventManager for this Conference
     * @param messageManager the messageManager for this Conference
     */
    public StringFormatter(EventManager eventManager, MessageManager messageManager){
        this.eventManager = eventManager;
        this.messageManager = messageManager;
    }

    /**
     * Formats events specified by given eventIDs to JSON strings
     * @param eventIDs a list of eventIDs for the events to be formatted
     * @return a list of JSON strings representing the specified events in this format:
     *          "{'eventID': '...', 'name': '...', 'startTime': 'yyyy-MM-ddTHH:mm',
     *          'endTime': 'yyyy-MM-ddTHH:mm', 'roomNumber': '...', 'capacity': ..., 'remainingSeats': ...}"
     */
    public ArrayList<String> eventsToJSONString(ArrayList<String> eventIDs){
        ArrayList<String> eventStrings = new ArrayList<>();
        for (String eventID: eventIDs){
            String eventName = eventManager.getName(eventID);
            if (eventName== null){
                eventName = "unnamed event";
            }
            String startTime = eventManager.startTimeOfEvent(eventID).toString();
            String endTime = eventManager.endTimeOfEvent(eventID).toString();
            String roomNumber = eventManager.getRoomNumberOfEvent(eventID);
            String capacity = String.valueOf(eventManager.getCapacity(eventID));
            String remainingSeats = String.valueOf(eventManager.getRemainingSeats(eventID));
            String eventString =  "{'eventID': '" + eventID
                                 + "', 'name': '" + eventName +
                              "', 'startTime': '" + startTime +
                                "', 'endTime': '" + endTime +
                             "', 'roomNumber': '" + roomNumber +
                               "', 'capacity': " + capacity+
                         ", 'remainingSeats': "  + remainingSeats + "}";
            eventStrings.add(eventString);
        }
        return eventStrings;
    }

    /**
     * Formats messages specified by given messageIDs to JSON strings
     * @param messageIDs a list of messageIDs for the events to be formatted
     * @return a list of JSON strings representing the specified messages in this format:
     *          "{'messageID': '...', 'senderID': '...', 'receiverID': '...',
     *            'content': '...', 'time': 'yyyy-MM-ddTHH:mm', 'hasBeenRead': ...}"
     */
    public ArrayList<String> messageToJSONString(ArrayList<String> messageIDs){
        ArrayList<String> messageStrings = new ArrayList<>();
        for (String messageID: messageIDs){
            String messageContent = messageManager.getContentOfMessage(messageID);
            String senderID = messageManager.getSenderIDOfMessage(messageID);
            String receiverID = messageManager.getReceiverIDOfMessage(messageID);
            String time = messageManager.getTimeOfMessage(messageID).toString();
            String hasBeenRead = String.valueOf(messageManager.getHasMessageBeenRead(messageID));
            String messageString =  "{'messageID': '" + messageID
                                 + "', 'senderID': '" + senderID +
                                 "', 'receiverID': '" + receiverID +
                                    "', 'content': '" + messageContent +
                                       "', 'time': "  + time +
                                "', 'hasBeenRead': "  + hasBeenRead + "}";
            messageStrings.add(messageString);
        }
        return messageStrings;
    }

}
