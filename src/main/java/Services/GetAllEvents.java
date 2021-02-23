package Services;
import model.Event;

public class GetAllEvents {
    public Event[] getAllEvents() {
        return null;
    }
}


    /*/event
    URL Path: /event
    Description: Returns ALL events for ALL family members of the current user. The current user is determined from the provided auth token.
    HTTP Method: GET
    Auth Token Required: Yes
    Request Body: None
    Errors: Invalid auth token, Internal server error
    Success Response Body: The response body returns a JSON object with a “data” attribute that contains an array of Event objects.  Each Event object has the same format as described in previous section on the /event/[eventID] API.
    {
        "data": [  /* Array of Event objects   ]
            “success”:true		// Boolean identifier
            }
            Error Response Body:
            {
            “message”: “Error: [Description of the error]”
            “success”:false		// Boolean identifier
            }
            */