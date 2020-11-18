package Modules.Presenters;

import Modules.Entities.Message;

import java.util.ArrayList;

public class MessagePresenter {

    public ArrayList<String> getMessageList(ArrayList<Message> messages){

        ArrayList<String> conversation = new ArrayList<>();

        for(Message message: messages){

            conversation.add("Sender ID : " + message.getSenderID() +
            "\t\tReceiver ID: " + message.getReceiverID() +
            "\t\t\t" + message.getContent()) ;
        }

        return conversation;
    }
}
