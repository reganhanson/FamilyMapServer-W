package results;

import model.*;
import java.util.ArrayList;

/**
 * FillResult contains
 * the result of the /fill command
 * an error or success message
 * --and--
 * success boolean
 */
public class FillResult {
    private String message;
    private boolean success;

    /*========================= Constructors =============================*/
    public FillResult(String message, boolean success) {
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
