package model;

import java.util.Objects;
import java.util.UUID;

/**
 * Event class: model for the database table of the same name
 */
public class Event {
    private String associatedUsername;
    private String eventID;
    private String personID;
    private float latitude;
    private float longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;

    /**
     * empty constructor for this class
     */
    public Event() {
        this.associatedUsername = null;
        this.eventID = UUID.randomUUID().toString();
        this.personID = null;
        this.latitude = 0;
        this.longitude = 0;
        this.country = null;
        this.city = null;
        this.eventType = null;
        this.year = 0;
    }


    public Event(String username, String personID, float lat, float longi, String country, String city, String eventType, int yr) {
        this.associatedUsername = username;
        this.eventID = UUID.randomUUID().toString();
        this.personID = personID;
        this.latitude = lat;
        this.longitude = longi;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = yr;
    }

    /**
     * Full constructor for this class
     * @param eventID
     * @param username
     * @param personID
     * @param latitude
     * @param longitude
     * @param country
     * @param city
     * @param eventType
     * @param year
     */
    public Event(String eventID, String username, String personID, float latitude, float longitude, String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.associatedUsername = username;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
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

}
