package services;

import java.util.ArrayList;
import model.User;

public class GetTree {
    /**
     *
     * @param personID
     * @param authToken
     * @return
     */
    public ArrayList<User> getTree(String personID, String authToken) {
        return null;
    }
}

    /*/person
    URL Path: /person
    Description: Returns ALL family members of the current user. The current user is determined from the provided auth token.
    HTTP Method: GET
    Auth Token Required: Yes
    Request Body: None
    Errors: Invalid auth token, Internal server error
    Success Response Body: The response body returns a JSON object with a “data” attribute that contains an array of Person objects.  Each Person object has the same format as described in previous section on the /person/[personID] API.
    {
        "data": [  /* Array of Person objects   ]
            “success”:true		// Boolean identifier
            }
            Error Response Body:
            {
            “message”: “Error: [Description of the error]”
            “success”:false		// Boolean identifier
            }
            */
