package requests;

/**
 * userLoginRequest is a request for an authToken from an existing user
 * It requires:
 * username and password
 */
public class UserLoginRequest {
    private String userName;
    private String password;

    public UserLoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
