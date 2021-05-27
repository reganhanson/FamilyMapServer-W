package model;

/**
 * AuthToken class: model for the database table of the same name
 */
public class AuthToken {
    private String authTokenID;  // ?
    private String userName;
    private String password;

    /**
     * Constructor for authToken.
     * Requires the below parameters
     * @param authTokenID
     * @param userName
     */
    public AuthToken(String authTokenID, String userName, String passWord) {
        this.authTokenID = authTokenID;
        this.userName = userName;
        this.password = passWord;
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
