package model;

/**
 * AuthToken class: model for the database table of the same name
 */
public class AuthToken {
    private String authTokenID;  // ?
    private String userName;

    /**
     * Constructor for authToken.
     * Requires the below parameters
     * @param authTokenID
     * @param userName
     * @param personID
     */
    public AuthToken(String authTokenID, String userName, String personID) {
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

}
