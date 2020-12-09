package modules.gateways;

//import com.sun.org.apache.xpath.internal.operations.Or;
import modules.entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.sql.DatabaseMetaData;

/**
 * A Gateway class used to read and write user data from/to the database
 */
public class UserGatewayDB implements UserStrategy {

//    private String filename = "src\\main\\resources\\web\\database\\conference.db";
    private String filename = "src/main/resources/web/database/conference.db";

    /**
     * Creates a users table to store user data
     * If a users table has already been created, nothing occurs
     */
    public void createUsers() {
        //Query for creating new users table if one does not already exist
        String createSql = "CREATE TABLE IF NOT EXISTS users (\n"
                + "	userId VARCHAR(20) PRIMARY KEY,\n"
                + "	username VARCHAR(100) NOT NULL,\n"
                + "	password VARCHAR(100) NOT NULL,\n"
                + "	isVIP BOOLEAN \n"
                + ");";
        //Check if trying to create users table results in an error (users table already exists)
        try (
                Connection conn = DBConnect.connect(this.filename);
                Statement stmt = conn.createStatement()) {
            // creating a new table
            stmt.execute(createSql);
            conn.close();
            //If table has already been created
        } catch (SQLException | ClassNotFoundException e2) {
            System.out.println(e2.getMessage());
        }
    }

    /**
     * Creates a friends table to model the relationship between different users
     * userId is the user in question and friendId is userId's friend
     * NOTE: userId may not be friendId's friend
     */
    public void createFriends(){
        //Query for creating friends table
        String createRel = "Create TABLE IF NOT EXISTS friends (\n"
                + " fId INTEGER PRIMARY KEY AUTOINCREMENT, \n"
                + " userId VARCHAR(20), \n"
                + " friendId VARCHAR(20), \n"
                + " UNIQUE (userId, friendId) \n"
                + ");";
        try (Connection conn = DBConnect.connect(this.filename);
             Statement stmt = conn.createStatement()) {
            // create a new friends table
            stmt.execute(createRel);
            conn.close();
        } catch (SQLException | ClassNotFoundException e4) {
            System.out.println(e4.getMessage());
        }
    }

    /**
     * A helper method to return the friends of a given user
     * @param user the sql ResultSet of a user query
     * @return A list of user's friends
     * @throws SQLException when db connection fails
     */
    public ArrayList<String> updateFriends(ResultSet user) throws SQLException {
        //Query for getting user's friends
        String friendQuery = "SELECT userId, friendId FROM friends WHERE userId == '"+user.getString("userId")+"'";
        //The list of friends to be returned
        ArrayList<String> friendIds = new ArrayList<>();
        try (Connection dbConn = DBConnect.connect(this.filename);
                Statement stmt3 = dbConn.createStatement();
             ResultSet rs3 = stmt3.executeQuery(friendQuery)) {
            while (rs3.next()) {
                friendIds.add(rs3.getString("friendId"));
            }
            dbConn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //Return the list of user's friends
        return friendIds;
    }

    /**
     * Reads the data pertaining to Users from the database an instantiates the resulting Attendee, Speaker or Organizer
     * @return the list of Users present in the database
     */
    @Override
    public ArrayList<User> readData() {
        //List of users to be returned
        ArrayList<User> userList = new ArrayList<>();
        //Create users table if it hasn't already
        createUsers();
        //Assume relations table doesn't exists, we will verify this later
        boolean relationsTableExists = Boolean.FALSE;
        //Query for selecting contents of users table
        String sql = "SELECT userId, username, password, isVIP FROM users";
        //Executing the query for selecting users contents
        try (Connection dbConn = DBConnect.connect(this.filename);
             Statement stmt = dbConn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            //Check if the relations table exists
            DatabaseMetaData dbm = dbConn.getMetaData();
            // check if "relations" table is there
            ResultSet tables = dbm.getTables(null, null, "relations", null);
            if (tables.next()) {
                relationsTableExists = true;
            }
            //For each row in the users table, create a new user using the data
            while (rs.next()) {
            //Add attendee if rs corresponds to attendee data
                if(rs.getString("userId").charAt(0) == 'a') {
                    Attendee newAttendee = new Attendee(rs.getString("username"), rs.getString("password"), rs.getString("userId"));
                    if (rs.getBoolean("isVIP")) {
                        newAttendee.setAsVIP();
                    }
                    if (relationsTableExists) {
                        //Get the events this attendee goes to
                        String getEventQuery = "SELECT eventId, userId FROM relations WHERE userId == '"+rs.getString("userId")+"'";
                        try (Statement stmt3 = dbConn.createStatement();
                             ResultSet rs3 = stmt3.executeQuery(getEventQuery)) {
                            while (rs3.next()) {
                                newAttendee.addEvent(rs3.getString("eventId"));
                            }
                        }
                    }
                    //Get this attendee's friends
                    ArrayList<String> friends = updateFriends(rs);
                    for(String friend: friends){
                        newAttendee.addToFriendList(friend);
                    }
                    //Add this attendee to the return list
                    userList.add(newAttendee);
                } else if(rs.getString("userId").charAt(0) == 's') {
                    //Add speaker if rs corresponds to speaker data
                    Speaker newSpeaker = new Speaker(rs.getString("username"), rs.getString("password"), rs.getString("userId"));
                    if (relationsTableExists) {
                        //Get the events this speaker hosts
                        String getEventQuery = "SELECT eventId, userId FROM relations WHERE userId == '"+rs.getString("userId")+"'";
                        try (Statement stmt3 = dbConn.createStatement();
                             ResultSet rs3 = stmt3.executeQuery(getEventQuery)) {
                            while (rs3.next()) {
                                newSpeaker.addEvent(rs3.getString("eventId"));
                            }
                        }
                    }
                    //Get this speaker's friends
                    ArrayList<String> friends = updateFriends(rs);
                    for(String friend: friends){
                        newSpeaker.addToFriendList(friend);
                    }
                    //Add this speaker to the return list
                    userList.add(newSpeaker);
                } else {
                    //Add organizer if rs corresponds to organizer data
                    Organizer newOrganizer = new Organizer(rs.getString("username"), rs.getString("password"), rs.getString("userId"));
                    if (relationsTableExists) {
                        //Get events this organizer is managing
                        String getEventQuery = "SELECT eventId, userId FROM relations WHERE userId == '"+rs.getString("userId")+"'";
                        try (Statement stmt3 = dbConn.createStatement();
                             ResultSet rs3 = stmt3.executeQuery(getEventQuery)) {
                            while (rs3.next()) {
                                newOrganizer.addManagedEvent(rs3.getString("eventId"));
                            }
                        }
                    }
                    //Get this organizer's friends
                    ArrayList<String> friends = updateFriends(rs);
                    for(String friend: friends){
                        newOrganizer.addToFriendList(friend);
                    }
                    //Add this organizer to the return list
                    userList.add(newOrganizer);
                }
            }
            dbConn.close();
            //If user select query fails
        } catch (ClassNotFoundException | SQLException e3) {
            System.out.println(e3.getMessage());
        }
        //The list of users in the database
        return userList;
    }

    /**
     * Writes the data of a list of existing Users entities into the database
     * @param writeUsers the list of existing users
     */
    @Override
    public void writeData(ArrayList<User> writeUsers) {
        try(Connection dbConn = DBConnect.connect(this.filename)) {
            //Create user table if it hasn't already been
            createUsers();
            //Create friends table if it hasn't already
            createFriends();
            for (User user : writeUsers) {
                //Query for writing the user to the database
                if (user instanceof Speaker || user instanceof Organizer) {
                    String sql = "REPLACE INTO users (userId, username, password)" +
                            " Values('" + user.getID() + "', '" + user.getUsername() + "', '" + user.getPassword() + "')";
                    try (Statement stmt = dbConn.createStatement()) {
                        stmt.execute(sql);
                        //We must update the relations table for the events this organizer is managing
                        if (user instanceof Organizer) {
                            //Check if relations table exists
                            DatabaseMetaData dbm = dbConn.getMetaData();
                            // check if "relations" table is there
                            ResultSet tables = dbm.getTables(null, null, "relations", null);
                            if (tables.next()) {
                                //Update in relations the events being managed by this organizer
                                for (String eventId : ((Organizer) user).getManagedEvents()) {
                                    String manageQuery = "REPLACE INTO relations (eventId, userId) VALUES('" + eventId + "', '" + user.getID() + "')";
                                    try (Statement newStmt = dbConn.createStatement()) {
                                        newStmt.execute(manageQuery);
                                    } catch (SQLException e2) {
                                        System.out.println(e2.getMessage());
                                    }
                                }
                                //Delete unwanted managed events
                                String getFriends = "SELECT eventId, userId FROM relations WHERE userId == '" + user.getID() + "'";
                                try (Statement stmt3 = dbConn.createStatement();
                                     ResultSet rs3 = stmt3.executeQuery(getFriends)) {
                                    while (rs3.next()) {
                                        //Delete unwanted managed event
                                        if (!(((Organizer) user).getManagedEvents().contains(rs3.getString("eventId")))) {
                                            String removeQuery = "DELETE FROM relations WHERE eventId == '" + rs3.getString("eventId") + "' AND userId == '" + user.getID() + "'";
                                            try (Statement delStmt = dbConn.createStatement()) {
                                                delStmt.execute(removeQuery);
                                            } catch (SQLException e2) {
                                                System.out.println(e2.getMessage());
                                            }
                                        }
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (SQLException e2) {
                        System.out.println(e2.getMessage());
                    }
                    //If user is an attendee (Since we must populate the isVIP variable)
                } else {
                    int vip = ((Attendee) user).getVIPStatus() ? 1 : 0;
                    String sql = "REPLACE INTO users (userId, username, password, isVIP)" +
                            " Values('" + user.getID() + "', '" + user.getUsername() + "', '" + user.getPassword() + "', '" + vip + "')";
                    try (Statement stmt = dbConn.createStatement()) {
                        stmt.execute(sql);
                    } catch (SQLException e2) {
                        System.out.println(e2.getMessage());
                    }
                }
                //Query for adding friendships to database
                for (String friend : user.getFriendList()) {
                    String friendQuery = "REPLACE INTO friends (userId, friendId) VALUES('" + user.getID() + "', '" + friend + "')";
                    try (Statement stmt = dbConn.createStatement()) {
                        stmt.execute(friendQuery);
                    } catch (SQLException e2) {
                        System.out.println(e2.getMessage());
                    }
                }
                //Deleting unwanted friendships
                String getFriends = "SELECT userId, friendId FROM friends WHERE userId == '" + user.getID() + "'";
                try (Statement stmt3 = dbConn.createStatement();
                     ResultSet rs3 = stmt3.executeQuery(getFriends)) {
                    while (rs3.next()) {
                        //Delete unwanted friend
                        if (!(user.getFriendList().contains(rs3.getString("friendId")))) {
                            String removeQuery = "DELETE FROM friends WHERE userId == '" + user.getID() + "' AND friendId == '" + rs3.getString("friendId") + "'";
                            try (Statement stmt = dbConn.createStatement()) {
                                stmt.execute(removeQuery);
                            } catch (SQLException e2) {
                                System.out.println(e2.getMessage());
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException | ClassNotFoundException e){
            System.out.println(e.getMessage());
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
