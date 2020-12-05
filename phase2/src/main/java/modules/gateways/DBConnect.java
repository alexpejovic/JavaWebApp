package modules.gateways;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

    public static Connection connect(String filepath) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + filepath);
            return conn;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}
