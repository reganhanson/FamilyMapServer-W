package requests;

/**
 * userLoginRequest is a request for an authToken from an existing user
 * It requires:
 * username and password
 */
public class UserLoginRequest {
    // variables
    private String userName;
    private String password;

    /*==================Constructor===================*/
    public UserLoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    /*================Setters & Getters===============*/
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }
}
