package dataAccess;

import model.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that confers between the model Event and the database table of the same name
 */
public class EventDAO {
    private final Connection conn;

    /**
     * Constructor for EventDAO
     * @param conn
     */
    public EventDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * insert an Event into the table using sql statements
     * @param event
     */
    public boolean insert(Event event) {
        String sql = "INSERT INTO Event(EventID, AssociatedUserName, PersonID, Latitude, Longitude," +
                " Country, City, EventType, Year) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getAssociatedUsername());
            stmt.setString(3, event.getPersonID());
            stmt.setFloat(4, event.getLatitude());
            stmt.setFloat(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Find an event with given ID in the Event database table
     * @param EventID event identification number
     * @return event
     */
    public Event findByID(String EventID) /*throws DataAccessException*/ {
        Event event;
        ResultSet result = null;
        String sql = "SELECT * FROM Event WHERE EventID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, EventID);
            result = stmt.executeQuery();
            if (result.next()) {
                event = new Event(result.getString("EventID"), result.getString("AssociatedUserName"),
                        result.getString("PersonID"), result.getFloat("Latitude"),
                        result.getFloat("Longitude"), result.getString("Country"),
                        result.getString("City"), result.getString("EventType"),
                        result.getInt("Year"));
                return event;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // throw new DataAccessException("SQL Exception: problem with executing the query");
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    // throw new DataAccessException("SQL Exception throw in closing the result set");
                }
            }
        }
        return null;
    }

    /**
     *
     * @param associatedUserName username associated with the event
     * @return null for now
     */
    public Event findByUsername(String associatedUserName) /*throws DataAccessException*/ {
        Event event;
        ResultSet result = null;
        String sql = "SELECT * FROM event WHERE AssociatedUserName = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, associatedUserName);
            result = stmt.executeQuery();
            if (result.next()) {
                event = new Event (result.getString("EventID"), result.getString("AssociatedUserName"),
                        result.getString("PersonID"), result.getFloat("Latitude"),
                        result.getFloat("Longitude"), result.getString("Country"),
                        result.getString("City"), result.getString("EventType"),
                        result.getInt("Year"));
                return event;
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
            // throw new DataAccessException("Problem executing query");
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * Delete all events in the database
     */
    public boolean deleteAllEvents() /*throws DataAccessException*/ {
        String sql = "DELETE FROM Event";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            // throw new DataAccessException("could not execute the update from that prepared statement");
            return false;
        }
    }


}
