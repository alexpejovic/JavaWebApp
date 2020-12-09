package modules.controllers;

import modules.entities.Event;
import modules.entities.Message;
import modules.entities.Room;
import modules.entities.User;
import modules.gateways.EventGateway;
import modules.gateways.MessageGateway;
import modules.gateways.RoomGateway;
import modules.gateways.UserGateway;

import java.util.ArrayList;

/**
 * A controller class that other controllers use to use the gateway classes to update the database
 */
public class UpdateInfo {
    private MessageGateway messageGateway;
    private EventGateway eventGateway;
    private UserGateway userGateway;
    private RoomGateway roomGateway;

    /**
     * Constructor for UpdateInfo
     * @param messageGateway the gateway for message information
     * @param eventGateway the gateway for event information
     * @param userGateway the gateway for user information
     * @param roomGateway the gateway for room information
     */
    public UpdateInfo(MessageGateway messageGateway, EventGateway eventGateway,
                      UserGateway userGateway, RoomGateway roomGateway){
        this.messageGateway = messageGateway;
        this.eventGateway = eventGateway;
        this.userGateway = userGateway;
        this.roomGateway = roomGateway;
    }

    /**
     * Uses a gateway class to update the information for a Message
     * @param message the message to update
     */
    public void updateMessage(Message message){
        ArrayList<Message> messageUpdate = new ArrayList();
        messageUpdate.add(message);
        messageGateway.writeData(messageUpdate);
    }

    /**
     * Uses a gateway class to update the information for a Event
     * @param event the event to update
     */
    public void updateEvent(Event event){
        ArrayList<Event> eventUpdate = new ArrayList();
        eventUpdate.add(event);
        eventGateway.writeData(eventUpdate);
    }

    /**
     * Uses a gateway class to update the information for an User
     * @param user the user to update
     */
    public void updateUser(User user){
        ArrayList<User> userUpdate = new ArrayList<>();
        userUpdate.add(user);
        userGateway.writeData(userUpdate);
    }

    /**
     * Uses a gateway class to update the information for an User
     * @param users the users to update
     */
    public void updateUser(ArrayList<User> users){
        userGateway.writeData(users);
    }

    /**
     * Uses a gateway class to update the information for a Room
     * @param room the room to update
     */
    public void updateRoom(Room room){
        ArrayList<Room> roomUpdate = new ArrayList<>();
        roomUpdate.add(room);
        roomGateway.writeData(roomUpdate);
    }


}
