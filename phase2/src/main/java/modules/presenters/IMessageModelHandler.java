package modules.presenters;

import modules.entities.Message;

import java.util.ArrayList;
import java.util.HashMap;

public interface IMessageModelHandler {
    public void setMessages(ArrayList<HashMap<String, String>> messages);
}
