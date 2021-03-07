package dataAccess;

import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
    public void add (AuthToken token) {
        String sql = "INSERT into AuthToken(AuthTokenID, UserName)" +
                " VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token.getAuthTokenID());
            stmt.setString(2, token.getUserName());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param user
     * @return the sought after authtoken
     */
    public AuthToken find (User user) {
        return null;
    }
}
