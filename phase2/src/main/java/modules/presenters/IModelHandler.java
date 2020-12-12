package modules.presenters;

import modules.entities.Message;

import java.util.ArrayList;
import java.util.HashMap;

public interface IModelHandler {
    public void setMessages(ArrayList<HashMap<String, String>> messages);

    public void setEvents(ArrayList<HashMap<String, String>> events);

    public Model getModel();
}
