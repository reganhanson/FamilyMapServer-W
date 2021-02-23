package Services;

public class GetEvent {
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