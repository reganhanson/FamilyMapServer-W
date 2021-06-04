package services;

import dataAccess.AuthTokenDAO;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.PersonDAO;
import model.AuthToken;
import model.Person;
import results.GetTreeResult;

import java.util.ArrayList;

public class GetTree {
    /**
     * @param authToken
     * @return
     */
    public GetTreeResult getTree(String authToken) {
        Database db = new Database();
        AuthTokenDAO tokenAccess = new AuthTokenDAO(db.getConnection());

        try {
            AuthToken userToken = tokenAccess.find(authToken);
            //System.out.println("Database OPENED in TREE");

            PersonDAO personAccess = new PersonDAO(db.getConnection());


            if (userToken != null) {
                ArrayList<Person> returnArray = personAccess.findByUsername(userToken.getUserName());
                db.closeConnection(false);
                //System.out.println("Database CLOSED in TREE");
                return new GetTreeResult(returnArray);

            } else {
                db.closeConnection(false);
                //System.out.println("Database CLOSED in TREE");
                return new GetTreeResult("Error: Couldn't find the requested user with the given token");
            }

        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            //System.out.println("Database CLOSED in TREE");
            return new GetTreeResult("Error: internal server error");
        }
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
