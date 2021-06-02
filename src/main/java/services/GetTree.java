package services;

import java.util.ArrayList;

import dataAccess.AuthTokenDAO;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.PersonDAO;
import model.AuthToken;
import model.User;
import results.GetTreeResult;

public class GetTree {
    /**
     *
     * @param personID
     * @param authToken
     * @return
     */
    public GetTreeResult getTree(String personID, String authToken) {
        Database db = new Database();
        AuthTokenDAO tokenAccess = new AuthTokenDAO(db.getConnection());
        PersonDAO personAccess = new PersonDAO(db.getConnection());

        AuthToken token = null;
        try {
            token = tokenAccess.find(authToken);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        if (token == null) {
            db.closeConnection(false);
            return new GetTreeResult("Couldn't find the requested user with the given token");
        }
        GetTreeResult result = new GetTreeResult(personAccess.findByUsername(token.getUserName()));
        db.closeConnection(false);
        return result;
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
