package modules.presenters;

import modules.usecases.MessageManager;

import java.util.ArrayList;

public class MessagePresenter {
    MessageManager messageManager;

    public MessagePresenter(MessageManager messageManager){
        this.messageManager = messageManager;
    }


    public ArrayList<String> getMessageList(ArrayList<String> messages){

        ArrayList<String> conversation = new ArrayList<>();

        for(String messageID: messages){

            conversation.add("Sender ID : " + messageManager.getSenderIDOfMessage(messageID) +
            "\t\tReceiver ID: " + messageManager.getReceiverIDOfMessage(messageID)  +
            "\t\t\t" + messageManager.getContentOfMessage(messageID));
        }

        return conversation;
    }
}
