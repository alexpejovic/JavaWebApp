package modules.gateways;

import modules.entities.Event;
import modules.gateways.DBConnect;
import modules.usecases.EventManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Date;

/**
 * A class to write event data to the database and read data from the database
 */
public class EventGatewayDB implements EventStrategy{

    private String filename = "src\\main\\resources\\web\\database\\conference.db";

    /**
     * Creates a relations table which keeps track of the relationships between attendees and the events they are attending
     * as well as the relationship between speakers and the events they are speaking at.
     * If the relations table already exists, nothing occurs
     */
    public void createRelations(){
        //Query for creating relations table
        String createRel = "Create TABLE IF NOT EXISTS relations (\n"
                + " relId INTEGER PRIMARY KEY AUTOINCREMENT, \n"
                + " eventId VARCHAR(20), \n"
                + " userId VARCHAR(20), \n"
                + " UNIQUE (eventId, userId) \n"
                + ");";
        try (Connection conn = DBConnect.connect(this.filename);
             Statement stmt = conn.createStatement()) {
            // create a new relations table
            stmt.execute(createRel);
        } catch (SQLException | ClassNotFoundException e4) {
            System.out.println(e4.getMessage());
        }
    }

    /**
     * Creates an events table if it has not already been created. This events table store data for all the events
     * in the conference except for the speakers and attendees of the event.
     */
    public void createEvents(){
        //Query for creating new event table if one does not already exist
        String createSql = "CREATE TABLE IF NOT EXISTS events (\n"
                + "	eventId VARCHAR(20) PRIMARY KEY,\n"
                + "	roomNumber VARCHAR(20) NOT NULL,\n"
                + "	startTime TIMESTAMP NOT NULL,\n"
                + "	endTime TIMESTAMP NOT NULL,\n"
                + "	name VARCHAR(250),\n"
                + "	isVIP BOOLEAN NOT NULL,\n"
                + "	capacity INTEGER(20) NOT NULL \n"
                + ");";
        //Check if trying to create event table results in an error (event table already exists)
        try (Connection conn = DBConnect.connect(this.filename);
             Statement stmt = conn.createStatement()) {
            // creating a new table
            stmt.execute(createSql);
            //If table has already been created
        } catch (SQLException | ClassNotFoundException e2) {
            System.out.println(e2.getMessage());
        }
    }

    /**
     * Writes to the database the relationship between an attendee/speaker and the event they go to/host
     * @param event they event the attendee/speaker goes to/hosts
     * @param id the Id of the speaker/attendee
     */
    public void isInvolved(Event event, String id){
        String relationQuery = "REPLACE INTO relations (eventId, userId)" + " Values('"+event.getID()+"', '"+id+"')";
        try (Connection rConn = DBConnect.connect(this.filename);
             Statement stmt = rConn.createStatement()) {
            //Write relationship
            stmt.execute(relationQuery);
        } catch (SQLException | ClassNotFoundException e5) {
            System.out.println(e5.getMessage());
        }
    }
    /**
     * Reads all the data pertaining to events from the database and creates the resulting event entities.
     * This includes populating the attendeeList and speakerList variables.
     * @return the list of events in the database
     */
    @Override
    public ArrayList<Event> readData() {
        //List of events to be returned
        ArrayList<Event> eventList = new ArrayList<>();
        //Create events table if it hasn't already
        createEvents();
        //Create relations table if it hasn't already
        createRelations();
        //Query for selecting contents of event table
        String sql = "SELECT roomNumber, startTime, endTime, eventId, capacity, name, isVIP FROM events";
        //Executing the query for selecting event contents
        try (Connection dbConn = DBConnect.connect(this.filename);
             Statement stmt = dbConn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            //For each row in the event table, create a new event using the data
            while (rs.next()) {
                //Query for selecting attendees of event
                String attendeeQuery = "SELECT userId, eventId FROM relations WHERE eventId == '"+rs.getString("eventId")+"' AND substr(userId, 1, 1) == 'a'";
                ArrayList<String> attendeeIds = new ArrayList<>();
                try(Statement stmt2 = dbConn.createStatement();
                    ResultSet rs2 = stmt2.executeQuery(attendeeQuery)){
                    while (rs2.next()) {
                        attendeeIds.add(rs2.getString("userId"));
                    }
                }
                //Query for selecting speakers of event
                String speakerQuery = "SELECT userId, eventId FROM relations WHERE eventId == '"+rs.getString("eventId")+"' AND substr(userId, 1, 1) == 's'";
                ArrayList<String> speakerIds = new ArrayList<>();
                try(Statement stmt3 = dbConn.createStatement();
                    ResultSet rs3 = stmt3.executeQuery(speakerQuery)){
                    while (rs3.next()) {
                        speakerIds.add(rs3.getString("userId"));
                    }
                }
                //Use the data retrieved from the tables to create Event entity
                Event newEvent = new Event(rs.getString("roomNumber"),
                        rs.getTimestamp("startTime").toLocalDateTime(),
                        rs.getTimestamp("endTime").toLocalDateTime(), rs.getString("eventId"),
                        rs.getInt("capacity"));
                newEvent.setName(rs.getString("name"));
                if(rs.getBoolean("isVIP")) {
                    newEvent.setAsVIP();
                }
                //Add attendees to Event entity
                for(String id: attendeeIds){
                    newEvent.addAttendee(id);
                }
                //Schedule speakers to Event entity
                for(String id: speakerIds){
                    newEvent.scheduleSpeaker(id);
                }
                //Add new event to list of events
                eventList.add(newEvent);
            }
        //If event select query fails
        } catch (ClassNotFoundException | SQLException e3) {
            System.out.println(e3.getMessage());
        }
    return eventList;
    }

    /**
     * Writes the data from a list of events into the database so it can be referenced in the future
     * @param writeEvents the list of events to be represented in the database
     */
    @Override
    public void writeData(ArrayList<Event> writeEvents) {
        //Create event table if it hasn't already been
        createEvents();
        //Create relations table if it hasn't already been
        createRelations();
        for(Event event: writeEvents){
            //Query for writing the event to the database
            String sql = "REPLACE INTO events (eventId, roomNumber, startTime, endTime, capacity, name, isVIP)" +
                    " Values('"+event.getID()+"', '"+event.getRoomNumber()+"', '"+event.getStartTime()+"', '"+event.getEndTime()+"', '"+event.getCapacity()+"', '"+event.getName()+"', '"+event.getVIPStatus()+"')";
            try (Connection iConn = DBConnect.connect("this.filename");
                 Statement stmt = iConn.createStatement()) {
                stmt.execute(sql);
            } catch (SQLException | ClassNotFoundException e2) {
                System.out.println(e2.getMessage());
            }
            //For each attendee going to this event, we write this relationship into the database
            for(String id: event.getAttendees()) {
                isInvolved(event, id);
            }
            //For each speaker talking at this event, we write this relationship into the database
            for(String id: event.getSpeakers()) {
                isInvolved(event, id);
            }
            //Query for deleting unwanted users
            String getAttendees = "SELECT eventId, userId FROM relations WHERE eventId == '"+event.getID()+"'";
            try(Connection dbConn = DBConnect.connect("this.filename");
                Statement stmt3 = dbConn.createStatement();
                ResultSet rs3 = stmt3.executeQuery(getAttendees)){
                while (rs3.next()) {
                    //Delete unwanted attendee
                    if(rs3.getString("userId").charAt(0) == 'a' && !(event.getAttendees().contains(rs3.getString("userId")))){
                        String removeQuery = "DELETE FROM relations WHERE eventId == '"+event.getID()+"' AND userId == '"+rs3.getString("userId")+"'";
                        try (Statement stmt = dbConn.createStatement()) {
                            stmt.execute(removeQuery);
                        } catch (SQLException e2) {
                            System.out.println(e2.getMessage());
                        }
                    }
                    //Delete unwanted speaker
                    if(rs3.getString("userId").charAt(0) == 's' && !(event.getSpeakers().contains(rs3.getString("userId")))){
                        String remove = "DELETE FROM relations WHERE eventId == '"+event.getID()+"' AND userId == '"+rs3.getString("userId")+"'";
                        try (Statement stmt = dbConn.createStatement()) {
                            stmt.execute(sql);
                        } catch (SQLException e2) {
                            System.out.println(e2.getMessage());
                        }
                    }
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param newFilename The new filepath for the database
     */
    @Override
    public void setFilename(String newFilename) {
        this.filename = newFilename;
    }
}
