package results;

/**
 * /clear command
 * Contains:
 * 1 message: error or success
 * 1 success boolean
 */
public class ClearResult {
    private String message;
    private boolean success;

    /*========================= Constructor ==============================*/

    /**
     * Constructor
     * @param message
     * @param success
     */
    public ClearResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    /*====================== Getters and Setters =========================*/
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
