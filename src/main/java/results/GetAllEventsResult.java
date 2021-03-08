package results;

import model.Event;
import java.util.ArrayList;

/**
 * GetAllEventsResult is the result of the /event command
 * Contains:
 * a list of all events pertaining to the user
 * an error message string
 */
public class GetAllEventsResult {
    private ArrayList<Event> data;
    private String message;

    /*========================= Constructors =============================*/
    public GetAllEventsResult() {
        this.data = null;
        this.message = null;
    }

    public GetAllEventsResult(ArrayList<Event> data) {
        this.data = data;
        this.message = null;
    }

    public GetAllEventsResult(String message) {
        this.data = null;
        this.message = message;
    }

    public GetAllEventsResult(ArrayList<Event> data, String message) {
        this.data = data;
        this.message = message;
    }

    /*====================== Getters and Setters =========================*/
    public ArrayList<Event> getData() {
        return data;
    }

    public void setData(ArrayList<Event> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
