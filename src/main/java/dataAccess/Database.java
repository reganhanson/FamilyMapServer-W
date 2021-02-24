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

    /**
     * open the connection to the sqlite database
     * @return Connection
     */
    public Connection openConnection() {
        // driver(jdbc):language(sqlite):path()
        final String URL = "jdbc:sqlite:FamilyMapServerStudent-master.sqlite";
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
        if (conn != null) {
            return conn;
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
     * Delete all tables in the database
     */
    public void deleteTables() {
        String sql = "DELETE FROM Events DELETE FROM User DELETE FROM Person DELETE FROM AuthToken";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
