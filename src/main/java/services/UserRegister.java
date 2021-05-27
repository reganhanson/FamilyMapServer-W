package services;

import model.AuthToken;
import model.User;
import results.UserRegisterResult;

public class UserRegister {
    /**
     *
     * @param registerRequest
     * @return AuthToken
     */
    public UserRegisterResult registerUser(User registerRequest) {
        return null;
    }
}

        /*/user/register
        URL Path: /user/register
        Description: Creates a new user account, generates 4 generations of ancestor data for the new user, logs the user in, and returns an auth token.
        HTTP Method: POST
        Auth Token Required: No
        Request Body:
        {
            "username": "susan",		// Non-empty string
            "password": "mysecret",	// Non-empty string
            "email": "susan@gmail.com",	// Non-empty string
            "firstName": "Susan",		// Non-empty string
            "lastName": "Ellis",		// Non-empty string
         "gender": "f"			// “f” or “m”
        }
        Errors: Request property missing or has invalid value, Username already taken by another user, Internal server error
        Success Response Body:
        {
            "authtoken": "cf7a368f",	// Non-empty auth token string
            "username": "susan",		// Username passed in with request
            "personID": "39f9fe46"		// Non-empty string containing the Person ID of the
                    //  user’s generated Person object
        “success”:true		// Boolean identifier
        }
        Error Response Body:
        {
            “message”: “Error: [Description of the error]”
        “success”:false		// Boolean identifier
        }
        */
