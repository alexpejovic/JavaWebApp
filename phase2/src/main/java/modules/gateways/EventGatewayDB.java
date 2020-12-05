package modules.gateways;

import modules.entities.Event;
import modules.gateways.DBConnect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Date;

public class EventGatewayDB implements EventStrategy{
    @Override
    public ArrayList<Event> readData() {
        //Query for selecting contents of event table
        String sql = "SELECT roomNumber, startTime, endTime, eventId, capacity, name, isVIP FROM events";
        ArrayList<Event> eventList = new ArrayList<>();
        //Create events table if it hasn't already
        createEvents();
        //Create relations table if it hasn't already
        createRelations();
        //Executing the query for selecting event contents
        try (Connection dbConn = DBConnect.connect("src\\main\\resources\\web\\database\\conference.db");
             Statement stmt = dbConn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            //For each row in the event table, create a new event using the data
            while (rs.next()) {
                //Query for selecting attendees of event
                String attendeeQuery = "SELECT userId FROM relations WHERE eventId == '"+rs.getString("eventId")+"'";
                ArrayList<String> attendeeIds = new ArrayList<>();
                try(Statement stmt2 = dbConn.createStatement();
                    ResultSet rs2 = stmt2.executeQuery(attendeeQuery)){
                    while (rs2.next()) {
                        attendeeIds.add(rs2.getString("eventId"));
                    }
                }
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
                //Add new event to list of events
                eventList.add(newEvent);
            }
        //If event select query fails
        } catch (ClassNotFoundException | SQLException e3) {
            System.out.println(e3.getMessage());
        }
    return eventList;
    }

    @Override
    public void writeData(ArrayList<Event> writeEvents) {
        createEvents();
        createRelations();
        for(Event event: writeEvents){
            String sql = "INSERT INTO events (eventId, roomNumber, startTime, endTime, capacity, name, isVIP)" +
                    " Values('"+event.getID()+"', '"+event.getRoomNumber()+"', '"+event.getStartTime()+"', '"+event.getEndTime()+"', '"+event.getCapacity()+"', '"+event.getName()+"', '"+event.getVIPStatus()+"')";
            try (Connection iConn = DBConnect.connect("src\\main\\resources\\web\\database\\conference.db");
                 Statement stmt = iConn.createStatement()) {
                stmt.execute(sql);
            } catch (SQLException | ClassNotFoundException e2) {
                System.out.println(e2.getMessage());
            }
            for(String id: event.getAttendees()) {
                String relationQuery = "INSERT INTO relations (eventId, userId)" + " Values('"+event.getID()+"', '"+id+"')";
                try (Connection rConn = DBConnect.connect("src\\main\\resources\\web\\database\\conference.db");
                     Statement stmt = rConn.createStatement()) {
                    // create a new table
                    stmt.execute(relationQuery);
                } catch (SQLException | ClassNotFoundException e5) {
                    System.out.println(e5.getMessage());
                }
            }
        }
    }

    @Override
    public void setFilename(String newFilename) {
    }

    public void createRelations(){
        String createRel = "Create TABLE IF NOT EXISTS relations (\n"
                + " relId INTEGER PRIMARY KEY AUTOINCREMENT, \n"
                + " eventId VARCHAR(20), \n"
                + " userId VARCHAR(20) \n"
                + ");";
        try (Connection conn = DBConnect.connect("src\\main\\resources\\web\\database\\conference.db");
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(createRel);
        } catch (SQLException | ClassNotFoundException e4) {
            System.out.println(e4.getMessage());
        }
    }

    public void createEvents(){
        //Query for creating new event table if one does not already exist
        String createSql = "CREATE TABLE IF NOT EXISTS events (\n"
                + "	eventId VARCHAR(20) PRIMARY KEY,\n"
                + "	roomNumber VARCHAR(20) NOT NULL,\n"
                + "	startTime TIMESTAMP NOT NULL,\n"
                + "	endTime TIMESTAMP NOT NULL,\n"
                + "	name VARCHAR(250),\n"
                + "	isVIP BOOLEAN NOT NULL,\n"
                + "	capacity INTEGER(20) NOT NULL\n"
                + ");";
        //Check if trying to create event table results in an error (event table already exists)
        try (Connection conn = DBConnect.connect("src\\main\\resources\\web\\database\\conference.db");
             Statement stmt = conn.createStatement()) {
            // creating a new table
            stmt.execute(createSql);
            //If table has already been created
        } catch (SQLException | ClassNotFoundException e2) {
            System.out.println(e2.getMessage());
        }
    }
}
