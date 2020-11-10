package Modules.Controllers;

import Modules.Entities.Attendee;
import Modules.Entities.Event;
import Modules.Entities.User;
import Modules.Gateways.UserGateway;
import Modules.UseCases.AttendeeManager;
import Modules.UseCases.EventManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AttendeeController {
    private AttendeeManager attendeeManager;
    private EventManager eventManager;
    private MessageController messageController;
    private Attendee attendee;
    public AttendeeController(AttendeeManager attendeeManager, EventManager eventManager,Attendee attendee){
        this.attendeeManager = attendeeManager;
        this.eventManager = eventManager;
        this.attendee = attendee;
        this.messageController = new MessageController();
    }
    public void addAttendees(ArrayList<User> users){
        for(User user : users){
            String id = user.getID();
                    if (id.startsWith("a")){
                        attendeeManager.addAttendee((Attendee) user);
                    }
        }
    }
    public void run(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<Event> events = eventManager.getEventList();

        System.out.println("Type \"message\" to send a message, \"show events\" to see the list of events, or " +
                "\"sign up\" to sign up for an event.");
        try{
            String input = br.readLine();
            while(input.equals("message")){
                //messageController.run()
            }
            while (input.equals("show events")){
                ArrayList<String> displayEvents = new ArrayList<String>();
                for(Event event: events){
                    String element = event.getName() + ": " + event.getStartTime() + "-" +event.getEndTime() +
                            " ID: " + event.getID();
                    displayEvents.add(element);
                }
                System.out.println(displayEvents);
            }
            while (input.equals("sign up")){
                System.out.println("Please type the ID of the event you would like to attend");
            }
            while (input.startsWith("E")){
                Boolean eventExists = false;
                for (Event event: events){
                    if (event.getID().equals(input)){
                        attendeeManager.addEventToAttendee(attendee, event, eventManager);
                        //eventManager.addAttendee or smth
                        eventExists = true;
                    }
                }
                if (eventExists == false){
                    System.out.println("Sorry, looks like there's no event with that ID. Please try enter a new" +
                            "event ID.");
                }
            }
        }
        catch (IOException e){
            System.out.println("uh oh");
        }
    }


}
