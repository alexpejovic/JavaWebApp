package modules.presenters;

import java.util.ArrayList;
import java.util.HashMap;

public interface IUserModelHandler {
    void setAttendingEvents(ArrayList<HashMap<String, String>> attendingEvents);

    void setNotAttendingEvents(ArrayList<HashMap<String, String>> notAttendingEvents);
}
