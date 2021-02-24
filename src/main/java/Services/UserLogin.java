package Services;

import model.AuthToken;
import model.User;


public class UserLogin {
    AuthToken authToken;
    /**
     * @param loginRequest
     * @return AuthToken
     */
    public AuthToken login(User loginRequest) {
        return null;
    }

}
    /* /user/login
    URL Path: /user/login
    Description: Logs in the user and returns an auth token.
    HTTP Method: POST
    Auth Token Required: No
    Request Body:
    {
        "username": "susan",		// Non-empty string
        "password": "mysecret"	// Non-empty string
    }
    Errors: Request property missing or has invalid value, Internal server error
    Success Response Body:
    {
        "authtoken": "cf7a368f",	// Non-empty auth token string
        "username": "susan",		// Username passed in with request
        "personID": "39f9fe46"	// Non-empty string containing the Person ID of the
    //    user’s generated Person object
    “success”:true		// Boolean identifier
    }
    Error Response Body:
    {
        “message”: “Error: [Description of the error]”
    “success”:false		// Boolean identifier
    }
     */