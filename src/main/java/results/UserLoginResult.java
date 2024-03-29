package results;

public class UserLoginResult {
    private String authtoken;
    private String username;
    private String personID;
    private boolean success;
    private String message;

    /*========================= Constructors =============================*/
    // Success response
    public UserLoginResult(String authToken, String userName, String personID) {
        this.authtoken = authToken;
        this.username = userName;
        this.personID = personID;
        this.message = null;
        this.success = true;
    }

    // Failure response
    public UserLoginResult(String errorMessage) {
        this.message = errorMessage;
        this.success = false;
    }

    /*====================== Getters and Setters =========================*/

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
