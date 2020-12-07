package modules.controllers;

import modules.usecases.EventManager;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A class that creates a html schedule of all of the events in this conference
 * organized by date
 */
public class ScheduleCreator {
    private EventManager eventManager;

    /**
     * Constructor for ScheduleCreator
     * @param eventManager the eventManager for this conference
     */
    public ScheduleCreator(EventManager eventManager){
        this.eventManager = eventManager;
    }

    /**
     * Writes to a html file with a formatted schedule of all events in the conference organized by date
     */
    public void createSchedule(){
        File schedule = new File("src/main/resources/web/endpoints/schedule.html");
        try {
            // writer for html file
            BufferedWriter bufferedWriter = new BufferedWriter( new FileWriter(schedule));
            // setup html file
            bufferedWriter.write("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Schedule of Conference</title>\n" +
                    "</head>\n" +
                    "<style>\n" +
                    "    table, td, th {\n" +
                    "      border: 1px solid black;\n" +
                    "      border-collapse: collapse;\n" +
                    "    }\n" +
                    "    th{background-color: #E3E7E7 }\n" +
                    "    td{background-color: #F6F7F7}\n" +
                    "    body {background-color: #B3BEBE ;}"+
                    "</style>\n");
            bufferedWriter.write("<h1>\n    Schedule of Conference \n</h1> \n");
            // get hashmap with dates(no hour min) as keys and eventIDs as values
            HashMap<LocalDateTime, ArrayList<String>> eventsMap = eventManager.getAllEventsWithDates();
            // sort date keys
            List<LocalDateTime> sortedDates = eventsMap.keySet().stream().sorted().collect(Collectors.toList());
            for (LocalDateTime date : sortedDates){
                // String representation of date
                String dateString = date.getDayOfMonth() + " " + date.getMonth() + ", " +date.getYear();
                bufferedWriter.write("<body>\n");
                bufferedWriter.write("<h3>\n    " + dateString+ "\n </h3> \n");
                bufferedWriter.write("<table style = \"width:100%\">\n" +
                        "        <tr>\n" +
                        "            <th>Event ID</th>\n" +
                        "            <th>Name</th>\n" +
                        "            <th>Start time</th>\n" +
                        "            <th>End time</th>\n" +
                        "            <th>Capacity</th>\n" +
                        "            <th>Room</th>\n" +
                        "            <th>Speakers</th>\n" +
                        "            <th>Attendees</th>\n" +
                        "        </tr>\n");
                // looping over eventIDs for events starting at date
                for (String eventID: eventsMap.get(date)){
                    String formattedEvent = formatEvent(eventID); // a row of the table
                    bufferedWriter.write(formattedEvent);
                }
                bufferedWriter.write("</table>\n");
                bufferedWriter.write("</body>\n");
            }
            bufferedWriter.write("</html>");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper method to format event specified by given eventID to strings representing rows in a table
     */
    private String formatEvent(String eventID){
        //NAME
        String eventName = eventManager.getName(eventID);
        if (eventName== null){
            eventName = "unnamed event";
        }

        // TIME hour:minutes since date is in title
        String startTime = eventManager.startTimeOfEvent(eventID).getHour() + ":" +
                           eventManager.startTimeOfEvent(eventID).getMinute();
        // add extra 0 min if time is 00
        if (eventManager.startTimeOfEvent(eventID).getMinute() == 0){
            startTime += "0";
        }
        String endTime = eventManager.endTimeOfEvent(eventID).getHour() + ":" +
                         eventManager.endTimeOfEvent(eventID).getMinute();
        // add extra 0 min if time is 00
        if (eventManager.endTimeOfEvent(eventID).getMinute() == 0){
            endTime += "0";
        }

        //CAPACITY
        String capacity = String.valueOf(eventManager.getCapacity(eventID));

        //ROOM NUMBER
        String roomNumber = eventManager.getRoomNumberOfEvent(eventID);

        //SPEAKER IDS
        String speakers = "";
        for (String speakerID: eventManager.getSpeakersOfEvent(eventID)){
            speakers += speakerID + " ";
        }
        if (speakers.equals("")){
            speakers = "none";
        }

        //ATTENDEE IDS
        String attendees = "";
        for (String attendeeID: eventManager.getAttendeesOfEvent(eventID)){
            attendees += attendeeID + " ";
        }
        if (attendees.equals("")){
            attendees = "none";
        }

        String formattedEvent = "   <tr>\n"+
                            "            <td>" + eventID + "</td>\n" +
                            "            <td>" + eventName + "</td>\n" +
                            "            <td>" + startTime + "</td>\n" +
                            "            <td>" + endTime + "</td>\n" +
                            "            <td>" + capacity + "</td>\n" +
                            "            <td>" + roomNumber + "</td>\n" +
                            "            <td>" + speakers + "</td>\n" +
                            "            <td>" + attendees + "</td>\n" +
                                "   </tr>\n";
        return formattedEvent;
    }



}
