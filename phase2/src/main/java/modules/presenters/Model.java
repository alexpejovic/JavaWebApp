package modules.presenters;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Model {
    private String status;
    private String statusMessage;
    private String userType;
    private JSONArray participating = new JSONArray();
    private JSONArray notParticipating = new JSONArray();
    private JSONArray messages = new JSONArray();
    private JSONArray friends = new JSONArray();

    public void setErrorStatus(boolean status, String statusMessage) {
        this.status = status ? "ok" : "error";
        this.statusMessage = statusMessage;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void addMessages(ArrayList<HashMap<String,String>> messages) {
        for (HashMap<String, String> message : messages) {
            this.messages.add(makeMessage(message));
        }
    }

    public void addParticipatingEvents(ArrayList<HashMap<String, String>> events) {
        for (HashMap<String, String> event : events) {
            participating.add(makeEvent(event));
        }
    }

    public void addNotParticipatingEvents(ArrayList<HashMap<String, String>> events) {
        for (HashMap<String, String> event : events) {
            notParticipating.add(makeEvent(event));
        }
    }

    public void addFriends(ArrayList<HashMap<String, String>> friends) {
        for (HashMap<String, String> friend : friends) {
            JSONObject newFriend = new JSONObject();
//            friends.add(friendID);
            newFriend.put("ID", friend.get("ID"));
            newFriend.put("name", friend.get("name"));
            this.friends.add(newFriend);
        }
    }

    /**signin
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

    public void clear(boolean all) {
        status = null;
        statusMessage = null;
        participating = new JSONArray();
        notParticipating = new JSONArray();
        messages = new JSONArray();
        friends = new JSONArray();
        if (all) {
            userType = null;
        }
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("status", status);
        json.put("statusMessage", statusMessage);
        json.put("userType", userType);
        json.put("participating", participating);
        json.put("notParticipating", notParticipating);
        json.put("messages", messages);
        json.put("friends", friends);
        return json;
    }
}
