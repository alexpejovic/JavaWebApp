package modules.main;

import org.json.simple.JSONObject;

public class Status {
    private String status;
    private String message;

    public void setStatus(String newStatus) {
        status = newStatus;
    }

    public void setMessage(String newMessage) {
        message = newMessage;
    }

    public void clearStatus() {
        status = null;
    }

    public void clearMessage() {
        message = null;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("status", status);
        json.put("message", message);
        return json;
    }
}
