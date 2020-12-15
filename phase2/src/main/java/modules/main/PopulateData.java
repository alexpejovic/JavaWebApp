package modules.main;


import modules.entities.*;
import modules.gateways.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * class to populate sample data into database file conference.db under resources/web/database
 * ONLY FOR TESTING PURPOSES NOT PART OF PROGRAM
 */
//please run this once before pushing to make sure the data in res matches the sample data
// * if you want to test out a empty conference with no data make conference.db a empty file
public class PopulateData {
    private static EventGateway eventGateway = new EventGateway();
    private static MessageGateway messageGateway= new MessageGateway();
    private static RoomGateway roomGateway = new RoomGateway();
    private static UserGateway userGateway = new UserGateway();

    public static void main(String[] args) {
        eventGateway.writeData(getEvents());
        roomGateway.writeData(getRooms());
        userGateway.writeData(getUsers());
        messageGateway.writeData(getMessages());
    }

    private static ArrayList<Event> getEvents(){
        ArrayList<Event> events = new ArrayList<>();

        //events in the sample data
        Event event0 = new Event("r0",
                LocalDateTime.of(2020,11,20,9,0), //start time
                LocalDateTime.of(2020,11,20,10,0), // end time
                "e0",2);
        event0.setName("Welcome event");
        event0.scheduleSpeaker("s0");
        event0.addAttendee("a1");
        events.add(event0);

        Event event1 = new Event("r0",
                LocalDateTime.of(2020,11,20,16,0), //start time
                LocalDateTime.of(2020,11,20,17,0), // end time
                "e1",2);
        event1.setName("Wrap up");
        event1.scheduleSpeaker("s0");
        events.add(event1);

        Event event2 = new Event("r1",
                LocalDateTime.of(2020,11,20,11,0), //start time
                LocalDateTime.of(2020,11,20,12,0), // end time
                "e2",2);
        event2.setName("Therapy dog");
        event2.scheduleSpeaker("s2");
        event2.addAttendee("o0");
        events.add(event2);

        Event event3 = new Event("r1",
                LocalDateTime.of(2020,11,20,14,0), //start time
                LocalDateTime.of(2020,11,20,15,0), // end time
                "e3",2);
        event3.setName("Pet Sparky");
        event3.scheduleSpeaker("s2");
        event3.addAttendee("a1");
        events.add(event3);

        Event event4 = new Event("r1",
                LocalDateTime.of(2020,11,20,16,0), //start time
                LocalDateTime.of(2020,11,20,17,0), // end time
                "e4",2);
        event4.setName("how to convince your owner to go on a walk");
        event4.scheduleSpeaker("s2");
        event4.addAttendee("a1");
        event4.addAttendee("a0");
        events.add(event4);

        Event event5 = new Event("r2",
                LocalDateTime.of(2020,11,20,16,0), //start time
                LocalDateTime.of(2020,11,20,17,0), // end time
                "e5",2);
        event5.setName("top 10 reasons why vets should be illegal");
        event5.scheduleSpeaker("s1");
        event5.addAttendee("o0");
        events.add(event5);

        Event event6 = new Event("r2",
                LocalDateTime.of(2020,11,21,16,0), //start time
                LocalDateTime.of(2020,11,21,17,0), // end time
                "e6",2);
        event6.setName("Afterparty");
        events.add(event6);

        Event event7 = new Event("r3",
                LocalDateTime.of(2020,11,19,2,0), //start time
                LocalDateTime.of(2020,11,21,17,0), // end time
                "e7",10);
        event7.setName("Very long event");
        events.add(event7);

        return events;

    }

    private static ArrayList<Room> getRooms(){
        ArrayList<Room> rooms = new ArrayList<>();

        Room room0 = new Room("r0",2);
        room0.addEvent("e0");
        room0.addEvent("e1");
        rooms.add(room0);

        Room room1 = new Room("r1",2);
        room1.addEvent("e2");
        room1.addEvent("e3");
        room1.addEvent("e4");
        rooms.add(room1);

        Room room2 = new Room("r2",2);
        room2.addEvent("e5");
        room2.addEvent("e6");
        rooms.add(room2);

        Room room3 = new Room("r3",20);
        room3.addEvent("e7");
        rooms.add(room3);

        return rooms;
    }

    private static ArrayList<User> getUsers(){
        ArrayList<User> users = new ArrayList<>();

        //speakers
        Speaker speaker0 = new Speaker("steve","p1","s0");
        speaker0.addEvent("e0");
        speaker0.addEvent("e1");
        users.add(speaker0);

        Speaker speaker1 = new Speaker("snowball","meow","s1");
        speaker1.addEvent("e5");
        users.add(speaker1);

        Speaker speaker2 = new Speaker("sparky","bark","s2");
        speaker2.addEvent("e2");
        speaker2.addEvent("e3");
        speaker2.addEvent("e4");
        users.add(speaker2);

        // organizers
        Organizer organizer0 = new Organizer("spot","123","o0");
        organizer0.addEvent("e2");
        organizer0.addEvent("e5");
        organizer0.addToFriendList("s0");
        organizer0.addToFriendList("s1");
        organizer0.addToFriendList("s2");
        organizer0.addToFriendList("a0");
        organizer0.addToFriendList("a2");
        users.add(organizer0);

        Organizer organizer1 = new Organizer("org","password","o1");
        organizer1.addToFriendList("s0");
        organizer1.addToFriendList("a1");
        users.add(organizer1);

        // attendees
        Attendee attendee0 = new Attendee("mochi", "woof", "a0");
        attendee0.addEvent("e4");
        attendee0.addToFriendList("s0");
        attendee0.addToFriendList("s2");
        attendee0.addToFriendList("a2");
        attendee0.addToFriendList("o0");
        users.add(attendee0);

        Attendee attendee1 = new Attendee("lemon", "chirp", "a1");
        attendee1.addEvent("e0");
        attendee1.addEvent("e3");
        attendee1.addEvent("e4");
        attendee1.addToFriendList("a0");
        attendee1.addToFriendList("a2");
        attendee1.addToFriendList("o1");
        users.add(attendee1);

        Attendee attendee2 = new Attendee("kiki", "meow", "a2");
        attendee2.addToFriendList("s1");
        users.add(attendee2);

        Attendee attendee3 = new Attendee("vip", "pass", "a3");
        attendee2.setAsVIP();
        users.add(attendee3);


        return users;

    }

    private static ArrayList<Message> getMessages(){
        ArrayList<Message> messages = new ArrayList<>();

        Message message0 = new Message("woof","a0","o0");
        messages.add(message0);
        Message message1 = new Message("wuff","o0","a0");
        messages.add(message1);
        Message message2 = new Message("chirp","a1","a2");
        messages.add(message2);
        Message message3 = new Message("meow","a2","s1");
        messages.add(message3);
        Message message4 = new Message("bark", "s2","a1" );
        messages.add(message4);

        return messages;

    }




}
