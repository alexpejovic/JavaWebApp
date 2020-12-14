package modules.controllers;

public interface Attendable {
    /*
    An interface with all the common methods between attendees and organizers
     */

    boolean attendEvent(String eventId);

    boolean cancelEnrollment(String eventId);

    void sendMessage(String receiverId, String message);



}
