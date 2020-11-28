package modules.ui;

import modules.controllers.SpeakerController;
import modules.entities.Attendee;
import modules.entities.Event;
import modules.entities.Speaker;
import modules.presenters.EventPresenter;
import modules.presenters.MessagePresenter;
import modules.usecases.AttendeeManager;
import modules.usecases.EventManager;
import modules.usecases.MessageManager;
import modules.usecases.SpeakerManager;


import java.time.LocalDateTime;
import java.util.ArrayList;


public class SpeakerUITest {

    // only for informal testing with keyboard inputs

    public static void main(String[] args) {


        // setting up speakerController
        Event event0 = new Event("r0", LocalDateTime.of(2020,11,15,1,0),
                LocalDateTime.of(2020,11,15,2,0), "e0",2);
        event0.addAttendee("a0");
        event0.addAttendee("a1");
        ArrayList<Event> events = new ArrayList<>();
        events.add(event0);
        EventManager eventManager = new EventManager(events);

        EventPresenter eventPresenter = new EventPresenter(eventManager);

        Attendee attendee0 = new Attendee("at0", "pass", "a0");
        Attendee attendee1 = new Attendee("at1", "pass", "a1");
        ArrayList<Attendee> attendees = new ArrayList<>();
        attendees.add(attendee0);
        attendee0.addEvent(event0.getID());
        attendees.add(attendee1);
        attendee1.addEvent(event0.getID());
        AttendeeManager attendeeManager = new AttendeeManager(attendees);

        Speaker speaker = new Speaker("username", "pass","s0");
        speaker.addEvent(event0.getID());
        ArrayList<Speaker> speakers = new ArrayList();
        speakers.add(speaker);
        SpeakerManager speakerManager = new SpeakerManager(speakers);


        MessageManager messageManager = new MessageManager(new ArrayList<>());

        SpeakerController speakerController = new SpeakerController("s0",eventManager,speakerManager,
                attendeeManager, messageManager);
        MessagePresenter messagePresenter = new MessagePresenter(messageManager);

        SpeakerUI speakerUI = new SpeakerUI(speakerController,eventPresenter, messagePresenter);


        System.out.println(speakerUI.run());



    }




}
