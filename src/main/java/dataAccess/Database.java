package dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Way to create and connect to the actual database
 */
public class Database {
    private Connection conn;

    public Database() {
        this.conn = openConnection();
    }
    /**
     * open the connection to the sqlite database
     * @return Connection
     */
    public Connection openConnection() {
        // driver(jdbc):language(sqlite):path()
        final String URL = "jdbc:sqlite:FamilyMapServerStudent.sqlite";

        try {
            conn = DriverManager.getConnection(URL);
            conn.setAutoCommit(false);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            // throw data access exception
        }
        return conn;
    }

    /**
     * simple way to return the connection variable to our database
     * @return Connection
     */
    public Connection getConnection() {
        if (this.conn != null) {
            return this.conn;
        }
         else {
            return openConnection();
        }
    }

    /**
     * close the connection to the database and commit the changes (or not)
     * @param commit
     */
    public void closeConnection(boolean commit) {
        try {
            if (commit) {
                conn.commit();
            }
            else {
                conn.rollback();
            }
            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create all tables in the database
     */
    public void createTables() {
        try {
            String sql1 = "CREATE TABLE IF NOT EXISTS user (\n" +
                    "    UserName text NOT NULL UNIQUE,\n" +
                    "    Password text NOT NULL,\n" +
                    "    Email text NOT NULL,\n" +
                    "    FirstName text NOT NULL,\n" +
                    "    LastName text NOT NULL,\n" +
                    "    Gender text NOT NULL,\n" +
                    "    PersonID String not null,\n" +
                    "    PRIMARY KEY (UserName),\n" +
                    "    FOREIGN KEY (PersonID) REFERENCES person(PersonID)\n" +
                    ");\n" +
                    "\n";
            String sql2 = "CREATE TABLE IF NOT EXISTS person (\n" +
                            "    PersonID text NOT NULL UNIQUE,\n" +
                            "    UserName text NOT NULL,\n" +
                            "    FirstName text NOT NULL,\n" +
                            "    LastName text NOT NULL,\n" +
                            "    Gender text NOT NULL,\n" +
                            "    FatherID text,\n" +
                            "    MotherID text,\n" +
                            "    SpouseID text,\n" +
                            "    PRIMARY KEY (PersonID),\n" +
                            "    FOREIGN KEY (UserName) REFERENCES user(UserName)\n" +
                            ");\n" +
                            "\n";
            String sql3 = "CREATE TABLE IF NOT EXISTS event (\n" +
                            "    EventID text NOT NULL UNIQUE,\n" +
                            "    AssociatedUserName text NOT NULL,\n" +
                            "    PersonID text NOT NULL,\n" +
                            "    Latitude float NOT NULL,\n" +
                            "    Longitude float NOT NULL, \n" +
                            "    Country text NOT NULL,\n" +
                            "    City text NOT NULL,\n" +
                            "    EventType text NOT NULL,\n" +
                            "    Year int NOT NULL,\n" +
                            "    PRIMARY KEY (EventID),\n" +
                            "    FOREIGN KEY (AssociatedUserName) REFERENCES user(UserName),\n" +
                            "    FOREIGN KEY (PersonID) REFERENCES person(PersonID)\n" +
                            ");\n" +
                            "\n";
            String sql4 = "CREATE TABLE  IF NOT EXISTS authToken (\n" +
                            "    AuthToken text NOT NULL UNIQUE,\n" +
                            "    UserName text,\n" +
                            "    Password text,\n" +
                            "    FOREIGN KEY (UserName) REFERENCES user(UserName),\n" +
                            "    FOREIGN KEY (Password) REFERENCES user(Password)\n" +
                            ");";
            // prepare statements and submit
            PreparedStatement stmt = conn.prepareStatement(sql1);
            stmt.executeUpdate();
            stmt = conn.prepareStatement(sql2);
            stmt.executeUpdate();
            stmt = conn.prepareStatement(sql3);
            stmt.executeUpdate();
            stmt = conn.prepareStatement(sql4);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete all tables in the database
     */
    public boolean clearAllTables() {
        String sqlEvents = "DELETE FROM Event";
        String sqlUsers = "DELETE FROM User";
        String sqlPersons = "DELETE FROM Person";
        String sqlTokens = "DELETE FROM AuthToken";
        try {
            PreparedStatement stmt = conn.prepareStatement(sqlEvents);
            stmt.executeUpdate();
            stmt = conn.prepareStatement(sqlUsers);
            stmt.executeUpdate();
            stmt = conn.prepareStatement(sqlPersons);
            stmt.executeUpdate();
            stmt = conn.prepareStatement(sqlTokens);
            stmt.executeUpdate();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
