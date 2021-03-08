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
    private String authToken;
    private String userName;
    private String personID;
    private boolean success;
    private String message;

    /*========================= Constructors =============================*/
    public UserRegisterResult(String authToken, String userName, String personID) {
        this.authToken = authToken;
        this.userName = userName;
        this.personID = personID;
        this.success = true;
        this.message = null;
    }

    public UserRegisterResult(String error) {
        this.authToken = null;
        this.userName = null;
        this.personID = null;
        this.success = false;
        this.message = error;
    }

    /*====================== Getters and Setters =========================*/
    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
