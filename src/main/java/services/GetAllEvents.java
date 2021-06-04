package services;

import dataAccess.AuthTokenDAO;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.EventDAO;
import model.AuthToken;
import model.Event;
import results.GetAllEventsResult;

import java.util.ArrayList;

public class GetAllEvents {
    /**
     *
     * @param userToken
     * @return
     */
    public GetAllEventsResult getAllEvents(AuthToken userToken) {
        Database database = new Database();
        if (userToken == null) {
            return new GetAllEventsResult("Error: Bad authToken");
        }
        database.openConnection();
        //System.out.println("Database OPENED in ALL EVENTS");
        EventDAO eventAccess = new EventDAO(database.getConnection());
        AuthTokenDAO tokenAccess = new AuthTokenDAO(database.getConnection());
        try {
            AuthToken token = tokenAccess.find(userToken.getAuthTokenID());
            if (token != null) {
                if (token.getUserName().equals(userToken.getUserName())) {
                    ArrayList<Event> eventList = new ArrayList<>();
                    GetTree tree = new GetTree();
                    eventList = eventAccess.findByUsername(userToken.getUserName());
                    if (eventList == null) {
                        //System.out.println("Database CLOSED in ALL EVENTS");
                        database.closeConnection(false);
                        return new GetAllEventsResult("Error: No events found");
                    } else {
                        database.closeConnection(true);
                        //System.out.println("Database CLOSED in ALL EVENTS");
                        return new GetAllEventsResult(eventList);
                    }
                } else {
                    database.closeConnection(false);
                    //System.out.println("Database CLOSED in ALL EVENTS");
                    return new GetAllEventsResult("Error: invalid token");
                }
            }
        } catch (DataAccessException e) {
            database.closeConnection(false);
            //System.out.println("Database CLOSED in ALL EVENTS");
            return new GetAllEventsResult("Error: Internal Server Error");
        }
        database.closeConnection(false);
        //System.out.println("Database CLOSED in ALL EVENTS");
        return new GetAllEventsResult("Error: Bad token");
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