package Modules.Entities;



import Modules.Exceptions.UserNotFoundException;
import org.junit.Test;


import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class EventTest {

    //Example Event Entity with only the start Time
    public Event testEventAtStartTime(){
        LocalDateTime startTime = LocalDateTime.of(2020, 11, 5, 9, 0);
        return new Event("21", startTime, "e123");
    }
    //Example Event Entity with Start and End Time
    public Event testEvent(LocalDateTime startTime, LocalDateTime endTime){
        return new Event("21", startTime, endTime, "e123",2);
    }

    //Example Start Time for Event
    public LocalDateTime startTime(){return LocalDateTime.of(2020, 11, 5, 9, 0);}

    //Example Start Time for Event
    public LocalDateTime endTime(){return LocalDateTime.of(2020, 11, 5, 10, 0);}


    @Test
    public void testGetters(){
        assertEquals(2, testEvent(startTime(), endTime()).getCapacity());
        assertEquals("21", testEvent(startTime(), endTime()).getRoomNumber());
        assertEquals(startTime(), testEvent(startTime(), endTime()).getStartTime());
        assertEquals(endTime(), testEvent(startTime(),endTime()).getEndTime());
        assertEquals(endTime(), testEventAtStartTime().getEndTime());
        assertEquals(2, testEvent(startTime(), endTime()).getAvailableSeats());
        assertEquals("e123", testEvent(startTime(),endTime()).getID());

    }

    @Test
    public void testSetters(){
        LocalDateTime startTime = LocalDateTime.of(2020,11,5,1,0);
        Event event = testEvent(startTime(),endTime());
        event.setStartTime(startTime);
        assertEquals(startTime, event.getStartTime());
        event.setName("Exam Study Tips");
        assertEquals("Exam Study Tips", event.getName());
        LocalDateTime endTime = LocalDateTime.of(2020, 11, 5, 3,0);
        event.setEndTime(endTime);
        assertEquals(endTime, event.getEndTime());

    }

    @Test
    public void testMethods(){
        Event event = testEvent(startTime(),endTime());
        assertFalse(event.hasSpeaker());
        event.scheduleSpeaker("John Doe");
        assertTrue(event.hasSpeaker());
    }

    @Test (expected = UserNotFoundException.class)
    public void testRemovingAttendeesNotAttendingEvent(){
        Event event = testEvent(startTime(),endTime());
        event.removeAttendee("John Doe");
    }

    @Test
    public void testAddingAttendeesAndRemoving(){
        Event event = testEvent(startTime(),endTime());
        event.addAttendee("John Doe");
        event.removeAttendee("John Doe");
    }

}