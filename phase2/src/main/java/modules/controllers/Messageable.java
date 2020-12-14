package modules.controllers;

/*
An interface with all common methods for all user controllers
 */
public interface Messageable {

    public void sendMessage(String recipientId, String message);

    public void updateModel();

}
