package modules.controllers;

import modules.usecases.EventManager;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Tests for ScheduleCreator
 */
public class ScheduleCreatorTest {

    // informal test - check schedule html file in resources/web/endpoints
    public static void main(String[] args) {
        EventManager eventManager = new EventManager(new ArrayList<>());
        eventManager.createEvent("r0", LocalDateTime.of(2020,12,25,5,0),
                LocalDateTime.of(2020,12,25,12,0),"e0",5);
        eventManager.renameEvent("e0","Christmas");
        eventManager.addSpeakerToEvent("s1","e0");

        eventManager.createEvent("r6", LocalDateTime.of(2020,12,25,6,0),
                LocalDateTime.of(2020,12,25,22,0),"e4",2);
        eventManager.renameEvent("e4","Open Presents");
        eventManager.addAttendee("e4","a1");

        eventManager.createEvent("r2", LocalDateTime.of(2020,12,24,6,0),
                LocalDateTime.of(2020,12,24,7,0),"e2",2);
        eventManager.renameEvent("e2","Christmas eve");
        eventManager.addAttendee("e2","a4");
        eventManager.addAttendee("e2","a3");
        eventManager.addAttendee("e2","a5");

        eventManager.createEvent("r3", LocalDateTime.of(2020,12,26,6,0),
                LocalDateTime.of(2020,12,26,7,0),"e1",2);
        eventManager.renameEvent("e1","Boxing Day");

        ScheduleCreator scheduleCreator  = new ScheduleCreator(eventManager);
        scheduleCreator.createSchedule(); // now the file should have the correct info
    }

}
