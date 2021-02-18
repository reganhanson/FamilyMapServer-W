package dataAccess;

import model.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EventDAO {
    private final Connection conn;

    public EventDAO(Connection conn) {
        this.conn = conn;
    }

    public void insert(Event event) {
        String sql = "INSERT INTO Event(EventID, AssociatedUserName, PersonID, Latitude, Longitude," +
                " Country, City, EventType, Year) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // we need to set 9 values
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Event find(String EventID) {
        Event event;
        ResultSet result = null;
        String sql = "SELECT * FROM Events WHERE EventID = ?";
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
}
