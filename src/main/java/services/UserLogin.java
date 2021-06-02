package services;

import dataAccess.*;
import model.AuthToken;
import model.User;
import requests.UserLoginRequest;
import results.UserLoginResult;


public class UserLogin {
    /**
     * @param loginRequest
     * @return UserLoginResult
     */
    public UserLoginResult login(UserLoginRequest loginRequest) {
        Database database = new Database();
        UserDAO accessUser = new UserDAO(database.getConnection());

        User foundUser = accessUser.find(loginRequest.getUsername());
        if (foundUser.getPassword().equals(loginRequest.getPassword())) {
            AuthToken sessionToken = new AuthToken(foundUser.getUserName());
            AuthTokenDAO token = new AuthTokenDAO(database.getConnection());
            token.add(sessionToken);
            return new UserLoginResult(sessionToken.getAuthTokenID(), foundUser.getUserName(), foundUser.getPersonID());
        }
        else {
            return new UserLoginResult("");
        }
        // UserDAO
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