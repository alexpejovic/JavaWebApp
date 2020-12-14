package modules.controllers;

public interface Attendable {
    /*
    An interface with all the common methods between attendees and organizers
     */

    void attendEvent(String eventId);

    void cancelEnrollment(String eventId);

    void sendMessage(String receiverId, String message);



}
