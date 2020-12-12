package modules.presenters;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Model {
    private String status;
    private String statusMessage;
    private JSONArray allEvents = new JSONArray();
    private JSONArray attending = new JSONArray();
    private JSONArray notAttending = new JSONArray();
    private JSONArray speaking = new JSONArray();
    private JSONArray messages = new JSONArray();

    public void setStatus(String newStatus) {
        status = newStatus;
    }

    public void setStatusMessage(String newMessage) {
        statusMessage = newMessage;
    }

    public void clearStatus() {
        status = null;
    }

    public void clearStatusMessage() {
        statusMessage = null;
    }

    public void addMessages(ArrayList<HashMap<String,String>> messages) {
        for (HashMap<String, String> message : messages) {
            this.messages.add(makeMessage(message));
        }
    }

    public void addEvents(ArrayList<HashMap<String, String>> events) {
        for (HashMap<String, String> event : events) {
            allEvents.add(makeEvent(event));
        }
    }

    public void addAttendingEvents(ArrayList<HashMap<String, String>> events) {
        for (HashMap<String, String> event : events) {
            attending.add(makeEvent(event));
        }
    }

    public void addNotAttendingEvents(ArrayList<HashMap<String, String>> events) {
        for (HashMap<String, String> event : events) {
            notAttending.add(makeEvent(event));
        }
    }

    /**
     * @param message an arraylist containing information about a message formatted like:
     *                messageID, senderID, receiverID, content, time, hasBeenRead, isArchived
     * @return a JSONObject containing the above keys and their corresponding values
     */
    private JSONObject makeMessage(HashMap<String, String> message) {
        String[] keys = {"messageID", "senderID", "receiverID", "content", "time", "hasBeenRead", "isArchived"};
        JSONObject newMessage = new JSONObject();
        for (String key : keys) {
            newMessage.put(key, message.get(key));
        }
        return newMessage;
    }

    /**
     * @param event an arraylist containing information about an event formatted like:
     *        eventID, name, startTime, endTime, roomNumber, capacity, remainingSeats
     * @return a JSONObject containing the above keys and their corresponding values
     */
    private JSONObject makeEvent(HashMap<String, String> event) {
        String[] keys = {"eventID", "name", "startTime", "endTime", "roomNumber", "capacity", "remainingSeats"};
        JSONObject newEvent = new JSONObject();
        for (String key : keys) {
            newEvent.put(key, event.get(key));
        }
        return newEvent;
    }

    public void clear() {
        allEvents = new JSONArray();
        attending = new JSONArray();
        notAttending = new JSONArray();
        speaking = new JSONArray();
        messages = new JSONArray();
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("status", status);
        json.put("statusMessage", statusMessage);
        json.put("allEvents", allEvents);
        json.put("attending", attending);
        json.put("notAttending", notAttending);
        json.put("speaking", speaking);
        json.put("messages", messages);
        return json;
    }
}
