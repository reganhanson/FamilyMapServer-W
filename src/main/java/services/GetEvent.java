package services;

import dataAccess.*;
import model.Event;
import model.Person;
import results.GetEventResult;

import java.util.Objects;

public class GetEvent {
    /**
     *
     * @param eventID
     * @param authToken
     * @return Event
     */
    public GetEventResult getEvent(String eventID, String authToken){
        Database database = new Database();
        database.getConnection();

        EventDAO eventAccess = new EventDAO(database.getConnection());
        AuthTokenDAO tokenAccess = new AuthTokenDAO(database.getConnection());

        Event foundEvent = eventAccess.findByID(eventID);
        if (foundEvent != null) {
            try {
                if (foundEvent.getAssociatedUsername().equals(tokenAccess.find(authToken).getUserName())) {
                    database.closeConnection(false);
                    return new GetEventResult(foundEvent.getAssociatedUsername(), foundEvent.getEventID(),
                            foundEvent.getPersonID(), foundEvent.getLatitude(), foundEvent.getLongitude(),
                            foundEvent.getCountry(), foundEvent.getCity(), foundEvent.getEventType(),
                            foundEvent.getYear());
                }
            } catch (DataAccessException e) {
                database.closeConnection(false);
                e.printStackTrace();
            }
        }
        database.closeConnection(false);
        return new GetEventResult("error");
    }
}

    /* /event/[eventID]
    URL Path: /event/[eventID]
    Example: /event/251837d7
    Description: Returns the single Event object with the specified ID.
    HTTP Method: GET
    Auth Token Required: Yes
    Request Body: None
    Errors: Invalid auth token, Invalid eventID parameter, Requested event does not belong to this user, Internal server error
    Success Response Body:
    {
        "associatedUsername": "susan"  // Username of user account this event belongs to
    // (non-empty string)
        "eventID": "251837d7",	// Event’s unique ID (non-empty string)
        "personID": "7255e93e",	// ID of the person this event belongs to (non-empty string)
        "latitude": 65.6833,		// Latitude of the event’s location (number)
        "longitude": -17.9,		// Longitude of the event’s location (number)
        "country": "Iceland",		// Name of country where event occurred (non-empty
    //    string)
        "city": "Akureyri",		// Name of city where event occurred (non-empty string)
        "eventType": "birth",		// Type of event (“birth”, “baptism”, etc.) (non-empty string)
        "year": 1912,			// Year the event occurred (integer)
    “success”:true		// Boolean identifier
    }
    Error Response Body:
    {
        “message”: “Error: [Description of the error]”
    “success”:false		// Boolean identifier
    }
    */