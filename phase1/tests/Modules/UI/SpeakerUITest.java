package Modules.UI;

import Modules.Controllers.SpeakerController;
import Modules.Entities.Attendee;
import Modules.Entities.Event;
import Modules.Entities.Message;
import Modules.Entities.Speaker;
import Modules.Presenters.EventPresenter;
import Modules.Presenters.MessagePresenter;
import Modules.UseCases.AttendeeManager;
import Modules.UseCases.EventManager;
import Modules.UseCases.MessageManager;
import Modules.UseCases.SpeakerManager;
import org.junit.Test;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.*;


public class SpeakerUITest {

    // only for informal testing with keyboard inputs

    public static void main(String[] args) {
        EventPresenter eventPresenter = new EventPresenter();

        // setting up speakerController
        Event event0 = new Event("r0", LocalDateTime.of(2020,11,15,1,0),
                LocalDateTime.of(2020,11,15,2,0), "e0");

        ArrayList<Event> events = new ArrayList<>();
        events.add(event0);
        EventManager eventManager = new EventManager(events);

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

        SpeakerUI speakerUI = new SpeakerUI(speakerController,eventPresenter);


        System.out.println(speakerUI.run());

        MessagePresenter messagePresenter = new MessagePresenter();

        // testing that messages are sent to attendee0
        ArrayList<Message> msgs = messageManager.getUserMessages("a0");
        System.out.println("Messages for attendee 0 : ");
        for (String msg: messagePresenter.getMessageList(msgs)){
            System.out.println(msg);
        }

        // testing that messages are sent to attendee1
        ArrayList<Message> msgs2 = messageManager.getUserMessages("a1");
        System.out.println("Messages for attendee 1 : ");
        for (String msg: messagePresenter.getMessageList(msgs2)){
            System.out.println(msg);
        }

        // testing that messages are sent to attendee1
        ArrayList<Message> msgs3 = messageManager.getUserMessages("s0");
        System.out.println("Messages for speaker0 : ");
        for (String msg: messagePresenter.getMessageList(msgs3)){
            System.out.println(msg);
        }
    }




}
