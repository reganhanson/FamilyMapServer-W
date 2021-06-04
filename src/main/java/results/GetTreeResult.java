package results;

import model.Person;
import java.util.ArrayList;

/**
 * GetTreeResults comes from the /person command, requires:
 * an array of all of the members of person's family tree if successful
 * --or--
 * an error message string if unsuccessful
 * --and--
 * success boolean
 */
public class GetTreeResult {
    private ArrayList<Person> data;
    private boolean success;
    private String message;

    /*========================= Constructors =============================*/

    public GetTreeResult(ArrayList<Person> familyTree) {
        this.data = familyTree;
        this.success = true;
        this.message = null;
    }

    public GetTreeResult(String error) {
        this.message = error;
        this.success = false;
        this.data = null;
    }
    /*====================== Getters and Setters =========================*/

    public ArrayList<Person> getData() {
        return data;
    }

    public void setData(ArrayList<Person> data) {
        this.data = data;
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
