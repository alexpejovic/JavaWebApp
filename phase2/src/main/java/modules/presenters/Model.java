package modules.main;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Model {
    private String status;
    private String statusMessage;
    private JSONArray allEvents = new JSONArray();
    private JSONArray attending = new JSONArray();
    private JSONArray speaking = new JSONArray();
    private JSONArray messages = new JSONArray();

    public void setStatus(String newStatus) {
        status = newStatus;
    }

    public void setStatusMessage(String newMessage) {
        statusMessage = newMessage;
//        ArrayList<String> evt = new ArrayList<>();
//        evt.add("abcd");
//        evt.add("name");
//        evt.add("12:35");
//        evt.add("13:45");
//        evt.add("d10");
//        evt.add("100");
//        evt.add("23");
//        addEvent(evt);
    }

    public void clearStatus() {
        status = null;
    }

    public void clearStatusMessage() {
        statusMessage = null;
    }

    public void addEvents(ArrayList<ArrayList<String>> events) {
        for (ArrayList<String> event : events) {
            allEvents.add(makeEvent(event));
        }
    }

    /**
     * @param event an arraylist containing information about an event formatted like:
     *        eventID, name, startTime, endTime, roomNumber, capacity, remainingSeats
     * @return a JSONObject containing the above keys and their corresponding values
     */
    private JSONObject makeEvent(ArrayList<String> event) {
        JSONObject newEvent = new JSONObject();
        newEvent.put("eventID", event.get(0));
        newEvent.put("name", event.get(1));
        newEvent.put("startTime", event.get(2));
        newEvent.put("endTime", event.get(3));
        newEvent.put("roomNumber", event.get(4));
        newEvent.put("capacity", event.get(5));
        newEvent.put("remainingSeats", event.get(6));
        return newEvent;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("status", status);
        json.put("statusMessage", statusMessage);
        json.put("allEvents", allEvents);
        json.put("attending", attending);
        json.put("speaking", speaking);
        json.put("messages", messages);
        return json;
    }
}
