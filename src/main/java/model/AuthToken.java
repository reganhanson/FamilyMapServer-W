package model;

import java.util.Objects;
import java.util.UUID;

/**
 * AuthToken class: model for the database table of the same name
 */
public class AuthToken {
    private String authTokenID;
    private String userName;

    /**
     * Constructor for authToken.
     * Requires the below parameters
     * @param userName
     */
    public AuthToken(String userName) {
        this.authTokenID = UUID.randomUUID().toString();
        this.userName = userName;
    }

    /**
     * Constructor for authToken.
     * Requires the below parameters
     * @param authTokenID
     * @param userName
     */
    public AuthToken(String authTokenID, String userName) {
        this.authTokenID = authTokenID;
        this.userName = userName;
    }

    public String getAuthTokenID() {
        return authTokenID;
    }

    public void setAuthTokenID(String authTokenID) {
        this.authTokenID = authTokenID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken = (AuthToken) o;
        return Objects.equals(authTokenID, authToken.authTokenID) && Objects.equals(userName, authToken.userName);
    }

}
