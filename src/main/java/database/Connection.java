package database;

import entry.ProjectProperties;

import java.sql.DriverManager;
import java.sql.SQLException;

import static entry.main.customProperties;

public class Connection {
    private static class database {
        static final Connection INSTANCE = new Connection();
    }

    private java.sql.Connection dbConnection = null;
    private Connection() {
        try {
            dbConnection = DriverManager.getConnection(
                    customProperties.getProperty(ProjectProperties.DB_LINK.label)
                    ,customProperties.getProperty(ProjectProperties.DB_USER.label)
                    ,customProperties.getProperty(ProjectProperties.DB_PASSWD.label));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Connection returnInstance (){
        return database.INSTANCE;
    }
    public java.sql.Connection connect(){
        return dbConnection;
    }
}
