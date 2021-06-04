package dataAccess;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that confers between the model User and the database table of the same name
 */
public class UserDAO {
    private final Connection conn;

    /**
     * Constructor for UserDAO class
     * @param conn
     */
    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * insert User into the User database table
     * @param user
     */
    public void insert (User user) throws DataAccessException {
        String sql = "INSERT into user(UserName, Password, Email, FirstName, LastName, Gender, PersonID)" +
                " Values(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("SQL Exception");
        }
    }

    /**
     * Find the user based on a given UserID
     * @param UserID
     * @return
     */
    public User find(String UserID) {
        ResultSet rs = null;
        User user = null;
        String sql = "SELECT * FROM user WHERE userName = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, UserID);
            rs = stmt.executeQuery();
            // if there is anything in the result set
            if (rs.next()) {
                user = new User(rs.getString("Username"), rs.getString("Password"),
                        rs.getString("Email"), rs.getString("FirstName"),
                        rs.getString("LastName"), rs.getString("Gender"),
                        rs.getString("PersonID"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * Delete all user objects in the database
     */
    public void deleteAllUsers() throws DataAccessException{
        String sql = "DELETE FROM user";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException();
        }
    }
}
