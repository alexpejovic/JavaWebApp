package modules.presenters;

import modules.views.IMessageView;

import java.util.ArrayList;

/**
 * Presenter class for the Message actions that every user has access to
 */
public class MessagePresenter {
    IMessageView iMessageView;

    /**
     * Constructor for MessagePresenter
     * @param iMessageView the view for the page where the messages are displayed to user
     */
    public MessagePresenter(IMessageView iMessageView){
        this.iMessageView = iMessageView;
    }


    /**
     * Sends list of message info to homepage to display
     * @param formattedMessages a list of json strings representing all messages between two users
     */
    public void seeMessages(ArrayList<String> formattedMessages){
        iMessageView.displayMessages(formattedMessages);
    }


    /**
     * Displays a message that a specified event was not in the system
     */
    public void noMessagesFound(){
        iMessageView.displayMessage("Sorry, no messages exist between you two");
    }

    /**
     * Displays a message that a specified message was not in the system
     */
    public void messageDoesNotExist(){ iMessageView.displayMessage("Sorry, that message does not exist");}
}
