package dataAccess;

import model.AuthToken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that confers between the model AuthToken and the database table of the same name
 */
public class AuthTokenDAO {
    private final Connection conn;

    /**
     * Constructor for AuthToken DAO
     * @param conn
     */
    public AuthTokenDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * add an authToken
     * @param token
     */
    public boolean add (AuthToken token) {
        String sql = "INSERT into AuthToken(AuthTokenID, UserName)" +
                " VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token.getAuthTokenID());
            stmt.setString(2, token.getUserName());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param username
     * @return the sought after authtoken
     */
    public AuthToken findByID (String username) throws DataAccessException {
        ResultSet rs = null;
        AuthToken foundToken;
        String sql = "SELECT * FROM authToken WHERE UserName = ?";

        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                foundToken = new AuthToken(rs.getString("authTokenID"), rs.getString("username"));
                return foundToken;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException();
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


    public AuthToken find (String token) throws DataAccessException {
        ResultSet rs = null;
        // AuthToken foundToken = null;
        String sql = "SELECT * FROM authToken WHERE AuthTokenID = ?";

        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            rs = stmt.executeQuery();
            if (rs.next()) {
                AuthToken foundToken = new AuthToken(rs.getString("AuthTokenID"), rs.getString("UserName"));
                return foundToken;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException();
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
     * Delete all AuthToken objects in the database
     */
    public boolean deleteAllAuthTokens() {
        String sql = "DELETE FROM authToken";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
