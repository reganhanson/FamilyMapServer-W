package results;

public class UserLoginResult {
    private String authToken;
    private String userName;
    private String personID;
    private boolean success;
    private String errorMessage;

    /*========================= Constructors =============================*/
    // Success response
    public UserLoginResult(String authToken, String userName, String personID) {
        this.authToken = authToken;
        this.userName = userName;
        this.personID = personID;
        this.success = true;
    }

    // Failure response
    public UserLoginResult(String errorMessage) {
        this.errorMessage = errorMessage;
        this.success = false;
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

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
