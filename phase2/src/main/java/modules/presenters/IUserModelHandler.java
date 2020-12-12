package modules.presenters;

import java.util.ArrayList;
import java.util.HashMap;

public interface IUserModelHandler {
    public void setAttendingEvents(ArrayList<HashMap<String, String>> attendingEvents);

    public void setNotAttendingEvents(ArrayList<HashMap<String, String>> notAttendingEvents);
}
