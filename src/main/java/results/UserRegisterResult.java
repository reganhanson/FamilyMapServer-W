package results;

/**
 * RegisterResult requires either:
 * username, personId, and authToken,
 * --or--
 * an error message if unsuccessful
 * --and--
 * a success boolean
 */
public class UserRegisterResult {
    private String authtoken;
    private String username;
    private String personID;
    private boolean success;
    private String message;

    /*========================= Constructors =============================*/
    public UserRegisterResult(String authToken, String userName, String personID) {
        this.authtoken = authToken;
        this.username = userName;
        this.personID = personID;
        this.success = true;
        this.message = null;
    }

    public UserRegisterResult(String error) {
        this.authtoken = null;
        this.username = null;
        this.personID = null;
        this.success = false;
        this.message = error;
    }

    /*====================== Getters and Setters =========================*/
    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authToken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
