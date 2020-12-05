package modules.gateways;

import modules.entities.Message;
import modules.entities.Room;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.DatabaseMetaData;

/**
 * A Gateway class used to read room data from the database and write room data to the database
 */
public class RoomGatewayDB implements RoomStrategy {
    /**
     * Creates a rooms table in the database to store data pertaining to room entities.
     * If the table has already been created, nothing occurs
     */
    public void createRooms(){
        //Query for creating rooms table
        String createRel = "Create TABLE IF NOT EXISTS rooms (\n"
                + " roomNumber VARCHAR(20) PRIMARY KEY, \n"
                + " capacity INTEGER(20) \n"
                + ");";
        try (Connection conn = DBConnect.connect("src\\main\\resources\\web\\database\\conference.db");
             Statement stmt = conn.createStatement()) {
            // create a new rooms table
            stmt.execute(createRel);
        } catch (SQLException | ClassNotFoundException e4) {
            System.out.println(e4.getMessage());
        }
    }

    /**
     * Reads the data from the room table in the database and creates the resulting room entities
     * Precondition: All events hosted in this room must be written in the database
     * @return the list of rooms in the database
     */
    @Override
    public ArrayList<Room> readData() {
        //List of rooms to be returned
        ArrayList<Room> roomList = new ArrayList<>();
        //Create rooms table if it hasn't already
        createRooms();
        //Query for selecting contents of rooms table
        String sql = "SELECT roomNumber, capacity FROM rooms";
        //Executing the query for selecting rooms contents
        try (Connection dbConn = DBConnect.connect("src\\main\\resources\\web\\database\\conference.db");
             Statement stmt = dbConn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            //For each row in the rooms table, create a new room using the data
            while (rs.next()) {
                //Use the data retrieved from the tables to create room entity
                Room newRoom = new Room(rs.getString("roomNumber"),
                        rs.getInt("capacity"));
                //Query for getting all events in this room
                DatabaseMetaData dbm = dbConn.getMetaData();
                // check if "events" table is there
                ResultSet tables = dbm.getTables(null, null, "events", null);
                if (tables.next()) {
                    // Table exists, then add the events hosted in this room to the room entity
                    String eventQuery = "SELECT eventId, roomNumber FROM events WHERE roomNumber == '"+rs.getString("roomNumber")+"'";
                    try(Statement stmt3 = dbConn.createStatement();
                        ResultSet rs3 = stmt3.executeQuery(eventQuery)){
                        while (rs3.next()) {
                            newRoom.addEvent(rs3.getString("eventId"));
                        }
                    }
                }
                else {
                    // Table does not exist
                    //Since the event table does not exist, this room cannot possibly be hosting any events in the database
                }
                //Add new room to list of rooms
                roomList.add(newRoom);
            }
            //If room select query fails
        } catch (ClassNotFoundException | SQLException e3) {
            System.out.println(e3.getMessage());
        }
        return roomList;
    }

    /**
     * Writes the data from a list of existing rooms into the database
     * @param writeRooms the list of rooms to be added to the database
     */
    @Override
    public void writeData(ArrayList<Room> writeRooms) {
        //Create rooms table if it hasn't already been
        createRooms();
        for(Room room: writeRooms){
            //Query for writing the room to the database
            String sql = "INSERT INTO rooms (roomNumber, capacity)" +
                    " Values('"+room.getRoomNumber()+"', '"+room.getCapacity()+"')";
            try (Connection iConn = DBConnect.connect("src\\main\\resources\\web\\database\\conference.db");
                 Statement stmt = iConn.createStatement()) {
                stmt.execute(sql);
            } catch (SQLException | ClassNotFoundException e2) {
                System.out.println(e2.getMessage());
            }
        }
    }

    @Override
    public void setFilename(String newFilename) {

    }
}
