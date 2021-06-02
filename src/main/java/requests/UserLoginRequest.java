package requests;

/**
 * userLoginRequest is a request for an authToken from an existing user
 * It requires:
 * username and password
 */
public class UserLoginRequest {
    // variables
    private String username;
    private String password;

    /*==================Constructor===================*/
    public UserLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /*================Setters & Getters===============*/
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
