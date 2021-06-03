package services;

import dataAccess.AuthTokenDAO;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.UserDAO;
import model.AuthToken;
import model.User;
import requests.UserRegisterRequest;
import results.FillResult;
import results.UserRegisterResult;

public class UserRegister {
    /**
     * @param request
     * @return UserRegisterResult
     */
    public UserRegisterResult registerUser(UserRegisterRequest request) {
        Database database = new Database();

        try {
            database.openConnection();
            UserDAO accessUser = new UserDAO(database.getConnection());

            if (accessUser.find(request.getUsername()) != null) {
                database.closeConnection(false);
                return new UserRegisterResult("Username already registered");
            }
            if (request.getUsername().equals("") || request.getPassword().equals("") || request.getEmail().equals("") ||
            request.getGender().equals("") || request.getFirstName().equals("") || request.getLastName().equals("")) {
                database.closeConnection(false);
                return new UserRegisterResult("Empty request values not allowed");
            }

            if (!(request.getGender().equals("f") || request.getGender().equals("m"))) {
                database.closeConnection(false);
                return new UserRegisterResult("Invalid gender values");
            }

            User newUser = new User(request.getUsername(), request.getPassword(), request.getEmail(), request.getFirstName(), request.getLastName(), request.getGender());


            FillService fillService = new FillService();
            accessUser.insert(newUser);
            database.closeConnection(true);

            FillResult result = fillService.fill(newUser.getUserName(), 4);
            if (!result.isSuccess()) {
                return new UserRegisterResult("Problem with FillService: adding 4 generations of data");
            }
            database.getConnection();
            AuthToken sessionToken = new AuthToken(request.getUsername());
            AuthTokenDAO accessToken = new AuthTokenDAO(database.getConnection());
            accessToken.add(sessionToken);
            database.closeConnection(true);
            return new UserRegisterResult(sessionToken.getAuthTokenID(), request.getUsername(), newUser.getPersonID());
        } catch (DataAccessException e) {
            database.closeConnection(true);
            e.printStackTrace();
            return null;
        }
    }
}

/*
/user/register
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