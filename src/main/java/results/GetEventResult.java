package results;

import model.Event;

/**
 * /event/[eventID] command
 * Contains:
 * username, eventID, personID, latitude, longitude, country, city, eventType. year
 * --or--
 * if unsuccessful: error messsage
 * --and--
 * always a success boolean
 */
public class GetEventResult {
    private String associatedUsername;
    private String eventID;
    private String personID;
    private double latitude;
    private double longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;
    private boolean success;
    private String message;

    /*========================= Constructors =============================*/

    /**
     * Constructor for successful response
     * @param associatedUsername username for the account
     * @param eventID ID string for the event
     * @param personID personId associated with event creation
     * @param lat latitude double
     * @param longi longitude location double
     * @param country country of event
     * @param city city of event
     * @param type type of event
     * @param yr year of the event occurrence
     */
    public GetEventResult(String associatedUsername, String eventID, String personID, double lat, double longi, String country, String city, String type, int yr) {
        this.associatedUsername = associatedUsername;
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = lat;
        this.longitude = longi;
        this.country = country;
        this.city = city;
        this.eventType = type;
        this.year = yr;
        this.success = true;
        this.message = null;
    }

    /**
     * Constructor for unsuccessful response
     * @param message error message
     */
    public GetEventResult(String message) {
        this.success = false;
        this.message = message;
        this.associatedUsername = null;
        this.eventID = null;
        this.personID = null;
        this.latitude = 0.0;
        this.longitude = 0.0;
        this.country = null;
        this.city = null;
        this.eventType = null;
        this.year = 0;
    }

    /*====================== Getters and Setters =========================*/

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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
