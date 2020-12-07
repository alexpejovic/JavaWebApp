package modules.gateways;

import modules.entities.Message;
import modules.entities.Event;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * A Gateway class used to read message data from the database and create message entities as well as
 * write message data to the database.
 */
public class MessageGatewayDB implements MessageStrategy {
    /**
     * Creates a messages table in the database that stores data pertaining to message entities.
     * If the messages table has already been created, nothing happens.
     */
    public void createMessages(){
        //Query for creating new messages table if one does not already exist
        String createSql = "CREATE TABLE IF NOT EXISTS messages (\n"
                + "	messageId VARCHAR(20) PRIMARY KEY,\n"
                + "	content VARCHAR(3000) NOT NULL,\n"
                + "	senderId VARCHAR(20) NOT NULL,\n"
                + "	receiverId VARCHAR(20) NOT NULL,\n"
                + "	dateTime TIMESTAMP,\n"
                + "	hasBeenRead BOOLEAN NOT NULL \n"
                + ");";
        //Check if trying to create messages table results in an error (messages table already exists)
        try (Connection conn = DBConnect.connect("src\\main\\resources\\web\\database\\conference.db");
             Statement stmt = conn.createStatement()) {
            // creating a new messages table
            stmt.execute(createSql);
            //If table has already been created
        } catch (SQLException | ClassNotFoundException e2) {
            System.out.println(e2.getMessage());
        }
    }

    @Override
    /**
     * Reads the data from the messages table in the database and creates the resultant message entities.
     * @return the list of messages in the database
     */
    public ArrayList<Message> readData() {
        //List of messages to be returned
        ArrayList<Message> messageList = new ArrayList<>();
        //Create messages table if it hasn't already
        createMessages();
        //Query for selecting contents of messages table
        String sql = "SELECT messageId, content, senderId, receiverId, dateTime, hasBeenRead FROM messages";
        //Executing the query for selecting messages contents
        try (Connection dbConn = DBConnect.connect("src\\main\\resources\\web\\database\\conference.db");
             Statement stmt = dbConn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            //For each row in the messages table, create a new message using the data
            while (rs.next()) {
                //Use the data retrieved from the tables to create Message entity
                Message newMessage = new Message(rs.getString("content"),
                        rs.getString("senderId"),
                        rs.getString("receiverId"), rs.getString("messageId"),
                        rs.getTimestamp("dateTime").toLocalDateTime());
                if(rs.getBoolean("hasBeenRead")) {
                    newMessage.markAsRead();
                }
                //Add new message to list of messages
                messageList.add(newMessage);
            }
            //If message select query fails
        } catch (ClassNotFoundException | SQLException e3) {
            System.out.println(e3.getMessage());
        }
        return messageList;
    }

    /**
     * Writes data from a list of existing message entities into the database.
     * @param writeMessage the list of message entities to be added to the database.
     */
    @Override
    public void writeData(ArrayList<Message> writeMessage) {
        //Create messages table if it hasn't already been
        createMessages();
        for(Message message: writeMessage){
            //Query for writing the message to the database
            String sql = "INSERT INTO messages (messageId, content, senderId, receiverId, dateTime, hasBeenRead)" +
                    " Values('"+message.getID()+"', '"+message.getContent()+"', '"+message.getSenderID()+"', '"+message.getReceiverID()+"', '"+message.getDateTime()+"', '"+message.getHasBeenRead()+"')";
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
